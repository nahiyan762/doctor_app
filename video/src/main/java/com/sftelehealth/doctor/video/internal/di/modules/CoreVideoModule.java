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
package com.sftelehealth.doctor.video.internal.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import com.sftelehealth.doctor.data.executor.JobExecutor;
import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.video.UIThread;
import com.sftelehealth.doctor.video.helper.AgoraStatusHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.sftelehealth.doctor.video.Constant.USER_PREFS;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class CoreVideoModule {

  private final Context context;

  public CoreVideoModule(Context context) {
    this.context = context;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.context;
  }

  @Provides @Singleton
  SharedPreferences providesPrivateSharedPreference(Context context) {
    return context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
  }

  @Provides @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton
  PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton
  AgoraStatusHelper providesAgoraStatusHelper(AgoraStatusHelper agoraStatusHelper) {
    return agoraStatusHelper;
  }

}