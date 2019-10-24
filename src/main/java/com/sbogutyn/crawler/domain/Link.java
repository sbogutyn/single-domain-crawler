package com.sbogutyn.crawler.domain;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import java.util.Objects;

public class Link {
  @JacksonXmlText
  private final String url;

  public Link(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Link link = (Link) o;
    return url.equals(link.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url);
  }

  @Override
  public String toString() {
    return "Link{" +
            "url='" + url + '\'' +
            '}';
  }
}

