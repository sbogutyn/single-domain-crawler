package com.sbogutyn.crawler;

import com.beust.jcommander.JCommander;
import com.sbogutyn.crawler.crawler.CrawlLimit;
import com.sbogutyn.crawler.crawler.Crawler;
import com.sbogutyn.crawler.crawler.CrawlerFactory;
import com.sbogutyn.crawler.domain.CrawlResults;
import com.sbogutyn.crawler.params.CrawlerParams;
import com.sbogutyn.crawler.serializer.CrawlResultsSerializer;
import com.sbogutyn.crawler.serializer.XmlCrawlResultsSerializer;
import com.sbogutyn.crawler.util.LinkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Main {
  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    CrawlerParams crawlerParams = new CrawlerParams();
    JCommander jCommander = JCommander.newBuilder()
            .addObject(crawlerParams)
            .build();

    jCommander.parse(args);

    if (crawlerParams.isHelp()) {
      jCommander.usage();
      System.exit(0);
    }

    CrawlLimit crawlLimit = Optional.ofNullable(crawlerParams.getCrawlLimit()).map(CrawlLimit::new).orElse(new CrawlLimit());

    log.info("Parameters\n {}", crawlerParams);
    String baseDomain = LinkUtil.findDomain(crawlerParams.getStartingUrl()).orElseThrow(RuntimeException::new);
    log.info("Crawling page: {} in domain: {}", crawlerParams.getStartingUrl(), baseDomain);

    Crawler crawler = CrawlerFactory.crawler(CrawlerFactory.CrawlerType.JSOUP, crawlerParams.getStartingUrl(), crawlLimit);
    CrawlResults crawlResults = crawler.crawlSite(crawlerParams.getStartingUrl());
    CrawlResultsSerializer<String> crawlResultsSerializer = new XmlCrawlResultsSerializer();

    log.info("Crawl results: \n {}", crawlResultsSerializer.serialize(crawlResults));
  }
}
