package arvoreBmais;

public class Bmais {
    private NoBmais raiz;
    private int ordem;

    // Recebemos a ordem dinamicamente para poder testar n=4 e n=5 depois!
    public Bmais(int ordem) {
        this.raiz = null;
        this.ordem = ordem;
    }

    // --- MÉTODOS DE NAVEGAÇÃO ---

    private NoBmais navegarAteFolha(int info) {
        NoBmais atual = this.raiz;
        while (!atual.isEhFolha()) {
            int pos = atual.procurarPosicao(info);
            atual = atual.getvLig(pos);
        }
        return atual;
    }

    private NoBmais localizarPai(NoBmais no, int info) {
        NoBmais pai = this.raiz;
        NoBmais atual = this.raiz;

        while (atual != no && !atual.isEhFolha()) {
            pai = atual;
            int pos = atual.procurarPosicao(info);
            atual = atual.getvLig(pos);
        }
        return pai;
    }

    // --- INSERÇÃO E SPLIT ---

    public void inserir(int info) {
        if (this.raiz == null) {
            this.raiz = new NoBmais(this.ordem, true);
            this.raiz.setvInfo(0, info);
            this.raiz.setTL(1);
        } else {
            NoBmais folha = navegarAteFolha(info);
            int pos = folha.procurarPosicao(info);

            // Abre espaço e insere na folha
            folha.remanejar(pos);
            folha.setvInfo(pos, info);
            folha.setTL(folha.getTL() + 1);

            // Se atingiu a ordem (Overflow temporário), precisa quebrar!
            if (folha.getTL() == this.ordem) {
                NoBmais pai = localizarPai(folha, folha.getvInfo(0));
                split(folha, pai);
            }
        }
    }

    private void split(NoBmais noAtual, NoBmais pai) {
        NoBmais novoNo = new NoBmais(this.ordem, noAtual.isEhFolha());
        int meio = this.ordem / 2;
        int infoPromovida;

        if (noAtual.isEhFolha()) {
            // Regra do Split de Folha: A chave do meio sobe, mas TAMBÉM fica na folha direita.
            infoPromovida = noAtual.getvInfo(meio);

            // Copia a metade direita para o novo nó
            int j = 0;
            for (int i = meio; i < this.ordem; i++) {
                novoNo.setvInfo(j, noAtual.getvInfo(i));
                novoNo.setTL(novoNo.getTL() + 1);
                j++;
            }
            noAtual.setTL(meio); // Corta o tamanho do nó original

            // Refaz as ligações da Lista Encadeada das folhas
            novoNo.setProx(noAtual.getProx());
            novoNo.setAnt(noAtual);
            if (noAtual.getProx() != null) {
                noAtual.getProx().setAnt(novoNo);
            }
            noAtual.setProx(novoNo);

        } else {
            // Regra do Split de Nó Interno: A chave do meio sobe e NÃO fica em nenhum dos dois.
            infoPromovida = noAtual.getvInfo(meio);

            // Copia a metade direita (pós-meio) para o novo nó
            int j = 0;
            for (int i = meio + 1; i < this.ordem; i++) {
                novoNo.setvInfo(j, noAtual.getvInfo(i));
                novoNo.setvLig(j, noAtual.getvLig(i));
                novoNo.setTL(novoNo.getTL() + 1);
                j++;
            }
            novoNo.setvLig(novoNo.getTL(), noAtual.getvLig(this.ordem)); // Puxa o último ponteiro
            noAtual.setTL(meio); // Corta o tamanho do nó original (o elemento do meio fica órfão, pois subiu)
        }

        // Agora, acomodamos a informação promovida no pai
        if (noAtual == this.raiz) {
            // Se quebrou a raiz, a árvore cresce para cima!
            NoBmais novaRaiz = new NoBmais(this.ordem, false);
            novaRaiz.setvInfo(0, infoPromovida);
            novaRaiz.setvLig(0, noAtual);
            novaRaiz.setvLig(1, novoNo);
            novaRaiz.setTL(1);
            this.raiz = novaRaiz;
        } else {
            // Insere no pai existente
            int pos = pai.procurarPosicao(infoPromovida);
            pai.remanejar(pos);
            pai.setvInfo(pos, infoPromovida);
            pai.setvLig(pos + 1, novoNo);
            pai.setTL(pai.getTL() + 1);

            // Verifica se o pai também estourou (Efeito cascata)
            if (pai.getTL() == this.ordem) {
                NoBmais avo = localizarPai(pai, pai.getvInfo(0));
                split(pai, avo);
            }
        }
    }

    // ==========================================
    // LETRA A - Exibir todas as folhas da árvore
    // ==========================================
    public void exibirFolhas() {
        if (this.raiz == null) {
            System.out.println("Árvore vazia.");
        } else {
            NoBmais atual = this.raiz;

            // 1. Desce em todas as ligações 0 até a folha (Conforme exigido no enunciado)
            while (!atual.isEhFolha()) {
                atual = atual.getvLig(0);
            }

            // 2. Percorre as folhas até o final usando a Lista Encadeada
            System.out.println("--- EXIBINDO FOLHAS ---");
            while (atual != null) {
                System.out.print("[ ");
                int i = 0;
                while (i < atual.getTL()) {
                    System.out.print(atual.getvInfo(i) + " ");
                    i++;
                }
                System.out.print("] -> ");
                atual = atual.getProx();
            }
            System.out.println("NULL\n");
        }
    }

    // ==========================================
    // LETRA B - Caminhamento In-Ordem
    // ==========================================
    public void emOrdem() {
        if (this.raiz == null) {
            System.out.println("Árvore vazia.");
        } else {
            System.out.println("--- CAMINHAMENTO IN-ORDEM ---");
            emOrdemRecursivo(this.raiz, 1);
            System.out.println();
        }
    }

    private void emOrdemRecursivo(NoBmais no, int nivel) {
        if (no != null) {
            int i = 0;
            // Varre intercalando: Filho à esquerda -> Chave -> Filho à direita
            while (i < no.getTL()) {
                if (!no.isEhFolha()) {
                    emOrdemRecursivo(no.getvLig(i), nivel + 1);
                }
                System.out.println("Nível " + nivel + " | Chave: " + no.getvInfo(i));
                i++;
            }
            // Visita o último filho (ponteiro mais à direita)
            if (!no.isEhFolha()) {
                emOrdemRecursivo(no.getvLig(no.getTL()), nivel + 1);
            }
        }
    }
}