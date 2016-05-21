package br.com.natanael.listadecompras.dao.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 631610277 on 21/05/16.
 */
public class BancoDadosOpenHelper extends SQLiteOpenHelper{

    private static String nome="crudListaCompras.db";
    private static String createProduto="CREATE TABLE Produto" +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "nome VARCHAR(100))";

    private static String createListaCompras = "CREATE TABLE ListaCompras" +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "data DATE," +
            "valor_total double precision)";

    private static String createListaComprasItem = "CREATE TABLE ListaComprasItem" +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "id_listacompras INTEGER," +
            "sequencia INTEGER," +
            "id_produto INTEGER," +
            "quantidade INTEGER," +
            "valor_unitario DOUBLE PRECISION," +
            "valor_total DOUBLE PRECISION)";

    public BancoDadosOpenHelper(Context contexto) {
        super(contexto, nome, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase banco) {
        banco.execSQL(createProduto);
        banco.execSQL(createListaCompras);
        banco.execSQL(createListaComprasItem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase banco, int versaoAntiga, int versaoNova) {

    }
}
