package com.sftelehealth.doctor.data.database.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sftelehealth.doctor.data.model.DoctorCategory;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DoctorCategoryConverter {

    @TypeConverter
    public static ArrayList<DoctorCategory> fromString(String value) {
        Type listType = new TypeToken<ArrayList<DoctorCategory>>() {}.getType();
        return (value == null)? null : new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<DoctorCategory> list) {
        String json = new Gson().toJson(list);
        return (json == null)? null : json;
    }
}