package br.com.natanael.listadecompras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import br.com.natanael.listadecompras.Estruturas.ListaCompras;
import br.com.natanael.listadecompras.dao.bd.ListaComprasDaoBd;

public class VisualizadorListaCompras extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizador_lista_compras);

        Intent it = this.getIntent();
        int id_lista = it.getIntExtra("id_lista", -1);

        ListaComprasDaoBd DAOListaCompras = new ListaComprasDaoBd(this);
        ListaCompras lista = DAOListaCompras.procurarPorId(id_lista);

        TextView textView_data = (TextView) findViewById(R.id.textView_dataListaCompras);
        textView_data.setText("Data da compra: " + lista.getData());
        TextView textView_quantidadeItens = (TextView) findViewById(R.id.textView_quantidadeItensListaCompras);
        textView_quantidadeItens.setText("Quantidade de itens comprados: " + lista.getQuantidadeTotalItens());
        ListView listView = (ListView) findViewById(R.id.listView_itensListaCompras);
        ListaComprasAdapter adapter = new ListaComprasAdapter(this, lista, false);
        listView.setAdapter(adapter);
    }
}
