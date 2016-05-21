package br.com.natanael.listadecompras.dao;

import java.util.List;

/**
 * Created by 631610277 on 21/05/16.
 */
public interface GenericDao<T> {
    public void insert(T entidade);
    public void excluir(T entidade);
    public void update(T entidade);
    public List<T> listar();
    public T procurarPorId(Integer id);
}
