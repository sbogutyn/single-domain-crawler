package com.sbogutyn.crawler;


import com.sbogutyn.crawler.classifier.LinkClassifier;
import com.sbogutyn.crawler.client.jsoup.JsoupHttpClient;
import com.sbogutyn.crawler.crawler.Crawler;
import com.sbogutyn.crawler.crawler.SimpleCrawler;
import com.sbogutyn.crawler.db.InMemoryLinksDb;
import com.sbogutyn.crawler.parser.jsoup.JsoupParser;
import com.sbogutyn.crawler.util.LinkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
  private static final Logger log = LoggerFactory.getLogger(Main.class);
  private static final String TEST_URL = "http://example.com/";

  public static void main(String[] args) {
    String baseDomain = LinkUtil.findDomain(TEST_URL).get();
    log.info("Crawling page: {} in domain: {}", TEST_URL, baseDomain);
    Crawler crawler = new SimpleCrawler(
            new JsoupHttpClient(),
            new JsoupParser(baseDomain),
            new LinkClassifier(baseDomain),
            new InMemoryLinksDb()
    );
    crawler.crawlSite(TEST_URL);
  }
}
