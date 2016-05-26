package br.com.natanael.listadecompras.dao;

import java.util.List;

import br.com.natanael.listadecompras.Estruturas.ListaCompras;

/**
 * Created by 631610277 on 21/05/16.
 */
public interface ListaComprasDao extends GenericDao<ListaCompras> {
    public ListaCompras retornaListaNaoFinalizada();
    public List<ListaCompras> retornaListasFinalizadas();
    public ListaCompras carregaListaFinalizada(Integer id);
}
