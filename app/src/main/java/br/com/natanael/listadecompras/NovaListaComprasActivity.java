package br.com.natanael.listadecompras;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.natanael.listadecompras.Estruturas.ListaCompras;
import br.com.natanael.listadecompras.Estruturas.ListaComprasItem;
import br.com.natanael.listadecompras.dao.bd.ListaComprasDaoBd;
import br.com.natanael.listadecompras.dao.bd.ListaComprasItemDaoDb;

public class NovaListaComprasActivity extends AppCompatActivity {
    private final static int AddProdutoRequest = 1;
    private final static int EditProdutoRequest = 2;

    private static ListaCompras listaAtual;
    ListaComprasDaoBd DAOListaCompras = new ListaComprasDaoBd(this);
    ListaComprasItemDaoDb DAOListaItem = new ListaComprasItemDaoDb(this);

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
        ListaComprasAdapter adapter = new ListaComprasAdapter(this, listaAtual, false);
        ListView listView = (ListView)findViewById(R.id.listView_addedProducts);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
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
        setResult(RESULT_OK);
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (requestCode == AddProdutoRequest || requestCode == EditProdutoRequest) {
            if(resultCode == Activity.RESULT_OK){
                DAOListaCompras = new ListaComprasDaoBd(this);
                listaAtual = DAOListaCompras.procurarPorId(data.getIntExtra("id_listacompras", -1));
                PopulaListView();
            }
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Editar");
        menu.add(0, v.getId(), 0, "Excluir");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        if (item.getTitle().equals("Editar")) {

            Intent it = new Intent(getBaseContext(), AddProdutoActivity.class);
            it.putExtra("id_listacompras", listaAtual.getId());
            it.putExtra("edit_produto", listaAtual.getListaItens().get(info.position).getId());
            startActivityForResult(it, EditProdutoRequest);

        } else if(item.getTitle().equals("Excluir")) {

            int id = listaAtual.getListaItens().get(info.position).getId();
            listaAtual.getListaItens().remove(info.position);
            ListaComprasItem objItem = DAOListaItem.procurarPorId(id);
            DAOListaItem.excluir(objItem);
            PopulaListView();

        } else {
            return false;
        }
        return true;
    }
}
