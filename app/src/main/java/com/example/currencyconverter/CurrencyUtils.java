package com.example.currencyconverter;

import android.util.Log;
import android.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyUtils {
    public static List<String> currencies;
    public static Map<String, Double> rateList;
    static {
        currencies = Arrays.asList("USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "SEK", "NZD");
        rateList = new HashMap<>();
                rateList.put("USD", 1D);
                rateList.put("EUR", 1.17D);
                rateList.put("JPY", 0.0095);
                rateList.put("GBP", 1.29);
                rateList.put("AUD", 0.71);
                rateList.put("CAD", 0.76);
                rateList.put("CHF", 1.09);
                rateList.put("CNY", 0.15);
                rateList.put("SEK", 0.11);
                rateList.put("NZD", 0.66);
        Log.d("RATE LIST: ",rateList.toString());
    }

    public static Double convert(String from, String to, Double value) {
        return (rateList.get(from) / rateList.get(to)) * value;
    }
}
