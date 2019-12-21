package com.atrinfanavaran.kheiriyeh.Kernel.Helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MarkerWithLabel extends Marker {
    Paint textPaint = null;
    String mLabel = null;

    public MarkerWithLabel(MapView mapView, String label) {
        super( mapView);
        mLabel = label;
        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#ffffff"));
        textPaint.setTextSize(25f);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setShadowLayer(6, 1, 1, Color.BLACK);

    }
    public void draw(final Canvas c, final MapView osmv, boolean shadow) {
        draw( c, osmv);
    }

    public void draw( final Canvas c, final MapView osmv) {
        super.draw( c, osmv, false);
        Point p = this.mPositionPixels;  // already provisioned by Marker
        c.drawText( mLabel, p.x, p.y+60, textPaint);
    }
}