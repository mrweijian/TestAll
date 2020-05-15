package com.spring;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.startup.Tomcat;

/**
 * 内置tomcat启动流程
 */
public class App {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(80);
        Context context = tomcat.addContext("/", System.getProperty("java.io.tmpdir"));
        context.addLifecycleListener((LifecycleListener)Class.forName(tomcat.getHost().getConfigClass()).newInstance());
        tomcat.start();
        tomcat.getServer().await();
    }
}
