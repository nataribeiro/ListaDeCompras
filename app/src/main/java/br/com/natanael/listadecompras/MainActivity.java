package br.com.natanael.listadecompras;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.natanael.listadecompras.Estruturas.ListaCompras;
import br.com.natanael.listadecompras.Estruturas.ListaComprasItem;
import br.com.natanael.listadecompras.dao.bd.BancoDadosOpenHelper;
import br.com.natanael.listadecompras.dao.bd.ListaComprasDaoBd;
import br.com.natanael.listadecompras.dao.bd.ListaComprasItemDaoDb;

public class MainActivity extends AppCompatActivity {
    private final static int NovaListaComprasRequest = 1;
    private final static int EditListaComprasRequest = 2;

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

        InstanciaTabFragment();
    }

    public void onClickFinalizaCompra(View v){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Finalizar compra?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listaComprasAtual.finalizarLista(getBaseContext());
                ListaComprasDaoBd DAOListaCompras = new ListaComprasDaoBd(getBaseContext());
                DAOListaCompras.update(listaComprasAtual);
                InstanciaTabFragment();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void InstanciaTabFragment(){
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void AbrirActivityNovaListaCompras(int request){
        Intent it = new Intent(this, NovaListaComprasActivity.class);
        startActivityForResult(it, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (requestCode == NovaListaComprasRequest) {
            if(resultCode == Activity.RESULT_OK){
                InstanciaTabFragment();
            }
        }
        else if(requestCode == EditListaComprasRequest){
            if(resultCode == Activity.RESULT_OK){

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if(!ExisteListaCorrente) {
            MenuItem menuItem = menu.findItem(R.id.action_editalista);
            menuItem.setVisible(false);
        }
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
                AbrirActivityNovaListaCompras(NovaListaComprasRequest);
            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Deseja excluir a lista atual?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListaComprasDaoBd DAOListaCompras = new ListaComprasDaoBd(getBaseContext());
                        DAOListaCompras.excluir(listaComprasAtual);
                        AbrirActivityNovaListaCompras(NovaListaComprasRequest);
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
        if (id == R.id.action_editalista) {
               AbrirActivityNovaListaCompras(EditListaComprasRequest);
        }
        if (id == R.id.action_limparbancodados){
            BancoDadosOpenHelper bdHelper = new BancoDadosOpenHelper(this);
            bdHelper.ClearDatabase();
            Toast.makeText(this, "Base de dados foi zerada", Toast.LENGTH_SHORT).show();
            InstanciaTabFragment();
        }
        if (id == R.id.action_sair) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
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
        public void onResume() {
            super.onResume();
            ListaComprasDaoBd DAOListaCompras = new ListaComprasDaoBd(getContext());
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                ListaCompras listaCompras = DAOListaCompras.retornaListaNaoFinalizada();
                if(listaCompras != null) {
                    ListView listView = (ListView) this.getView().findViewById(R.id.listView_listaAtual);
                    ListaComprasAdapter adapter = new ListaComprasAdapter(this.getView().getContext(), listaCompras, true);
                    listView.setAdapter(adapter);
                }
            }
            else{
                List<ListaCompras> historico = DAOListaCompras.retornaListasFinalizadas();
                ListView listView = (ListView)this.getView().findViewById(R.id.listView_listaAtual);
                ListaHistoricoAdapter adapter = new ListaHistoricoAdapter(this.getView().getContext(), historico);
                listView.setAdapter(adapter);
                (this.getView().findViewById(R.id.button_finalizarCompra)).setVisibility(View.GONE);
            }
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
                    ListaComprasAdapter adapter = new ListaComprasAdapter(rootView.getContext(), listaCompras, true);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                            CheckBox checkBox_comprado = (CheckBox) view.findViewById(R.id.checkBox_comprado);
                            checkBox_comprado.setChecked(!checkBox_comprado.isChecked());
                        }
                    });
                } else {
                    ExisteListaCorrente = false;
                    (rootView.findViewById(R.id.button_finalizarCompra)).setVisibility(View.GONE);
                }
            }else {
                final List<ListaCompras> historico = DAOListaCompras.retornaListasFinalizadas();

                ListView listView = (ListView)rootView.findViewById(R.id.listView_listaAtual);
                ListaHistoricoAdapter adapter = new ListaHistoricoAdapter(rootView.getContext(), historico);
                listView.setAdapter(adapter);
                (rootView.findViewById(R.id.button_finalizarCompra)).setVisibility(View.GONE);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent it = new Intent(getContext(), VisualizadorListaCompras.class);
                        it.putExtra("id_lista", historico.get(position).getId());
                        startActivity(it);
                    }
                });
            }

            return rootView;
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {


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
