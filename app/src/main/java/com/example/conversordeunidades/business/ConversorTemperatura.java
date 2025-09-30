package com.example.conversordeunidades.business;

public class ConversorTemperatura implements IConversor {

    @Override
    public double converter(double valor, String origem, String destino) {
        if (origem.equals(destino)) {
            return valor;
        }

        double resultado = 0.0;

        double valorEmCelsius = valor;

        if (origem.equals("Fahrenheit")) {
            // F -> C: (F - 32) * 5/9
            valorEmCelsius = (valor - 32) * 5 / 9;
        } else if (origem.equals("Kelvin")) {
            // K -> C: K - 273.15
            valorEmCelsius = valor - 273.15;
        }

        if (destino.equals("Fahrenheit")) {
            // C -> F: (C * 9/5) + 32
            resultado = (valorEmCelsius * 9 / 5) + 32;
        } else if (destino.equals("Kelvin")) {
            // C -> K: C + 273.15
            resultado = valorEmCelsius + 273.15;
        } else {
            resultado = valorEmCelsius;
        }

        return resultado;
    }
}

