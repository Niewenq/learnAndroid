package com.ieening.androidipcserver.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VectorQuantity implements Parcelable {
    private static final String REGEX_PATTERN = "(?<=\\()( *?\\d+? *?,?)+?(?=\\))";

    public ArrayList<Double> getComponents() {
        return components;
    }

    private ArrayList<Double> components = new ArrayList<>();

    protected VectorQuantity(Parcel in) {
        this.components.clear();
        for (double component :
                Objects.requireNonNull(in.createDoubleArray())) {
            this.components.add(component);
        }
    }

    public static final Creator<VectorQuantity> CREATOR = new Creator<VectorQuantity>() {
        @Override
        public VectorQuantity createFromParcel(Parcel in) {
            return new VectorQuantity(in);
        }

        @Override
        public VectorQuantity[] newArray(int size) {
            return new VectorQuantity[size];
        }
    };

    public VectorQuantity(ArrayList<Double> arrayList) {
        this.components.clear();
        this.components.addAll(arrayList);
    }

    public static VectorQuantity getNewInstance(String componentsString) {
        ArrayList<Double> doubles = getDoublesFromString(componentsString);
        return new VectorQuantity(doubles);
    }

    @NonNull
    private static ArrayList<Double> getDoublesFromString(String componentsString) {
        String componentsStringTrim = componentsString.trim();
        Matcher matcher = Pattern.compile(REGEX_PATTERN).matcher(componentsStringTrim);
        ArrayList<Double> doubles = new ArrayList<>();
        while (matcher.find()) {
            int begin = matcher.start();
            int end = matcher.end();
            for (String component : componentsStringTrim.substring(begin, end).split(",")) {
                doubles.add(Double.parseDouble(component.trim()));
            }
        }
        return doubles;
    }

    @NonNull
    @Override
    public String toString() {
        return this.components.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeDoubleArray(Arrays.stream(this.components.toArray()).mapToDouble(k -> (double) k).toArray());
    }

    public void update(String componentsString) {
        ArrayList<Double> doubles = getDoublesFromString(componentsString);
        this.components.clear();
        this.components.addAll(doubles);
    }

    public void update(VectorQuantity vectorQuantity) {
        this.components.clear();
        this.components.addAll(vectorQuantity.getComponents());
    }

    public void readFromParcel(Parcel in) {
        this.components.clear();
        for (double component :
                Objects.requireNonNull(in.createDoubleArray())) {
            this.components.add(component);
        }
    }
}
