package com.sbogutyn.crawler.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestUtils {
  public static String EMPTY_PAGE = "emptyPage.html";
  public static String TEST_PAGE = "testPage.html";

  public static String getFileFromResources(String fileName) {
    ClassLoader classLoader = TestUtils.class.getClassLoader();

    URL resource = classLoader.getResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException("file is not found!");
    } else {
      try {
        return Files.readString(Path.of(new File(resource.getFile()).toURI()));
      } catch (IOException e) {
        throw new IllegalArgumentException("Invalid file: " + fileName);
      }
    }
  }

}
