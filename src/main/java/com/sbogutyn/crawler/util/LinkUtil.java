package com.sbogutyn.crawler.util;

import io.mola.galimatias.GalimatiasParseException;
import io.mola.galimatias.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LinkUtil {
  private static final Logger log = LoggerFactory.getLogger(LinkUtil.class);

  private LinkUtil() {
    throw new UnsupportedOperationException();
  }

  public static Optional<String> findDomain(String url) {
    try {
      URL parsedUrl = URL.parse(url);
      URL resolve = parsedUrl.resolve("/");
      return Optional.of(resolve.toString());
    } catch (GalimatiasParseException e) {
      log.error("Couldn't find domain in {}", url, e);
      return Optional.empty();
    }
  }

  public static Optional<String> findPath(String url) {
    try {
      URL parsedUrl = URL.parse(url);
      return Optional.of(parsedUrl.path());
    } catch (GalimatiasParseException e) {
      log.error("Couldn't find domain in {}", url, e);
      return Optional.empty();
    }
  }
}
