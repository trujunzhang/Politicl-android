package com.politicl;

public final class Constants {
    // Keep loader IDs unique to each loader. If the loader specified by the ID already exists, the
    // last created loader is reused.
    public static final int HISTORY_FRAGMENT_LOADER_ID = 100;
    public static final int RECENT_SEARCHES_FRAGMENT_LOADER_ID = 101;
    public static final int USER_OPTION_ROW_FRAGMENT_LOADER_ID = 102;

    public static final String POLITICL_URL = "http://www.politicl.com/";

    public static final int ACTIVITY_REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 44;

    public static final int MAX_SUGGESTION_RESULTS = 3;
    public static final int SUGGESTION_REQUEST_ITEMS = 5;

    public static final int PREFERRED_THUMB_SIZE = 320;

    private Constants() {
    }
}
