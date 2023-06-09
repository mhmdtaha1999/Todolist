package com.example.todolist_java;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String TABLE_TASKS="tbl_tasks";
    private static final String TAG= "SQLiteHelper";

    public SqliteHelper(@Nullable Context context) {
        super(context, "db_app", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("CREATE TABLE "+ TABLE_TASKS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,completed BOOLEAN);");
        }catch (SQLException e){
            Log.e(TAG, "onCreate: "+ e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addTask(Task task){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",task.getTitle());
        contentValues.put("completed",task.getisCompleted());
        long result = sqLiteDatabase.insert(TABLE_TASKS,"null",contentValues);
        sqLiteDatabase.close();
        return result;
    }
    public List<Task> getTasks(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_TASKS,null );
        List<Task> tasks = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                Task task = new Task();
                task.setId(cursor.getLong(0));
                task.setTitle(cursor.getString(1));
                task.setisCompleted(cursor.getInt(2) == 1);
                tasks.add(task);
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return tasks;
    }

    public List<Task> searchInTasks(String query){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_TASKS + " WHERE title LIKE '%"+ query+"%'",null);
        List<Task> tasks = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                Task task = new Task();
                task.setId(cursor.getLong(0));
                task.setTitle(cursor.getString(1));
                task.setisCompleted(cursor.getInt(2) == 1);
                tasks.add(task);
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return tasks;

    }


    public int updateTask(Task task){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",task.getTitle());
        contentValues.put("completed",task.getisCompleted());
        int result = sqLiteDatabase.update(TABLE_TASKS,contentValues ,"id = ?",new String[]{String.valueOf(task.getId())});
        sqLiteDatabase.close();
        return result;
    }

    public int deleteTask(Task task){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int result = sqLiteDatabase.delete(TABLE_TASKS,"id = ?" , new String[]{String.valueOf(task.getId())});
        sqLiteDatabase.close();
        return result;
    }

    public void clearAllTasks(){
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.execSQL("DELETE FROM "+ TABLE_TASKS );
        }catch (SQLException e){
            Log.e(TAG,"Clear All tasks :" + e);
        }

    }

}
