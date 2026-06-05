package arvoreBmais;

public class NoBmais {
    private int[] vInfo;       // Chaves
    private NoBmais[] vLig;    // Ponteiros para os filhos
    private int TL;            // Tamanho Lógico (quantas chaves tem agora)
    private boolean ehFolha;   // Flag mágica que substitui a herança e o polimorfismo
    private NoBmais prox;      // Ponteiro para a próxima folha (Lista Encadeada)
    private NoBmais ant;       // Ponteiro para a folha anterior

    public NoBmais(int ordem, boolean ehFolha) {
        // O pulo do gato: criamos os vetores com capacidade igual à ordem (N).
        // Se N=4, o nó suporta temporariamente 4 elementos antes de fazer o Split.
        this.vInfo = new int[ordem];
        this.vLig = new NoBmais[ordem + 1];
        this.TL = 0;
        this.ehFolha = ehFolha;
        this.prox = null;
        this.ant = null;
    }

    // --- Getters e Setters Básicos ---
    public int getvInfo(int pos) { return vInfo[pos]; }
    public void setvInfo(int pos, int info) { vInfo[pos] = info; }

    public NoBmais getvLig(int pos) { return vLig[pos]; }
    public void setvLig(int pos, NoBmais lig) { vLig[pos] = lig; }

    public int getTL() { return TL; }
    public void setTL(int TL) { this.TL = TL; }

    public boolean isEhFolha() { return ehFolha; }
    public void setEhFolha(boolean ehFolha) { this.ehFolha = ehFolha; }

    public NoBmais getProx() { return prox; }
    public void setProx(NoBmais prox) { this.prox = prox; }

    public NoBmais getAnt() { return ant; }
    public void setAnt(NoBmais ant) { this.ant = ant; }

    // --- Métodos Auxiliares Inteligentes ---

    // Retorna a posição exata onde uma chave deve entrar ou por qual ponteiro descer
    public int procurarPosicao(int info) {
        int pos = 0;
        while (pos < TL && info >= vInfo[pos]) {
            pos++;
        }
        return pos;
    }

    // "Abre espaço" empurrando as chaves e os ponteiros para a direita
    public void remanejar(int pos) {
        for (int i = TL + 1; i > pos; i--) {
            vLig[i] = vLig[i - 1];
        }
        for (int i = TL; i > pos; i--) {
            vInfo[i] = vInfo[i - 1];
        }
    }

    // "Fecha o buraco" após uma exclusão puxando tudo para a esquerda
    public void remanejarExclusao(int pos) {
        for (int i = pos; i < TL - 1; i++) {
            vInfo[i] = vInfo[i + 1];
        }
        for (int i = pos + 1; i < TL; i++) {
            vLig[i] = vLig[i + 1];
        }
        vLig[TL] = null; // Limpa o último ponteiro duplicado
    }
}