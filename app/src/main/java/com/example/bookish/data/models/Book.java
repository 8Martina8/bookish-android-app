package com.example.bookish.data.models;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
    private final String id;
    private final VolumeInfo volumeInfo;
    private final SaleInfo saleInfo;
    private final AccessInfo accessInfo;

    public Book(String id, VolumeInfo volumeInfo, SaleInfo saleInfo, AccessInfo accessInfo) {
        this.id = id;
        this.volumeInfo = volumeInfo;
        this.saleInfo = saleInfo;
        this.accessInfo = accessInfo;
    }


    public String getId() {
        return id;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public SaleInfo getSaleInfo() {
        return saleInfo;
    }

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    public static class VolumeInfo implements Serializable {
        private String title;
        private List<String> authors;
        private String publisher;
        private String publishedDate;
        private String description;
        //private List<IndustryIdentifier> industryIdentifiers;
        private int pageCount;
        //private Dimensions dimensions;
        //private String printType;
        private String mainCategory;
        private List<String> categories;
        private double averageRating;
        private int ratingsCount;
        //private String contentVersion;
        private ImageLinks imageLinks;
        private String language;
        //private String infoLink;
        //private String canonicalVolumeLink;

    }

    public static class SaleInfo implements Serializable {
        private String country;
        private String saleability;
        private boolean isEbook;
        private Price listPrice;
        private Price retailPrice;
        private String buyLink;

    }

    public static class AccessInfo implements Serializable {
        private String country;
        private String viewability;
        //private boolean embeddable;
        private boolean publicDomain;
        //private String textToSpeechPermission;
        private Epub epub;
        private Pdf pdf;
        private String accessViewStatus;

    }

//    public static class IndustryIdentifier implements Serializable {
//        private String type;
//        private String identifier;
//
//    }

//    public static class Dimensions implements Serializable {
//        private String height;
//        private String width;
//        private String thickness;
////    }

    public static class ImageLinks implements Serializable {
        private String smallThumbnail;
        private String thumbnail;
        private String small;
        private String medium;
        private String large;
        private String extraLarge;
    }

    public static class Price implements Serializable {
        private double amount;
        private String currencyCode;

    }

    public static class Epub implements Serializable {
        private boolean isAvailable;
        //private String acsTokenLink;

    }

    public static class Pdf implements Serializable {
        private boolean isAvailable;

    }
}
