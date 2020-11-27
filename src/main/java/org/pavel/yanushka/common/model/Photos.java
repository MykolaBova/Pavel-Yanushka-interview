package org.pavel.yanushka.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Photos implements Serializable {
    int height;
    int width;
    @JsonProperty("html_attributions")
    List<String> htmlAttributions;
    String photoReference;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }


    public static final class PhotosBuilder {
        int height;
        int width;
        List<String> htmlAttributions;
        String photoReference;

        private PhotosBuilder() {
        }

        public static PhotosBuilder aPhotos() {
            return new PhotosBuilder();
        }

        public PhotosBuilder height(int height) {
            this.height = height;
            return this;
        }

        public PhotosBuilder width(int width) {
            this.width = width;
            return this;
        }

        public PhotosBuilder htmlAttributions(List<String> htmlAttributions) {
            this.htmlAttributions = htmlAttributions;
            return this;
        }

        public PhotosBuilder photoReference(String photoReference) {
            this.photoReference = photoReference;
            return this;
        }

        public Photos build() {
            Photos photos = new Photos();
            photos.setHeight(height);
            photos.setWidth(width);
            photos.setHtmlAttributions(htmlAttributions);
            photos.setPhotoReference(photoReference);
            return photos;
        }
    }
}
