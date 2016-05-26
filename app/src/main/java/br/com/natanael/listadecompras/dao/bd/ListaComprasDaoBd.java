package br.com.natanael.listadecompras.dao.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLInput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.natanael.listadecompras.Estruturas.ListaCompras;
import br.com.natanael.listadecompras.dao.ListaComprasDao;

/**
 * Created by 631610277 on 21/05/16.
 */
public class ListaComprasDaoBd implements ListaComprasDao {
    private Context contexto;
    private BancoDadosOpenHelper bdOpenHelper;

    public ListaComprasDaoBd(Context contexto)
    {
        this.contexto = contexto;
        bdOpenHelper = new BancoDadosOpenHelper(contexto);
    }

    @Override
    public void insert(ListaCompras listaCompras) {
        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("data", listaCompras.getInsertableData());
        dados.put("valor_total", listaCompras.getValorTotalLista());
        dados.put("finalizado", listaCompras.getFinalizado() ? "S" : "N");
        long id = banco.insert("ListaCompras", null, dados);
        listaCompras.setId((int) id);
        banco.close();
    }

    @Override
    public void excluir(ListaCompras listaCompras) {
        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();
        banco.delete("ListaCompras", "id=?", new String[]{listaCompras.getId().toString()});
        banco.close();
    }

    @Override
    public void update(ListaCompras listaCompras) {
        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("data", listaCompras.getInsertableData());
        dados.put("valor_total", listaCompras.getValorTotalLista());
        dados.put("finalizado", listaCompras.getFinalizado() ? "S" : "N");
        banco.update("ListaCompras", dados, "id=?", new String[]{listaCompras.getId().toString()});
        banco.close();
    }

    @Override
    public List<ListaCompras> listar() {
        List<ListaCompras> listaListaCompras = new ArrayList<ListaCompras>();

        SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        Cursor cursor = banco.query("ListaCompras",
                new String[]{"id","data","valor_total","finalizado"},
                null, null, null, null, null);

        while(cursor.moveToNext()){
            ListaCompras listaCompras = new ListaCompras(contexto,
                    (cursor.getInt(cursor.getColumnIndex("id"))),
                    ConvertToCalendar(cursor.getString(cursor.getColumnIndex("data"))),
                    cursor.getDouble(cursor.getColumnIndex("valor_total")),
                    cursor.getString(cursor.getColumnIndex("finalizado")), false);

            listaListaCompras.add(listaCompras);
        }
        banco.close();
        return(listaListaCompras);
    }

    private Calendar ConvertToCalendar(String date) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d1 = null;
        Calendar tdy1;

        try {
            d1 = form.parse(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        tdy1 = Calendar.getInstance();
        tdy1.setTime(d1);
        return tdy1;
    }
    @Override
    public ListaCompras procurarPorId(Integer id) {
        SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        Cursor cursor = banco.query("ListaCompras",
                new String[]{"id","data","valor_total","finalizado"},
                "id=?", new String[]{id.toString()},
                null, null, null);

        if(cursor.moveToNext()){
            ListaCompras listaCompras = new ListaCompras(contexto,
                    (cursor.getInt(cursor.getColumnIndex("id"))),
                    ConvertToCalendar(cursor.getString(cursor.getColumnIndex("data"))),
                    cursor.getDouble(cursor.getColumnIndex("valor_total")),
                    cursor.getString(cursor.getColumnIndex("finalizado")),
                    false);

            banco.close();
            return(listaCompras);
        }
        banco.close();
        return null;
    }

    @Override
    public ListaCompras retornaListaNaoFinalizada() {
        SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        Cursor cursor = banco.query("ListaCompras",
                new String[] { "id", "data", "valor_total", "finalizado"},
                "finalizado=?", new String[] { "N" },
                null, null, null);
        if(cursor.moveToNext()){
            ListaCompras listaCompras = new ListaCompras(contexto,
                    (cursor.getInt(cursor.getColumnIndex("id"))),
                    ConvertToCalendar(cursor.getString(cursor.getColumnIndex("data"))),
                    cursor.getDouble(cursor.getColumnIndex("valor_total")),
                    cursor.getString(cursor.getColumnIndex("finalizado")), false);

            banco.close();
            return listaCompras;
        }
        banco.close();
        return null;
    }

    @Override
    public List<ListaCompras> retornaListasFinalizadas() {
        List<ListaCompras> listaListaCompras = new ArrayList<ListaCompras>();

        SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        Cursor cursor = banco.query("ListaCompras",
                new String[]{"id","data","valor_total","finalizado"},
                "finalizado=?", new String[] { "S" },
                null, null, "id desc");

        while(cursor.moveToNext()){
            ListaCompras listaCompras = new ListaCompras(contexto,
                    (cursor.getInt(cursor.getColumnIndex("id"))),
                    ConvertToCalendar(cursor.getString(cursor.getColumnIndex("data"))),
                    cursor.getDouble(cursor.getColumnIndex("valor_total")),
                    cursor.getString(cursor.getColumnIndex("finalizado")), true);

            listaListaCompras.add(listaCompras);
        }
        banco.close();
        return(listaListaCompras);
    }

    @Override
    public ListaCompras carregaListaFinalizada(Integer id) {
        SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        Cursor cursor = banco.query("ListaCompras",
                new String[]{"id","data","valor_total","finalizado"},
                "id=?", new String[]{id.toString()},
                null, null, null);

        if(cursor.moveToNext()){
            ListaCompras listaCompras = new ListaCompras(contexto,
                    (cursor.getInt(cursor.getColumnIndex("id"))),
                    ConvertToCalendar(cursor.getString(cursor.getColumnIndex("data"))),
                    cursor.getDouble(cursor.getColumnIndex("valor_total")),
                    cursor.getString(cursor.getColumnIndex("finalizado")),
                    true);

            banco.close();
            return(listaCompras);
        }
        banco.close();
        return null;
    }
}
