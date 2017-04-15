package com.acme;

import com.appian.spike.suite.ContentService;
import com.appian.spike.suite.DocumentationService;
import com.appian.spike.suite.Plugin;

public class AcmeAppianPlugin implements Plugin {
  public void start() {
    print("Hello from an AcmeAppianPlugin instance method!");
    ContentService contentSvc = ContentService.getInstance();
    int id = contentSvc.generateId();

    someStaticFunc();
  }

  private static void someStaticFunc() {
    print("Hello from an AcmeAppianPlugin static method!");
    DocumentationService docSvc = DocumentationService.getInstance();
    String doc = docSvc.getDocument();
  }

  private static void print(String msg) {
    System.out.println("^^^ " + msg);
  }
}
