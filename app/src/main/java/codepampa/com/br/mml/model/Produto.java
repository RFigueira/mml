package codepampa.com.br.mml.model;


import java.io.Serializable;
import java.math.BigDecimal;

public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long _id;
    public String nome;
    public BigDecimal preco;
    public String marca;
    public String localCompra;
    public String urlImagem;

    public Produto() {

    }

    public Produto(String nome, BigDecimal preco, String marca, String localCompra, String urlImagem) {

        this.nome = nome;
        this.preco = preco;
        this.marca = marca;
        this.localCompra = localCompra;
        this.urlImagem = urlImagem;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "_id=" + _id +
                ", nome='" + nome + '\'' +
                ", preco='" + preco + '\'' +
                ", marca='" + marca + '\'' +
                ", localCompra='" + localCompra + '\'' +
                ", imagem=" + urlImagem +
                '}';
    }
}
