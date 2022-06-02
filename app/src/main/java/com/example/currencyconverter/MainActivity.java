package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int selectedField = 1;
    private Double convertValue = 0d;
    private final StringBuilder valueString = new StringBuilder();

    Spinner fromSpinner;
    Spinner toSpinner;
    TextView ft;
    TextView tt;
    RecyclerView kb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* SETTING UP SPINNER */
        fromSpinner = findViewById(R.id.to_spinner);
        toSpinner = findViewById(R.id.from_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, CurrencyUtils.currencies);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);
        fromSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        convert();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                }
        );
        toSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        convert();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );
        /* END */

        /* SETTING UP TEXT VIEW */
        ft = findViewById(R.id.from_text);
        tt = findViewById(R.id.to_text);
        tt.setTextColor(getResources().getColor(R.color.focus));

        ft.setOnClickListener(view -> {
            selectedField = 0;
            convertValue = 0d;
            valueString.setLength(0);
            tt.setTextColor(getResources().getColor(R.color.not_focus));
            ((TextView) view).setTextColor(getResources().getColor(R.color.focus));
            System.out.println(selectedField);
        });
        tt.setOnClickListener(view -> {
            selectedField = 1;
            convertValue = 0d;
            valueString.setLength(0);
            ft.setTextColor(getResources().getColor(R.color.not_focus));
            ((TextView) view).setTextColor(getResources().getColor(R.color.focus));
            System.out.println(selectedField);
        });
        /* END */

        /* SETTING UP RECYCLE VIEW  */
        kb = findViewById(R.id.keyboard);
        RecyclerView.LayoutManager gridLM = new GridLayoutManager(this, 3);
        kb.setLayoutManager(gridLM);
        KeyboardAdapter kba = new KeyboardAdapter(view -> {
            String pressedKey = ((TextView) view).getText().toString();
            if (pressedKey.equals("BACK")) {
                if (valueString.toString().equals("0")) {
                    valueString.setLength(0);
                } else valueString.setLength(Math.max(valueString.length() - 1, 0));
                if (valueString.length() > 0)
                    convertValue = Double.parseDouble(valueString.toString());
                else convertValue = 0d;
                this.convert();
                if (selectedField == 0) {
                    ft.setText(valueString.length() > 0 ? valueString.toString() : "0");
                } else {
                    tt.setText(valueString.length() > 0 ? valueString.toString() : "0");
                }
                return;
            }
            if (pressedKey.equals("CLEAR")) {
                convertValue = 0d;
                valueString.setLength(0);
                convert();
                if (selectedField == 0) {
                    ft.setText("0");
                } else {
                    tt.setText("0");
                }
                return;
            }

            if (valueString.length() <= 0 && pressedKey.equals(".")) {
                valueString.append("0.");
            } else {
                valueString.append(pressedKey);
            }
            try {
                convertValue = Double.parseDouble(valueString.toString());
                this.convert();
                if (selectedField == 0) {
                    ft.setText(valueString.toString());

                } else {
                    tt.setText(valueString.toString());
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Not a valid number", Toast.LENGTH_SHORT).show();
                valueString.setLength(Math.max(valueString.length() - 1, 0));
            }
        });
        kb.setAdapter(kba);
        /* END */
    }

    /**
     * Conversion magic happens here
     * */
    public void convert() {
        Log.d("From", toSpinner.getSelectedItem().toString());
        Log.d("To", fromSpinner.getSelectedItem().toString());
        if (selectedField == 1) {
            Double res = CurrencyUtils.convert(fromSpinner.getSelectedItem().toString(), toSpinner.getSelectedItem().toString(), this.convertValue);
            this.ft.setText(String.format(Locale.ENGLISH, "%.2f", res));
        } else {
            Double res = CurrencyUtils.convert(toSpinner.getSelectedItem().toString(), fromSpinner.getSelectedItem().toString(), this.convertValue);
            this.tt.setText(String.format(Locale.ENGLISH, "%.2f", res));
        }
    }
}