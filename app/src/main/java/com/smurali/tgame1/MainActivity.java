package com.smurali.tgame1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smurali.tgame1.R;

public class MainActivity extends AppCompatActivity {

    private LinearLayout container;
    private Boolean visible;
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateDB();
        final Button btn = (Button) findViewById(R.id.playbtn);
        JosBold scorecard = (JosBold) findViewById(R.id.scorecard);
        pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        scorecard.setText("Your Score: "+pref.getString("score","0"));
        container = (LinearLayout) findViewById(R.id.main_container);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,PlayActivity.class));
            }
        });
    }


    public void updateDB(){
        //Offline File
        ParseJSON pj = new ParseJSON(getApplicationContext());
        String json = pj.loadJSONFromAsset("questions.json");
        ParseJSON parseJSON = new ParseJSON(json,getApplicationContext());
        parseJSON.updateDB();
    }

    private void loadmore(){
        Toast.makeText(getApplicationContext(),"Loading more questions",Toast.LENGTH_LONG).show();
    }

    private void reset(){
        Toast.makeText(getApplicationContext(),"Resetting scores",Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("score","0");
        editor.putString("qno","0");
        editor.apply();

    }
    private void viewscore(){
        Toast.makeText(getApplicationContext(),"Loading more questions",Toast.LENGTH_LONG).show();
    }
}
