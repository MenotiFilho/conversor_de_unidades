package com.example.conversordeunidades;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.conversordeunidades.business.ConversorTemperatura;

import java.util.Locale;

public class TempActivity extends AppCompatActivity {

    private EditText editTextTemperatura;
    private TextView textViewResultado;
    private Spinner spinnerOrigem;
    private Spinner spinnerDestino;

    private ConversorTemperatura conversor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextTemperatura = findViewById(R.id.editTextNumberTemp);
        textViewResultado = findViewById(R.id.TextViewResultado);
        spinnerOrigem = findViewById(R.id.spinnerTempOrigem);
        spinnerDestino = findViewById(R.id.spinnerResultado);
        this.configurarSpinners();
        this.adicionarTextWatcher();

        conversor = new ConversorTemperatura();
    }

    public void adicionarTextWatcher(){
        editTextTemperatura.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcularTemperatura();
            }
        });
    }

    public void configurarSpinners(){
        String[] unidades = {"Celsius", "Fahrenheit", "Kelvin"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unidades);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOrigem.setAdapter(adapter);
        spinnerDestino.setAdapter(adapter);

        spinnerOrigem.setSelection(0);
        spinnerDestino.setSelection(1);

        SpinnerListener listener = new SpinnerListener();
        spinnerOrigem.setOnItemSelectedListener(listener);
        spinnerDestino.setOnItemSelectedListener(listener);
    }

    private class SpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            calcularTemperatura();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }


    private void calcularTemperatura() {
        String origem = spinnerOrigem.getSelectedItem().toString();
        String destino = spinnerDestino.getSelectedItem().toString();

        String inputStr = editTextTemperatura.getText().toString();

        if (inputStr.isEmpty()) {
            textViewResultado.setText("");
            return;
        }
        try {
            double valorEntrada = Double.parseDouble(inputStr);

            double resultado = conversor.converter(valorEntrada, origem, destino);

            textViewResultado.setText(String.format(Locale.getDefault(), "%.2f", resultado));

        } catch (NumberFormatException e) {
            textViewResultado.setText(e.getMessage());
        }
    }



}