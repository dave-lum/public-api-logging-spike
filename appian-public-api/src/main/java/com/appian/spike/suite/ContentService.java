package com.appian.spike.suite;

/**
 * A fake Appian service class, calls to which must be logged.
 */
public class ContentService {
  public static ContentService getInstance() {
    return new ContentService();
  }

  public int generateId() {
    return 42;
  }
}
