package com.smurali.tgame1;

import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.smurali.tgame1.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {

    private String rightAnswer,selectedAnswer;
    private String opt1,opt2,opt3,opt4;
    private String question;
    private int score,qNo;
    private List<QuizPOJO> quiz = new ArrayList<>();
    private SharedPreferences pref;
    private int current=0;
    private Button btn1,btn2,btn3,btn4;
    private JosBold qview;
    private TextView qnoview,scoreview;
    private MediaPlayer m;
    private View Successlayout,faliureLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        score = Integer.parseInt(pref.getString("score","0"));
        qNo = Integer.parseInt(pref.getString("qno","0"));
        current = qNo;
        DatabaseHandler db = new DatabaseHandler(this);
        quiz = db.getAllQuestions();
        //Binding Views
        qview = (JosBold) findViewById(R.id.question);
        btn1 = (Button) findViewById(R.id.text1);
        btn2 = (Button) findViewById(R.id.text2);
        btn3 = (Button) findViewById(R.id.text3);
        btn4 = (Button) findViewById(R.id.text4);
        qnoview = (TextView) findViewById(R.id.qno);
        scoreview = (TextView) findViewById(R.id.score);
        LayoutInflater inflater = getLayoutInflater();
        Successlayout = inflater.inflate(R.layout.success_toast,
                (ViewGroup) findViewById(R.id.success_toast));
        faliureLayout = inflater.inflate(R.layout.faliure_toast,
                (ViewGroup) findViewById(R.id.faliure_toast));
        loadnext();
    }

    private void loadnext(){
        qview.setText(quiz.get(current).getQue());
        score = qNo*5;
        qNo = current+1;
        qnoview.setText("Quiz No. "+qNo);
        scoreview.setText("Score "+score);
        rightAnswer = quiz.get(current).getAns();
        btn1.setText(quiz.get(current).getOpt1());
        btn2.setText(quiz.get(current).getOpt2());
        btn3.setText(quiz.get(current).getOpt3());
        btn4.setText(quiz.get(current).getOpt4());
    }

    private void setUpPref(){
        if(pref.getBoolean("isConfigured",false)){
            writePref("score","0");
            writePref("qno","0");
        }
    }

    private void writePref(String tag,String val){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(tag, val);
        editor.apply();
    }

    public void check(View v){
        btn1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btn2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btn3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btn4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        final Button b = (Button) v;
        b.setBackgroundColor(getResources().getColor(R.color.red));
        selectedAnswer = b.getText().toString();
        if(selectedAnswer.equals(rightAnswer)){
            b.setBackgroundColor(getResources().getColor(R.color.green));
            showSuccess();
            writePref("score",""+qNo*5);
            writePref("qno",""+qNo);
            Log.e("Data","Score: "+qNo*5+"\nQno:"+qNo);
            current++;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    b.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    loadnext();
                }
            }, Toast.LENGTH_LONG);
        }else{
            showFailure();
        }
    }
    private void showSuccess(){
        playSound(true);
        Toast toast1 = Toast.makeText(getApplicationContext(),"InCorrect Answer",Toast.LENGTH_SHORT);
        toast1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast1.setView(Successlayout);
        toast1.show();
    }
    private void showFailure(){
        playSound(false);
        Toast toast1 = Toast.makeText(getApplicationContext(),"InCorrect Answer",Toast.LENGTH_SHORT);
        toast1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast1.setView(faliureLayout);
        toast1.show();
    }

    private void playSound(boolean b){
        m = new MediaPlayer();
        try {
            if (m.isPlaying()) {
                m.stop();
                m.release();
                m = new MediaPlayer();
            }
            AssetFileDescriptor descriptor;
            if(b)
                descriptor = getAssets().openFd("success.mp3");
            else
                descriptor = getAssets().openFd("wrong.mp3");
            m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            m.prepare();
            m.setVolume(1f, 1f);
            m.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
