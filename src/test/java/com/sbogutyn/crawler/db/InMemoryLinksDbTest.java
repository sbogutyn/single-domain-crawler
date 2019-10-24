package com.sbogutyn.crawler.db;

import com.sbogutyn.crawler.domain.Link;
import com.sbogutyn.crawler.domain.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryLinksDbTest {

  private InMemoryLinksDb inMemoryLinksDb;

  @BeforeEach
  void setUp() {
    inMemoryLinksDb = new InMemoryLinksDb();
  }

  @Test
  void shouldAddPageToDb() {
    //given
    Link testLink = new Link("testLink");
    Page page = new Page(testLink, Set.of(), Set.of(), Set.of());
    //when
    inMemoryLinksDb.addPage(page);
    //then
    assertThat(inMemoryLinksDb.getAllPages()).containsExactly(page);
    assertThat(inMemoryLinksDb.isLinkAlreadyIndexed(testLink)).isTrue();
  }

  @Test
  void addToPendingLinks() {
    //given
    Link testLink = new Link("testLink");
    //when
    assertThat(inMemoryLinksDb.isPendingLinksQueueEmpty()).isTrue();
    inMemoryLinksDb.addToPendingLinks(testLink);
    //then
    assertThat(inMemoryLinksDb.isPendingLinksQueueEmpty()).isFalse();
    assertThat(inMemoryLinksDb.takeLinkFromQueue()).isEqualTo(testLink);
  }
}