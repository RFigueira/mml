package codepampa.com.br.mml.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import codepampa.com.br.mml.R;
import codepampa.com.br.mml.fragment.ProdutoFragment;
import codepampa.com.br.mml.model.Produto;
import codepampa.com.br.mml.util.Util;

public class ProdutoActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        Produto produto = (Produto) getIntent().getSerializableExtra("produto");

        if(!Util.isObjectsNull(produto)) {
            replaceFragment(R.id.content_main, new ProdutoFragment(produto));
        } else {
            replaceFragment(R.id.content_main, new ProdutoFragment());
        }

    }

}
