package com.databox.onlinebookshop;

public class Books {
   // String bookImage;
    String imgURL;
    String bookName;
    String bookCategory;
    String bookISBN;
    String bookPrice;

    public Books() {}

    public Books(String imgURL, String bookName, String bookCategory, String bookISBN, String bookPrice) {
        this.imgURL = imgURL;
        this.bookName = bookName;
        this.bookCategory = bookCategory;
        this.bookISBN = bookISBN;
        this.bookPrice = bookPrice;
    }

    public Books(String trim, String toString) {
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Books(String toString) {
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }
}


