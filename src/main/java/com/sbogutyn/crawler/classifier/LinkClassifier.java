package com.sbogutyn.crawler.classifier;

import com.sbogutyn.crawler.domain.Link;
import com.sbogutyn.crawler.util.LinkUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LinkClassifier {
  private final String baseDomain;
  private final Pattern staticContentPattern =
          Pattern.compile("^.+\\.(aac" +
                  "|abw" +
                  "|arc" +
                  "|avi" +
                  "|azw" +
                  "|bin" +
                  "|bmp" +
                  "|bz" +
                  "|bz2" +
                  "|csh" +
                  "|css" +
                  "|csv" +
                  "|doc" +
                  "|docx" +
                  "|eot" +
                  "|epub" +
                  "|gz" +
                  "|gif" +
                  "|ico" +
                  "|ics" +
                  "|jar" +
                  "|jpeg" +
                  "|jpg" +
                  "|js" +
                  "|json" +
                  "|jsonld" +
                  "|mid" +
                  "|midi" +
                  "|mjs" +
                  "|mp3" +
                  "|mpeg" +
                  "|mpkg" +
                  "|odp" +
                  "|ods" +
                  "|odt" +
                  "|oga" +
                  "|ogv" +
                  "|ogx" +
                  "|opus" +
                  "|otf" +
                  "|png" +
                  "|pdf" +
                  "|php" +
                  "|ppt" +
                  "|pptx" +
                  "|rar" +
                  "|rtf" +
                  "|sh" +
                  "|svg" +
                  "|swf" +
                  "|tar" +
                  "|tif" +
                  "|tiff" +
                  "|torrent" +
                  "|ts" +
                  "|ttf" +
                  "|txt" +
                  "|vsd" +
                  "|wav" +
                  "|weba" +
                  "|webm" +
                  "|webp" +
                  "|woff" +
                  "|woff2" +
                  "|xls" +
                  "|xlsx" +
                  "|xml" +
                  "|xul" +
                  "|zip" +
                  "|3gp" +
                  "|3g2" +
                  "|7z)$");

  public LinkClassifier(String baseDomain) {
    this.baseDomain = Objects.requireNonNull(baseDomain);
  }

  private LinkClassification classify(Link link) {
    if (isStaticContent(link)) {
      return LinkClassification.STATIC_CONTENT;
    } else if (isInTheSameDomain(link)) {
      return LinkClassification.INTERNAL;
    } else {
      return LinkClassification.EXTERNAL;
    }
  }

  private boolean isStaticContent(Link link) {
    return LinkUtil.findPath(link.getUrl())
            .map(staticContentPattern::matcher)
            .map(Matcher::matches)
            .orElse(false);
  }

  private boolean isInTheSameDomain(Link link) {
    return LinkUtil.findDomain(link.getUrl())
            .map(domain -> domain.equalsIgnoreCase(baseDomain))
            .orElse(false);
  }

  public LinkClassificationGroups classifyAll(Set<Link> links) {
    Map<LinkClassification, Set<Link>> linksClassificationMap = links.stream().collect(
            Collectors.groupingBy(this::classify,
                    Collectors.mapping(Function.identity(), Collectors.toSet())));

    return new LinkClassificationGroups(
            linksClassificationMap.getOrDefault(LinkClassification.INTERNAL, new HashSet<>()),
            linksClassificationMap.getOrDefault(LinkClassification.EXTERNAL, new HashSet<>()),
            linksClassificationMap.getOrDefault(LinkClassification.STATIC_CONTENT, new HashSet<>()));
  }
}
