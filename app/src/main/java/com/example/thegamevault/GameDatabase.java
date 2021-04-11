package com.example.thegamevault;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.thegamevault.pojo.Game;

import java.util.ArrayList;

public class GameDatabase  extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    //Name of the database
    public static final String DATABASE_NAME = "gamedatabase";

    //Name of table
    public static final String TABLE_GAMES = "Games";

    //Column names
    public static final String COLUMN_ID = "id";

    //The table
    public static final String COLUMN_NAME = "name"; //Game name
    public static final String COLUMN_IMAGE = "image"; //Image url
    public static final String COLUMN_DESCRIPTION = "description"; //Game description
    public static final String COLUMN_DEVELOPER = "developer"; //Game developer
    public static final String COLUMN_RATING = "rating"; //Game rating

    //Create table
    public static final String CREATE_GAMES_TABLE = "CREATE TABLE " +
            TABLE_GAMES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_NAME + " TEXT, " + COLUMN_IMAGE + " TEXT, " +
            COLUMN_DESCRIPTION + " TEXT, " + COLUMN_DEVELOPER + " TEXT, " +
            COLUMN_RATING + " DOUBLE)";

    public GameDatabase(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_GAMES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*
    THE CRUD
     */

    //Add a new game to the database
    public void addGame(Game game){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, game.getName());
        values.put(COLUMN_IMAGE, game.getImage());
        values.put(COLUMN_RATING, game.getRating());

        db.insert(TABLE_GAMES, null, values);
        db.close();
    }

    //Get a game from the database
    public Game getGame(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Game game = null;
        Cursor cursor = db.query(TABLE_GAMES, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_IMAGE, COLUMN_DESCRIPTION, COLUMN_DEVELOPER, COLUMN_RATING}, COLUMN_ID + "= ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()){
            game = new Game(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
        }
        db.close();
        return game;
    }

    //Get all of the games from the database
    public ArrayList<Game> getAllGames(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GAMES, null);
        ArrayList<Game> games = new ArrayList<>();
        while (cursor.moveToNext()){
            games.add(new Game(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            ));
        }
        db.close();
        return games;
    }

    //Update the games in the database
    public int updateGame(Game game){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, game.getName());
        values.put(COLUMN_IMAGE, game.getImage());
        values.put(COLUMN_RATING, game.getRating());

        return db.update(TABLE_GAMES, values, COLUMN_ID + "=?",
                new String[]{String.valueOf(game.getId())});
    }

    //Delete the game
    public void deleteGame(int game){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GAMES, COLUMN_ID + "=?", new String[]{String.valueOf(game)});
        db.close();
    }

}

