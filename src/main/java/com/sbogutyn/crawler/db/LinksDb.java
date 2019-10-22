package com.sbogutyn.crawler.db;

import com.sbogutyn.crawler.domain.Link;
import com.sbogutyn.crawler.domain.Page;

import java.util.Set;

public interface LinksDb {
  boolean isLinkAlreadyIndexed(Link link);
  void addToPendingLinks(Link link);
  void addPage(Page page);
  Link takeLinkFromQueue();
  boolean isPendingLinksQueueEmpty();
  Set<Page> getAllPages();

}
