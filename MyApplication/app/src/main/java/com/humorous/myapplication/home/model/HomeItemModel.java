package com.humorous.myapplication.home.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangzhiquan on 2017/6/5.
 */

public class HomeItemModel implements Parcelable{
    private String title;
    private String link;

    public HomeItemModel(){

    }

    protected HomeItemModel(Parcel in) {
        title = in.readString();
        link = in.readString();
    }

    public static final Creator<HomeItemModel> CREATOR = new Creator<HomeItemModel>() {
        @Override
        public HomeItemModel createFromParcel(Parcel in) {
            return new HomeItemModel(in);
        }

        @Override
        public HomeItemModel[] newArray(int size) {
            return new HomeItemModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("title:"+title+"\n");
        stringBuilder.append("link:"+link+"\n");
        return stringBuilder.toString();
    }
}
