AOP-Based Public API Logging Spike
==================================

Overview
--------

This project contains spike code to explore whether we can use
Aspect-Oriented Programming (AOP) to implement public API logging for
Appian. The goal is to easily log to a CSV file every time customer
plugin code invokes one of Appian's public API methods.

Quick Start
-----------

From the command line, run the following commands in this directory:

```bash
mvn package

$JAVA_HOME/bin/java \
  -cp appian-plugin-loader/target/appian-plugin-loader-1.0-SNAPSHOT-jar-with-dependencies.jar \
  com.appian.spike.pluginloader.PluginLoader \
  acme-customer-plugin/target/acme-customer-plugin-1.0-SNAPSHOT.jar
```

The program pretends to be the Appian plugin loader code, which loads
a (fake) ACME customer jar file using a special class loader. The
class loader uses AspectJ AOP runtime weaving to instrument all calls
to (fake) Appian public API methods. The plugin loader finishes by
invoking the plugin's `start` method, which causes the plugin to begin
printing log messages and calling Appian public API methods from the
(fake) Appian services name `ContentService` and
`DocumentationService`. Both instance methods and static methods of
the customer plugin are run.

The output should look something like the following:

```
   ^^^ Hello from an AcmeAppianPlugin instance method!
   *** Call to ContentService com.appian.spike.suite.ContentService.getInstance() from {pluginName=Acme Plugin, pluginVersion=1.2.3
   *** Call to int com.appian.spike.suite.ContentService.generateId() from {pluginName=Acme Plugin, pluginVersion=1.2.3
   ^^^ Hello from an AcmeAppianPlugin static method!
   *** Call to DocumentationService com.appian.spike.suite.DocumentationService.getInstance() from {pluginName=Acme Plugin, pluginVersion=1.2.3
   *** Call to String com.appian.spike.suite.DocumentationService.getDocument() from {pluginName=Acme Plugin, pluginVersion=1.2.3
```

The lines preceded by "^^^" are printed normally by the customer
plugin code before it calls inti Appian services.

The lines preceded by "\*\*\*" are printed automatically by the AspectJ
`@Aspect` class named `LoggingAspect`. This aspect code is called
automatically before any call to an Appian public API, which is
assumed to be any method on a class in the `com.appian.spike.suite`
package.

Project Information
-------------------

This directory contains the following projects:

| Project                   | Description
|---------------------------|----------------------------------------------------------------
| **acme-customer-plugin**  | A Java class written by the ACME corporation which implement's Appian's `Plugin` interface and calls into some Appian services. Also has a plugin descriptor.
| **appian-plugin-loader**  | The `PluginLoader.main()` method that loads customer plugin jars, along with its supporting class loader and AspectJ `@Aspect` logging class.
| **appian-public-api**     | The fake public API of Appian, contained in the "suite" package. Provides `Plugin`, `ContentService`, and `DocumentationService`.

Pros and Cons of AOP Approach
-----------------------------

The main _advantages_ of an AOP-based approach are:

* The effect is as if someone waved a magic wand and modified the customer code
  to perform the logging before every call into Appian

* It requires relatively little code

* It's 100% effective with no chance of missed calls

There are also some _disadvantages_:

* The Java project is not very IntelliJ-friendly since IntelliJ doesn't seem to
  support the
  [aspectj-maven-plugin](http://www.mojohaus.org/aspectj-maven-plugin/). Command-line
  builds work fine, but testing interactively from within IntelliJ may not be
  possible.

* It requires adding [AspectJ](https://eclipse.org/aspectj/) to the Appian
  runtime package. Fortunately our Appian ae project already depends on AspectJ.
  
* It relies on a custom (AspectJ) class loader to load customer plugin .jar
  files. Custom class loaders are generally suspect since they may collide with
  JBoss class-loading behavior, could perhaps lead to resource leaks,
  etc. Careful testing is important.
  

