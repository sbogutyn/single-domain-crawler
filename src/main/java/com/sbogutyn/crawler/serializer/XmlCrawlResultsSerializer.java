package com.sbogutyn.crawler.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.sbogutyn.crawler.domain.CrawlResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlCrawlResultsSerializer implements CrawlResultsSerializer<String> {
  private static final Logger log = LoggerFactory.getLogger(XmlCrawlResultsSerializer.class);
  private final XmlMapper xmlMapper;

  public XmlCrawlResultsSerializer() {
    xmlMapper = new XmlMapper();
    configureMapper();
  }

  private void configureMapper() {
    xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
    xmlMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
    xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
  }

  @Override
  public String serialize(CrawlResults crawlResults) {
    try {
      return xmlMapper.writeValueAsString(crawlResults);
    } catch (JsonProcessingException e) {
      throw new ParsingException("Cannot serialize CrawlResults to xml", e);
    }
  }
}
