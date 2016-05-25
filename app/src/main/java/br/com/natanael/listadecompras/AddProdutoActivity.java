package br.com.natanael.listadecompras;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import br.com.natanael.listadecompras.Estruturas.ListaComprasItem;
import br.com.natanael.listadecompras.Estruturas.Produto;
import br.com.natanael.listadecompras.dao.bd.ListaComprasItemDaoDb;
import br.com.natanael.listadecompras.dao.bd.ProdutoDaoBd;

public class AddProdutoActivity extends AppCompatActivity {
    ProdutoDaoBd DAOProduto = new ProdutoDaoBd(this);
    ListaComprasItemDaoDb DAOListaItem = new ListaComprasItemDaoDb(this);
    private int id_listacompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produto);

        Intent intent = getIntent();
        id_listacompras = intent.getIntExtra("id_listacompras", -1);
    }

    public void onClickAdicionarProduto(View v){
        EditText editText_nomeProduto = (EditText)findViewById(R.id.editText_nomeproduto);
        EditText editText_quantidadeProduto = (EditText)findViewById(R.id.editText_quantidade);
        if(editText_nomeProduto.getText().toString().equals("")) {
            editText_nomeProduto.requestFocus();
            Toast.makeText(this, "Preencha o nome do produto", Toast.LENGTH_SHORT).show();
            return;
        }
        if(editText_quantidadeProduto.getText().toString().equals("")){
            editText_quantidadeProduto.requestFocus();
            Toast.makeText(this, "Informe a quantidade do produto", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantidade = Integer.parseInt(editText_quantidadeProduto.getText().toString());
        Produto produto = DAOProduto.procurarPorNome(editText_nomeProduto.getText().toString());
        if(produto == null) {
            produto = new Produto(editText_nomeProduto.getText().toString());
            DAOProduto.insert(produto);
        }
        ListaComprasItem item = new ListaComprasItem(produto, quantidade);
        item.setListaComprasId(id_listacompras);
        DAOListaItem.insert(item);
        Intent it = new Intent();
        it.putExtra("id_listacompras", id_listacompras);
        setResult(Activity.RESULT_OK, it);
        this.finish();
    }

    public void onClickCancelar(View v){
        setResult(Activity.RESULT_CANCELED);
        this.finish();
    }
}
