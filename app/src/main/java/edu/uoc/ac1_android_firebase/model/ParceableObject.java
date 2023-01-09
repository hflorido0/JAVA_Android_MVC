package edu.uoc.ac1_android_firebase.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ParceableObject implements Parcelable {
    protected ParceableObject(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParceableObject> CREATOR = new Creator<ParceableObject>() {
        @Override
        public ParceableObject createFromParcel(Parcel in) {
            return new ParceableObject(in);
        }

        @Override
        public ParceableObject[] newArray(int size) {
            return new ParceableObject[size];
        }
    };
}
