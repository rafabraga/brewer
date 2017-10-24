package com.algaworks.brewer.config.format;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

public class BigDecimalFormatter implements Formatter<BigDecimal> {

    private final DecimalFormat decimalFormat;

    public BigDecimalFormatter(final String pattern) {
        final NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));
        this.decimalFormat = (DecimalFormat) format;
        this.decimalFormat.setParseBigDecimal(true);
        this.decimalFormat.applyPattern(pattern);
    }

    @Override
    public String print(final BigDecimal object, final Locale locale) {
        return this.decimalFormat.format(object);
    }

    @Override
    public BigDecimal parse(final String text, final Locale locale) throws ParseException {
        return (BigDecimal) this.decimalFormat.parse(text);
    }

}