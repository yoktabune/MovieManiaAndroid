package com.example.moviemaniaapp;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MovieApplication extends Application {
    ExecutorService srv= Executors.newCachedThreadPool();
}
