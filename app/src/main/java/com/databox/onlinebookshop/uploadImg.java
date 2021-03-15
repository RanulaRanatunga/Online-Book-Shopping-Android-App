package com.databox.onlinebookshop;

import android.widget.EditText;

public class uploadImg {

    public String imageName;
    public String  imgUrl;
    public uploadImg(){}

    public uploadImg(String imageName, String imgUrl) {
        this.imageName = imageName;
        this.imgUrl = imgUrl;
    }

    public uploadImg(EditText bookName) {
    }

    public uploadImg(String trim) {
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
