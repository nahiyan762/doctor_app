/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sftelehealth.doctor.app.internal.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.sftelehealth.doctor.app.Doctor24x7Application;
import com.sftelehealth.doctor.app.UIThread;
import com.sftelehealth.doctor.app.utils.Constant;
import com.sftelehealth.doctor.app.utils.EventBus;
import com.sftelehealth.doctor.data.database.DoctorDatabase_Impl;
import com.sftelehealth.doctor.data.executor.JobExecutor;
import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final Doctor24x7Application application;

  public ApplicationModule(Doctor24x7Application application) {
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application;
  }

  // @Provides @Singleton
  // Navigator provideNavigator() {return new Navigator();}

  @Provides @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton
  PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  /*
  @Provides @Singleton UserCache provideUserCache(UserCacheImpl userCache) {
    return userCache;
  }
  */


  @Provides @Singleton
  SharedPreferences providesPrivateSharedPreference(Context context) {
    return context.getSharedPreferences(Constant.USER_PREFS, Context.MODE_PRIVATE);
  }

  @Provides @Singleton
  EventBus provideEventBus() {
    return EventBus.instanceOf();
  }

  @Provides @Singleton
  DoctorDatabase_Impl provideDatabase(Context context) {
    return Room.databaseBuilder(context,
            DoctorDatabase_Impl.class, "doctor_db").build();
  }
}