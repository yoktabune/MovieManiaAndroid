package com.example.moviemaniaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.moviemaniaapp.databinding.ActivityMainBinding;

import java.util.List;

public class ActivityDetails extends AppCompatActivity {
    TextView title;
    TextView director;
    TextView description;
    TextView rating;
    TextView comments;
    Button btn;
    EditText userInput;
    EditText commentInput;
    String id;
    ImageView img;
    int photoID;
    Handler movieHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            MovieModel mv=(MovieModel)message.obj;
            title.setText(mv.getName());
            director.setText(mv.getDirector());
            description.setText(mv.getDescription());
            img.setImageResource(photoID);
            List<String> comlist=mv.getComments();
            StringBuilder stringBuilder = new StringBuilder();

            for (String element : comlist) {
                stringBuilder.append(element).append("\n").append("\n");
            }
            String result = stringBuilder.toString();
            comments.setText(result);
            rating.setText(Double.toString(mv.getRating()));
            return true;
        }
    });
    Handler commentHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            List<String> comlist= (List<String>)message.obj;
            Log.e("DEV",comlist.toString());
            StringBuilder stringBuilder = new StringBuilder();

            for (String element : comlist) {
                stringBuilder.append(element).append("\n").append("\n");
            }

            String result = stringBuilder.toString();
            comments.setText(result);
            userInput.setText("");
            commentInput.setText("");
            return true;
        }
    });
    Handler posthandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            String returned=(String)message.obj;
            Log.e("DEV",returned);

                MovieRepository repo=new MovieRepository();
                repo.getCommentsForMovie(((MovieApplication)getApplication()).srv,commentHandler,id);
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        title=findViewById(R.id.movie_name);
        director=findViewById(R.id.director_name);
        description=findViewById(R.id.movie_description);
        comments=findViewById(R.id.comments);
        rating=findViewById(R.id.rating);
        btn=findViewById(R.id.add_comment_button);
        userInput=findViewById(R.id.user_name_input);
        commentInput=findViewById(R.id.comment_input);
        img=findViewById(R.id.movie_photo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MovieRepository repo=new MovieRepository();
        id =getIntent().getExtras().getString("id");
        photoID=getIntent().getExtras().getInt("photo");
        repo.getMovieById(((MovieApplication)getApplication()).srv,movieHandler,id);
        btn.setOnClickListener(v->{
            String userIn= userInput.getText().toString();
            String comIn =commentInput.getText().toString();
            StringBuilder builder=new StringBuilder();
            builder.append(userIn);
            builder.append(": ");
            builder.append(comIn);
            if(userIn!="" || comIn!=""){
                repo.postCommentsForMovie(((MovieApplication)getApplication()).srv,posthandler,id,builder.toString());
            }else{
                Log.e("DEV","comment is not acceptable");
            }

        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // Back button on the action bar
                onBackPressed(); // Handle the back button action
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // Handle the back button action here
        finish(); // Finish the current activity and navigate back
    }
}