package br.com.prototipoat.controller;

import java.text.Normalizer;

public class Biblioteca {

    public static String parserNome (String nome) {

        nome = nome.toLowerCase().trim();
        nome = nome.replaceAll(" ", "_");

        return Normalizer.normalize(nome, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]","");
    }
}
