package it.polimi.awt.images.entity;

import it.polimi.awt.images.ImageProvider;
import org.apache.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.net.URI;
import java.net.URISyntaxException;

@Entity
@Table(name = "images")
public class Image {

    private static final Logger logger = Logger.getLogger(Image.class);

    @Id
    private String id;
    private String title;
    private String provider;
    private String server;
    private int farm;
    private String secret;
    private double longitude;
    private double latitude;
    private String uri;

    @Transient
    private final String FILE_FORMAT = ".jpg";
    @Transient
    private final String PROTOCOL_URI = "https://";
    @Transient
    private final String FARM_URI = "farm";
    @Transient
    private final String STATIC_FLICKR_URI = ".staticflickr.com/";
    @Transient
    private final String MEDIUM_SIZE = "m";
    @Transient
    private final String URI_SEPARATOR = "/";
    @Transient
    private final String IDS_SEPARATOR = "_";

    public Image() {
    }

    public Image(String id, String title, String secret, String server, ImageProvider provider, int farm, double longitude, double latitude) {
        this.id = id;
        this.title = title;
        this.secret = secret;
        this.server = server;
        this.provider = provider.name();
        this.farm = farm;
        this.longitude = longitude;
        this.latitude = latitude;
        createImageURI();
    }

    public void createImageURI() {
        StringBuilder auxUri = new StringBuilder();
        auxUri.append(PROTOCOL_URI);
        auxUri.append(FARM_URI + farm);
        auxUri.append(STATIC_FLICKR_URI);
        auxUri.append(server + URI_SEPARATOR);
        auxUri.append(id + IDS_SEPARATOR);
        auxUri.append(secret + IDS_SEPARATOR);
        auxUri.append(MEDIUM_SIZE);
        auxUri.append(FILE_FORMAT);
        uri = auxUri.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}