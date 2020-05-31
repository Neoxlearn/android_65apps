package com.gmail.neooxpro.adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public final class ContactItemDecoration extends RecyclerView.ItemDecoration {

    private final int margin;

    public ContactItemDecoration(int margin) {
        this.margin = margin;
    }


    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(margin + margin, margin, margin + margin, margin);
        int position = parent.getChildAdapterPosition(view);
        if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = margin + margin;
        }
        if (position == 0) {
            outRect.top = margin + margin;
        }
    }

}
