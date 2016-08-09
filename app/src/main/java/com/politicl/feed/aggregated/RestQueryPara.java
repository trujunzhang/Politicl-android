package com.politicl.feed.aggregated;


import android.os.Parcel;
import android.os.Parcelable;

public class RestQueryPara implements Parcelable {
    private boolean allArticles;
    private int currentPageNumber;
    private int totalPages;
    private int category_id;

    public RestQueryPara() {
        currentPageNumber = 1;
        totalPages = 1;
        allArticles = true;
    }

    public RestQueryPara(int category_id) {
        this();
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
        currentPageNumber = in.readInt();
        totalPages = in.readInt();
        category_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(currentPageNumber);
        parcel.writeInt(totalPages);
        parcel.writeInt(category_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void reset() {
        currentPageNumber = 1;
    }

    public void nextPagination() {
        currentPageNumber += 1;
    }

    public boolean isAllArticles() {
        return allArticles;
    }
}
