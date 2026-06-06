package arvoreBmais;

public class Bmais {
    private NoBmais raiz;
    private int ordem;

    public Bmais(int ordem) {
        this.raiz = null;
        this.ordem = ordem;
    }

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


    public void inserir(int info) {
        if (this.raiz == null) {
            this.raiz = new NoBmais(this.ordem, true);
            this.raiz.setvInfo(0, info);
            this.raiz.setTL(1);
        }
        else {
            NoBmais folha = navegarAteFolha(info);
            int pos = folha.procurarPosicao(info);
            folha.remanejar(pos);
            folha.setvInfo(pos, info);
            folha.setTL(folha.getTL() + 1);

            if (folha.getTL() == this.ordem) {
                NoBmais pai = localizarPai(folha, folha.getvInfo(0));
                split(folha, pai);
            }
        }
    }

    private void split(NoBmais noAtual, NoBmais pai) {
        NoBmais novoNo = new NoBmais(this.ordem, noAtual.isEhFolha());
        int meio = this.ordem / 2;
        int folhaSubiu;

        if (noAtual.isEhFolha()) {
            // A chave do meio sobe, mas fica na folha direita
            folhaSubiu = noAtual.getvInfo(meio);

            // Copia a metade direita para o novo nó
            int j = 0;
            for (int i = meio; i < this.ordem; i++) {
                novoNo.setvInfo(j, noAtual.getvInfo(i));
                novoNo.setTL(novoNo.getTL() + 1);
                j++;
            }
            noAtual.setTL(meio); // Corta o tamanho do nó original

            // Refaz as ligações das folhas
            novoNo.setProx(noAtual.getProx());
            novoNo.setAnt(noAtual);
            if (noAtual.getProx() != null) {
                noAtual.getProx().setAnt(novoNo);
            }
            noAtual.setProx(novoNo);
        }
        else {
            // A chave do meio sobe, so que nao fica em nenhum dos dois, nem na esquerda e nem na direita
            folhaSubiu = noAtual.getvInfo(meio);
            // Copia a metade direita para o novo nó
            int j = 0;
            for (int i = meio + 1; i < this.ordem; i++) {
                novoNo.setvInfo(j, noAtual.getvInfo(i));
                novoNo.setvLig(j, noAtual.getvLig(i));
                novoNo.setTL(novoNo.getTL() + 1);
                j++;
            }
            novoNo.setvLig(novoNo.getTL(), noAtual.getvLig(this.ordem)); // Puxa o último ponteiro
            noAtual.setTL(meio); // Corta o tamanho do nó original, dai o elemento do meio fica "órfão" por conta que ele subiu
        }

        // Colocamos as informação que veio do pai
        if (noAtual == this.raiz) {
            // Se caso quebrou a raiz, dai nesse caso a arvore vai crescer para cima
            NoBmais novaRaiz = new NoBmais(this.ordem, false);
            novaRaiz.setvInfo(0, folhaSubiu);
            novaRaiz.setvLig(0, noAtual);
            novaRaiz.setvLig(1, novoNo);
            novaRaiz.setTL(1);
            this.raiz = novaRaiz;
        }
        else {
            // Insere no pai existente
            int pos = pai.procurarPosicao(folhaSubiu);
            pai.remanejar(pos);
            pai.setvInfo(pos, folhaSubiu);
            pai.setvLig(pos + 1, novoNo);
            pai.setTL(pai.getTL() + 1);
            // Verifica se o pai também estourou, seria o efeito cascata visto em aula
            if (pai.getTL() == this.ordem) {
                NoBmais avo = localizarPai(pai, pai.getvInfo(0));
                split(pai, avo);
            }
        }
    }

