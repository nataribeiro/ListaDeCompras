package br.com.natanael.listadecompras.Estruturas;

import java.util.ArrayList;

/**
 * Created by Natanael on 16/05/2016.
 */
public class Produto {

    private String nome;
    private int quantidade;
    private double valorUnitario;
    private double valorTotal;

    private ArrayList<Double> historicoValores;

    public Produto(String nome, int quantidade){
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public Produto(String nome, int quantidade, double valorUnitario){
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = this.valorUnitario * this.quantidade;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}
