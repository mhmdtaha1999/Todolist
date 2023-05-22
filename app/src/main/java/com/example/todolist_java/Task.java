package com.example.todolist_java;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Task implements Parcelable {
    private long id;
    private String title;
    private boolean isCompleted;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public Boolean getisCompleted(){
        return isCompleted;
    }

    public void setisCompleted(Boolean isCompleted){
        this.isCompleted = isCompleted;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeByte(this.isCompleted ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.title = source.readString();
        this.isCompleted = source.readByte() != 0;
    }

    public Task() {
    }

    protected Task(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.isCompleted = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
