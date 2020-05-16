package ro.pub.cs.systems.eim.PracticalTestV2.model;

public class Information {

    private String countryName;
    private String continentName;
    private String countryCode;
    private Double latitude;
    private Double longitude;
    private static final String urlStart = "https://www.countryflags.io/";
    private static final String urlEnd = "/flat/64.png";

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
                ", " + longitude.toString() + ", " + urlStart + countryCode + urlEnd;
    }
}
