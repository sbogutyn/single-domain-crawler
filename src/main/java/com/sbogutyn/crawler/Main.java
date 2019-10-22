package com.sbogutyn.crawler;


import com.sbogutyn.crawler.classifier.LinkClassificationGroups;
import com.sbogutyn.crawler.classifier.LinkClassifier;
import com.sbogutyn.crawler.client.HttpClient;
import com.sbogutyn.crawler.client.jsoup.JsoupHttpClient;
import com.sbogutyn.crawler.domain.Link;
import com.sbogutyn.crawler.parser.HtmlParser;
import com.sbogutyn.crawler.parser.jsoup.JsoupParser;
import com.sbogutyn.crawler.util.LinkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

public class Main {
  private static final Logger log = LoggerFactory.getLogger(Main.class);
  public static final String TEST_URL = "http://example.com/";

  public static void main(String[] args) {
    log.info("Start crawling");
    HttpClient client = new JsoupHttpClient();

    Link link = new Link(TEST_URL);
    Optional<String> html = client.getHtml(link);

    if (html.isPresent()) {
      log.info("Got html: \n{}", html.get());

      HtmlParser parser = new JsoupParser(TEST_URL);
      Set<Link> links = parser.parse(html.get());
      links.forEach(l -> log.info("Extracted link: {}", l));

      LinkClassifier linkClassifier = LinkUtil.findDomain(TEST_URL).map(LinkClassifier::new).orElseThrow();
      LinkClassificationGroups linkClassificationGroups = linkClassifier.classifyAll(links);
      log.info("Classified links into:\n {}", linkClassificationGroups);
    } else {
      log.error("Error fetching {}", link.getUrl());
    }

  }
}
