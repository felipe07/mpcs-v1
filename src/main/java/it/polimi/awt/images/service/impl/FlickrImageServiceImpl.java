package it.polimi.awt.images.service.impl;

import it.polimi.awt.images.APIRequestException;
import it.polimi.awt.images.service.ImageService;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;

@Service
public class FlickrImageServiceImpl implements ImageService {

    private static final Logger logger = Logger.getLogger(FlickrImageServiceImpl.class);

    private String url;
    private String apiKey;
    private String secret;
    private String format;
    private int page;

    public FlickrImageServiceImpl() {}

    public FlickrImageServiceImpl(String url, String apiKey, String secret, String format) {
        this.url = url;
        this.apiKey = apiKey;
        this.secret = secret;
        this.format = format;
    }

    @Override
    public String getImages() throws APIRequestException {
        try {
            URI query = buildQuery();
            logger.info("Trying to retrieve photos from: " + url);
            URLConnection curl = query.toURL().openConnection();
            curl.setConnectTimeout(185000);
            curl.setReadTimeout(180000);

            InputStream inputStream = curl.getInputStream();
            String response = IOUtils.toString(inputStream, "UTF-8");
            inputStream.close();
            logger.info("Flickr response: " + response);
            return response;
        } catch (IOException e) {
            throw new APIRequestException(e.toString(), e);
        }
    }

    @Override
    public URI buildQuery() throws APIRequestException {
        try {
            StringBuilder auxUri = new StringBuilder();
            auxUri.append(url);
            auxUri.append("&api_key=" + apiKey);
            auxUri.append("&tags=mountain"); // With mountain tag
            auxUri.append("&text=mountain"); // With text containing mountain
            auxUri.append("&sort=date-posted-desc"); // Descending posted order
            auxUri.append("&privacy_filter=1"); // Public photos
            auxUri.append("&accuracy=1"); // World level
            auxUri.append("&content_type=1"); // Only photos
            auxUri.append("&media=photos"); // Media photos
            auxUri.append("&format=" + format); // Json format
            auxUri.append("&extras=geo"); // Return coordinates (latitude and longitude)
            auxUri.append("&page=" + (page == 0 ? 1 : page)); // Page to return
            auxUri.append("&nojsoncallback=1");
            String uriString = auxUri.toString();
            logger.info("Flickr api query: " + uriString);
            return new URI(uriString);
        } catch (URISyntaxException e) {
            throw new APIRequestException(e.toString(), e);
        }
    }

    @Override
    public void setPage(int page) {
        this.page = page;
    }
}