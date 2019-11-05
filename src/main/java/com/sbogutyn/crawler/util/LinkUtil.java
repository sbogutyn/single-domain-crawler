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
      return Optional.ofNullable(parsedUrl.path());
    } catch (GalimatiasParseException e) {
      log.error("Couldn't find domain in {}", url, e);
      return Optional.empty();
    }
  }

  public static Optional<String> withoutFragment(String url) {
    try {
      URL parsedUrl = URL.parse(url);
      if (parsedUrl.fragment() != null) {
        parsedUrl = parsedUrl.withFragment(null);
      }
      return Optional.ofNullable(parsedUrl).map(URL::toString);
    } catch (GalimatiasParseException e) {
      return Optional.empty();
    }
  }

  public static String withoutTrailingSlash(String url) {
    if (url.endsWith("/")) {
      return url.substring(0, url.length() - 1);
    } else {
      return url;
    }
  }

  public static Optional<String> normalizeUrl(String url) {
    return Optional.ofNullable(url)
            .flatMap(LinkUtil::withoutFragment)
            .map(LinkUtil::withoutTrailingSlash);
  }
}
