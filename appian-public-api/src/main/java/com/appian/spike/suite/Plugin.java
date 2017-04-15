package com.appian.spike.suite;

/**
 * The interface which any customer-written Appian plugin must implement.
 */
public interface Plugin {
  /**
   * Initializes 'this' plugin.
   */
  public void start();
}
