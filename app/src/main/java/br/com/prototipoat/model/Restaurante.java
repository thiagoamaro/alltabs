package br.com.prototipoat.model;

import org.json.JSONObject;

import java.io.Serializable;

public class Restaurante implements Serializable{
    private String id;
    private String nome;
    private Integer logo;
    private String rank;
    private String descricao;

    public Restaurante() {
        setNome("");
    }

    public Restaurante(String linha){
        String[] dados = linha.split(";");
        setId(dados[0]);
        setNome(dados[1]);
        setRank(dados[2]);
        setDescricao(dados[3]);
    }

    public Restaurante(String id, String nome, Integer logo, String rank, String descricao) {
        this.id = id;
        this.nome = nome;
        this.logo = logo;
        this.rank = rank;
        this.descricao = descricao;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getLogo() {
        return logo;
    }

    public void setLogo(Integer logo) {
        this.logo = logo;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return getNome() + getRank();
    }

    public String toJson(){
        JSONObject obj = new JSONObject();


        try	{
            obj.put("id", getId());
            obj.put("nome", getNome());
            obj.put("logo", getLogo());
            obj.put("rank", getRank());
            obj.put("descricao", getDescricao());
        }	catch(Exception e)	{

        }
        return obj.toString();
    }
}
