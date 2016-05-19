package br.com.natanael.listadecompras.Estruturas;

import android.provider.CalendarContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Natanael on 16/05/2016.
 */
public class ListaCompras {
    private Calendar data;
    private List<Produto> listaProdutos;

    public  ListaCompras(){
        listaProdutos = new ArrayList<>();
        //Usar quando for finalizar a lista!
            data = Calendar.getInstance();
            data.get(Calendar.DATE);
    }

    public void addProduto(Produto p){
        this.listaProdutos.add(p);
    }

    public double getValorTotalLista(){
        double rValorTotal = 0;
        for (Produto p:this.listaProdutos) {
            rValorTotal += p.getValor();
        }

        return rValorTotal;
    }

    public Calendar getData() {
        return data;
    }

    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }
}
