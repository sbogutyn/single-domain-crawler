package com.sbogutyn.crawler.serializer;

import com.sbogutyn.crawler.domain.CrawlResults;
import com.sbogutyn.crawler.domain.Link;
import com.sbogutyn.crawler.domain.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.xmlunit.assertj.XmlAssert.assertThat;

class XmlCrawlResultsSerializerTest {
  private XmlCrawlResultsSerializer xmlCrawlResultsSerializer;

  @BeforeEach
  void setUp() {
    xmlCrawlResultsSerializer = new XmlCrawlResultsSerializer();
  }

  @Test
  void shouldSerializeEmptyResults() {
    //given
    CrawlResults crawlResults = new CrawlResults(new Link("http://example.com"), new HashSet<>());
    //when
    String result = xmlCrawlResultsSerializer.serialize(crawlResults);
    //then
    assertThat(result)
            .and(
                    "<?xml version='1.0' encoding='UTF-8'?>\n" +
                            "<CrawlResults>\n" +
                            "  <baseUrl>http://example.com</baseUrl>\n" +
                            "  <pages/>\n" +
                            "</CrawlResults>\n").ignoreWhitespace().areIdentical();
  }

  @Test
  void shouldSerializeResultsWithSingleEmptyPage() {
    //given
    CrawlResults crawlResults = new CrawlResults(new Link("http://example.com"),
            Set.of(new Page(new Link("http://example.com/link1"), new HashSet<>(), new HashSet<>(), new HashSet<>())));
    //when
    String result = xmlCrawlResultsSerializer.serialize(crawlResults);
    //then
    assertThat(result)
            .and(
                    "<?xml version='1.0' encoding='UTF-8'?>\n" +
                            "<CrawlResults>\n" +
                            "  <baseUrl>http://example.com</baseUrl>\n" +
                            "  <pages>\n" +
                            "    <page>\n" +
                            "      <rootAddress>http://example.com/link1</rootAddress>\n" +
                            "      <internalLinks/>\n" +
                            "      <externalLinks/>\n" +
                            "      <staticContentLinks/>\n" +
                            "    </page>\n" +
                            "  </pages>\n" +
                            "</CrawlResults>\n").ignoreWhitespace().areIdentical();
  }

  @Test
  void shouldSerializeResultsWithMultiplePages() {
    //given
    Link link1 = new Link("http://example.com/link1");
    Link link2 = new Link("http://example.com/link2");
    Link link3 = new Link("http://example.com/link3");
    //LinkedHashSet to preserve ordering - for comparison with expected results
    Set<Page> pages = new LinkedHashSet<>();
    pages.add(new Page(link1, Set.of(link1), Set.of(link2), Set.of(link3)));
    pages.add(new Page(link2, Set.of(link2), Set.of(link1), Set.of(link3)));
    pages.add(new Page(link3, Set.of(link3), Set.of(link1), Set.of(link2)));
    CrawlResults crawlResults = new CrawlResults(new Link("http://example.com"), pages);
    //when
    String result = xmlCrawlResultsSerializer.serialize(crawlResults);
    //then

    assertThat(result)
            .and("<?xml version='1.0' encoding='UTF-8'?>\n" +
                    "<CrawlResults>\n" +
                    "  <baseUrl>http://example.com</baseUrl>\n" +
                    "  <pages>\n" +
                    "    <page>\n" +
                    "      <rootAddress>http://example.com/link1</rootAddress>\n" +
                    "      <internalLinks>\n" +
                    "        <internalLink>http://example.com/link1</internalLink>\n" +
                    "      </internalLinks>\n" +
                    "      <externalLinks>\n" +
                    "        <externalLink>http://example.com/link2</externalLink>\n" +
                    "      </externalLinks>\n" +
                    "      <staticContentLinks>\n" +
                    "        <staticContentLink>http://example.com/link3</staticContentLink>\n" +
                    "      </staticContentLinks>\n" +
                    "    </page>\n" +
                    "    <page>\n" +
                    "      <rootAddress>http://example.com/link2</rootAddress>\n" +
                    "      <internalLinks>\n" +
                    "        <internalLink>http://example.com/link2</internalLink>\n" +
                    "      </internalLinks>\n" +
                    "      <externalLinks>\n" +
                    "        <externalLink>http://example.com/link1</externalLink>\n" +
                    "      </externalLinks>\n" +
                    "      <staticContentLinks>\n" +
                    "        <staticContentLink>http://example.com/link3</staticContentLink>\n" +
                    "      </staticContentLinks>\n" +
                    "    </page>\n" +
                    "    <page>\n" +
                    "      <rootAddress>http://example.com/link3</rootAddress>\n" +
                    "      <internalLinks>\n" +
                    "        <internalLink>http://example.com/link3</internalLink>\n" +
                    "      </internalLinks>\n" +
                    "      <externalLinks>\n" +
                    "        <externalLink>http://example.com/link1</externalLink>\n" +
                    "      </externalLinks>\n" +
                    "      <staticContentLinks>\n" +
                    "        <staticContentLink>http://example.com/link2</staticContentLink>\n" +
                    "      </staticContentLinks>\n" +
                    "    </page>\n" +
                    "  </pages>\n" +
                    "</CrawlResults>\n").ignoreWhitespace().areSimilar();
  }
}