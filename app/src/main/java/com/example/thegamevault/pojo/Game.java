package com.example.thegamevault.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {

    private int id;
    private String name;
    private String image;
    private String released;
    private String rating;

    //Create the game object to insert
    public Game(String name, String description, String image, String developer, String released , String rating){
        this.name = name;
        this.image = image;
        this.released = released;
        this.rating = rating;
    }

    //Create game object to read
    public Game(int id,String name, String description, String image,String released , String developer, String rating){
        this.id = id;
        this.name = name;
        this.image = image;
        this.rating = rating;
    }

    protected Game(Parcel in){
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        released = in.readString();
        rating = in.readString();
    }

    public Game(){

    }

    public Game(String name, String released, String metacritic, String background_image) {
        this.name = name;
        this.released = released;
        this.rating = metacritic;
        this.image = background_image;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeString(rating);
        parcel.writeString(released);
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

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return name;
    }
}
