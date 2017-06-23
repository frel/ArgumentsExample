package com.subgarden.arguments.arguments;

import android.os.Bundle;

/**
 * @author Fredrik Larsen <fredrik@subgarden.com>
 */

public class DataArguments extends Arguments {

    public static final String KEY_DATA = "data";

    private final String mData;

    public DataArguments(String query) {
        mData = query;
    }

    public DataArguments(Bundle bundle) {
        mData = bundle.getString(KEY_DATA);
    }

    public String getData() {
        return mData;
    }


    public Bundle makeBundle() {
        Bundle bundle = new Bundle();
        if (mData != null) {
            bundle.putString(KEY_DATA, mData);
        }
        return bundle;
    }

    @Override
    public String toString() {
        return "DataArguments{" +
               "mData='" + mData + '\'' +
               '}';
    }
}
