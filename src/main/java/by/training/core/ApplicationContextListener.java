package by.training.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

    private final static Logger LOGGER = LogManager.getLogger(ApplicationContextListener.class);


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext.getInstance().init();
        LOGGER.info("Context was initialized.");
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ApplicationContext.getInstance().destroy();
        LOGGER.info("Context was destroyed.");
    }

}