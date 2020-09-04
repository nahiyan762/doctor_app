package com.sftelehealth.doctor.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sftelehealth.doctor.data.database.dao.AppointmentDao;
import com.sftelehealth.doctor.data.database.dao.CaseDao;
import com.sftelehealth.doctor.data.database.dao.DoctorDao;
import com.sftelehealth.doctor.data.database.dao.SystemInfoDao;
import com.sftelehealth.doctor.data.database.dao.UserDao;
import com.sftelehealth.doctor.data.database.entity.Appointment;
import com.sftelehealth.doctor.data.database.entity.Case;
import com.sftelehealth.doctor.data.database.entity.Doctor;
import com.sftelehealth.doctor.data.database.entity.SystemInfo;
import com.sftelehealth.doctor.data.database.entity.User;

/**
 * Created by Rahul on 04/12/17.
 */

@Database(version = 4, entities = {User.class, Doctor.class, Case.class, Appointment.class, SystemInfo.class}, exportSchema = true)
public abstract class DoctorDatabase extends RoomDatabase {

    abstract public UserDao userDao();
    abstract public DoctorDao doctorDao();
    abstract public CaseDao casesDao();
    abstract public AppointmentDao appointmentDao();
    abstract public SystemInfoDao systemDao();

}