package com.example.pizzariadagiovanna;

public class Sabor {
    private String nome;
    private int imagemResId;

    public Sabor(String nome, int imagemResId) {
        this.nome = nome;
        this.imagemResId = imagemResId;
    }

    public String getNome() {
        return nome;
    }

    public int getImagemResId() {
        return imagemResId;
    }
}
