package org.yoneco.ict.tithandizane.Class;

/**
 * Created by Moses on 9/7/2017.
 */
public class ServiceProvider {
    private String district;
    private String organization;
    private String focalPerson;
    private String contact;
    private String email;

    private String lat;
    private String longt;
    private String Category;


    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongt() {
        return longt;
    }

    public void setLongt(String longt) {
        this.longt = longt;
    }

    public String getDistrict() {
        return district;
    }

    public void setTitle(String district) {
        this.district = district;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getFocalPerson() {
        return focalPerson;
    }

    public void setFocalPerson(String focalPerson) {
        this.focalPerson = focalPerson;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
