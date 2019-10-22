package com.sbogutyn.crawler.classifier;

import com.sbogutyn.crawler.domain.Link;

import java.util.Set;

public class LinkClassificationGroups {
  private final Set<Link> internalLinks;
  private final Set<Link> externalLinks;
  private final Set<Link> staticContentLinks;

  public LinkClassificationGroups(Set<Link> internalLinks, Set<Link> externalLinks, Set<Link> staticContentLinks) {
    this.internalLinks = internalLinks;
    this.externalLinks = externalLinks;
    this.staticContentLinks = staticContentLinks;
  }

  public Set<Link> getInternalLinks() {
    return internalLinks;
  }

  public Set<Link> getExternalLinks() {
    return externalLinks;
  }

  public Set<Link> getStaticContentLinks() {
    return staticContentLinks;
  }

  @Override
  public String toString() {
    return "LinkClassificationGroups{" +
            "internalLinks=" + internalLinks +
            ", externalLinks=" + externalLinks +
            ", staticContentLinks=" + staticContentLinks +
            '}';
  }
}
