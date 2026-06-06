package arvoreBmais;

public class NoBmais {
    private int[] vInfo;
    private NoBmais[] vLig;
    private int TL;
    private boolean ehFolha;
    private NoBmais prox;
    private NoBmais ant;

    public NoBmais(int ordem, boolean ehFolha) {
        this.vInfo = new int[ordem];
        this.vLig = new NoBmais[ordem + 1];
        this.TL = 0;
        this.ehFolha = ehFolha;
        this.prox = null;
        this.ant = null;
    }

    public int getvInfo(int pos) {
        return vInfo[pos];
    }
    public void setvInfo(int pos, int info) {
        vInfo[pos] = info;
    }

    public NoBmais getvLig(int pos) {
        return vLig[pos];
    }
    public void setvLig(int pos, NoBmais lig) {
        vLig[pos] = lig;
    }

    public int getTL() {
        return TL;
    }
    public void setTL(int TL) {
        this.TL = TL;
    }

    public boolean isEhFolha() {
        return ehFolha;
    }
    public void setEhFolha(boolean ehFolha) {
        this.ehFolha = ehFolha;
    }

    public NoBmais getProx() {
        return prox;
    }
    public void setProx(NoBmais prox) {
        this.prox = prox;
    }

    public NoBmais getAnt() { return ant; }
    public void setAnt(NoBmais ant) {
        this.ant = ant;
    }

    public int procurarPosicao(int info) {
        int pos = 0;
        while (pos < TL && info >= vInfo[pos])
            pos++;
        return pos;
    }

    public void remanejar(int pos) {
        for (int i = TL + 1; i > pos; i--)
            vLig[i] = vLig[i - 1];
        for (int i = TL; i > pos; i--)
            vInfo[i] = vInfo[i - 1];
    }

    public void remanejarExclusao(int pos) {
        for (int i = pos; i < TL - 1; i++)
            vInfo[i] = vInfo[i + 1];
        for (int i = pos + 1; i < TL; i++)
            vLig[i] = vLig[i + 1];
        vLig[TL] = null;
    }
}