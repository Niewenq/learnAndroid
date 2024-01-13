package com.ieening.server;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Student implements Parcelable {
    private String name;
    private int age;

    public void setScore(float score) {
        this.score = score;
    }

    private float score;

    public Student() {

    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
        this.score = -1;
    }

    protected Student(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeFloat(score);
    }

    public void readFromParcel(Parcel in) {
        name = in.readString();
        age = in.readInt();
        score = in.readFloat();
    }

    @NonNull
    @Override
    public String toString() {
        return "name= " + name + ", age= " + age + ", score= " + score;
    }
}
