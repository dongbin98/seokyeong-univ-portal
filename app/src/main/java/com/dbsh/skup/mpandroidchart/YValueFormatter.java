package com.dbsh.skup.mpandroidchart;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class YValueFormatter extends ValueFormatter {

    private DecimalFormat mFormat;

    public YValueFormatter() {
        mFormat = new DecimalFormat("###,###,##0"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value) {
        // write your logic here
        return mFormat.format(value) + "%"; // e.g. append a dollar-sign
    }
}