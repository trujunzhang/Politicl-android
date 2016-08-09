package com.politicl.feed.aggregated;


public class RestQueryPara {
    private int currentPageNumber = 1;
    private int totalPages;

    private int category_id;

    public RestQueryPara(int category_id) {
        this.category_id = category_id;
    }

    public RestQueryPara() {
    }


    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCategory_id() {
        return category_id;
    }
}
