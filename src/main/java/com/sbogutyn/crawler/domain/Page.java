package com.sbogutyn.crawler.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Objects;
import java.util.Set;

public class Page {
  private final Link rootAddress;

  @JacksonXmlElementWrapper(localName = "internalLinks")
  @JacksonXmlProperty(localName = "internalLink")
  private final Set<Link> internalLinks;

  @JacksonXmlElementWrapper(localName = "externalLinks")
  @JacksonXmlProperty(localName = "externalLink")
  private final Set<Link> externalLinks;

  @JacksonXmlElementWrapper(localName = "staticContentLinks")
  @JacksonXmlProperty(localName = "staticContentLink")
  private final Set<Link> staticContentLinks;

  public Page(Link rootAddress, Set<Link> internalLinks, Set<Link> externalLinks, Set<Link> staticContentLinks) {
    this.rootAddress = rootAddress;
    this.internalLinks = internalLinks;
    this.externalLinks = externalLinks;
    this.staticContentLinks = staticContentLinks;
  }

  public Link getRootAddress() {
    return rootAddress;
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Page page = (Page) o;
    return rootAddress.equals(page.rootAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rootAddress);
  }

  @Override
  public String toString() {
    return "Page{" +
            "rootAddress=" + rootAddress +
            ", internalLinks=" + internalLinks +
            ", externalLinks=" + externalLinks +
            ", staticContentLinks=" + staticContentLinks +
            '}';
  }

}
