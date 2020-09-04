package com.sftelehealth.doctor.app.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.sftelehealth.doctor.app.Doctor24x7Application;
import com.sftelehealth.doctor.app.internal.di.components.ApplicationComponent;
import com.sftelehealth.doctor.app.internal.di.modules.ActivityModule;
import com.sftelehealth.doctor.app.receiver.NetworkChangeReceiver;
import com.sftelehealth.doctor.app.utils.NetworkConnectionHelper;

import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

/**
 * BaseClass for all Activities, contains helper functions for repeating tasks
 * Created by Rahul on 21/06/17.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    //@Inject
    //Navigator navigator;

    //@Inject
    //EventBus eventBus;
    NetworkChangeReceiver networkChangeReceiver;

    @Inject
    SharedPreferences sp;

    //SharedPreferences.Editor spEdit;
    private View rootView;
    private String logText;

    public boolean callInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT < 26)
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Appsee.start("fdd30b78806541aba0dad10143934130");

        //spEdit = sp.edit();
        networkChangeReceiver = new NetworkChangeReceiver();
        //logUser();
    }

    public void logViewEvent(String screenName, String screenId) {
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName(screenName)
                .putContentType("screen_view")
                .putContentId(screenId));
        this.getClass().getName();
    }

    public void logCustomEvent(String eventName, Map<String, String> customAttributes) {
        CustomEvent ce = new CustomEvent(eventName);
        Iterator iterator = customAttributes.keySet().iterator();
        for(Map.Entry entry: customAttributes.entrySet()) {
            ce.putCustomAttribute(entry.getKey().toString(), entry.getValue().toString());
        }
        Answers.getInstance().logCustom(ce);
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment        The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((Doctor24x7Application) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    /**
     * Get Shared preferences
     *
     * @return (@link SharedPreferences)
     */
    public SharedPreferences getSharedPreferences() {
        return sp;
    }

    /*
     * Get Navigator
     *
     * @return (@link Navigator)
     */
    //public Navigator getNavigator() {return navigator;}

    /**
     * Get EventBus
     *
     * @return (@link Navigator)
     */
    //public EventBus getEventBus() {return eventBus;}

    @NonNull
    public View getRootView() {
        return rootView;
    }

    public void setRootView(@NonNull View rootView) {
        this.rootView = rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        logText = "\u27F3 " + this.getClass().getSimpleName() + " reentered activity";
        Log.d("Lifecycle", appendLog(logText));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        logText = "\u27F3 " + this.getClass().getSimpleName() + " savedInstanceState";
        Log.d("Lifecycle", appendLog(logText));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        logText = "\u27F3 " + this.getClass().getSimpleName() + " restoredInstanceState";
        Log.d("Lifecycle", appendLog(logText));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        logText = "\u27F3 " + this.getClass().getSimpleName() + " created";
        Log.d("Lifecycle", appendLog(logText));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logText = "\u27F3 " + this.getClass().getSimpleName() + " restarted";
        Log.d("Lifecycle", appendLog(logText));
    }

    @Override
    protected void onStart() {
        super.onStart();
        logText = "\u27F3 " + this.getClass().getSimpleName() + " started";
        Log.d("Lifecycle", appendLog(logText));
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityResumed();
        NetworkConnectionHelper.registerNetworkChangeReceiver(this, networkChangeReceiver);
        logText = "\u27F3 " + this.getClass().getSimpleName() + " resumed";
        Log.d("Lifecycle", appendLog(logText));
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityPaused();
        NetworkConnectionHelper.unRegisterNetworkChangeReceiver(this, networkChangeReceiver);
        logText = "\u27F3 " + this.getClass().getSimpleName() + " paused";
        Log.d("Lifecycle", appendLog(logText));
    }

    @Override
    protected void onStop() {
        super.onStop();
        logText = "\u27F3 " + this.getClass().getSimpleName() + " stop";
        Log.d("Lifecycle", appendLog(logText));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logText = "\u27F3 " + this.getClass().getSimpleName() + " destroy";
        Log.d("Lifecycle", appendLog(logText));
    }

    public String appendLog(String text) {
        /*File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        storageDir.mkdir();
        File logFile = new File(storageDir + "/" + "doctor24x7_test_log.txt");

        //File logFile = new File("sdcard/Downloadstest_log.txt");

        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append("\n" + text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        return text;
    }

    /**
     * To check whether activity is visible
     * so that any fragment transaction(starting payment disclaimer) on it can be called after that
     */
    private static boolean activityVisible;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    /*private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier(getSharedPreferences().getString(Constant.PHONE, ""));
        Crashlytics.setUserEmail(getSharedPreferences().getString(Constant.EMAIL, ""));
        Crashlytics.setUserIdentifier(String.valueOf(getSharedPreferences().getInt(Constant.USER_ID, 0)));
    }*/

}