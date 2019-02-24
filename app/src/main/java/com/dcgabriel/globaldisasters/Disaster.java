package com.dcgabriel.globaldisasters;

public class Disaster {

    private String name;
    private String type;
    private String date;
    private String url;
    private String countries;
    private String status;

    public Disaster(String name, String type, String countries, String date, String status, String url) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.countries = countries;
        this.status = status;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
