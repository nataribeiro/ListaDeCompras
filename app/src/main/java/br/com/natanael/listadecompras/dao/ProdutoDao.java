package br.com.natanael.listadecompras.dao;

import java.util.List;

import br.com.natanael.listadecompras.Estruturas.Produto;
import br.com.natanael.listadecompras.dao.GenericDao;

/**
 * Created by 631610277 on 21/05/16.
 */
public interface ProdutoDao extends GenericDao<Produto> {
    public List<Produto> containingNome(String filtro);
    public Produto procurarPorNome(String nome);
}
