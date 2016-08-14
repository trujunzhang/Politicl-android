package com.politicl.feed.aggregated;


import android.os.Parcel;
import android.os.Parcelable;

public class RestQueryPara implements Parcelable {
    private boolean allArticles;
    private int currentPageNumber;
    private int totalPages;
    private int category_id;
    private String title;
    private boolean shouldShowDialog;

    public RestQueryPara(String title) {
        currentPageNumber = 1;
        totalPages = 1;
        allArticles = true;
        this.title = title;
        shouldShowDialog = true;
    }

    public RestQueryPara(int category_id, String title) {
        this(title);
        this.category_id = category_id;
        allArticles = false;
    }

    public static final Creator<RestQueryPara> CREATOR = new Creator<RestQueryPara>() {
        @Override
        public RestQueryPara createFromParcel(Parcel in) {
            return new RestQueryPara(in);
        }

        @Override
        public RestQueryPara[] newArray(int size) {
            return new RestQueryPara[size];
        }
    };

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCategory_id() {
        return category_id;
    }

    private RestQueryPara(Parcel in) {
        allArticles = in.readInt() == 1;
        currentPageNumber = in.readInt();
        totalPages = in.readInt();
        category_id = in.readInt();
        title = in.readString();
        shouldShowDialog = in.readInt() == 1;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(allArticles ? 1 : 0);
        parcel.writeInt(currentPageNumber);
        parcel.writeInt(totalPages);
        parcel.writeInt(category_id);
        parcel.writeString(title);
        parcel.writeInt(shouldShowDialog ? 1 : 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void reset() {
        shouldShowDialog = true;
        currentPageNumber = 1;
    }

    public void nextPagination() {
        shouldShowDialog = false;
        currentPageNumber += 1;
    }

    public boolean shouldShowDialog() {
        return shouldShowDialog;
    }

    public boolean isAllArticles() {
        return allArticles;
    }

    public String getTitle() {
        return title;
    }
}
