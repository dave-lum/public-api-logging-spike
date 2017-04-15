package com.appian.spike.pluginloader;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.SourceLocation;

/**
 * An AspectJ aspect class that gets called whenever a customer's plugin code invokes any (fake) Appian
 * public API method in a com.appian.spike.suite.* class. This aspect logs information about those calls.
 */
@Aspect
public class LoggingAspect {

  @Before("call(* com.appian.spike.suite..*(..))")
  public void anySuiteCall(JoinPoint joinPoint) throws Throwable {
    SourceLocation srcLoc = joinPoint.getSourceLocation();
    Class<?> pluginClass = srcLoc.getWithinType();
    ClassLoader baseClassLoader = pluginClass.getClassLoader();
    if (baseClassLoader instanceof AppianPluginClassLoader) {
      AppianPluginClassLoader classLoader = (AppianPluginClassLoader)baseClassLoader;
      PluginProperties pluginProperties = classLoader.getPluginProperties();
      Signature sig = joinPoint.getSignature();
      print("Call to " + sig + " from {pluginName=" + pluginProperties.getPluginName() + ", pluginVersion=" +
          pluginProperties.getPluginVersion());
    }
  }

  private static void print(String msg) {
    System.out.println("*** " + msg);
  }
}
