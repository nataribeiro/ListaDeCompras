package br.com.natanael.listadecompras.dao.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.natanael.listadecompras.Estruturas.ListaCompras;
import br.com.natanael.listadecompras.Estruturas.ListaComprasItem;
import br.com.natanael.listadecompras.dao.ListaComprasItemDao;

/**
 * Created by 631610277 on 21/05/16.
 */
public class ListaComprasItemDaoDb implements ListaComprasItemDao {
    private BancoDadosOpenHelper bdOpenHelper;
    private Context contexto;

    public ListaComprasItemDaoDb(Context contexto)
    {
        this.contexto = contexto;
        bdOpenHelper = new BancoDadosOpenHelper(contexto);
    }
    @Override
    public void insert(ListaComprasItem item) {
        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("id_listacompras", item.getListaComprasId());
        dados.put("sequencia", item.getSequencia());
        dados.put("id_produto", item.getProdutoId());
        dados.put("quantidade", item.getQuantidade());
        dados.put("valor_unitario", item.getValor_unitario());
        dados.put("valor_total", item.getValor_total());
        dados.put("comprado", item.getComprado() ? "S" : "N");
        long id = banco.insert("ListaComprasItem", null, dados);
        item.setId((int) id);
        banco.close();
    }

    @Override
    public void excluir(ListaComprasItem item) {
        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();
        banco.delete("ListaComprasItem", "id=?", new String[]{ item.getId().toString()});
        banco.close();
    }

    @Override
    public void update(ListaComprasItem item) {
        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("id_listacompras", item.getListaComprasId());
        dados.put("sequencia", item.getSequencia());
        dados.put("id_produto", item.getProdutoId());
        dados.put("quantidade", item.getQuantidade());
        dados.put("valor_unitario", item.getValor_unitario());
        dados.put("valor_total", item.getValor_total());
        dados.put("comprado", item.getComprado() ? "S" : "N");
        banco.update("ListaComprasItem", dados, "id=?", new String[]{item.getId().toString()});
        banco.close();
    }

    @Override
    public List<ListaComprasItem> listar() {
        List<ListaComprasItem> listaItens = new ArrayList<ListaComprasItem>();

        SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        Cursor cursor = banco.query("ListaComprasItem",
                new String[]{"id","id_listacompras","sequencia","id_produto","quantidade","valor_unitario","valor_total","comprado"},
                null, null, null, null, null);

        while(cursor.moveToNext()){
            ListaComprasItem item = new ListaComprasItem(contexto,
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getInt(cursor.getColumnIndex("id_listacompras")),
                    cursor.getInt(cursor.getColumnIndex("sequencia")),
                    cursor.getInt(cursor.getColumnIndex("id_produto")),
                    cursor.getInt(cursor.getColumnIndex("quantidade")),
                    cursor.getDouble(cursor.getColumnIndex("valor_unitario")),
                    cursor.getDouble(cursor.getColumnIndex("valor_total")),
                    cursor.getString(cursor.getColumnIndex("comprado")).equals("S"));

            listaItens.add(item);
        }
        banco.close();
        return(listaItens);
    }

    @Override
    public ListaComprasItem procurarPorId(Integer id) {
        SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        Cursor cursor = banco.query("ListaComprasItem",
                new String[]{"id","id_listacompras","sequencia","id_produto","quantidade","valor_unitario","valor_total","comprado"},
                "id=?", new String[]{id.toString()},
                null, null, null);

        if(cursor.moveToNext()){
            ListaComprasItem item = new ListaComprasItem(contexto,
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getInt(cursor.getColumnIndex("id_listacompras")),
                    cursor.getInt(cursor.getColumnIndex("sequencia")),
                    cursor.getInt(cursor.getColumnIndex("id_produto")),
                    cursor.getInt(cursor.getColumnIndex("quantidade")),
                    cursor.getDouble(cursor.getColumnIndex("valor_unitario")),
                    cursor.getDouble(cursor.getColumnIndex("valor_total")),
                    cursor.getString(cursor.getColumnIndex("comprado")).equals("S"));

            banco.close();
            return(item);
        }
        banco.close();
        return(null);
    }

