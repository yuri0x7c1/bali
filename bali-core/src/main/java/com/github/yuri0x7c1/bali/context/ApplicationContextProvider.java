package com.github.yuri0x7c1.bali.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationContextProvider implements InitializingBean, ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        synchronized (ApplicationContextProvider.class) {
            if (context != null) {
                log.warn("The application context has already been set. Do you have multiple instances of ApplicationContextProvider in your application?");
            }
            context = applicationContext;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("{} initialized", getClass().getName());
    }
}
