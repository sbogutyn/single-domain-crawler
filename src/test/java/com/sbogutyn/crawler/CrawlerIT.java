package com.sbogutyn.crawler;

import com.sbogutyn.crawler.crawler.CrawlLimit;
import com.sbogutyn.crawler.crawler.Crawler;
import com.sbogutyn.crawler.crawler.CrawlerFactory;
import com.sbogutyn.crawler.domain.CrawlResults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class CrawlerIT {
  private ClientAndServer mockServer;
  private String BASE_URL;
  private Crawler crawler;


  @BeforeEach
  void setUp() {
    mockServer = startClientAndServer(1080);
    BASE_URL = "http://localhost:" + mockServer.getLocalPort() + "/";
    crawler = CrawlerFactory.crawler(CrawlerFactory.CrawlerType.JSOUP, BASE_URL, new CrawlLimit());
  }

  @AfterEach
  void shutDown() {
    mockServer.stop();
  }

  @Test
  void shouldCrawlBasicSite() {
    //given
    prepareMockSite("/path",
            IntStream.range(1, 10)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.toList()));
    //when
    CrawlResults crawlResults = crawler.crawlSite(prepareInternalLink("path"));
    //then
    assertThat(crawlResults).isNotNull();
    assertThat(crawlResults.getBaseUrl()).isNotNull();
    assertThat(crawlResults.getBaseUrl().getUrl()).isEqualTo("http://localhost:1080/path");
    assertThat(crawlResults.getPages()).isNotEmpty();
    assertThat(crawlResults.getPages().size()).isEqualTo(10);
  }

  @Test
  void shouldNotCrawlExternalLinks() {
    //given
    mockServer
            .when(request()
                    .withPath("/path"))
            .respond(
                    response()
                            .withBody("<html><head></head><body>" +
                                    formatLinkTag("http://external-site.com", "external-site") +
                                    formatLinkTag(prepareInternalLink("internal"), "internal") +
                                    "</body></html>")
            );

    prepareMockResponseWithEmptyBody("internal");

    //when
    CrawlResults crawlResults = crawler.crawlSite(prepareInternalLink("path"));
    //then
    assertThat(crawlResults).isNotNull();
    assertThat(crawlResults.getPages()).isNotEmpty();
    assertThat(crawlResults.getPages().size()).isEqualTo(2);
    assertThat(crawlResults.getPages()).filteredOn(page -> !page.getExternalLinks().isEmpty()).size().isEqualTo(1);
    assertThat(crawlResults.getPages()).filteredOn(page -> page.getRootAddress().getUrl().contains("external")).isEmpty();
  }

  @Test
  void shouldHandleCyclesInInternalLinks() {
    //given
    mockServer
            .when(request()
                    .withPath("/path"))
            .respond(
                    response()
                            .withBody("<html><head></head><body>" +
                                    formatLinkTag("/path", "path") + "</body></html>")
            );
    //when
    CrawlResults crawlResults = crawler.crawlSite(prepareInternalLink("path"));
    //then
    assertThat(crawlResults).isNotNull();
    assertThat(crawlResults.getBaseUrl()).isNotNull();
    assertThat(crawlResults.getBaseUrl().getUrl()).isEqualTo("http://localhost:1080/path");
    assertThat(crawlResults.getPages()).isNotEmpty();
    assertThat(crawlResults.getPages().size()).isEqualTo(1);
  }

  private String prepareInternalLink(String name) {
    return BASE_URL + name;
  }

  private void prepareMockSite(String path, List<String> links) {
    mockServer
            .when(request()
                    .withPath(path))
            .respond(
                    response()
                            .withBody("<html><head></head><body>" +
                                    links.stream().map(link -> formatLinkTag(prepareInternalLink(link), link))
                                            .collect(Collectors.joining("")) + "</body></html>")
            );

    links.forEach(this::prepareMockResponseWithEmptyBody);
  }

  private String formatLinkTag(String url, String linkText) {
    return String.format("<a href=\"%s\">%s</a>", url, linkText);
  }

  private void prepareMockResponseWithEmptyBody(String path) {
    mockServer.when(request().withPath("/" + path))
            .respond(response().withBody("<html><head></head><body></body></html>"));
  }

}
