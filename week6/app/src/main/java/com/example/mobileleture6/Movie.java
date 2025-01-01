package com.example.mobileleture6;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie  implements Parcelable , Serializable {
    private String name;
    private String director;
    private int year;
    private List<String> stars = new ArrayList<>();
    private String description;

    public Movie(String name, String director, int year, List<String> stars, String description) {
        this.name = name;
        this.director = director;
        this.year = year;
        this.stars = stars;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDirector() {
        return director;
    }

    public int getYear() {
        return year;
    }

    public List<String> getStars() {
        return stars;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public void setStars(List<String> stars) {
        this.stars = stars;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.director);
        dest.writeInt(this.year);
        dest.writeStringList(this.stars);
        dest.writeString(this.description);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
        this.director = source.readString();
        this.year = source.readInt();
        this.stars = source.createStringArrayList();
        this.description = source.readString();
    }

    protected Movie(Parcel in) {
        this.name = in.readString();
        this.director = in.readString();
        this.year = in.readInt();
        this.stars = in.createStringArrayList();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
