package com.sbogutyn.crawler.db;


import com.sbogutyn.crawler.domain.Link;
import com.sbogutyn.crawler.domain.Page;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class InMemoryLinksDb implements LinksDb {
  private final Set<Page> indexedPages;
  private final Queue<Link> processingQueue;
  private final Set<Link> processedInternalLinks;

  public InMemoryLinksDb(Set<Page> indexedPages, Queue<Link> processingQueue, Set<Link> processedInternalLinks) {
    this.indexedPages = indexedPages;
    this.processingQueue = processingQueue;
    this.processedInternalLinks = processedInternalLinks;
  }

  public InMemoryLinksDb() {
    this.indexedPages = new HashSet<>();
    this.processingQueue = new ArrayDeque<>();
    this.processedInternalLinks = new HashSet<>();
  }

  @Override
  public boolean isLinkAlreadyIndexed(Link link) {
    return processingQueue.contains(link) || processedInternalLinks.contains(link);
  }

  @Override
  public void addToPendingLinks(Link link) {
    processingQueue.offer(link);
  }

  @Override
  public void addPage(Page page) {
    indexedPages.add(page);
    processedInternalLinks.add(page.getRootAddress());
  }

  @Override
  public Link takeLinkFromQueue() {
    return processingQueue.poll();
  }

  @Override
  public boolean isPendingLinksQueueEmpty() {
    return processingQueue.isEmpty();
  }

  @Override
  public Set<Page> getAllPages() {
    return indexedPages;
  }
}
