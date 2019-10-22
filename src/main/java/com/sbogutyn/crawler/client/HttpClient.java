package com.sbogutyn.crawler.client;

import com.sbogutyn.crawler.domain.Link;

import java.util.Optional;


@FunctionalInterface
public interface HttpClient {
  Optional<String> getHtml(Link link);
}
