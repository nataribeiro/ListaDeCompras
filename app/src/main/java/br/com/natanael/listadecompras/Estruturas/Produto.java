package br.com.natanael.listadecompras.Estruturas;

import java.util.ArrayList;

/**
 * Created by Natanael on 16/05/2016.
 */
public class Produto {

    private Integer id;
    private String nome;

    public Produto(Integer id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public Produto(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
