package com.databox.onlinebookshop;

public class BookListAdapter {

    // model
    private String bookName;
    private String bookImage;
    private String bookPrice;

    // constructor
    public BookListAdapter() {

    }
    public BookListAdapter(String bookName, String bookImage, String bookPrice) {
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.bookPrice = bookPrice;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }
}
