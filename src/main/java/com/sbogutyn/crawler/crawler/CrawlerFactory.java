package com.sbogutyn.crawler.crawler;

import com.sbogutyn.crawler.classifier.LinkClassifier;
import com.sbogutyn.crawler.client.jsoup.JsoupHttpClient;
import com.sbogutyn.crawler.db.InMemoryLinksDb;
import com.sbogutyn.crawler.parser.jsoup.JsoupParser;
import io.mola.galimatias.GalimatiasParseException;
import io.mola.galimatias.URL;

public class CrawlerFactory {
  public static Crawler crawler(CrawlerType crawlerType, String baseUrl, CrawlLimit crawlLimit) {
    try {
      String resolvedBaseUrl = URL.parse(baseUrl).resolve("/").toString();

      if (crawlerType == CrawlerType.JSOUP) {
        return new SimpleCrawler(new JsoupHttpClient(),
                new JsoupParser(resolvedBaseUrl),
                new LinkClassifier(resolvedBaseUrl),
                new InMemoryLinksDb(),
                crawlLimit);
      }
      throw new CrawlingException("Unsupported crawler exception: " + crawlerType);
    } catch (GalimatiasParseException e) {
      throw new CrawlingException("Could not parse url " + baseUrl, e);
    }
  }

  public enum CrawlerType {
    JSOUP
  }
}
