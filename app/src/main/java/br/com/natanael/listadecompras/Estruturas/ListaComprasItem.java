package br.com.natanael.listadecompras.Estruturas;

import android.content.Context;

import br.com.natanael.listadecompras.dao.bd.ProdutoDaoBd;

/**
 * Created by 631610277 on 21/05/16.
 */
public class ListaComprasItem {
    private ProdutoDaoBd DAOProduto;
    private Integer id;
    private int sequencia;
    private int listaComprasId;
    private Produto produto;
    private int quantidade;
    private double valor_unitario;
    private double valor_total;
    private Boolean comprado;

    public  ListaComprasItem(Context contexto, Integer id, int listaComprasId, int sequencia, int idProduto, int quantidade, double valor_unitario, double valor_total, Boolean comprado){
        this.DAOProduto = new ProdutoDaoBd(contexto);
        this.id = id;
        this.listaComprasId = listaComprasId;
        this.sequencia = sequencia;
        this.produto = DAOProduto.procurarPorId(idProduto);
        this.quantidade = quantidade;
        this.valor_unitario = valor_unitario;
        this.valor_total = valor_total;
        this.comprado = comprado;
    }

    public ListaComprasItem(Produto produto, int quantidade, double valor_unitario) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor_unitario = valor_unitario;
        this.valor_total = (this.quantidade * this.valor_unitario);
        this.comprado = false;
    }

    public ListaComprasItem(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor_unitario = -1;
        this.valor_total = -1;
        this.comprado = false;
    }

    public Integer getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getProdutoId() {
        return produto.getId();
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValor_unitario() {
        return valor_unitario;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValor_unitario(double valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    public void setListaComprasId(int listaComprasId) {
        this.listaComprasId = listaComprasId;
    }

    public int getListaComprasId() {
        return listaComprasId;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public void setComprado(Boolean comprado) {
        this.comprado = comprado;
    }

    public Boolean getComprado() {
        return comprado;
    }
}
