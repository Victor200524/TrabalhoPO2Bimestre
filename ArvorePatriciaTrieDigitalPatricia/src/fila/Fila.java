package fila;

import arvorePatricia.NoPatricia;

public class Fila {
    private NoFila inicio;
    private NoFila fim;

    public Fila() {
        this.inicio = null;
        this.fim = null;
    }

    public boolean isEmpty() {
        return this.inicio == null;
    }

    public void enqueue(NoPatricia info, int nivel) {
        NoFila novo = new NoFila(info, nivel);
        if (isEmpty()) {
            this.inicio = novo;
            this.fim = novo;
        } else {
            this.fim.setProx(novo);
            this.fim = novo;
        }
    }

    public NoFila dequeue() {
        NoFila retornado = null;
        if (!isEmpty()) {
            retornado = this.inicio;
            this.inicio = this.inicio.getProx();
            if (this.inicio == null) {
                this.fim = null;
            }
        }
        return retornado;
    }
}