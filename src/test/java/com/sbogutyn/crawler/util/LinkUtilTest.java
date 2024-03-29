package com.sbogutyn.crawler.util;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LinkUtilTest {

  @Test
  void shouldFindDomainInSimpleUrl() {
    //given
    String link = "http://www.example.com";
    //when
    Optional<String> domain = LinkUtil.findDomain(link);
    //then
    assertThat(domain).isNotEmpty();
    assertThat(domain.orElse("")).isEqualToIgnoringWhitespace("http://www.example.com/");
  }

  @Test
  void shouldFindDomainInUrlWithPath() {
    //given
    String link = "http://www.example.com/some/more/complex/path";
    //when
    Optional<String> domain = LinkUtil.findDomain(link);
    //then
    assertThat(domain).isNotEmpty();
    assertThat(domain.orElse("")).isEqualToIgnoringWhitespace("http://www.example.com/");
  }

  @Test
  void shouldReturnEmptyOptionalWhenFindingPathInMailtoLinks() {
    //given
    String link = "mailto:test@email.com";
    //when
    Optional<String> result = LinkUtil.findPath(link);
    //then
    assertThat(result).isEmpty();
  }

  @Test
  void shouldReturnEmptyOptionalWhenFindingDomainInMailToLinks() {
    //given
    String link = "mailto:test@email.com";
    //when
    Optional<String> result = LinkUtil.findDomain(link);
    //then
    assertThat(result).isEmpty();
  }

  @Test
  void shouldRemoveLinkFragments() {
    //given
    String link = "http://example.com/page#some-element";
    //when
    Optional<String> result = LinkUtil.withoutFragment(link);
    //then
    assertThat(result).isNotEmpty();
    assertThat(result).get().isEqualTo("http://example.com/page");
  }


  @Test
  void shouldRemoveTrailingSlash() {
    //given
    String link = "http://example.com/page/";
    //when
    String result = LinkUtil.withoutTrailingSlash(link);
    //then
    assertThat(result).isNotEmpty();
    assertThat(result).isEqualTo("http://example.com/page");
  }
}