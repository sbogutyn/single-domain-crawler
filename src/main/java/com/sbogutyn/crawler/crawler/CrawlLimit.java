package com.sbogutyn.crawler.crawler;

public class CrawlLimit {
  private final long crawledLinksLimit;
  private final boolean limitCrawling;

  public CrawlLimit(long crawledLinksLimit) {
    this.crawledLinksLimit = crawledLinksLimit;
    this.limitCrawling = true;
  }

  public CrawlLimit() {
    this.crawledLinksLimit = 0L;
    this.limitCrawling = false;
  }

  public long getCrawledLinksLimit() {
    return crawledLinksLimit;
  }

  public boolean isLimitCrawling() {
    return limitCrawling;
  }
}
