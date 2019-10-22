package com.sbogutyn.crawler.client.jsoup;

import com.sbogutyn.crawler.domain.Link;
import com.sbogutyn.crawler.util.TestUtils;
import org.junit.jupiter.api.*;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class JsoupHttpClientTest {
  private static ClientAndServer mockServer;
  private JsoupHttpClient jsoupHttpClient;
  private MockServerClient mockServerClient;


  @BeforeAll
  static void setUp() {
    mockServer = startClientAndServer(1080);
  }

  @AfterAll
  static void shutDown() {
    mockServer.stop();
  }

  @BeforeEach
  void setUpTest() {
    jsoupHttpClient = new JsoupHttpClient();
    mockServerClient = new MockServerClient("localhost", mockServer.getLocalPort());
  }

  @Test
  void shouldGetHtmlOfAValidUrl() {
    //given
    String pageBody = TestUtils.getFileFromResources("testPage.html");
    mockServerClient
            .when(request().withPath(mockServerClient.contextPath()))
            .respond(response().withBody(pageBody));
    //when
    Link link = new Link("http://localhost:" + mockServer.getLocalPort());
    Optional<String> html = jsoupHttpClient.getHtml(link);
    //then
    assertTrue(html.isPresent());
    assertThat(html.get()).isNotBlank();
    assertThat(html.get()).isEqualToIgnoringWhitespace(pageBody);
  }

  @Test
  void shouldReturnEmptyOptionalOnInvalidUrl() {
    //given
    //when
    Optional<String> invalidLink = jsoupHttpClient.getHtml(new Link("SomeInvalidLink"));
    //then
    assertThat(invalidLink).isEmpty();
  }
}