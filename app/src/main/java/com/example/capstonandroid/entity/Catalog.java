package com.example.capstonandroid.entity;

public class Catalog {
    private String catalogID;
    private String catalogName;
    private String catalogDescription;
    private String catalogImage;
    private boolean isDeleted;

    public Catalog(String catalogName, String catalogDescription, String catalogImage) {
        this.catalogName = catalogName;
        this.catalogDescription = catalogDescription;
        this.catalogImage = catalogImage;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getCatalogImage() {
        return catalogImage;
    }

    public void setCatalogImage(String catalogImage) {
        this.catalogImage = catalogImage;
    }

    public Catalog() {
    }

    public String getCatalogID() {
        return catalogID;
    }

    public void setCatalogID(String catalogID) {
        this.catalogID = catalogID;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalogDescription() {
        return catalogDescription;
    }

    public void setCatalogDescription(String catalogDescription) {
        this.catalogDescription = catalogDescription;
    }
}
