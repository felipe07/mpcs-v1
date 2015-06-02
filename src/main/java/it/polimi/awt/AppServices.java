package it.polimi.awt;

import it.polimi.awt.images.dao.ImageDao;
import it.polimi.awt.images.service.ImageService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppServices implements ServletContextListener {

    private static ApplicationContext context;
    private static AppServices appServices;

    private static final String IMAGE_DAO_SERVICE = "imageDao";
    private static final String FLICKR_IMAGES_SERVICE = "flickrImagesService";

    /**
     * Creates an instance of AppServices in case it doesn't exists
     *
     * @return static instance of AppService
     */
    public static AppServices getInstance() {
        if (appServices == null) {
            appServices = new AppServices();
        }
        return appServices;
    }

    private static Object getService(String service) {
        return AppServices.getInstance().context.getBean(service);
    }

    public static ImageDao getImageDaoService() {
        return (ImageDao) AppServices.getInstance().context.getBean(IMAGE_DAO_SERVICE);
    }

    public static ImageService getFlickrImagesService() {
        return (ImageService) AppServices.getInstance().context.getBean(FLICKR_IMAGES_SERVICE);
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    }

    /**
     * @param servletContextEvent
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}