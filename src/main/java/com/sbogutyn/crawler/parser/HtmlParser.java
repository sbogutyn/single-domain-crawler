package com.sbogutyn.crawler.parser;

import com.sbogutyn.crawler.domain.Link;

import java.util.Set;

@FunctionalInterface
public interface HtmlParser {
  Set<Link> parse(String html);
}
