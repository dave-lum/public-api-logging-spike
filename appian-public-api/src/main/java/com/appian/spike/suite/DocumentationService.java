package com.appian.spike.suite;

/**
 * A fake Appian service class, calls to which must be logged.
 */
public class DocumentationService {
  public static DocumentationService getInstance() {
    return new DocumentationService();
  }

  public String getDocument() {
    return "Some Document";
  }
}
