package br.com.natanael.listadecompras;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.natanael.listadecompras.Estruturas.ListaCompras;
import br.com.natanael.listadecompras.Estruturas.ListaComprasItem;
import br.com.natanael.listadecompras.Estruturas.Produto;
import br.com.natanael.listadecompras.dao.bd.ListaComprasDaoBd;

public class MainActivity extends AppCompatActivity {
    private static Boolean ExisteListaCorrente;
    private static ListaCompras listaComprasAtual;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_novalista) {
            if(!ExisteListaCorrente) {
                AbrirActivityNovaListaCompras();
            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Deseja excluir a lista atual?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListaComprasDaoBd DAOListaCompras = new ListaComprasDaoBd(getBaseContext());
                        DAOListaCompras.excluir(listaComprasAtual);
                        AbrirActivityNovaListaCompras();
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        }
        if (id == R.id.action_historico) {
            return true;
        }
        if (id == R.id.action_config) {
            return true;
        }
        if (id == R.id.action_sair) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void AbrirActivityNovaListaCompras(){
        Intent it = new Intent(this, NovaListaComprasActivity.class);
        startActivity(it);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ListaComprasDaoBd DAOListaCompras = new ListaComprasDaoBd(getContext());

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                ListaCompras listaCompras = DAOListaCompras.retornaListaNaoFinalizada();

                if(listaCompras != null) {
                    listaComprasAtual = listaCompras;
                    ExisteListaCorrente = true;
                    ListView listView = (ListView) rootView.findViewById(R.id.listView_listaAtual);
                    ListaComprasAdapter adapter = new ListaComprasAdapter(rootView.getContext(), listaCompras);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                            //TODO Abrir fragment de cadastro de valor
                            view.setBackgroundColor(Color.GREEN);
                        }
                    });
                } else {
                    ExisteListaCorrente = false;
                }
            }else {
                List<ListaCompras> historico = DAOListaCompras.retornaListasFinalizadas();

                ListView listView = (ListView)rootView.findViewById(R.id.listView_listaAtual);
                ListaHistoricoAdapter adapter = new ListaHistoricoAdapter(rootView.getContext(), historico);
                listView.setAdapter(adapter);
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Lista Atual";
                case 1:
                    return "Listas anteriores";
            }
            return null;
        }
    }
}
