package com.sbogutyn.crawler;

import com.sbogutyn.crawler.crawler.Crawler;
import com.sbogutyn.crawler.crawler.CrawlerFactory;
import com.sbogutyn.crawler.domain.CrawlResults;
import com.sbogutyn.crawler.serializer.CrawlResultsSerializer;
import com.sbogutyn.crawler.serializer.XmlCrawlResultsSerializer;
import com.sbogutyn.crawler.util.LinkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
  private static final Logger log = LoggerFactory.getLogger(Main.class);
  private static final String TEST_URL = "http://example.com/";

  public static void main(String[] args) {
    String baseDomain = LinkUtil.findDomain(TEST_URL).get();
    log.info("Crawling page: {} in domain: {}", TEST_URL, baseDomain);
    Crawler crawler = CrawlerFactory.crawler(CrawlerFactory.CrawlerType.JSOUP, TEST_URL);
    CrawlResults crawlResults = crawler.crawlSite(TEST_URL);
    CrawlResultsSerializer<String> crawlResultsSerializer = new XmlCrawlResultsSerializer();
    log.info("Crawl results: \n {}", crawlResultsSerializer.serialize(crawlResults));
  }
}
