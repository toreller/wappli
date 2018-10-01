package org.wappli.common.server.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    private static Logger log = LoggerFactory.getLogger(ApplicationStartup.class.getName());

    @Autowired
    private Environment env;
    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
            log.info(event.getSpringApplication().getMainApplicationClass().getSimpleName()
                + " build version: " + env.getProperty("info.project.version"));

        return;
    }

}