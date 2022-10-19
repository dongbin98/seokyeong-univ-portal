package com.dbsh.skup.mpandroidchart;

import android.content.Context;
import android.widget.TextView;

import com.dbsh.skup.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class MyMarkerView extends MarkerView {

    private TextView text;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        text = (TextView) findViewById(R.id.grade_all_marker_text);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
//        if (e instanceof CandleEntry) {
//            CandleEntry ce = (CandleEntry) e;
//            text.setText("" + ce.getY());
//        } else {
//            text.setText("" + e.getY());
//        }
        text.setText("" + e.getY());
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2) , -getHeight() - 20f);
    }
}
