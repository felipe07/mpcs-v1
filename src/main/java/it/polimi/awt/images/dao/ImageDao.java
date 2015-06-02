package it.polimi.awt.images.dao;

import it.polimi.awt.images.entity.Image;

import java.util.List;

public interface ImageDao {

    void saveImage(Image image);

    List<Image> getImages();
}
