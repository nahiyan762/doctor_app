package com.sftelehealth.doctor.video.internal.di;

/**
 * Created by Rahul on 26/06/17.
 */

/**
 * Interface representing a contract for clients that contains a component for dependency injection.
 */
public interface HasComponent<C> {
    C getComponent();
}
