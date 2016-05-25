package br.com.natanael.listadecompras;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import br.com.natanael.listadecompras.Estruturas.ListaCompras;
import br.com.natanael.listadecompras.dao.bd.ListaComprasDaoBd;

public class NovaListaComprasActivity extends AppCompatActivity {
    private final static int AddProdutoRequest = 1;

    private static ListaCompras listaAtual;
    ListaComprasDaoBd DAOListaCompras = new ListaComprasDaoBd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_lista_compras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaAtual = DAOListaCompras.retornaListaNaoFinalizada();
        if(listaAtual == null)
            listaAtual = new ListaCompras(this);

        PopulaListView();
    }

    private void PopulaListView(){
        ListaComprasAdapter adapter = new ListaComprasAdapter(this, listaAtual);
        ListView listView = (ListView)findViewById(R.id.listView_addedProducts);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem menuItem = menu.findItem(R.id.action_addProduto);
        menuItem.setVisible(true);

        menu.findItem(R.id.action_editalista).setVisible(false);
        menu.findItem(R.id.action_novalista).setVisible(false);
        menu.findItem(R.id.action_limparbancodados).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_sair) {
            this.finish();
        }
        if(id == R.id.action_addProduto) {
            if(listaAtual.getId() == null)
                DAOListaCompras.insert(listaAtual);
            Intent it = new Intent(this, AddProdutoActivity.class);
            it.putExtra("id_listacompras", listaAtual.getId());
            startActivityForResult(it, AddProdutoRequest);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSalvarLista(View v){
        if(listaAtual.getId() == null)
            DAOListaCompras.insert(listaAtual);
        else
            DAOListaCompras.update(listaAtual);
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddProdutoRequest) {
            if(resultCode == Activity.RESULT_OK){
                DAOListaCompras = new ListaComprasDaoBd(this);
                listaAtual = DAOListaCompras.procurarPorId(data.getIntExtra("id_listacompras", -1));
                PopulaListView();
            }
        }
    }
}
