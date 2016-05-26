package br.com.natanael.listadecompras;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import br.com.natanael.listadecompras.Estruturas.ListaCompras;

/**
 * Created by Natanael on 16/05/2016.
 */
public class ListaComprasAdapter extends BaseAdapter {
    ListaCompras listaCompras;
    Context contexto;
    Boolean checkBoxHabilitado;

    public ListaComprasAdapter(Context contexto, ListaCompras listaCompras, Boolean checkBoxHabilitado){
        this.contexto = contexto;
        this.listaCompras = listaCompras;
        this.checkBoxHabilitado = checkBoxHabilitado;
    }

    @Override
    public int getCount() {
        return listaCompras.getListaItens().size();
    }

    @Override
    public Object getItem(int position) {
        return listaCompras.getListaItens().get(position);
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
            nomeProd.setText(listaCompras.getListaItens().get(position).getProduto().getNome());
            TextView valorProd = (TextView) convertView.findViewById(R.id.id_quantidadeProduto);
            valorProd.setText(String.valueOf(listaCompras.getListaItens().get(position).getQuantidade()));
            CheckBox checkBox_comprado = (CheckBox) convertView.findViewById(R.id.checkBox_comprado);
            checkBox_comprado.setChecked(listaCompras.getListaItens().get(position).getComprado());
            if(!checkBoxHabilitado)
                checkBox_comprado.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
