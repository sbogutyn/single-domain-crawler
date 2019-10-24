package com.sbogutyn.crawler.serializer;

import com.sbogutyn.crawler.domain.CrawlResults;

public interface CrawlResultsSerializer<T> {
  T serialize(CrawlResults crawlResults);
}
