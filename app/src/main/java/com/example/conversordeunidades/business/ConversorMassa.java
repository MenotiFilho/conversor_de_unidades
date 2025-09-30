package com.example.conversordeunidades.business;

public class ConversorMassa implements IConversor{
    @Override
    public double converter(double valor, String origem, String destino) {

        if (origem.equals(destino)) {
            return valor;
        }

        double valorEmKg = 0.0;

        switch (origem) {
            case "Quilograma":
                valorEmKg = valor;
                break;
            case "Tonelada métrica":
                valorEmKg = valor * 1000.0; // t -> kg
                break;
            case "Grama":
                valorEmKg = valor / 1000.0; // g -> kg
                break;
            case "Miligrama":
                valorEmKg = valor / 1_000_000.0; // mg -> kg
                break;
            case "Micrograma":
                valorEmKg = valor / 1_000_000_000.0; // mcg -> kg
                break;
            case "Libra":
                valorEmKg = valor / 2.20462262; // lb -> kg
                break;
            case "Onça":
                valorEmKg = valor / 35.27396195; // oz -> kg
                break;
            case "Stone":
                valorEmKg = valor / 0.157473; // st -> kg (1 stone tem 6.35029 kg)
                break;
            case "Tonelada curta":
                valorEmKg = valor / 0.00110231; // short ton -> kg (1 short ton tem 907.185 kg)
                break;
            case "Tonelada de deslocamento":
                valorEmKg = valor / 0.000984207; // long ton -> kg (1 long ton tem 1016.05 kg)
                break;
            default:
                return Double.NaN;
        }

        double resultado = 0.0;

        switch (destino) {
            case "Quilograma":
                resultado = valorEmKg;
                break;
            case "Tonelada métrica":
                resultado = valorEmKg / 1000.0; // kg -> t
                break;
            case "Grama":
                resultado = valorEmKg * 1000.0; // kg -> g
                break;
            case "Miligrama":
                resultado = valorEmKg * 1_000_000.0; // kg -> mg
                break;
            case "Micrograma":
                resultado = valorEmKg * 1_000_000_000.0; // kg -> mcg
                break;
            case "Libra":
                resultado = valorEmKg * 2.20462262; // kg -> lb
                break;
            case "Onça":
                resultado = valorEmKg * 35.27396195; // kg -> oz
                break;
            case "Stone":
                resultado = valorEmKg * 0.157473; // kg -> st
                break;
            case "Tonelada curta":
                resultado = valorEmKg * 0.00110231; // kg -> short ton
                break;
            case "Tonelada de deslocamento":
                resultado = valorEmKg * 0.000984207; // kg -> long ton
                break;
            default:
                return Double.NaN;
        }

        return resultado;
    }
}
