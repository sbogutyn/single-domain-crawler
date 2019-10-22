package com.sbogutyn.crawler.parser.jsoup;

import com.sbogutyn.crawler.domain.Link;
import com.sbogutyn.crawler.parser.HtmlParser;
import org.jsoup.Jsoup;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsoupParser implements HtmlParser {

  private static final String LINK_TAG = "a";
  private static final String HREF_ATTR = "abs:href";

  private final String baseUrl;

  public JsoupParser(String baseUrl) {
    this.baseUrl = Objects.requireNonNull(baseUrl);
  }

  @Override
  public Set<Link> parse(String html) {
    return Jsoup.parse(html, baseUrl)
            .getElementsByTag(byLinkTag())
            .eachAttr(absoluteHref())
            .stream()
            .map(toLink())
            .collect(Collectors.toSet());
  }

  private String absoluteHref() {
    return HREF_ATTR;
  }

  private String byLinkTag() {
    return LINK_TAG;
  }

  private Function<String, Link> toLink() {
    return Link::new;
  }

}
