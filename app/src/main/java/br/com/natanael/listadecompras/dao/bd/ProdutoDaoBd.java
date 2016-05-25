package br.com.natanael.listadecompras.dao.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.natanael.listadecompras.Estruturas.Produto;
import br.com.natanael.listadecompras.dao.ProdutoDao;

/**
 * Created by 631610277 on 21/05/16.
 */
public class ProdutoDaoBd implements ProdutoDao {
    private BancoDadosOpenHelper bdOpenHelper;

    public ProdutoDaoBd(Context contexto)
    {
        bdOpenHelper = new BancoDadosOpenHelper(contexto);
    }

    @Override
    public void insert(Produto produto) {
        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("nome", produto.getNome());
        long id = banco.insert("Produto", null, dados);
        produto.setId((int) id);
        banco.close();
    }

    @Override
    public void excluir(Produto produto) {
        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();
        banco.delete("Produto", "id=?", new String[] { produto.getId().toString() });
        banco.close();
    }

    @Override
    public void update(Produto produto) {
        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("nome", produto.getNome());
        banco.update("Produto", dados, "id=?", new String[]{produto.getId().toString()});
        banco.close();
    }

    @Override
    public List listar() {
        List<Produto> listaProdutos = new ArrayList<Produto>();

        SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        Cursor cursor = banco.query("Produto",
                new String[] { "id", "nome" },
                null, null, null, null, null);

        while(cursor.moveToNext()){
            Produto produto = new Produto((cursor.getInt(cursor.getColumnIndex("id"))),
                    cursor.getString(cursor.getColumnIndex("nome")));

            listaProdutos.add(produto);
        }
        banco.close();
        return listaProdutos;
    }

    @Override
    public Produto procurarPorId(Integer id) {
        SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        Cursor cursor = banco.query("Produto",
                new String[]{"id","nome"},
                "id=?", new String[]{id.toString()},
                null, null, null);

        if(cursor.moveToNext()){
            Produto produto = new Produto((cursor.getInt(cursor.getColumnIndex("id"))),
                    cursor.getString(cursor.getColumnIndex("nome")));
            banco.close();
            return(produto);
        }
        banco.close();
        return(null);
    }

    @Override
    public List<Produto> containingNome(String filtro) {
        List<Produto> listaProdutos = new ArrayList<Produto>();

        SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        Cursor cursor = banco.query("Produto",
                new String[] { "id", "nome" },
                "nome LIKE '%?%'", new String[] { filtro },
                null, null, null);
        while(cursor.moveToNext()){
            Produto produto = new Produto((cursor.getInt(cursor.getColumnIndex("id"))),
                    cursor.getString(cursor.getColumnIndex("nome")));

            listaProdutos.add(produto);
        }
        banco.close();
        return listaProdutos;
    }

    @Override
    public Produto procurarPorNome(String nome) {
        SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        Cursor cursor = banco.query("Produto",
                new String[]{"id","nome"},
                "nome=?", new String[]{ nome },
                null, null, null);

        if(cursor.moveToNext()){
            Produto produto = new Produto((cursor.getInt(cursor.getColumnIndex("id"))),
                    cursor.getString(cursor.getColumnIndex("nome")));
            banco.close();
            return(produto);
        }
        banco.close();
        return(null);
    }
}
