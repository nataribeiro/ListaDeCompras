package br.com.natanael.listadecompras.Estruturas;

import android.content.Context;
import android.provider.CalendarContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.natanael.listadecompras.dao.ListaComprasItemDao;
import br.com.natanael.listadecompras.dao.bd.ListaComprasDaoBd;
import br.com.natanael.listadecompras.dao.bd.ListaComprasItemDaoDb;

/**
 * Created by Natanael on 16/05/2016.
 */
public class ListaCompras {
    private Integer id;
    private Calendar data;
    private double valor_total;
    private Boolean finalizado;
    private List<ListaComprasItem> listaItens;

    public ListaCompras(Context contexto, Integer id, Calendar data, double valor_total, String finalizado){
        this.id = id;
        this.data = data;
        this.valor_total = valor_total;
        this.finalizado = finalizado.equals("S");
        ListaComprasItemDaoDb DAOListaItem = new ListaComprasItemDaoDb(contexto);
        this.listaItens = DAOListaItem.carregaItensDaListaCompras(id);
    }

    public ListaCompras(Context contexto){
        ListaComprasDaoBd DAOListaCompras = new ListaComprasDaoBd(contexto);
        listaItens = new ArrayList<ListaComprasItem>();
        this.valor_total = 0;
        finalizado = false;
        data = Calendar.getInstance();
        data.get(Calendar.DATE);
    }

    public void addProduto(ListaComprasItem item){
        item.setListaComprasId(this.getId());
        item.setSequencia(this.listaItens.size());
        this.listaItens.add(item);
        this.valor_total += item.getValor_total();
    }

    public double getValorTotalLista(){
        return valor_total;
    }

    public String getData() {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(data.getTime());
        return formattedDate;
    }

    public List<ListaComprasItem> getListaItens() {
        return listaItens;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInsertableData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(data.getTime());

        return date;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }
}
