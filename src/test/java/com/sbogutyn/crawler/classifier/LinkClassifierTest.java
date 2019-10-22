package com.sbogutyn.crawler.classifier;

import com.sbogutyn.crawler.domain.Link;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class LinkClassifierTest {
  private static final String BASE_DOMAIN = "example.com";

  @Test
  void shouldClassifyExternalAndInternalLinks() {
    //given
    LinkClassifier linkClassifier = new LinkClassifier(BASE_DOMAIN);
    //when
    LinkClassificationGroups result = linkClassifier.classifyAll(
            Set.of(
                    new Link("http://example.com/1"),
                    new Link("http://example.com/2"),
                    new Link("http://example.com/3"),
                    new Link("http://example.com/4"),
                    new Link("http://external-domain.com/1"),
                    new Link("http://external-domain.com/2"),
                    new Link("http://external-domain.com/3")
            ));
    //then
    assertThat(result).isEqualToComparingFieldByFieldRecursively(
            new LinkClassificationGroups(
                    Set.of(
                            new Link("http://example.com/1"),
                            new Link("http://example.com/2"),
                            new Link("http://example.com/3"),
                            new Link("http://example.com/4")
                    ),
                    Set.of(
                            new Link("http://external-domain.com/1"),
                            new Link("http://external-domain.com/2"),
                            new Link("http://external-domain.com/3")),
                    Set.of()
            ));
  }

}