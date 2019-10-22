package com.sbogutyn.crawler.crawler;

import com.sbogutyn.crawler.classifier.LinkClassificationGroups;
import com.sbogutyn.crawler.classifier.LinkClassifier;
import com.sbogutyn.crawler.client.HttpClient;
import com.sbogutyn.crawler.db.LinksDb;
import com.sbogutyn.crawler.domain.Link;
import com.sbogutyn.crawler.domain.Page;
import com.sbogutyn.crawler.parser.HtmlParser;
import io.mola.galimatias.GalimatiasParseException;
import io.mola.galimatias.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class SimpleCrawler implements Crawler {
  private static final Logger log = LoggerFactory.getLogger(SimpleCrawler.class);
  private final HttpClient httpClient;
  private final HtmlParser htmlParser;
  private final LinkClassifier linkClassifier;
  private final LinksDb linksDb;
  private final AtomicLong crawlCount = new AtomicLong(0);
  private static final long CRAWL_LIMIT = 10L;

  public SimpleCrawler(HttpClient httpClient,
                HtmlParser htmlParser,
                LinkClassifier linkClassifier,
                LinksDb linksDb) {
    this.httpClient = httpClient;
    this.htmlParser = htmlParser;
    this.linkClassifier = linkClassifier;
    this.linksDb = linksDb;
  }

  public void crawlSite(String baseUrl) {
    try {
      URL url = URL.parse(baseUrl);
      log.info("Parsed seed url: {}", url.toHumanString());
      Link rootLink = new Link(url.toString());
      linksDb.addToPendingLinks(rootLink);

      while (!linksDb.isPendingLinksQueueEmpty() && crawlCount.longValue() < CRAWL_LIMIT) {
        Link link = linksDb.takeLinkFromQueue();
        crawlLink(link);
      }

      log.info("Crawling finished");
      log.info("Results");

      linksDb.getAllPages().forEach(p -> log.info("Page: {}", p));

    } catch (GalimatiasParseException e) {
      throw new CrawlingException("Couldn't crawl url: " + baseUrl, e);
    }
  }

  private void crawlLink(Link link) {
    log.info("Crawling link {}", link);

    Set<Link> linksFromPage = parsePageAndGetLinks(link);
    LinkClassificationGroups linkClassificationGroups = linkClassifier.classifyAll(linksFromPage);

    log.info("Found {} internal lins, {} external links, {} static content links",
            linkClassificationGroups.getInternalLinks().size(),
            linkClassificationGroups.getExternalLinks().size(),
            linkClassificationGroups.getStaticContentLinks().size());


    for (Link currentLink : linkClassificationGroups.getInternalLinks()) {
      if (!linksDb.isLinkAlreadyIndexed(currentLink)) {
        linksDb.addToPendingLinks(currentLink);
      } else {
        log.info("Link already indexed in db: {}", currentLink);
      }
    }

    Page page = new Page(link,
            linkClassificationGroups.getInternalLinks(),
            linkClassificationGroups.getExternalLinks(),
            linkClassificationGroups.getStaticContentLinks());

    linksDb.addPage(page);

    log.info("Crawled {} links", crawlCount.incrementAndGet());
  }

  private Set<Link> parsePageAndGetLinks(Link link) {
    return httpClient.getHtml(link).stream()
            .map(htmlParser::parse)
            .flatMap(Collection::stream).collect(Collectors.toSet());
  }


}
