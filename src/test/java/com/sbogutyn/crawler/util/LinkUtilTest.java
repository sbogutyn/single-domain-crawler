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
  void shouldFindDomainInUrlWithOutWWW() {
    //given
    String link = "http://example.com/some/more/complex/path";
    //when
    Optional<String> domain = LinkUtil.findDomain(link);
    //then
    assertThat(domain).isNotEmpty();
    assertThat(domain.orElse("")).isEqualToIgnoringWhitespace("http://www.example.com/");
  }

  @Test
  void shouldFindDomainInUrlWithSubDomain() {
    //given
    String link = "http://sub.example.com/some/more/complex/path";
    //when
    Optional<String> domain = LinkUtil.findDomain(link);
    //then
    assertThat(domain).isNotEmpty();
    assertThat(domain.orElse("")).isEqualToIgnoringWhitespace("http://www.example.com/");
  }
}