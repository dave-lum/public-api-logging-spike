package com.appian.spike.pluginloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads the not-very-realistic "appian-plugin.properties" file from a customer plugin jar.
 */
public class PluginProperties {
  private final String pluginName;
  private final String pluginVersion;

  public PluginProperties(ClassLoader customerPluginClassLdr, File customerPluginJar) throws IOException {
    try (InputStream propsStream = customerPluginClassLdr.getResourceAsStream("appian-plugin.properties")) {
      if (propsStream == null) {
        throw new RuntimeException(
            "Failed to find an appian-plugin.properties resource in " + customerPluginJar);
      }
      Properties pluginProps = new Properties();
      pluginProps.load(propsStream);
      pluginName = this.getProperty(pluginProps, "pluginName", customerPluginJar);
      pluginVersion = this.getProperty(pluginProps, "pluginVersion", customerPluginJar);
    }
  }

  public String getPluginName() {
    return pluginName;
  }

  public String getPluginVersion() {
    return pluginVersion;
  }

  private String getProperty(Properties pluginProps, String propName, File customerPluginJar) {
    String propValue = pluginProps.getProperty(propName);
    if (propValue == null) {
      throw new RuntimeException("Missing required property \"" + propName + "\" in " + customerPluginJar);
    }
    return propValue;
  }
}
