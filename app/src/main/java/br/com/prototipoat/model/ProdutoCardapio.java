package br.com.prototipoat.model;


public class ProdutoCardapio {

    private int idItem;
    private String categoriaItem;
    private String nomeItem;
    private Float precoItem;

    public ProdutoCardapio(){
    }

    public ProdutoCardapio(int idItem, String categoriaItem, String nomeItem, Float precoItem) {
        this.idItem = idItem;
        this.categoriaItem = categoriaItem;
        this.nomeItem = nomeItem;
        this.precoItem = precoItem;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getCategoriaItem() {
        return categoriaItem;
    }

    public void setCategoriaItem(String categoriaItem) {
        this.categoriaItem = categoriaItem;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public Float getPrecoItem() {
        return precoItem;
    }

    public void setPrecoItem(Float precoItem) {
        this.precoItem = precoItem;
    }

    @Override
    public String toString() {
        return String.valueOf(getIdItem());
    }
}
