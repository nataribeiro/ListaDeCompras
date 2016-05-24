package br.com.natanael.listadecompras.dao;

import java.util.List;

import br.com.natanael.listadecompras.Estruturas.ListaComprasItem;

/**
 * Created by 631610277 on 21/05/16.
 */
public interface ListaComprasItemDao extends GenericDao<ListaComprasItem> {
    public List<ListaComprasItem> carregaItensDaListaCompras(int id_listacompras);
}
