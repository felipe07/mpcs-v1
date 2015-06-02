package it.polimi.awt.images.service;

import it.polimi.awt.images.APIRequestException;

import java.net.URI;

public interface ImageService {

    String getImages() throws APIRequestException;
    URI buildQuery() throws APIRequestException;
    void setPage(int page);
}