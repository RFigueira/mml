package codepampa.com.br.mml.fragment;


import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import codepampa.com.br.mml.R;
import codepampa.com.br.mml.activity.ProdutosActivity;
import codepampa.com.br.mml.adapter.ProdutosAdapter;
import codepampa.com.br.mml.model.Produto;
import codepampa.com.br.mml.service.ProdutoService;

public class ProdutosFragment extends BaseFragment implements SearchView.OnQueryTextListener {

    protected RecyclerView reciclerview;
    private LinearLayoutManager linearLayoutManager;
    private ProdutoService produtoService;
    private List<Produto> produtos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getInstanciaService();

        setHasOptionsMenu(true);

        ((ProdutosActivity) getActivity()).getSupportActionBar().setTitle(R.string.produtos);

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == android.content.pm.PackageManager.PERMISSION_GRANTED) {
        }
    }

    private void getInstanciaService() {
        produtoService = ProdutoService.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_produtos, container, false);

        reciclerview = (RecyclerView) view.findViewById(R.id.recyclerview_fragment_produtos);

        linearLayoutManager = new LinearLayoutManager(getActivity());

        reciclerview.setLayoutManager(linearLayoutManager);
        reciclerview.setItemAnimator(new DefaultItemAnimator());
        reciclerview.setHasFixedSize(true);

        new ProdutosTask().execute();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_search_fragment_produtos, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search_produtos).getActionView();
        searchView.setQueryHint(getString(R.string.digite_aqui));
        searchView.setOnQueryTextListener(this);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    protected ProdutosAdapter.ProdutoOnClickListener onClickProduto() {
        //chama o contrutor da interface (implícito) para criar uma instância da interface declarada no adaptador.
        return new ProdutosAdapter.ProdutoOnClickListener() {
            // Aqui trata o evento onItemClick.
            @Override
            public void onClickProduto(View view, int idx) {
                //armazena o carro que foi clicado
                Produto produto = produtos.get(idx);
                //chama outra Activity para detalhar ou editar o carro clicado pelo usuário
                Intent intent = new Intent(getContext(), ProdutosActivity.class); //configura uma Intent explícita
                intent.putExtra("produto", produto); //inseri um extra com a referência para o objeto Carro
                startActivity(intent);
            }
        };
    }


    private class ProdutosTask extends AsyncTask<Void, Void, List<Produto>> { //<Params, Progress, Result>

        List<Produto> produtos;

        @Override
        protected List<Produto> doInBackground(Void... voids) {

            return produtoService.getAll();
        }

        @Override
        protected void onPostExecute(List<Produto> produtos) {
            super.onPostExecute(produtos);
            //copia a lista de carros para uso no onQueryTextChange()
            ProdutosFragment.this.produtos = produtos;
            //atualiza a view na UIThread
            reciclerview.setAdapter(new ProdutosAdapter(getContext(), produtos, onClickProduto())); //Context, fonte de dados, tratador do evento onClick
        }
    }//fim classe interna


}