    @Override
    public List<ListaComprasItem> carregaItensDaListaCompras(int id_listacompras) {
        List<ListaComprasItem> listaItens = new ArrayList<ListaComprasItem>();
        SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        Cursor cursor = banco.query("ListaComprasItem",
                new String[] {"id", "id_listacompras", "sequencia", "id_produto", "quantidade", "valor_unitario", "valor_total","comprado"},
                "id_listacompras=?", new String[] { String.valueOf(id_listacompras) },
                null, null, "sequencia");
        while(cursor.moveToNext()){
            ListaComprasItem item = new ListaComprasItem(contexto,
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getInt(cursor.getColumnIndex("id_listacompras")),
                    cursor.getInt(cursor.getColumnIndex("sequencia")),
                    cursor.getInt(cursor.getColumnIndex("id_produto")),
                    cursor.getInt(cursor.getColumnIndex("quantidade")),
                    cursor.getDouble(cursor.getColumnIndex("valor_unitario")),
                    cursor.getDouble(cursor.getColumnIndex("valor_total")),
                    cursor.getString(cursor.getColumnIndex("comprado")).equals("S"));

            listaItens.add(item);
        }
        banco.close();
        return listaItens;
    }

    public List<ListaComprasItem> carregaItensComprados() {
        List<ListaComprasItem> listaItens = new ArrayList<ListaComprasItem>();
        SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        Cursor cursor = banco.query("ListaComprasItem",
                new String[] {"id", "id_listacompras", "sequencia", "id_produto", "quantidade", "valor_unitario", "valor_total","comprado"},
                "comprado=?", new String[] { "S" },
                null, null, "sequencia");
        while(cursor.moveToNext()){
            ListaComprasItem item = new ListaComprasItem(contexto,
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getInt(cursor.getColumnIndex("id_listacompras")),
                    cursor.getInt(cursor.getColumnIndex("sequencia")),
                    cursor.getInt(cursor.getColumnIndex("id_produto")),
                    cursor.getInt(cursor.getColumnIndex("quantidade")),
                    cursor.getDouble(cursor.getColumnIndex("valor_unitario")),
                    cursor.getDouble(cursor.getColumnIndex("valor_total")),
                    cursor.getString(cursor.getColumnIndex("comprado")).equals("S"));

            listaItens.add(item);
        }
        banco.close();
        return listaItens;
    }

    @Override
    public List<ListaComprasItem> carregaItensCompradosListaCompras(int id_listacompras) {
        List<ListaComprasItem> listaItens = new ArrayList<ListaComprasItem>();
        SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        Cursor cursor = banco.query("ListaComprasItem",
                new String[] {"id", "id_listacompras", "sequencia", "id_produto", "quantidade", "valor_unitario", "valor_total","comprado"},
                "id_listacompras=? and comprado=?", new String[] { String.valueOf(id_listacompras), "S" },
                null, null, "sequencia");
        while(cursor.moveToNext()){
            ListaComprasItem item = new ListaComprasItem(contexto,
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getInt(cursor.getColumnIndex("id_listacompras")),
                    cursor.getInt(cursor.getColumnIndex("sequencia")),
                    cursor.getInt(cursor.getColumnIndex("id_produto")),
                    cursor.getInt(cursor.getColumnIndex("quantidade")),
                    cursor.getDouble(cursor.getColumnIndex("valor_unitario")),
                    cursor.getDouble(cursor.getColumnIndex("valor_total")),
                    cursor.getString(cursor.getColumnIndex("comprado")).equals("S"));

            listaItens.add(item);
        }
        banco.close();
        return listaItens;
    }
}
