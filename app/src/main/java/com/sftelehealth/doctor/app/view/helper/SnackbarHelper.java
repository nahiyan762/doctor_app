package com.sftelehealth.doctor.app.view.helper;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.sftelehealth.doctor.R;

import org.jetbrains.annotations.NotNull;

import androidx.core.content.ContextCompat;


/**
 * Created by rahul on 14/10/16.
 */
public class SnackbarHelper {

    public static enum SnackbarTypes {
        ERROR, SUCCESS, INFO, DEFAULT
    }

    /**
     * Function to get a custom snackbar based on the INFO_LEVEL defined by SnackbarTypes
     * @param view
     * @param text
     * @param action
     * @param type
     * @return
     */
    public static Snackbar getCustomSnackBar(View view, String text, String action, SnackbarTypes type) {
        Snackbar snackBar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        View snackBarView = snackBar.getView();
        TextView textView = (TextView) snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        switch (type) {
            case ERROR:
                textView.setTextColor(Color.WHITE);
                snackBarView.setBackgroundColor(Color.RED);
                break;
            case SUCCESS:
                textView.setTextColor(Color.WHITE);
                snackBarView.setBackgroundColor(Color.GREEN);
                break;
            case INFO:
                textView.setTextColor(Color.WHITE);
                snackBarView.setBackgroundColor(Color.BLUE);
                break;
            default:
                textView.setTextColor(Color.WHITE);
                snackBarView.setBackgroundColor(ContextCompat.getColor(snackBarView.getContext(), R.color.colorPrimary));
                break;
        }
        return  snackBar;
    }

    /**
     * Function to get a custom snackbar based on the INFO_LEVEL defined by SnackbarTypes
     * @param view
     * @param text
     * @param action
     * @param type
     * @return
     */
    public static Snackbar getCustomSnackBar(View view, String text, String action, SnackbarTypes type, int time, final SnackbarActionButtonListener snackbarActionButtonListener) {
        Snackbar snackBar = Snackbar.make(view, text, time)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbarActionButtonListener.snackbarActionClicked();
                    }
                });
        View snackBarView = snackBar.getView();
        TextView textView = (TextView) snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        switch (type) {
            case ERROR:
                textView.setTextColor(Color.WHITE);
                snackBarView.setBackgroundColor(Color.RED);
                break;
            case SUCCESS:
                textView.setTextColor(Color.WHITE);
                snackBarView.setBackgroundColor(Color.GREEN);
                break;
            case INFO:
                textView.setTextColor(Color.WHITE);
                snackBarView.setBackgroundColor(Color.BLUE);
                break;
            default:
                textView.setTextColor(Color.WHITE);
                snackBarView.setBackgroundColor(ContextCompat.getColor(snackBarView.getContext(), R.color.colorPrimary));
                break;
        }
        return  snackBar;
    }

    /**
     * Function to get a network failure snackbar
     * @param view
     * @param snackbarActionButtonListener
     * @return
     */
    public static Snackbar getNetworkFailureSnackBar(@NotNull View view, final SnackbarActionButtonListener snackbarActionButtonListener) {
        String text = "Network not available!";
        String action = "RETRY";
        int time = Snackbar.LENGTH_INDEFINITE;

        Snackbar snackBar = Snackbar.make(view, text, time)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbarActionButtonListener.snackbarActionClicked();
                    }
                });
        View snackBarView = snackBar.getView();
        //snackBarView.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.blue_outline));

        TextView textView = (TextView) snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        TextView actionView = (TextView) snackBarView.findViewById(com.google.android.material.R.id.snackbar_action);
        actionView.setTextColor(Color.WHITE);

        snackBarView.setBackgroundColor(ContextCompat.getColor(snackBarView.getContext(), R.color.colorPrimary));

        return  snackBar;
    }

    public interface SnackbarActionButtonListener {
        void snackbarActionClicked();
    }
}
