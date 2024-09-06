package com.myproject.expensetacker;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;

    public GridSpacingItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        Paint paint = new Paint();
        paint.setColor(0xFFDDDDDD); // Divider color
        paint.setStrokeWidth(space);

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            // Draw vertical dividers
            int left = child.getRight() + params.rightMargin;
            int right = left + space;
            int top = child.getTop() - params.topMargin;
            int bottom = child.getBottom() + params.bottomMargin;
            c.drawRect(left, top, right, bottom, paint);

            // Draw horizontal dividers
            int topDivider = child.getBottom() + params.bottomMargin;
            int bottomDivider = topDivider + space;
            int leftDivider = child.getLeft() - params.leftMargin;
            int rightDivider = child.getRight() + params.rightMargin;
            c.drawRect(leftDivider, topDivider, rightDivider, bottomDivider, paint);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            outRect.set(space, space, space, space);
        }
    }
}

