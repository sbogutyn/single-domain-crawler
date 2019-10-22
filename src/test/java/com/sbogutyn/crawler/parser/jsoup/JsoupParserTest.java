package com.sbogutyn.crawler.parser.jsoup;

import com.sbogutyn.crawler.domain.Link;
import com.sbogutyn.crawler.parser.jsoup.JsoupParser;
import com.sbogutyn.crawler.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class JsoupParserTest {
  private static final String BASE_URL = "http://my-test-page.org";
  private JsoupParser jsoupParser;

  @BeforeEach
  void setUp() {
    jsoupParser = new JsoupParser(BASE_URL);
  }

  @Test
  void shouldReturnEmptySetIfThereAreNoLinksOnPage() {
    //given
    String html = TestUtils.getFileFromResources(TestUtils.EMPTY_PAGE);
    //when
    Set<Link> links = jsoupParser.parse(html);
    //then
    assertThat(links).isEmpty();
  }

  @Test
  void shouldReturnAllLinksOnPage() {
    //given
    String html = TestUtils.getFileFromResources(TestUtils.TEST_PAGE);
    //when
    Set<Link> links = jsoupParser.parse(html);
    //then
    assertThat(links).isNotEmpty();
    assertThat(links.stream().map(Link::getUrl).collect(Collectors.toSet()))
            .containsExactlyInAnyOrder(
                    BASE_URL + "/relative.html",
                    BASE_URL + "/Absolute.html",
                    "http://test.domain/external.html"
            );
  }
}