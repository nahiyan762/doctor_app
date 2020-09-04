package com.sftelehealth.doctor.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sftelehealth.doctor.data.database.entity.Doctor;


/**
 * Created by Rahul on 04/12/17.
 */
@Dao
public interface DoctorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDoctor(Doctor doctor) ;

    @Query("SELECT * FROM 'doctor' WHERE 1 LIMIT 1")
    Doctor getDoctor();

    @Query("UPDATE 'doctor' SET image= :image WHERE id = :id")
    int setImage(String image, int id);

    @Query("UPDATE 'doctor' SET signatureImage= :signatureImage WHERE id = :id")
    int setSignature(String signatureImage, int id);
}

