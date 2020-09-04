package com.sftelehealth.doctor.data.model.response;

import java.util.ArrayList;
import java.util.List;

import com.sftelehealth.doctor.data.model.TimeSlot;

/**
 * Created by Rahul on 19/06/17.
 */

public class GetAllTimeSlotsResponse {

    private List<TimeSlot> timeslots = new ArrayList<TimeSlot>();;

    public List<TimeSlot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(List<TimeSlot> timeslots) {
        this.timeslots = timeslots;
    }
}
