package com.example.tickerlistv2;

import java.io.Serializable;

public class CompanyProfile implements Serializable {

    private String companyName;
    private String exchange;
    private String description;
    private String ipoDate;
    private String industry;
    private String sector;
    private String ceo;
    private String website;
    private String price;
    private String beta;
    private String volAvg;
    private String mktCap;
    private String lastDiv;
    private String changes; // get from api/v3/quote/
    private String range;
    private String image;
    private String changesPercentage; // get from api/v3/quote


    public CompanyProfile()
    {}

    public CompanyProfile(String companyName, String price, String beta, String volAvg, String mktCap, String lastDiv,
                          String range, String changes, String exchange, String industry, String website,
                          String description, String ceo, String sector, String image, String ipoDate, String changesPercentage) {
        this.companyName = companyName;
        this.price = price;
        this.beta = beta;
        this.volAvg = volAvg;
        this.mktCap = mktCap;
        this.lastDiv = lastDiv;
        this.range = range;
        this.changes = changes;
        this.exchange = exchange;
        this.industry = industry;
        this.website = website;
        this.description = description;
        this.ceo = ceo;
        this.sector = sector;
        this.image = image;
        this.ipoDate = ipoDate;
        this.changesPercentage = changesPercentage;
    }

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public String getChangesPercentage() {
        return changesPercentage;
    }

    public void setChangesPercentage(String changesPercentage) {
        this.changesPercentage = changesPercentage;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMktCap() {
        return mktCap;
    }

    public void setMktCap(String mktCap) {
        this.mktCap = mktCap;
    }

    public String getLastDiv() {
        return lastDiv;
    }

    public void setLastDiv(String lastDiv) {
        this.lastDiv = lastDiv;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpoDate() {
        return ipoDate;
    }

    public void setIpoDate(String ipoDate) {
        this.ipoDate = ipoDate;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public String getBeta() {
        return beta;
    }

    public void setBeta(String beta) {
        this.beta = beta;
    }

    public String getVolAvg() {
        return volAvg;
    }

    public void setVolAvg(String volAvg) {
        this.volAvg = volAvg;
    }

}
