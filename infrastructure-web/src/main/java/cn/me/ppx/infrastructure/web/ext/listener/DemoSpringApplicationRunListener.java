package cn.me.ppx.infrastructure.web.ext.listener;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author ym
 * @date 2022/12/5 14:24
 * springboot 的默认实现只有这一个EventPublishingRunListener，通过这个来监控整个启动的生命周期，发送各种event
 */
public class DemoSpringApplicationRunListener implements SpringApplicationRunListener {
    private final SpringApplication application;

    private final String[] args;
    /**
     * 必须要提供这个这两个参数类型的构造器，启动的时候要强行传过来
     *
     * @param application  启动类
     * @param args         启动类的指定参数
     * @param application
     * @param args
     */
    public DemoSpringApplicationRunListener(SpringApplication application, String[]  args){
        this.application = application;
        this.args = args;
        System.out.println("constructor");
    }

    /**
     * 准备启动，同时会发出ApplicationStartingEvent事件
     * @param bootstrapContext the bootstrap context
     */
    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        System.out.println("starting");
    }


    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        System.out.println("environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        System.out.println("contextPrepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        ConfigurableEnvironment environment = context.getEnvironment();

        System.out.println("contextLoaded");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        SpringApplicationRunListener.super.running(context);
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("failed");
    }
}
