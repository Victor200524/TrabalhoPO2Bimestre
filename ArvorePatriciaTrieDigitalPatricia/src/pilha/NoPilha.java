package pilha;

import arvorePatricia.NoPatricia;

public class NoPilha {
    private NoPatricia info;
    private NoPilha prox;

    public NoPilha(NoPatricia info) {
        this.info = info;
        this.prox = null;
    }

    public NoPatricia getInfo() {
        return info;
    }

    public void setInfo(NoPatricia info) {
        this.info = info;
    }

    public NoPilha getProx() {
        return prox;
    }

    public void setProx(NoPilha prox) {
        this.prox = prox;
    }
}