package com.example.moviemaniaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    public MovieAdapter(Context ctx, List<MovieModel> data) {
        this.ctx = ctx;
        this.data = data;
    }

    Context ctx;
    List<MovieModel> data;
    int [] resource = {
            R.drawable.shawshank,
            R.drawable.godfather,
            R.drawable.pulpfiction,
            R.drawable.darkknight,
            R.drawable.fightclub,
            R.drawable.inception,
            R.drawable.thematrix,
            R.drawable.forrestgump,
            R.drawable.lordoftherings,
            R.drawable.interstellar,
            R.drawable.avengers,
            R.drawable.lionking,
            R.drawable.thesocialnetwork,
            R.drawable.ingloriousbasterds,
            R.drawable.thedeparted
    };


    public MovieAdapter() {
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public List<MovieModel> getData() {
        return data;
    }

    public void setData(List<MovieModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root=LayoutInflater.from(ctx).inflate(R.layout.item_movie,parent,false);
        MovieHolder holder=new MovieHolder(root);
        holder.setIsRecyclable(false);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        holder.title.setText(data.get(position).getName());
        holder.rate.setText(Double.toString(data.get(position).getRating()));
        holder.img.setImageResource(data.get(position).getPhotoId());
        holder.row.setOnClickListener(v->{
            Intent intent=new Intent(ctx,ActivityDetails.class);
            intent.putExtra("id",data.get(position).getId());
            intent.putExtra("photo",data.get(position).getPhotoId());
            ((Activity)ctx).startActivity(intent);
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{
        ConstraintLayout row;
        ImageView img;
        TextView title;
        TextView rate;
        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            row=itemView.findViewById(R.id.constraintLayout);
            img=itemView.findViewById(R.id.movieImageView);
            title=itemView.findViewById(R.id.movieTitleText);
            rate=itemView.findViewById(R.id.ratingText);

        }
    }
}
