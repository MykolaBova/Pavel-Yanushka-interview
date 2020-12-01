package org.pavel.yanushka.common.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Photos implements Serializable {
    private int height;
    private int width;
    @JsonProperty("html_attributions")
    private List<String> htmlAttributions;
    private String photoReference;
    private String photo;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public static final class PhotosBuilder {
        private int height;
        private int width;
        private List<String> htmlAttributions;
        private String photoReference;
        private String photo;

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

        public PhotosBuilder photo(String photo) {
            this.photo = photo;
            return this;
        }

        public Photos build() {
            Photos photos = new Photos();
            photos.setHeight(height);
            photos.setWidth(width);
            photos.setHtmlAttributions(htmlAttributions);
            photos.setPhotoReference(photoReference);
            photos.setPhoto(photo);
            return photos;
        }
    }
}
