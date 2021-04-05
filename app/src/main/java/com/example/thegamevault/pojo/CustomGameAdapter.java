package com.example.thegamevault.pojo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.thegamevault.API.GameSingleton;
import com.example.thegamevault.GameDatabase;
import com.example.thegamevault.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomGameAdapter extends RecyclerView.Adapter<CustomGameAdapter.CustomGameViewHolder> {

    private ArrayList<Game> Games;
    private Context context;

    public CustomGameAdapter(ArrayList<Game> games, Context context){
        this.Games = games;
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
        Game game = Games.get(position);

        holder.name.setText(game.getName());
        holder.description.setText(game.getDescription());
        holder.gameImage.setImageResource(Integer.valueOf(game.getImage()));
        holder.developer.setText(game.getDeveloper());
        holder.rating.setText(String.valueOf(game.getRating()));
        String url = "https://api.rawg.io/api/games/{29c026ab8a7e414fb51447219aaa3397}";


        GameDatabase gameDatabase = new GameDatabase(context);
        if (gameDatabase.getAllGames() == null){
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject mainObject = response.getJSONObject("main");
                                game.setName(mainObject.getString("name"));
                                game.setDescription(mainObject.getString("description"));
                                game.setDeveloper(mainObject.getString("released"));
                                game.setRating(mainObject.getDouble("metacritic"));
                                game.setImage(mainObject.getString("background_image"));

                                GameDatabase db = new GameDatabase(context);
                                db.updateGame(game);
                                Log.d("UPDATE", game.getName() + " NAME UPDATED");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("VOLLEY_ERROR", error.getLocalizedMessage());
                        }
                    });
            GameSingleton.getInstance(context).getRequestQueue().add(request);
        }
    }

    @Override
    public int getItemCount() {
        return Games.size();
    }

    class CustomGameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView name;
        protected TextView description;
        protected ImageView gameImage;
        protected TextView rating;
        protected TextView developer;
        protected ImageView saveGame;

        public CustomGameViewHolder(@NonNull View view){
            super(view);
            this.name = view.findViewById(R.id.gameName);
            this.gameImage = view.findViewById(R.id.gameImage);
            this.description = view.findViewById(R.id.description);
            this.rating = view.findViewById(R.id.rating);
            this.developer = view.findViewById(R.id.developer);
            this.saveGame = view.findViewById(R.id.saveGame);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            new AlertDialog.Builder(context).setTitle("Delete").setMessage("Are you sure you would like to delete this game?").setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            GameDatabase db = new GameDatabase(context);
                            db.deleteGame(Games.get(getLayoutPosition()).getId());
                            Games.remove(getLayoutPosition());
                            notifyItemRemoved(getAdapterPosition());
                            db.close();
                        }
                    })
                    .setNegativeButton("No", null).show();
        }
    }
}
