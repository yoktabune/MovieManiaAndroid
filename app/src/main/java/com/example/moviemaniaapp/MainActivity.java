package com.example.moviemaniaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MovieAdapter adp;
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            List<MovieModel> data= (List<MovieModel>) message.obj;
            MovieAdapter adp = new MovieAdapter(MainActivity.this,data);
            recyclerView.setAdapter(adp);
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.movieRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        MovieRepository repo = new MovieRepository();
        repo.getAllMovies(((MovieApplication) getApplication()).srv, handler);
    }
}