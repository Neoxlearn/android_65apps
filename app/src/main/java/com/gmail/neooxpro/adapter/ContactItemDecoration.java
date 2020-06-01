package com.gmail.neooxpro.adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public final class ContactItemDecoration extends RecyclerView.ItemDecoration {

    private final int offsetPx;

    public ContactItemDecoration(int offsetPx) {
        this.offsetPx = offsetPx;
    }


    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(offsetPx, offsetPx, offsetPx , offsetPx);
        int position = parent.getChildAdapterPosition(view);

        if (position == state.getItemCount() - 1) {
            outRect.bottom = offsetPx + offsetPx;
        }
        if (position == 0) {
            outRect.top = offsetPx + offsetPx;
        }
    }

}
