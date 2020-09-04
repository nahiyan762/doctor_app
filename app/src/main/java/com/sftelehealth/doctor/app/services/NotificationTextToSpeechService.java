package com.sftelehealth.doctor.app.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.speech.tts.SynthesisCallback;
import android.speech.tts.SynthesisRequest;
import android.speech.tts.TextToSpeech;
import androidx.annotation.Nullable;
import android.util.Log;

import com.sftelehealth.doctor.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * An {@link IntentService} class which converts text to speech
 *
 */
public class NotificationTextToSpeechService extends android.speech.tts.TextToSpeechService implements TextToSpeech.OnInitListener {    // UtteranceProgressListener

    private static final String ACTION_TEXT_TO_SPEECH = BuildConfig.APPLICATION_ID + ".app.services.action.TEXT_TO_SPEECH";

    private static final String EXTRA_TEXT = BuildConfig.APPLICATION_ID + ".app.services.extra.TEXT";
    private static final String EXTRA_ENABLED = BuildConfig.APPLICATION_ID + ".app.services.extra.ENABLED";

    private TextToSpeech engine;

    private Intent speechIntent;
    private boolean talkEnabled;

    //int engineInitiationStatus = 1;

    //public TextToSpeechService() {super("TextToSpeechService");}

    /**
     * Starts the service to read the text to speech
     * @param context
     * @param text
     */
    public static void startTextToSpeech(Context context, String text, JSONObject jsonData) {
        Intent intent = new Intent(context, NotificationTextToSpeechService.class);

        intent.setAction(ACTION_TEXT_TO_SPEECH);
        try {
            intent.putExtra(EXTRA_TEXT, jsonData.getString("speech"));

            // if talkEnabled is true then start the service otherwise not
            if(jsonData.getBoolean("enableTalk"))
                context.startService(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            speechIntent = intent;
            engine = new TextToSpeech(this, this);
            final String action = intent.getAction();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    //@Override protected void onHandleIntent(Intent intent) {}

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Close the Text to Speech Library
        if(engine != null) {

            engine.stop();
            engine.shutdown();
            Log.d("TextToSpeechService", "TTS Destroyed");
        }
    }

    @Override
    protected int onIsLanguageAvailable(String lang, String country, String variant) {
        return 0;
    }

    @Override
    protected String[] onGetLanguage() {
        return new String[0];
    }

    @Override
    protected int onLoadLanguage(String lang, String country, String variant) {
        return 0;
    }

    @Override
    protected void onStop() {
    }

    @Override
    protected void onSynthesizeText(SynthesisRequest request, SynthesisCallback callback) {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void speak(String text, float pitch, float speed) {
        Log.d("Speech", "Pitch : " + pitch + ", Speed : " + speed);
        engine.setPitch(pitch);
        engine.setSpeechRate(speed);
        //engine.setOnUtteranceProgressListener(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, null) == TextToSpeech.SUCCESS) {Log.d("Speech", "Successfully queued");}
            else {Log.d("Speech", "Queueing unsuccessful");}
        } else {
            // HashMap<String, String> settings = new HashMap<>();
            if(engine.speak(text, TextToSpeech.QUEUE_FLUSH, null) == TextToSpeech.SUCCESS) {Log.d("Speech", "Successfully queued");}
            else {Log.d("Speech", "Queueing unsuccessful");}
        }

        // end the service once task is queued
        // stopSelf();
    }

    @Override
    public void onInit(int status) {

        // This should be run on a new thread, as setting language is an expensive operation
        Log.d("Speech", "OnInit - Status ["+status+"]");

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (status == TextToSpeech.SUCCESS) {

                    Log.d("Speech", "Success!");
                    engine.setLanguage(Locale.UK);

                    final String text = speechIntent.getStringExtra(EXTRA_TEXT);
                    try {
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    speak(text, 1.0f, 0.8f);
                }
            }
        }).start();
    }

}
