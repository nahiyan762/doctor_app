package com.sftelehealth.doctor.data.model.response;

import java.util.ArrayList;
import java.util.List;

import com.sftelehealth.doctor.data.model.Case;

/**
 * Created by Rahul on 19/06/17.
 */

public class DoctorCasesResponse {

    private List<Case> cases = new ArrayList<Case>();
    private Integer upgrade;

    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
    }

    public Integer getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(Integer upgrade) {
        this.upgrade = upgrade;
    }
}
