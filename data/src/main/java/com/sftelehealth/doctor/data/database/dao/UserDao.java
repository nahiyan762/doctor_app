package com.sftelehealth.doctor.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.sftelehealth.doctor.data.database.entity.User;

/**
 * Created by Rahul on 04/12/17.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    User getUser();
}
