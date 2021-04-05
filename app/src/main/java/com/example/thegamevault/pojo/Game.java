package com.example.thegamevault.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {

    private int id;
    private String name;
    private String image;
    private String description;
    private String developer;
    private double rating;

    //Create the game object to insert
    public Game(String name, String description, String image, String developer, double rating){
        this.name = name;
        this.image = image;
        this.description = description;
        this.developer = developer;
        this.rating = rating;
    }

    //Create game object to read
    public Game(int id,String name, String description, String image, String developer, double rating){
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.developer = developer;
        this.rating = rating;
    }

    protected Game(Parcel in){
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        description = in.readString();
        developer = in.readString();
        rating = in.readDouble();
    }

    public Game(){

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeString(description);
        parcel.writeString(developer);
        parcel.writeDouble(rating);
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel parcel) {
            return new Game(parcel);
        }

        @Override
        public Game[] newArray(int i) {
            return new Game[i];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return name;
    }
}
