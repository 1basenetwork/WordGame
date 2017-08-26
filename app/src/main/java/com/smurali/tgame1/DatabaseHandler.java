package com.smurali.tgame1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "quiz";

    // Contacts table name
    private static final String TABLE_CONTACTS = "questions";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_QUESTIONS = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_OPT1 = "opt1";
    private static final String KEY_OPT2 = "opt2";
    private static final String KEY_OPT3 = "opt3";
    private static final String KEY_OPT4 = "opt4";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";*/
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + " ("+KEY_ID+" INTEGER PRIMARY KEY,"+KEY_QUESTIONS+" TEXT,"+KEY_ANSWER+" TEXT,"+KEY_OPT1+" TEXT,"+KEY_OPT2+" TEXT,\n" +
                ""+KEY_OPT3+" TEXT, "+KEY_OPT4+" TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addQuestion(QuizPOJO obj) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, obj.getId()); // Contact Name
        values.put(KEY_QUESTIONS,obj.getQue());
        values.put(KEY_ANSWER,obj.getAns());
        values.put(KEY_OPT1,obj.getOpt1());
        values.put(KEY_OPT2,obj.getOpt2());
        values.put(KEY_OPT3,obj.getOpt3());
        values.put(KEY_OPT4,obj.getOpt4());
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    QuizPOJO getQue(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_QUESTIONS}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        QuizPOJO question = new QuizPOJO();
        question.setId(Integer.parseInt(cursor.getString(0)));
        question.setQue(cursor.getString(1));
        question.setAns(cursor.getString(2));
        question.setOpt1(cursor.getString(3));
        question.setOpt2(cursor.getString(4));
        question.setOpt3(cursor.getString(5));
        question.setOpt4(cursor.getString(6));
        // return contact
        return question;
    }

    // Getting All Contacts
    public List<QuizPOJO> getAllQuestions() {
        List<QuizPOJO> postList = new ArrayList<QuizPOJO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QuizPOJO question = new QuizPOJO();
                question.setId(Integer.parseInt(cursor.getString(0)));
                question.setQue(cursor.getString(1));
                question.setAns(cursor.getString(2));
                question.setOpt1(cursor.getString(3));
                question.setOpt2(cursor.getString(4));
                question.setOpt3(cursor.getString(5));
                question.setOpt4(cursor.getString(6));
                // Adding contact to list
                if(cursor.getString(0)!= null) {
                    postList.add(question);
                }
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return postList;
    }

    // Updating single contact
    /*public int updateContact(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, post.getId()); // Contact Name
        values.put(KEY_TITLE, post.getTitle()); // Contact Phone
        values.put(KEY_DATE,post.getDate());
        values.put(KEY_CONTENT,post.getContent());
        values.put(KEY_FEAT_IMG,post.getFeatImg());
        values.put(KEY_COMMENT_COUNT,post.getCommentCount());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(post.getId()) });
    }*/

    // Deleting single contact
    public void deleteContact(QuizPOJO post) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(post.getId()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        int count;
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

}
