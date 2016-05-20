package br.com.natanael.listadecompras;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.natanael.listadecompras.Estruturas.ListaCompras;

/**
 * Created by Natanael on 16/05/2016.
 */
public class ListaComprasAdapter extends BaseAdapter {
    ListaCompras listaCompras;
    Context contexto;

    public ListaComprasAdapter(Context contexto, ListaCompras listaCompras){
        this.contexto = contexto;
        this.listaCompras = listaCompras;
    }

    @Override
    public int getCount() {
        return listaCompras.getListaProdutos().size();
    }

    @Override
    public Object getItem(int position) {
        return listaCompras.getListaProdutos().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)contexto.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            TextView nomeProd = (TextView) convertView.findViewById(R.id.id_nomeProduto);
            nomeProd.setText(listaCompras.getListaProdutos().get(position).getNome());
            TextView valorProd = (TextView) convertView.findViewById(R.id.id_quantidadeProduto);
            valorProd.setText(String.valueOf(listaCompras.getListaProdutos().get(position).getQuantidade()));
        }

        return convertView;
    }
}
