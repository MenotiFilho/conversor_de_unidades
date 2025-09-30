package com.example.conversordeunidades;

import com.example.conversordeunidades.business.ConversorTemperatura;
import com.example.conversordeunidades.business.IConversor;

// Apenas estende a classe base, sem implementar AdapterView.OnItemSelectedListener
public class TempActivity extends ConversorBaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_temp; // Garanta que seu XML chame activity_temp
    }

    @Override
    protected IConversor getConversorInstance() {
        return new ConversorTemperatura();
    }

    @Override
    protected String[] getUnidades() {
        return new String[]{"Celsius", "Fahrenheit", "Kelvin"};
    }

    @Override protected int getOrigemSelection() { return 0; }
    @Override protected int getDestinoSelection() { return 1; }
}