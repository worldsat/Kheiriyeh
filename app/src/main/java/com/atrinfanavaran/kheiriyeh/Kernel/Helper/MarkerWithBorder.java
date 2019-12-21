package com.atrinfanavaran.kheiriyeh.Kernel.Helper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MarkerWithBorder extends Marker {

    int max_width;

    Paint textPaint = null;
    String mLabel = null;
    String mSelectedColorName;

    public MarkerWithBorder(MapView mapView, String selectedColorName, int width, int height) {
        super(mapView);
        mLabel = "";
        this.mSelectedColorName = selectedColorName;
        textPaint = new Paint();
        textPaint.setColor(Colors.getColorCode(selectedColorName));
        textPaint.setTextSize(25f);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeWidth(8);
        textPaint.setStyle(Paint.Style.STROKE);

        if (width > height) {
            max_width = width;
        } else {
            max_width = height;
        }
    }

    public void draw(final Canvas c, final MapView osmv, boolean shadow) {
        draw(c, osmv);
    }

    public void draw(final Canvas c, final MapView osmv) {
        super.draw(c, osmv, false);
        Point p = this.mPositionPixels;  // already provisioned by Marker
        int s = (max_width + 50) / 2;

        if (mSelectedColorName != null) {
            c.drawRect(p.x - s, p.y - s, p.x + s, p.y + s, textPaint);
        }


    }
}