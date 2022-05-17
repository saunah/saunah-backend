package ch.saunah.saunahbackend.model;

import java.net.URL;

import org.springframework.lang.Nullable;

/**
 * Wrapper containing a sauna image and the URL where the image can be downloaded.
 */
public class SaunaImageUrl {
    private SaunaImage saunaImage;

    @Nullable
    private URL url;

    /**
     * Create a new object from SaunaImage and the URL where the image
     * can be downloaded.
     * @param saunaImage the object containing image infomation.
     * @param url the URL to the image.
     */
    public SaunaImageUrl(SaunaImage saunaImage, URL url) {
        this.setSaunaImage(saunaImage);
        this.setUrl(url);
    }

    public SaunaImage getSaunaImage() {
        return saunaImage;
    }

    public void setSaunaImage(SaunaImage saunaImage) {
        this.saunaImage = saunaImage;
    }

    @Nullable
    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

}
