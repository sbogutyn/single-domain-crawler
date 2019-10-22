package com.sbogutyn.crawler.classifier;

import com.sbogutyn.crawler.domain.Link;
import com.sbogutyn.crawler.util.LinkUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LinkClassifier {
  private String baseDomain;

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

  //TODO: add detection of static content links
  private boolean isStaticContent(Link link) {
    return false;
  }

  private boolean isInTheSameDomain(Link link) {
    Optional<String> domain = LinkUtil.findDomain(link.getUrl());
    return domain.isPresent() && domain.get().equalsIgnoreCase(baseDomain);
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
