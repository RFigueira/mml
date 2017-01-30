package codepampa.com.br.mml.Enum;


public enum Operacao {

    SAVE("Salvar"), EDIT("Editar"),
    DELETE("Excluir"), FIND("Buscar");

    private final String nomePattern;

    Operacao(String nomePattern) {
        this.nomePattern = nomePattern;
    }

    public boolean isSave(){
       return this == SAVE;
    }
    public boolean isEdit(){
       return this == EDIT;
    }
    public boolean isDelete(){
       return this == DELETE;
    }
    public boolean isFind(){
       return this == FIND;
    }

}
