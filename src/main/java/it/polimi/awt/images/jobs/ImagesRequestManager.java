package it.polimi.awt.images.jobs;

import it.polimi.awt.images.APIRequestException;
import it.polimi.awt.images.ImageProvider;
import it.polimi.awt.images.converter.JSONConverter;
import it.polimi.awt.images.dao.ImageDao;
import it.polimi.awt.images.entity.Image;
import it.polimi.awt.images.service.ImageService;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ImagesRequestManager {

    private ImageService flickrImageService;
    private JSONConverter jsonConverter;
    private ImageDao imageDao;

    public ImagesRequestManager(ImageService flickrImageService, JSONConverter jsonConverter, ImageDao imageDao) {
        this.flickrImageService = flickrImageService;
        this.jsonConverter = jsonConverter;
        this.imageDao = imageDao;
    }

    public void checkImages() {
        try {
            String firstResponse = flickrImageService.getImages();

            // Save data retrieved from image provider
            List<Image> firstImageList = jsonConverter.convertJSON(firstResponse, ImageProvider.FLICKR);
            for (Image image : firstImageList) {
                imageDao.saveImage(image);
            }

            // Extract total number of pages and first page number
            JSONObject jsonFirstResponse = new JSONObject(firstResponse);

            // But first extract JSON parent document
            JSONObject parent = jsonFirstResponse.getJSONObject("photos");
            int totalPages = parent.getInt("pages");
            int currentPage = parent.getInt("page");

            for (int i = 0; i < totalPages; i++) {
                int nextPage = currentPage + 1;
                flickrImageService.setPage(nextPage);
                String response = flickrImageService.getImages();

                // Save data retrieved from image provider
                List<Image> imageList = jsonConverter.convertJSON(response, ImageProvider.FLICKR);
                for (Image image : imageList) {
                    imageDao.saveImage(image);
                }

                // Advance page
                currentPage = nextPage;
            }

        } catch (APIRequestException | JSONException e) {
            e.printStackTrace();
        }
    }
}
