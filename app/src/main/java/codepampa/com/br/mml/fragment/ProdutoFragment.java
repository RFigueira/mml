package codepampa.com.br.mml.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.math.BigDecimal;

import codepampa.com.br.mml.Enum.Operacao;
import codepampa.com.br.mml.R;
import codepampa.com.br.mml.activity.ProdutoActivity;
import codepampa.com.br.mml.model.Produto;
import codepampa.com.br.mml.service.ProdutoService;
import codepampa.com.br.mml.util.Util;

public class ProdutoFragment extends BaseFragment {

    public ProdutoFragment(){
    }

    public ProdutoFragment(Produto produto) {
        this.produto = produto;
    }

    private Produto produto;

    private ImageView imageViewFoto;
    private EditText editTextNomeProduto;
    private EditText editTextMarca;
    private EditText editTextLocalCompra;
    private EditText editTextValorProduto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_produto, container, false);

        acoes(view);

        return  view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_salvar:{
                if(validarInputs()) {
                    popularProduto();
                    new ProdutosTask().execute(Operacao.SAVE); //executa a operação CREATE em uma thread AsyncTask
                }
                break;

            }
            case R.id.menuitem_excluir:{
                new ProdutosTask().execute(Operacao.DELETE);
                break;
            }
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return false;
    }

        private boolean validarInputs(){
            String msg = "";
            if (editTextValorProduto.getText().toString().trim().isEmpty()) {
                msg += "Campo Preço obrigatorio\n";
                editTextValorProduto.requestFocus();
            }
            if (editTextMarca.getText().toString().trim().isEmpty()) {
                msg += "Campo Marca obrigatorio\n";
                editTextMarca.requestFocus();
            }
            if(editTextNomeProduto.getText().toString().trim().isEmpty()) {
                msg += "Campo Nome obrigatorio\n";
                editTextNomeProduto.requestFocus();
            }
            if (editTextLocalCompra.getText().toString().trim().isEmpty()) {
                msg += "Campo Local Compra obrigatorio\n";
                editTextLocalCompra.requestFocus();
            }

            if(msg.trim().length() > 0){
                toast(msg);
                return false;
            }
            return true;
    }

    private void popularProduto() {
        produto.nome = editTextNomeProduto.getText().toString();
        produto.marca = editTextMarca.getText().toString();
        produto.localCompra = editTextLocalCompra.getText().toString();
        produto.preco = new BigDecimal(editTextValorProduto.getText().toString());
    }
    private void popularTelaProduto() {

        editTextNomeProduto.setText(produto.nome);
        editTextMarca.setText(produto.marca);
        editTextLocalCompra.setText(produto.localCompra);
        editTextValorProduto.setText(produto.preco.toString());

    }

    private void mapearInputs(View view) {
            editTextNomeProduto = (EditText) view.findViewById(R.id.nome_produto_fragmentproduto);
            editTextMarca = (EditText) view.findViewById(R.id.marca_produto_fragmentproduto);
            editTextLocalCompra = (EditText) view.findViewById(R.id.local_compra_fragmentproduto);
            editTextValorProduto = (EditText) view.findViewById(R.id.valor_produto_fragmentproduto);
            imageViewFoto = (ImageView) view.findViewById(R.id.image_view_fragmentproduto);
            if(produto.urlImagem != null){
                imageViewFoto.setImageURI(Uri.parse(produto.urlImagem));
            }
    }

    private void editarProduto(View view) {
        mapearInputs(view);
        popularTelaProduto();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_acoes, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == getActivity().RESULT_OK){
            Uri arquivoUri = data.getData();

            if(arquivoUri.toString().contains("images")) {
                imageViewFoto.setImageURI(arquivoUri);
                produto.urlImagem = arquivoUri.toString();
            }

        }

    }

    private void acoes(View view) {

        if(Util.isObjectsNull(produto)) {
            produto = new Produto();
            novoProduto(view);
        } else {
            editarProduto(view);
        }

    }


    private void novoProduto(View view) {
        ((ProdutoActivity) getActivity()).getSupportActionBar().setTitle(R.string.produto);
        imageViewFoto = (ImageView) view.findViewById(R.id.image_view_fragmentproduto);
        imageViewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 0);
            }
        });

        editTextNomeProduto = (EditText) view.findViewById(R.id.nome_produto_fragmentproduto);
        editTextMarca = (EditText) view.findViewById(R.id.marca_produto_fragmentproduto);
        editTextValorProduto = (EditText) view.findViewById(R.id.valor_produto_fragmentproduto);
        editTextLocalCompra = (EditText) view.findViewById(R.id.local_compra_fragmentproduto);

    }



    private class ProdutosTask extends AsyncTask<Operacao, Void, Long> {

        @Override
        protected Long doInBackground(Operacao... operacao) {
            if(operacao[0].isSave()){
                return ProdutoService.getInstance(getContext()).save(produto);
            } else if(operacao[0].isDelete()){
                return ProdutoService.getInstance(getContext()).excluir(produto);
            }
            return null; // tenho que retornar pq la no service eu retorno um log
            //TODO: Melhor isso
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertWait(R.string.title_wait, R.string.title_wait);
        }


        @Override
        protected void onPostExecute(Long cont) {
            super.onPostExecute(cont);
            alertWaitDismiss();
            if(cont > 0){
                alertOk(R.string.title_resultado_operacao, R.string.title_realizado_com_sucesso);
            }else{
                alertOk(R.string.title_resultado_operacao, R.string.title_erro_operacao);
            }
        }
    }


}
