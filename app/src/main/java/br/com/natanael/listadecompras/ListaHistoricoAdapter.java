package br.com.natanael.listadecompras;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.natanael.listadecompras.Estruturas.ListaCompras;

/**
 * Created by Natanael on 19/05/2016.
 */
public class ListaHistoricoAdapter extends BaseAdapter {

    List<ListaCompras> historico;
    Context contexto;

    public ListaHistoricoAdapter(Context contexto, List<ListaCompras> historico){
        this.contexto = contexto;
        this.historico = historico;
    }

    @Override
    public int getCount() {
        return historico.size();
    }

    @Override
    public Object getItem(int position) {
        return historico.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)contexto.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_historico_item, null);
            TextView nomeProd = (TextView) convertView.findViewById(R.id.id_dataCompra);
            nomeProd.setText(historico.get(position).getData());
            TextView valorProd = (TextView) convertView.findViewById(R.id.id_valorCompra);
            valorProd.setText(String.valueOf(historico.get(position).getValorTotalLista()));
        }

        return convertView;
    }

}
