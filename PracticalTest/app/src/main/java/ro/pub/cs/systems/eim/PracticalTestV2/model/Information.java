package ro.pub.cs.systems.eim.PracticalTestV2.model;

import android.graphics.Bitmap;

import ro.pub.cs.systems.eim.PracticalTestV2.general.Constants;

public class Information {

    private String countryName;
    private String continentName;
    private String countryCode;
    private Double latitude;
    private Double longitude;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Information() {
        this.countryName = null;
        this.continentName = null;
        this.countryCode = null;
        this.latitude = null;
        this.longitude = null;
    }

    public Information(String countryName, String continentName, String countryCode, Double latitude, Double longitude) {
        this.countryName = countryName;
        this.continentName = continentName;
        this.countryCode = countryCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return countryName + ", " + continentName + ", " + countryCode + ", " + latitude.toString() +
                ", " + longitude.toString() + ", " + Constants.urlStart + countryCode + Constants.urlEnd;
    }
}
