package com.sftelehealth.doctor.video.helper;

import androidx.cardview.widget.CardView;

import android.content.Context;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

import com.sftelehealth.doctor.video.R;

public class AnimationHelper {

    private View v;
    Animation slideUp;
    Animation slideDown;

    public AnimationHelper(Context context) {
        slideUp = AnimationUtils.loadAnimation(context,
                R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(context,
                R.anim.slide_down);
    }

    public Animation getLocalScreenTransition(final View view, final int diameter) {

        final ViewGroup.LayoutParams layoutParams = ((SurfaceView)((FrameLayout)view).getChildAt(0)).getLayoutParams(); //view.getLayoutParams();
        final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        final int viewHeight = view.getMeasuredHeight();
        final int viewWidth = view.getMeasuredWidth();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                int radius = (int) diameter/2;

                // animate the local video view to the right corner of the screen
                // with shape as circular and width and height as 120dp and margin as 16dp
                layoutParams.height = (int)(viewHeight - ((viewHeight - diameter) * interpolatedTime));
                layoutParams.width = (int) (viewWidth - ((viewWidth - diameter) * interpolatedTime));

                // interpolate the proper value
                // ((ViewGroup.MarginLayoutParams)layoutParams).topMargin = (int) (22 * interpolatedTime);
                // ((ViewGroup.MarginLayoutParams)layoutParams).rightMargin = (int) (18 * interpolatedTime);

                ((SurfaceView)((FrameLayout)view).getChildAt(0)).setLayoutParams(layoutParams);

                // ((CardView)view).setRadius(radius * interpolatedTime);
            }

            @Override
            public boolean hasEnded() {
                view.getLayoutParams().height = diameter;
                view.getLayoutParams().width = diameter;
                return super.hasEnded();
            }
        };

        a.setInterpolator(new DecelerateInterpolator(1.0f));
        a.setDuration(1000);
        return a;
    }

    public void transformLocalVideoSize(View view, int height, int width) {

        int viewHeight = view.getMeasuredHeight();
        int viewWidth = view.getMeasuredWidth();

        view.getLayoutParams().height = viewHeight - (viewHeight - height);
        view.getLayoutParams().width = viewWidth - (viewWidth - width);

        // interpolate the proper value
        ((ViewGroup.MarginLayoutParams)view.getLayoutParams()).topMargin = 22;   // (int) (22 * interpolatedTime);
        ((ViewGroup.MarginLayoutParams)view.getLayoutParams()).rightMargin = 18;    //(int) (18 * interpolatedTime);

        view.requestLayout();
    }

    public Animation getLocalScreenTransitionReverse(final View view, final int diameter) {

        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        final int viewHeight = ((View)view.getParent()).getMeasuredHeight();
        final int viewWidth = ((View)view.getParent()).getMeasuredWidth();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                int radius = (int) diameter/2;

                // animate the local video view to the right corner of the screen
                // with shape as circular and width and height as 120dp and margin as 16dp

                layoutParams.height = (int)(viewHeight * interpolatedTime);
                layoutParams.width = (int) (viewWidth * interpolatedTime);

                // interpolate the proper value
                ((ViewGroup.MarginLayoutParams)layoutParams).topMargin = (int) (22 - ((22) * interpolatedTime));
                ((ViewGroup.MarginLayoutParams)layoutParams).rightMargin = (int) (18 - ((18) * interpolatedTime));

                view.setLayoutParams(layoutParams);

                ((CardView)view).setRadius(radius - (radius * interpolatedTime));
            }
        };

        a.setInterpolator(new DecelerateInterpolator(1.0f));
        a.setDuration(1000);
        return a;
    }

    public void hideDownLayout(View v, final boolean hidePermanently) {

        if(!slideDown.hasStarted() || slideDown.hasEnded()) {

            this.v = v;
            v.clearAnimation();

            slideDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (hidePermanently)
                        setVisibilityGone();
                    else
                        setVisibilityInvisible();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}

            });
            slideDown.setFillAfter(false);
            v.startAnimation(slideDown);
        }
    }

    public void hideDown(View v, final boolean hidePermanently) {

        /*if(!slideDown.hasStarted() || slideDown.hasEnded()) {

            this.v = v;
            v.clearAnimation();

            slideDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (hidePermanently)
                        setVisibilityGone();
                    else
                        setVisibilityInvisible();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}

            });
            slideDown.setFillAfter(false);
            v.startAnimation(slideDown);
        }*/
    }

    public void showUp(View v) {

        /*if(!slideUp.hasStarted() || slideUp.hasEnded()) {

            this.v = v;
            v.clearAnimation();

            slideUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) { setVisibilityVisible(); }

                @Override
                public void onAnimationRepeat(Animation animation) {}

            });
            slideUp.setFillAfter(true);
            v.startAnimation(slideUp);
        }*/
    }

    private void setVisibilityGone() {
        v.setVisibility(View.GONE);
    }

    private void setVisibilityInvisible() {v.setVisibility(View.INVISIBLE);}

    private void setVisibilityVisible() {
        //v.setZ(1000.0f);
        v.setVisibility(View.VISIBLE);
    }
}
