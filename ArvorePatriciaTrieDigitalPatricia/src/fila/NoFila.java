package fila;

import arvorePatricia.NoPatricia;

public class NoFila {
    private NoPatricia info;
    private int nivel;
    private NoFila prox;

    public NoFila(NoPatricia info, int nivel) {
        this.info = info;
        this.nivel = nivel;
        this.prox = null;
    }

    public NoPatricia getInfo() {
        return info;
    }

    public int getNivel() {
        return nivel;
    }

    public NoFila getProx() {
        return prox;
    }

    public void setProx(NoFila prox) {
        this.prox = prox;
    }
}