    // LETRA A
    public void exibirFolhas() {
        if (this.raiz == null)
            System.out.println("Árvore vazia");
        else {
            NoBmais atual = this.raiz;
            // Desce em todas as ligações 0 até a folha
            while (!atual.isEhFolha())
                atual = atual.getvLig(0);

            System.out.println("--- Exibindo Folhas ---");
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

    // LETRA B
    public void emOrdem() {
        if (this.raiz == null)
            System.out.println("Árvore vazia");
        else {
            emOrdemRecursivo(this.raiz);
            System.out.println();
        }
    }

    private void emOrdemRecursivo(NoBmais no) {
        if (no != null){
            if (no.isEhFolha()) {
                for (int i = 0; i < no.getTL(); i++) {
                    System.out.print("[" + no.getvInfo(i) + "]" + "->");
                }
            }
            else {
                for (int i = 0; i < no.getTL(); i++) {
                    emOrdemRecursivo(no.getvLig(i));
                }
                emOrdemRecursivo(no.getvLig(no.getTL()));
            }
        }
        else
            System.out.println("NULL");
    }

    // LETRA C (Exclusão)
    public void excluir(int info) {
        if (this.raiz == null) {
            System.out.println("Árvore vazia");
        }
        else {
            NoBmais folha = navegarAteFolha(info);
            int pos = -1;

            // Procura a chave na folha
            int i = 0;
            while (i < folha.getTL()) {
                if (folha.getvInfo(i) == info) {
                    pos = i;
                }
                i++;
            }

            // Chave não existe na árvore
            if (pos == -1)
                System.out.println("Chave " + info + " não encontrada");

            else {
                // Remove a chave da folha e fecha o buraco
                folha.remanejarExclusao(pos);
                folha.setTL(folha.getTL() - 1);

                // Se a raiz ficou vazia, a árvore esvazia
                if (folha == this.raiz && folha.getTL() == 0) {
                    this.raiz = null;

                }
                else if (folha != this.raiz) {
                    int minimo = (this.ordem + 1) / 2 - 1;
                    // Se a folha ficou abaixo do mínimo, precisa corrigir (underflow)
                    if (folha.getTL() < minimo) {
                        tratarIrmas(folha);
                    }
                    else if (pos == 0) {
                        // A chave removida era a primeira da folha,
                        // pode existir uma cópia dela em algum nó interno que precisa ser atualizada
                        atualizarNosInternos(info, folha.getTL() > 0 ? folha.getvInfo(0) : -1, folha);
                    }
                }
            }
        }
    }

    // Essa função, vai subir pela árvore atualizando cópias da chave removida nos nós internos
    private void atualizarNosInternos(int infoAntiga, int infoNova, NoBmais folha) {
        if (infoNova != -1) { // Se a não folha ficou vazia após a remoção atualiza, se não, não faz nada
            NoBmais atual = this.raiz;
            // Percorre os nós internos procurando a chave antiga para substituir
            while (!atual.isEhFolha()) {
                int j = 0;
                while (j < atual.getTL()) {
                    if (atual.getvInfo(j) == infoAntiga) {
                        atual.setvInfo(j, infoNova);
                    }
                    j++;
                }
                int pos = atual.procurarPosicao(infoAntiga);
                atual = atual.getvLig(pos);
            }
        }
    }

    // Essa função vai tabalhar em cima do redistribuir ou concatenar das respectivas irmãs, direita/esquerda
    private void tratarIrmas(NoBmais no) {
        NoBmais pai;
        // localizarPai precisa de uma chave do nó para navegar até ele
        if (no.getTL() > 0) {
            pai = localizarPai(no, no.getvInfo(0));
        } else {
            pai = localizarPai(no, -1);
        }
        int minimo = (this.ordem + 1) / 2 - 1;
        // Descobre em qual posição o nó atual está no pai
        int posPai = 0;
        while (posPai <= pai.getTL() && pai.getvLig(posPai) != no) {
            posPai++;
        }

        NoBmais irmaEsq;
        NoBmais irmaDir;

        if (posPai > 0)
            irmaEsq = pai.getvLig(posPai - 1);
        else
            irmaEsq = null;

        if (posPai < pai.getTL())
            irmaDir = pai.getvLig(posPai + 1);
        else
            irmaDir = null;

        if (irmaEsq != null && irmaEsq.getTL() > minimo) // Tenta redistribuir com a irmã esquerda primeiro
            redistribuirDaEsquerda(no, irmaEsq, pai, posPai);
        else if (irmaDir != null && irmaDir.getTL() > minimo) // Tenta redistribuir com a irmã direita
            redistribuirDaDireita(no, irmaDir, pai, posPai);
        else if (irmaEsq != null) // Se caso nenhuma irmã tem uma chave sobrando, então concatena com a esquerda
            concatenar(irmaEsq, no, pai, posPai - 1);
        else // Ou se não concatena com a irmã direita
            concatenar(no, irmaDir, pai, posPai);
    }

    // Pega a última chave da irmã esquerda e coloca no início do nó atual
    private void redistribuirDaEsquerda(NoBmais no, NoBmais irmaEsq, NoBmais pai, int posPai) {
        if (no.isEhFolha()) {
            // Empurra tudo para a direita e coloca a chave emprestada na posição 0
            no.remanejar(0);
            no.setvInfo(0, irmaEsq.getvInfo(irmaEsq.getTL() - 1));
            no.setTL(no.getTL() + 1);
            irmaEsq.setTL(irmaEsq.getTL() - 1);

            // Atualiza o separador no pai para que assim tenha a nova primeira chave do nó
            pai.setvInfo(posPai - 1, no.getvInfo(0));
        }
        else {
            // Na parte do no interno, ele vai descer a chave separadora do pai e sobe a última da irmã
            no.remanejar(0);
            no.setvInfo(0, pai.getvInfo(posPai - 1));
            no.setvLig(0, irmaEsq.getvLig(irmaEsq.getTL()));
            no.setTL(no.getTL() + 1);
            pai.setvInfo(posPai - 1, irmaEsq.getvInfo(irmaEsq.getTL() - 1));
            irmaEsq.setTL(irmaEsq.getTL() - 1);
        }
    }

    // Pega a primeira chave da irmã direita e coloca no final do nó atual
    private void redistribuirDaDireita(NoBmais no, NoBmais irmaDir, NoBmais pai, int posPai) {
        if (no.isEhFolha()) {
            // Coloca a primeira chave da irmã no final do nó atual
            no.setvInfo(no.getTL(), irmaDir.getvInfo(0));
            no.setTL(no.getTL() + 1);
            irmaDir.remanejarExclusao(0);
            irmaDir.setTL(irmaDir.getTL() - 1);
            pai.setvInfo(posPai, irmaDir.getvInfo(0)); // Com isso, agora separador no pai agora é a nova primeira chave da irmã direita
        }
        else {
            // Na parte do no interno, ele vai descer a chave separadora do pai e sobe a primeira chave da irmã
            no.setvInfo(no.getTL(), pai.getvInfo(posPai));
            no.setvLig(no.getTL() + 1, irmaDir.getvLig(0));
            no.setTL(no.getTL() + 1);
            pai.setvInfo(posPai, irmaDir.getvInfo(0));
            irmaDir.remanejarExclusao(0);
            irmaDir.setTL(irmaDir.getTL() - 1);
        }
    }

    private void concatenar(NoBmais esq, NoBmais dir, NoBmais pai, int posSepPai) {
        int minimo = (this.ordem + 1) / 2 - 1;

        if (esq.isEhFolha()) {
            // Copia todas as chaves do nó direito para o final do esquerdo
            int j = 0;
            while (j < dir.getTL()) {
                esq.setvInfo(esq.getTL(), dir.getvInfo(j));
                esq.setTL(esq.getTL() + 1);
                j++;
            }

            // Conserta as ligações das folhas
            esq.setProx(dir.getProx());
            if (dir.getProx() != null)
                dir.getProx().setAnt(esq);
        }
        else {
            // Na parte do no interno, a chave separadora do pai desce e entra no nó esquerdo
            esq.setvInfo(esq.getTL(), pai.getvInfo(posSepPai));
            esq.setTL(esq.getTL() + 1);

            int j = 0;
            while (j < dir.getTL()) {
                esq.setvInfo(esq.getTL(), dir.getvInfo(j));
                esq.setvLig(esq.getTL(), dir.getvLig(j));
                esq.setTL(esq.getTL() + 1);
                j++;
            }
            esq.setvLig(esq.getTL(), dir.getvLig(dir.getTL())); // Puxa o último ponteiro do nó direito
        }

        // Remove o separador do pai e ajusta o ponteiro para apontar para o nó concatenado
        pai.remanejarExclusao(posSepPai);
        pai.setvLig(posSepPai, esq);
        pai.setTL(pai.getTL() - 1);

        // Se o pai esvaziou e era a raiz, o esquerdo vira a nova raiz
        if (pai == this.raiz && pai.getTL() == 0)
            this.raiz = esq;

        else if (pai != this.raiz && pai.getTL() < minimo) // Se o pai entrou em underflow, dai nesse caso, ele vai propagar o tratamento para cima
            tratarIrmas(pai);
    }
}