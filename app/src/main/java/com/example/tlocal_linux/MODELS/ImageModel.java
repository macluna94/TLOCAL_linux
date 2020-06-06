package com.example.tlocal_linux.MODELS;

import com.google.gson.annotations.SerializedName;

public class ImageModel {
    @SerializedName("dataImage")
    private String dataImage;
    @SerializedName("contentTypeImage")
    private String contentTypeImage;
    @SerializedName("dirImage")
    private String dirImage;

    public ImageModel(String dataImage, String contentTypeImage, String dirImage) {
        this.dataImage = dataImage;
        this.contentTypeImage = contentTypeImage;
        this.dirImage = dirImage;
    }

    public String getDirImage() {
        return dirImage;
    }

    public void setDirImage(String dirImage) {
        this.dirImage = dirImage;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public String getContentTypeImage() {
        return contentTypeImage;
    }

    public void setContentTypeImage(String contentTypeImage) {
        this.contentTypeImage = contentTypeImage;
    }


}