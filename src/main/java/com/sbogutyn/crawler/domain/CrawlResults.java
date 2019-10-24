package com.sbogutyn.crawler.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Set;

public class CrawlResults {
  private final Link baseUrl;
  @JacksonXmlElementWrapper(localName = "pages")
  @JacksonXmlProperty(localName = "page")
  private final Set<Page> pages;

  public CrawlResults(Link baseUrl, Set<Page> pages) {
    this.baseUrl = baseUrl;
    this.pages = pages;
  }

  public Link getBaseUrl() {
    return baseUrl;
  }

  public Set<Page> getPages() {
    return pages;
  }
}
