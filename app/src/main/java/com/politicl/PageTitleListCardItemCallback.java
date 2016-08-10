package com.politicl;

import android.support.annotation.NonNull;

import com.politicl.history.HistoryEntry;

public interface PageTitleListCardItemCallback {
    void onSelectPage(@NonNull HistoryEntry entry);
    void onAddPageToList(@NonNull HistoryEntry entry);
    void onSharePage(@NonNull HistoryEntry entry);
}
