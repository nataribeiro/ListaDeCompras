package br.com.natanael.listadecompras.Estruturas;

import java.util.ArrayList;

/**
 * Created by Natanael on 16/05/2016.
 */
public class Produto {

    private String nome;
    private double valor;
    private ArrayList<Double> historicoValores;

    public Produto(String nome){
        this.nome = nome;
    }

    public Produto(String nome, double valor){
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }
}
