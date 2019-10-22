package com.sbogutyn.crawler.domain;

import java.util.Set;

public class InternalPage {
  private final Link url;
  private final Set<Link> links;

  public InternalPage(Link url, Set<Link> links) {
    this.url = url;
    this.links = links;
  }

  public Link getUrl() {
    return url;
  }

  public Set<Link> getLinks() {
    return links;
  }
}
