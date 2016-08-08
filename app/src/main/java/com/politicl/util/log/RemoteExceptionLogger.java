package com.politicl.util.log;

import android.support.annotation.NonNull;

public interface RemoteExceptionLogger {
    void log(@NonNull Throwable throwable);
}