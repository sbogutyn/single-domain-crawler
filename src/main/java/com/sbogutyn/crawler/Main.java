package com.sbogutyn.crawler;


import com.sbogutyn.crawler.client.HttpClient;
import com.sbogutyn.crawler.client.jsoup.JsoupHttpClient;
import com.sbogutyn.crawler.domain.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Main {
  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    log.info("Start crawling");
    HttpClient client = new JsoupHttpClient();

    Link link = new Link("http://example.com/");
    Optional<String> html = client.getHtml(link);

    if (html.isPresent()) {
      log.info("Got html: \n{}", html.get());
    } else {
      log.error("Error fetching {}", link.getUrl());
    }

  }
}
