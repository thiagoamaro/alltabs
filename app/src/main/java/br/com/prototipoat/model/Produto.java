package br.com.prototipoat.model;


public class Produto {

    private int id;
    private String categoria;
    private String nome;
    private Float preco;
    private String data;
    private String status;


    public Produto() {
    }

    public Produto(int id, String categoria, String nome, Float preco, String data, String status) {
        this.id = id;
        this.categoria = categoria;
        this.nome = nome;
        this.preco = preco;
        this.data = data;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Nome: " + getNome() + "Preço: " + getPreco() + "Status: " + getStatus();
    }
}
