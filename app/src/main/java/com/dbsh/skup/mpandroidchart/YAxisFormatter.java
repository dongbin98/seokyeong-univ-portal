package com.dbsh.skup.mpandroidchart;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class YAxisFormatter extends ValueFormatter {
    private DecimalFormat mFormat;

    public YAxisFormatter() {
        mFormat = new DecimalFormat("###,###,##0.00");
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value);
    }
}
