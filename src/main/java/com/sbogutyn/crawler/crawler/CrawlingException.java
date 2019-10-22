package com.sbogutyn.crawler.crawler;

class CrawlingException extends RuntimeException {
  CrawlingException(String message) {
    super(message);
  }

  CrawlingException(String message, Throwable cause) {
    super(message, cause);
  }
}
