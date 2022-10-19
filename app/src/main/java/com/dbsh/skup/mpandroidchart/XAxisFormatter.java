package com.dbsh.skup.mpandroidchart;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class XAxisFormatter extends ValueFormatter {
    private DecimalFormat mFormat;

    public XAxisFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0" + "        ");
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value);
    }
}
