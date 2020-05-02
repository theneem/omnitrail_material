package com.thenneem.omnitrail.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Book  implements Serializable {


    @SerializedName("BookID")
    private int BookID;


    @SerializedName("ReligionName")
    private String ReligionName;


    @SerializedName("BookName")
    private String BookName;

    @SerializedName("BookDesc")
    private String BookDesc;

    @SerializedName("Author")
    private String Author;

    @SerializedName("BookImg")
    private String BookImg;


    public Book      (int BookID,String ReligionName,String BookName,String BookDesc,String Author, String BookImg)
    {

        this.BookID = BookID;
        this.ReligionName = ReligionName;
        this.BookName = BookName;
        this.BookDesc = BookDesc;
        this.Author = Author;
        this.BookImg = BookImg;


    }

    public int getBookID() {
        return BookID;
    }

    public String getReligionName() {
        return ReligionName;
    }

    public String getBookName() {
        return BookName;
    }

    public String getBookDesc() {
        return BookDesc;
    }

    public String getAuthor() {
        return Author;
    }

    public String getBookImg() {
        return BookImg;
    }
}
