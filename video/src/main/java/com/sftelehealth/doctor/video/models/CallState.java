package com.sftelehealth.doctor.video.models;


/**
 * Store the info about the call in its present state.
 * This represents the state of video/audio call in its present state
 */
public class CallState {

    /**
     * Call is video or audio
     * VIDEO_CALL/AUDIO_CALL
     */
    int type;

    /**
     * Which state the call is in now
     * UNINITIATED, WAITING (SIGNED_IN), JOINING, RESUMED, ON_GOING, PAUSED, STOPPED, UNINITIATED (SIGNED_OUT) {UNINITIATED is a condition which will only exist when the app has not been started, STOPPED state is not a prevailing state, so when this event happens then the service has already lo}
     */
    int state;

    /**
     * Which event has occured
     * LOGGED_IN, INVITE_RECEIVED, CALL_STARTED, CALL_PAUSED, CALL_RESUMED, INVITE_DECLINED, LOGGED_OUT
     */

    /**
     * This service must be called whenever an event has occurred, this will be responsible maintaining the current state from the app
     */
    private void updateServiceState() {
        // When LOGGED_IN event has occurred then change state to WAITING
        /*

            if(event == LOGGED_IN)
                state = WAITING

            if(event == INVITE_RECEIVED) {
                if(state = WAITING)
                    state = JOINING
                else {
                    // Reject the call stating that the patient is on another call
                }
            }

            if(event == CALL_STARTED)
                state = ON_GOING

            if(event == CALL_PAUSED)
                state = PAUSED


         */
    }

    // when an invite is received determine whether it is a video or audio call
    //
}
