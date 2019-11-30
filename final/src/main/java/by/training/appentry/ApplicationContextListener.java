package by.training.appentry;

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
        ApplicationLoader.getInstance().init();
        LOGGER.info("Context was initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ApplicationLoader.getInstance().destroy();
        LOGGER.info("Context was destroyed.");
    }

}