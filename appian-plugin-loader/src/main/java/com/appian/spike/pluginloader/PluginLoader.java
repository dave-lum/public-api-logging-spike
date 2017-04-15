package com.appian.spike.pluginloader;

import java.io.File;
import java.net.URL;

import com.appian.spike.suite.Plugin;

/**
 * The class that loads customer-written plugin jar files and instruments them to perform
 * logging whenever they invoke Appian public API methods.
 * For now this is a Java "main" function that accepts a customer jar file as an argument.
 * USAGE:
 * java -cp ... PluginLoader /the/path/to/some/customerPlugin.jar
 */
public class PluginLoader {
  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      throw new Exception("Missing the required \"customer plugin\" jar file argument.");
    }
    String customerPluginJarPath = args[0];
    File customerPluginJar = new File(customerPluginJarPath);
    if (!customerPluginJar.isFile()) {
      throw new Exception("The specified customer plugin jar file doesn't exist: " + customerPluginJarPath);
    }
    if (!customerPluginJar.canRead()) {
      throw new Exception("The specified customer plugin jar file is unreadable: " + customerPluginJarPath);
    }
    ClassLoader defaultLdr = Thread.currentThread().getContextClassLoader();
    AppianPluginClassLoader weaver = new AppianPluginClassLoader(customerPluginJar,
        new URL[] {customerPluginJar.toURI().toURL()},
        new URL[] {PluginLoader.class.getProtectionDomain().getCodeSource().getLocation()}, defaultLdr);
    Class<?> pluginClass = weaver.loadClass("com.acme.AcmeAppianPlugin");
    Plugin plugin = (Plugin)pluginClass.newInstance();
    plugin.start();
  }
}
