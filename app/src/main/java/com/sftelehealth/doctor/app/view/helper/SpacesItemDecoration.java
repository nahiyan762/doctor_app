package com.sftelehealth.doctor.app.view.helper;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by RahulT on 16-07-2015.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int left, top, right, bottom;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    public SpacesItemDecoration(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = left;
        outRect.right = right;
        outRect.bottom = bottom;
        outRect.top = top;

        // Add top margin only for the first item to avoid double space between items
        if(parent.getChildLayoutPosition(view) != 0)
            outRect.top = 0;

        //Log.d("RecyclerView", "view count - " + itemCount + " parent.getChildLayoutPosition(view) - " + parent.getChildLayoutPosition(view));
        /*int itemCount = state.getItemCount();
        if(parent.getChildLayoutPosition(view) == (itemCount - 1))
            outRect.bottom = 0;*/

    }
}