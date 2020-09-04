package com.sftelehealth.doctor.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import com.sftelehealth.doctor.data.database.entity.Case;

/**
 * Created by Rahul on 04/12/17.
 */
@Dao
public interface CaseDao {

    @Query("SELECT * FROM 'Case'")
    List<Case> getCases();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCase(Case... cases);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCaseList(List<Case> cases);

    @Query("SELECT * FROM 'Case' WHERE caseId = :caseId")
    public Case getCaseFromId(int caseId);
}
