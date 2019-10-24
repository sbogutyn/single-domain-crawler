package com.sbogutyn.crawler.params;

import com.beust.jcommander.Parameter;

public class CrawlerParams {
  @Parameter(names = {"--startingUrl", "-s"}, description = "Base url to start the crawling process.")
  private String startingUrl = "http://example.com";
  @Parameter(names = {"--crawlLimit", "-l"}, description = "Maximum number of links to crawl.")
  private Long crawlLimit;

  @Parameter(names = {"--help", "-h"}, help = true)
  private boolean help;

  public String getStartingUrl() {
    return startingUrl;
  }

  public void setStartingUrl(String startingUrl) {
    this.startingUrl = startingUrl;
  }

  public Long getCrawlLimit() {
    return crawlLimit;
  }

  public void setCrawlLimit(Long crawlLimit) {
    this.crawlLimit = crawlLimit;
  }

  public boolean isHelp() {
    return help;
  }

  public void setHelp(boolean help) {
    this.help = help;
  }

  @Override
  public String toString() {
    return "CrawlerParams{" +
            "startingUrl='" + startingUrl + '\'' +
            ", crawlLimit=" + crawlLimit +
            ", help=" + help +
            '}';
  }
}
