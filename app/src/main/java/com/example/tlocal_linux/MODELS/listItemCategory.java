package com.example.tlocal_linux.MODELS;

public class listItemCategory {
    private String _id;
    private String nameLocal;
    private String description;
    private String dirImage;

    public listItemCategory() {

    }

    public String getDirImage() {
        return dirImage;
    }

    public void setDirImage(String dirImage) {
        this.dirImage = dirImage;
    }

    public listItemCategory(String _id, String nameLocal, String description, String dirImage) {
        this._id = _id;
        this.nameLocal = nameLocal;
        this.description = description;
        this.dirImage = dirImage;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNameLocal() {
        return nameLocal;
    }

    public void setNameLocal(String nameLocal) {
        this.nameLocal = nameLocal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
