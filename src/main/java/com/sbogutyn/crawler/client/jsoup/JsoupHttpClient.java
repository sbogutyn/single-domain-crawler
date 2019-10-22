package com.sbogutyn.crawler.client.jsoup;

import com.sbogutyn.crawler.client.HttpClient;
import com.sbogutyn.crawler.domain.Link;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class JsoupHttpClient implements HttpClient {
  private static final Logger log = LoggerFactory.getLogger(JsoupHttpClient.class);

  @Override
  public Optional<String> getHtml(Link link) {
    try {
      log.debug("Fetching {}", link.getUrl());
      return Optional.ofNullable(Jsoup.connect(link.getUrl()).get().html());
    } catch (Exception e) {
      log.error("Couldn't fetch {} - skipping this link", link.getUrl(), e);
      return Optional.empty();
    }
  }
}
