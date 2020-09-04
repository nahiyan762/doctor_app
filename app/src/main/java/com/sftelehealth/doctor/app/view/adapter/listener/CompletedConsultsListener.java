package com.sftelehealth.doctor.app.view.adapter.listener;

import com.sftelehealth.doctor.domain.model.Case;

/**
 * Created by Rahul on 29/12/17.
 */

public interface CompletedConsultsListener {

    void onCaseClicked(Case caseId);
}
