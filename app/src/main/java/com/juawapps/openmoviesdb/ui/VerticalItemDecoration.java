package com.juawapps.openmoviesdb.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Custom item decoration to display a space between RecyclerView elements
 *
 * Created by joaocevada on 08/10/16.
 */

public class VerticalItemDecoration extends RecyclerView.ItemDecoration {

    private final int mVerticalSpaceHeight;

    public VerticalItemDecoration(Context context, int idHeightDimen) {


        this.mVerticalSpaceHeight = (int) context.getResources().getDimension(idHeightDimen);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = mVerticalSpaceHeight;
    }
}
