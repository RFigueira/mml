package codepampa.com.br.mml.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import codepampa.com.br.mml.R;
import codepampa.com.br.mml.model.Produto;
import codepampa.com.br.mml.util.Util;


public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.ProdutosViewHolder> {


    private final List<Produto> produtos;
    private final Context context;

    private final ProdutoOnClickListener produtoOnClickListener;


    public ProdutosAdapter(Context context, List<Produto> produtos, ProdutoOnClickListener produtoOnClickListener) {
        this.context = context;
        this.produtos = produtos;
        this.produtoOnClickListener = produtoOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.produtos != null ? this.produtos.size() : 0;
    }

    @Override
    public ProdutosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_produtos, viewGroup, false);
        return new ProdutosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProdutosViewHolder produtosViewHolder, final int position) {

        Produto produto = produtos.get(position);
        produtosViewHolder.textViewNome.setText(produto.nome);
        produtosViewHolder.progressBar.setVisibility(View.VISIBLE);

        if(!Util.isNullOrEmpty(produto.urlImagem)){
            produtosViewHolder.imageView.setImageURI(Uri.parse(produto.urlImagem));
        }else{
            produtosViewHolder.imageView.setImageResource(R.drawable.ic_menu_camera);
        }

        if (!Util.isObjectsNull(produtoOnClickListener)) {
            produtosViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    produtoOnClickListener.onClickProduto(produtosViewHolder.itemView, position);
                }
            });
        }

        produtosViewHolder.progressBar.setVisibility(View.INVISIBLE);
    }


    public interface ProdutoOnClickListener {
         void onClickProduto(View view, int idx);
    }

    public static class ProdutosViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNome;
        ImageView imageView;
        ProgressBar progressBar;

        public ProdutosViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            textViewNome = (TextView) view.findViewById(R.id.cardview_adapter_titulo_produto);
            imageView = (ImageView) view.findViewById(R.id.cardview_adapter_imagem);
            progressBar = (ProgressBar) view.findViewById(R.id.cardview_adapter_prograss_bar);
        }
    }
}
