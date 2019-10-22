package com.sbogutyn.crawler.domain;

import java.util.*;

public class Page {
  private final Link rootAddress;
  private final Map<Link, Set<Link>> internalPages;
  private final Set<Link> externalLinks;

  public Page(Link rootAddress, Map<Link, Set<Link>> internalPages, Set<Link> externalLinks) {
    this.rootAddress = rootAddress;
    this.internalPages = internalPages;
    this.externalLinks = externalLinks;
  }

  public Page(Link rootAddress) {
    this.rootAddress = rootAddress;
    this.internalPages = new HashMap<>();
    this.externalLinks = new HashSet<>();
  }

  public Link getRootAddress() {
    return rootAddress;
  }

  public Map<Link, Set<Link>> getInternalPages() {
    return internalPages;
  }

  public Set<Link> getExternalLinks() {
    return externalLinks;
  }

  public Set<Link> getLinksForInternalPage(Link key) {
    return internalPages.get(key);
  }

  public void addInternalPage(Link key, Set<Link> value) {
    internalPages.put(key, value);
  }

  public boolean addExternalLink(Link link) {
    return externalLinks.add(link);
  }

  public boolean addAllExternalLinks(Collection<Link> c) {
    return externalLinks.addAll(c);
  }
}
