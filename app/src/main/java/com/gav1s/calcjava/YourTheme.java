package com.gav1s.calcjava;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.TypedValue;

public class YourTheme extends TypedValue implements Parcelable {
    private String name;

    public YourTheme() {
    }

    public YourTheme(String name) {
        setName(name);
    }

    public YourTheme(Parcel in) {
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public static final Creator<YourTheme> CREATOR = new Creator<YourTheme>() {
        @Override
        public YourTheme createFromParcel(Parcel in) {
            return new YourTheme(in);
        }

        @Override
        public YourTheme[] newArray(int size) {
            return new YourTheme[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
