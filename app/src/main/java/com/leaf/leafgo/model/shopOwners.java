package com.leaf.leafgo.model;

public class shopOwners {
    private String shopName;
    private String shopEmail;
    private String shopNumber;
    private String shopAddress;
    private String latitudeLocation;
    private String longitudeLocation;
    private String imageId;

    public shopOwners() {
    }


    public shopOwners(String shopName,String shopEmail, String shopNumber, String shopAddress, String latitudeLocation, String longitudeLocation,String imageId) {
        this.shopName = shopName;
        this.shopEmail = shopEmail;
        this.shopNumber = shopNumber;
        this.shopAddress = shopAddress;
        this.latitudeLocation = latitudeLocation;
        this.longitudeLocation = longitudeLocation;
        this.imageId=imageId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopEmail() { return shopEmail; }

    public void setShopEmail(String shopEmail) { this.shopEmail = shopEmail; }

    public String getShopNumber() {
        return shopNumber;
    }

    public void setShopNumber(String shopNumber) {
        this.shopNumber = shopNumber;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getLatitudeLocation() {
        return latitudeLocation;
    }

    public void setLatitudeLocation(String latitudeLocation) {
        this.latitudeLocation = latitudeLocation;
    }

    public String getLongitudeLocation() {
        return longitudeLocation;
    }

    public void setLongitudeLocation(String longitudeLocation) {
        this.longitudeLocation = longitudeLocation;
    }
}
