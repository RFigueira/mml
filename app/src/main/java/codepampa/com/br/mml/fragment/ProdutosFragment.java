package codepampa.com.br.mml.fragment;


import android.Manifest;
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

import codepampa.com.br.mml.R;
import codepampa.com.br.mml.activity.ProdutosActivity;

public class ProdutosFragment extends BaseFragment implements SearchView.OnQueryTextListener {

    private RecyclerView reciclerview;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        ((ProdutosActivity) getActivity()).getSupportActionBar().setTitle(R.string.produtos);

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == android.content.pm.PackageManager.PERMISSION_GRANTED) {
        }
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



}
