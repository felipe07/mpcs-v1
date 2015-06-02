package it.polimi.awt.dashboard.ui.gallery;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;
import com.vaadin.ui.Image;
import it.polimi.awt.AppServices;

import java.util.List;

public class SimpleImageGallery extends GridLayout {

    public SimpleImageGallery() {
        createLayout();
    }

    public void createLayout() {
        removeAllComponents();
        setRows(30);
        setColumns(3);
        setSizeFull();

        List<it.polimi.awt.images.entity.Image> images = AppServices.getImageDaoService().getImages();

        int index = 0;
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                it.polimi.awt.images.entity.Image flickrImage = images.get(index);
                Resource resource = new ExternalResource(flickrImage.getUri());
                Image image = new Image(flickrImage.getTitle(), resource);
                addComponent(image);
                setComponentAlignment(image, Alignment.MIDDLE_CENTER);
                setRowExpandRatio(row, 1.0f);
                setColumnExpandRatio(col, 1.0f);
                index++;
            }
        }
    }
}