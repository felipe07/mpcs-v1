package it.polimi.awt.images.dao.impl;

import it.polimi.awt.images.dao.ImageDao;
import it.polimi.awt.images.entity.Image;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ImageDaoImpl implements ImageDao {

    private SessionFactory sessionFactory;

    public ImageDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveImage(Image image) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(image);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Image> getImages() {
        Session session = sessionFactory.openSession();
        List<Image> images = session.createCriteria(Image.class).setMaxResults(100).list();
        return images;
    }
}
