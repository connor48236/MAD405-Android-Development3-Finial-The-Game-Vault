package com.example.thegamevault.pojo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thegamevault.GameDatabase;
import com.example.thegamevault.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomGameAdapter extends RecyclerView.Adapter<CustomGameAdapter.CustomGameViewHolder> {

    private ArrayList<Game> games;
    private Context context;

    public CustomGameAdapter(ArrayList<Game> games, Context context){
        this.games = games;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomGameAdapter.CustomGameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item_view, parent, false);
        return new CustomGameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomGameAdapter.CustomGameViewHolder holder, int position) {
        Game game = games.get(position);

        holder.name.setText(game.getName());
        holder.released.setText(game.getReleased());
        Picasso.get().load(game.getImage()).resize(525, 500).into(holder.gameImage);
        holder.rating.setText(game.getRating());

    }

    @Override
    public int getItemCount() {
        if (games == null){
            return -1;
        }
        return games.size();
    }

    class CustomGameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView name;
        protected ImageView gameImage;
        protected TextView rating;
        protected TextView released;
        protected ImageView saveGame;

        public CustomGameViewHolder(@NonNull View view){
            super(view);

            //Check for the size of the text
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            final String textSize = preferences.getString("font_size", "16");
            int fontSize = Integer.parseInt(textSize);

            this.name = view.findViewById(R.id.gameName);
            this.gameImage = view.findViewById(R.id.savedGameImage);
            this.rating = view.findViewById(R.id.rating);
            this.released = view.findViewById(R.id.developer);
            this.saveGame = view.findViewById(R.id.saveGame);

            name.setTextSize(fontSize + 10);
            rating.setTextSize(fontSize);
            released.setTextSize(fontSize);

            itemView.setOnClickListener(this);
            saveGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context).setTitle("Save Game").setMessage("Would you like to save this game to your Library").setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    GameDatabase db = new GameDatabase(context);
                                    db.addGame(new Game(name.getText().toString(), gameImage.getDrawable().toString(), rating.getText().toString(), released.getText().toString()));
                                    db.close();
                                }
                            })
                            .setNegativeButton("Don't Save", null).show();
                }
            });
        }

        @Override
        public void onClick(View view) {
            new AlertDialog.Builder(context).setTitle("Delete").setMessage("Are you sure you would like to delete this game?").setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            GameDatabase db = new GameDatabase(context);
                            db.deleteGame(games.get(getLayoutPosition()).getId());
                            games.remove(getLayoutPosition());
                            notifyItemRemoved(getAdapterPosition());
                            db.close();
                        }
                    })
                    .setNegativeButton("No", null).show();
        }
    }
}
