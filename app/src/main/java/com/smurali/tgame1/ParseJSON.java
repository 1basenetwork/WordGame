package com.smurali.tgame1;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Anubhav on 29-07-2017.
 */

public class ParseJSON {
    String json;
    DatabaseHandler db;
    Context context;

    public ParseJSON(Context context) {
        this.context = context;
    }

    public ParseJSON(String json, Context context) {
        this.json = json;
        this.context = context;
        db = new DatabaseHandler(context);
    }

    public String loadJSONFromAsset(String file) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void updateDB(){
        try {
            JSONArray array = new JSONArray(json);
            Log.e("Length",array.length()+"");
                for (int i=0;i<array.length();i++){
                    int id=0;
                    String q=null,a=null,o1 = null,o2=null,o3=null,o4=null;
                    JSONObject obj = array.getJSONObject(i);
                    if (obj.has("id")&&!obj.isNull("id")){
                        id = obj.getInt("id");
                    }
                    if(obj.has("qestion")&&!obj.isNull("qestion")){
                        q = obj.getString("qestion");
                    }
                    if(obj.has("correct")&&!obj.isNull("correct")){
                        a = obj.getString("correct");
                    }
                    if(obj.has("opt1")&&!obj.isNull("opt1")){
                        o1 = obj.getString("opt1");
                    }
                    if(obj.has("opt2")&&!obj.isNull("opt2")){
                        o2 = obj.getString("opt2");
                    }
                    if(obj.has("opt3")&&!obj.isNull("opt3")){
                        o3 = obj.getString("opt3");
                    }
                    if(obj.has("opt4")&&!obj.isNull("opt4")){
                        o4 = obj.getString("opt4");
                    }
                    db.addQuestion(new QuizPOJO(id,q,a,o1,o2,o3,o4));
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
