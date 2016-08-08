package com.politicl.feed.model;

import android.support.annotation.NonNull;

public class AuthorItem {

    @SuppressWarnings("unused,NullableProblems") @NonNull private int id;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String slug;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String name;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String first_name;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String last_name;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String nickname;

    @SuppressWarnings("unused,NullableProblems") @NonNull private String url;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String description;

    @NonNull
    public String author() {
        return nickname;
    }

}
