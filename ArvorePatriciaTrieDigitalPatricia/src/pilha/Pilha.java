package pilha;

import arvorePatricia.NoPatricia;

public class Pilha {
    private NoPilha topo;

    public Pilha() {
        this.topo = null;
    }

    public void push(NoPatricia info) {
        NoPilha novo = new NoPilha(info);
        novo.setProx(this.topo);
        this.topo = novo;
    }

    public NoPatricia pop() {
        NoPatricia noRetornado = null;
        if (!isEmpty()) {
            noRetornado = this.topo.getInfo();
            this.topo = this.topo.getProx();
        }
        return noRetornado;
    }

    public NoPatricia peek() {
        if (isEmpty()) {
            return null;
        }
        return this.topo.getInfo();
    }

    public boolean isEmpty() {
        return this.topo == null;
    }
}