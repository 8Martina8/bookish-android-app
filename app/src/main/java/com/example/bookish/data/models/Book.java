package com.example.bookish.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

//@Entity
public class Book {
    //@PrimaryKey(autoGenerate = false)
    //@NonNull
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

    public static class VolumeInfo {
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


        public String getTitle() {
            return title;
        }

        public List<String> getAuthors() {
            return authors;
        }

        public String getPublisher() {
            return publisher;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public String getDescription() {
            return description;
        }

        public int getPageCount() {
            return pageCount;
        }

        public String getMainCategory() {
            return mainCategory;
        }

        public List<String> getCategories() {
            return categories;
        }

        public double getAverageRating() {
            return averageRating;
        }

        public int getRatingsCount() {
            return ratingsCount;
        }

        public ImageLinks getImageLinks() {
            return imageLinks;
        }

        public String getLanguage() {
            return language;
        }
    }

    public static class SaleInfo {
        private String country;
        private String saleability;
        private boolean isEbook;
        private Price listPrice;
        private Price retailPrice;
        private String buyLink;

    }

    public static class AccessInfo {
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

    public static class ImageLinks {
        private String smallThumbnail;
        private String thumbnail;
        private String small;
        private String medium;
        private String large;
        private String extraLarge;
    }

    public static class Price {
        private double amount;
        private String currencyCode;

    }

    public static class Epub {
        private boolean isAvailable;
        //private String acsTokenLink;

    }

    public static class Pdf{
        private boolean isAvailable;

    }
}
