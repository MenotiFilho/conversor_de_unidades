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

import com.example.conversordeunidades.business.IConversor;

import java.util.Locale;

/**
 * Classe base abstrata para todas as Activities de conversão (Temperatura, Massa, etc.).
 * Esta classe contém todo o código de UI (TextWatcher, Spinners) que é idêntico
 * em todas as telas, delegando apenas a lógica específica (unidades e conversor)
 * para as classes filhas.
 */
public abstract class ConversorBaseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // CAMPOS COMPARTILHADOS: São 'protected' para serem acessíveis pelas classes filhas.
    protected EditText editTextInput;
    protected TextView textViewResultado;
    protected Spinner spinnerOrigem;
    protected Spinner spinnerDestino;

    // LÓGICA DE NEGÓCIOS: Usamos a Interface para polimorfismo.
    protected IConversor conversor;

    // ---------------------------------------------------------------------------------
    // MÉTODOS ABSTRATOS: OBRIGAM AS CLASSES FILHAS A FORNECEREM AS INFORMAÇÕES ESPECÍFICAS
    // ---------------------------------------------------------------------------------

    // 1. Deve retornar o ID do layout (ex: R.layout.activity_temp)
    protected abstract int getLayoutResId();

    // 2. Deve retornar a instância correta da classe IConversor (ex: new ConversorTemperatura())
    protected abstract IConversor getConversorInstance();

    // 3. Deve retornar o array de strings com as unidades específicas (ex: "Celsius", "Fahrenheit")
    protected abstract String[] getUnidades();

    // 4. (OPCIONAL) Permite definir a seleção inicial no Spinner
    protected int getOrigemSelection() { return 0; }
    protected int getDestinoSelection() { return 1; }

    // ---------------------------------------------------------------------------------
    // CÓDIGO DO CICLO DE VIDA E INICIALIZAÇÃO (COMPARTILHADO)
    // ---------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // 1. Define o Layout baseado no que a classe filha retornar
        setContentView(getLayoutResId());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. Associa as Views (IDs devem ser os mesmos em todos os XMLs!)
        editTextInput = findViewById(R.id.editTextNumberOrigem);
        textViewResultado = findViewById(R.id.TextViewResultado);
        spinnerOrigem = findViewById(R.id.spinnerOrigem);
        spinnerDestino = findViewById(R.id.spinnerResultado);

        // 3. Inicializa o Conversor (Polimorfismo em ação!)
        conversor = getConversorInstance();

        // 4. Configura Listeners
        configurarSpinners();
        adicionarTextWatcher();
    }


    // ---------------------------------------------------------------------------------
    // MÉTODOS DE COMPORTAMENTO (COMPARTILHADO)
    // ---------------------------------------------------------------------------------

    private void adicionarTextWatcher(){
        editTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { /* Não utilizado */ }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Não utilizado */ }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fazerConversao();
            }
        });
    }

    protected void configurarSpinners(){
        // Usa o array de unidades da classe filha
        String[] unidades = getUnidades();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unidades);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOrigem.setAdapter(adapter);
        spinnerDestino.setAdapter(adapter);

        // Define a seleção inicial usando os valores opcionais da classe filha
        spinnerOrigem.setSelection(getOrigemSelection());
        spinnerDestino.setSelection(getDestinoSelection());

        spinnerOrigem.setOnItemSelectedListener(this);
        spinnerDestino.setOnItemSelectedListener(this);
    }

    protected void fazerConversao() {
        String origem = spinnerOrigem.getSelectedItem().toString();
        String destino = spinnerDestino.getSelectedItem().toString();

        String inputStr = editTextInput.getText().toString();

        if (inputStr.isEmpty()) {
            textViewResultado.setText("");
            return;
        }
        try {
            double valorEntrada = Double.parseDouble(inputStr);

            // A chamada é idêntica para todas as classes filhas
            double resultado = conversor.converter(valorEntrada, origem, destino);

            textViewResultado.setText(String.format(Locale.getDefault(), "%.2f", resultado));

        } catch (NumberFormatException e) {
            // Em vez de 'e.getMessage()', que pode ser muito técnico, use uma mensagem amigável.
            textViewResultado.setText("Entrada inválida.");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        fazerConversao();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}