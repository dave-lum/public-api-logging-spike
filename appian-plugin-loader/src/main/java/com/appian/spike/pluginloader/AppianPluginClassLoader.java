package com.appian.spike.pluginloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.aspectj.weaver.loadtime.WeavingURLClassLoader;

/**
 * Loads customer plugin classes, using AspectJ runtime AOP weaving to inject logging before every
 * call into an Appian public API.
 */
public class AppianPluginClassLoader extends WeavingURLClassLoader {
  private final PluginProperties pluginProperties;

  public AppianPluginClassLoader(
      File customerPluginJar,
      URL[] classURLs,
      URL[] aspectURLs,
      ClassLoader parent) throws IOException {
    super(classURLs, aspectURLs, parent);
    pluginProperties = new PluginProperties(this, customerPluginJar);
  }

  public PluginProperties getPluginProperties() {
    return pluginProperties;
  }
}
