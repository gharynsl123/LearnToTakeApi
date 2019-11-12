package com.gharin.sharedprefence;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {

    private String name;
    private String email;
    private String password;
    private String kelamin;
    private String agrew;

    private boolean isStatusLogin;

    public boolean isStatusLogin() {
        return isStatusLogin;
    }

    public void setStatusLogin(boolean statusLogin) {
        isStatusLogin = statusLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public String getAgrew() {
        return agrew;
    }

    public void setAgrew(String agrew) {
        this.agrew = agrew;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.kelamin);
        dest.writeString(this.agrew);
    }

    public UserModel() {
    }

    protected UserModel(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.kelamin = in.readString();
        this.agrew = in.readString();
    }

    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
