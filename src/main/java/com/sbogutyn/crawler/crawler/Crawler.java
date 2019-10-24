package com.sbogutyn.crawler.crawler;


import com.sbogutyn.crawler.domain.CrawlResults;

public interface Crawler {
  CrawlResults crawlSite(String baseUrl);
}
