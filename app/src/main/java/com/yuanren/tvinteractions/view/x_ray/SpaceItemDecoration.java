package com.yuanren.tvinteractions.view.x_ray;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int lSpace = 0;
    private int rSpace = 0;
    private int tSpace = 0;
    private int bSpace = 0;

    public SpaceItemDecoration(int space){
        this.lSpace = space;
    }

    public SpaceItemDecoration(int lSpace, int rSpace, int tSpace, int bSpace){
        this.lSpace = lSpace;
        this.rSpace = rSpace;
        this.tSpace = tSpace;
        this.bSpace = bSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = lSpace;
        outRect.right = rSpace;
        outRect.top = tSpace;
        outRect.bottom = bSpace;
    }
}

