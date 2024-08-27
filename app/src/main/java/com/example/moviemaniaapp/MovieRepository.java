package com.example.moviemaniaapp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class MovieRepository {
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
    public void getAllMovies(@NonNull ExecutorService srv, Handler uiHandler){
        srv.submit(()->{
            List<MovieModel>data=new ArrayList<>();
            try {
                Log.e("DEV","connection preparing");
                URL url = new URL("http://10.0.2.2:8080/api/movies/");
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder builder= new StringBuilder();
                String line="";
                Log.e("DEV","connection okay");
                while((line= reader.readLine())!=null){
                    builder.append(line);
                }
                JSONArray jarr=new JSONArray(builder.toString());
                for (int i = 0; i < jarr.length(); i++) {
                    JSONObject jsonObject= jarr.getJSONObject(i);
                    Log.e("DEV",jsonObject.getString("name"));
                    JSONArray commarr=jsonObject.getJSONArray("comments");
                    List<String> comlist=new ArrayList<>();
                    String comment="";
                    for(int k=0;k< commarr.length();k++){
                        comment= commarr.getString(k);
                        comlist.add(comment);
                    }
                    MovieModel movie=new MovieModel(jsonObject.getString("id"),
                            jsonObject.getString("name"),
                            jsonObject.getDouble("rating"),
                            jsonObject.getString("director"),
                            jsonObject.getString("genre"),
                            jsonObject.getString("description"),
                            comlist
                            );
                    movie.setPhotoId(resource[i]);
                    data.add(movie);
                }
                conn.disconnect();
                Message msg=new Message();
                msg.obj=data;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                Log.e("DEV",e.getMessage());
            } catch (IOException e) {
                Log.e("DEV",e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
            }
        });
    }
    public void getMovieById(@NonNull ExecutorService srv, Handler uiHandler,String id){
        srv.submit(()->{
            try {
                URL url=new URL("http://10.0.2.2:8080/api/movies/"+id);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder builder= new StringBuilder();
                String line="";
                while((line= reader.readLine())!=null){
                    builder.append(line);
                }
                JSONObject jsonObject= new JSONObject(builder.toString());
                JSONArray commarr=jsonObject.getJSONArray("comments");
                List<String> comlist=new ArrayList<>();
                String comment;
                for(int k=0;k< commarr.length();k++){
                    comment= commarr.getString(k);
                    comlist.add(comment);
                }
                MovieModel movie=new MovieModel(jsonObject.getString("id"),
                        jsonObject.getString("name"),
                        jsonObject.getDouble("rating"),
                        jsonObject.getString("director"),
                        jsonObject.getString("genre"),
                        jsonObject.getString("description"),
                        comlist
                );
                Message msg=new Message();
                msg.obj=movie;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                Log.e("DEV",e.getMessage());
            } catch (IOException e) {
                Log.e("DEV",e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
            }

        });

    }
    public void getMoviesByGenre(@NonNull ExecutorService srv, Handler uiHandler,String genre){
        srv.submit(()->{
            List<MovieModel>data=new ArrayList<>();
            try {
                URL url=new URL("http://10.0.2.2:8080/api/movies/genre/"+genre);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder builder= new StringBuilder();
                String line="";
                while((line= reader.readLine())!=null){
                    builder.append(line);
                }
                JSONArray jarr=new JSONArray(builder.toString());
                for (int i = 0; i < jarr.length(); i++) {
                    JSONObject jsonObject= jarr.getJSONObject(i);
                    JSONArray commarr=jsonObject.getJSONArray("comments");
                    List<String> comlist=new ArrayList<>();
                    String comment="";
                    for(int k=0;k< commarr.length();k++){
                        JSONObject comjson= commarr.getJSONObject(k);
                        comment= comjson.toString();
                        comlist.add(comment);
                    }
                    MovieModel movie=new MovieModel(jsonObject.getString("id"),
                            jsonObject.getString("name"),
                            jsonObject.getDouble("rating"),
                            jsonObject.getString("director"),
                            jsonObject.getString("genre"),
                            jsonObject.getString("description"),
                            comlist
                    );
                    data.add(movie);
                }
                Message msg=new Message();
                msg.obj=data;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                Log.e("DEV",e.getMessage());
            } catch (IOException e) {
                Log.e("DEV",e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
            }
        });
    }
    public void getCommentsForMovie(@NonNull ExecutorService srv, Handler uiHandler,String id) {
        srv.submit(() -> {

            try {
                URL url = new URL("http://10.0.2.2:8080/api/movies/" + id + "/comments");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                JSONArray commarr = new JSONArray(builder.toString());
                List<String> comlist = new ArrayList<>();
                String comment = "";
                for (int k = 0; k < commarr.length(); k++) {
                    comment = commarr.getString(k);
                    comlist.add(comment);
                }
                Message msg = new Message();
                msg.obj = comlist;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                Log.e("DEV",e.getMessage());
            } catch (IOException e) {
                Log.e("DEV",e.getMessage());;
            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());;
            }


        });
    }
    public void postCommentsForMovie(@NonNull ExecutorService srv, Handler uiHandler,String id,String comment){
        srv.submit(()->{
            try {
                URL url = new URL("http://10.0.2.2:8080/api/movies/" + id + "/comments");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","text/plain");
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                writer.write(comment);
                writer.flush();
                BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder builder= new StringBuilder();
                String line="";
                while((line= reader.readLine())!=null){
                    builder.append(line);
                }
                Message msg = new Message();
                msg.obj = builder.toString();
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                Log.e("DEV",e.getMessage());
            } catch (IOException e) {
                Log.e("DEV",e.getMessage());
            }
        });
    }
}
