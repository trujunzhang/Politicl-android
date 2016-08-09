package com.politicl.feed.aggregated;


import android.os.Parcel;
import android.os.Parcelable;

public class RestQueryPara implements Parcelable {
    private int currentPageNumber = 1;
    private int totalPages;
    private int category_id;

    public RestQueryPara() {
    }

    public RestQueryPara(int category_id) {
        this.category_id = category_id;
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
}
