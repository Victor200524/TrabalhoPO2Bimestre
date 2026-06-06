package arvorePatricia;

import fila.Fila;
import fila.NoFila;
import pilha.Pilha;

public class Patricia {
    private NoPatricia raiz;

    public Patricia() {
        this.raiz = null;
    }

    // LETRA A
    public void inserir(String palavra) {
        if (this.raiz == null) {
            this.raiz = new NoPatricia();
            this.raiz.posicao = 1;

            int indiceLetra = palavra.charAt(0) - 'a';
            this.raiz.letras[indiceLetra] = palavra.charAt(0);

            NoPatricia primeiraFolha = new NoPatricia();
            primeiraFolha.palavraFinal = palavra;
            this.raiz.ponteiros[indiceLetra] = primeiraFolha;
        }
        else {
            boolean inserido = false;
            NoPatricia atual = this.raiz;
            NoPatricia pai = null;
            char letraCaminho = ' ';

            while (!inserido) {
                if (atual.posicao == -1) {
                    int difI = 0; // Encontra ali na string, andando nela, pra ver aonde que elas sao distintas
                    while (difI < palavra.length() && difI < atual.palavraFinal.length() &&
                            palavra.charAt(difI) == atual.palavraFinal.charAt(difI)) {
                        difI++;
                    }

                    // Trata palavra duplicada, nao inserindo ela
                    if (difI == palavra.length() && difI == atual.palavraFinal.length())
                        inserido = true;
                    else {
                        int posDivergencia = difI + 1;
                        NoPatricia diverge = new NoPatricia();
                        diverge.posicao = posDivergencia;

                        // Se uma das palavras é prefixo da outra, precisa de tratamento especial
                        if (difI == palavra.length() || difI == atual.palavraFinal.length()) {
                            if (difI == palavra.length()) {
                                diverge.palavraFinal = palavra;
                                char charFolha = atual.palavraFinal.charAt(difI);
                                diverge.letras[charFolha - 'a'] = charFolha;
                                diverge.ponteiros[charFolha - 'a'] = atual;
                            }
                            else {
                                diverge.palavraFinal = atual.palavraFinal;
                                char charPalavra = palavra.charAt(difI);
                                NoPatricia novaFolha = new NoPatricia();
                                novaFolha.palavraFinal = palavra;
                                diverge.letras[charPalavra - 'a'] = charPalavra;
                                diverge.ponteiros[charPalavra - 'a'] = novaFolha;
                            }

                            // Cria um nó intermediário para representar a posição da última letra do prefixo
                            if (pai != null && difI > pai.posicao) {
                                NoPatricia quebra = new NoPatricia();
                                quebra.posicao = difI;
                                char charQuebra = palavra.charAt(difI - 1);
                                quebra.letras[charQuebra - 'a'] = charQuebra;
                                quebra.ponteiros[charQuebra - 'a'] = diverge;
                                pai.ponteiros[letraCaminho - 'a'] = quebra;
                            }
                            else if (pai != null)
                                pai.ponteiros[letraCaminho - 'a'] = diverge;
                            else // Split na raiz com pai null
                                this.raiz = diverge;
                        }
                        else {
                            // Diferença no meio das palavras durante o percurso
                            char charFolha = atual.palavraFinal.charAt(difI);
                            diverge.letras[charFolha - 'a'] = charFolha;
                            diverge.ponteiros[charFolha - 'a'] = atual;

                            char charPalavra = palavra.charAt(difI);
                            NoPatricia novaFolha = new NoPatricia();
                            novaFolha.palavraFinal = palavra;
                            diverge.letras[charPalavra - 'a'] = charPalavra;
                            diverge.ponteiros[charPalavra - 'a'] = novaFolha;

                            if (pai != null) {
                                pai.ponteiros[letraCaminho - 'a'] = diverge;
                            }
                            else // Split na raiz com pai null
                                this.raiz = diverge;
                        }
                        inserido = true;
                    }
                }
                // Ocorreu um caso no qual a palavra terminou no meio de um salto
                else if (palavra.length() < atual.posicao) {
                    NoPatricia quebra = new NoPatricia();
                    quebra.posicao = palavra.length();
                    char charQuebra = palavra.charAt(palavra.length() - 1);
                    quebra.letras[charQuebra - 'a'] = charQuebra;
                    quebra.ponteiros[charQuebra - 'a'] = atual;

                    // Guarda a palavra nesse nó intermediário que representa o fim dela
                    atual.palavraFinal = palavra;

                    if (pai != null)
                        pai.ponteiros[letraCaminho - 'a'] = quebra;
                    else // Se caso a palvra for menor que a posição da raiz propria
                        this.raiz = quebra;
                    inserido = true;
                }
                // Navega normalmente enquanto nenhum dos casos acima bater
                else {
                    char letraAtual = palavra.charAt(atual.posicao - 1);
                    int indicePonteiro = letraAtual - 'a';

                    if (atual.ponteiros[indicePonteiro] == null) {
                        atual.letras[indicePonteiro] = letraAtual;
                        NoPatricia novaFolha = new NoPatricia();
                        novaFolha.palavraFinal = palavra;
                        atual.ponteiros[indicePonteiro] = novaFolha;
                        inserido = true;
                    }
                    else {
                        pai = atual;
                        letraCaminho = letraAtual;
                        atual = atual.ponteiros[indicePonteiro];
                    }
                }
            }
        }
    }

    // LETRA B
    public void exibirPalavras() {
        if (this.raiz == null)
            System.out.println("Árvore vazia");
        else {
            Pilha pilha = new Pilha();
            pilha.push(this.raiz);
            while (!pilha.isEmpty()) {
                NoPatricia atual = pilha.pop();
                if (atual.palavraFinal != null)
                    System.out.println(atual.palavraFinal);
                int i = 25;
                while (i >= 0) {
                    if (atual.ponteiros[i] != null)
                        pilha.push(atual.ponteiros[i]);
                    i--;
                }
            }
        }
    }

    // LETRA C
    public boolean buscar(String palavra) {
        boolean encontrou = false;
        boolean fimDaBusca = false;
        NoPatricia atual = this.raiz;

        while (!fimDaBusca && atual != null) {
            if (atual.posicao == -1) {
                if (palavra.equals(atual.palavraFinal))
                    encontrou = true;
                fimDaBusca = true;
            }
            else if (atual.posicao > palavra.length()) { // A palavra é menor que a posição do nó
                if (palavra.equals(atual.palavraFinal)) // Verifica se ela parou exatemente nesse nó, ai seria o la "Ocorreu um caso no qual a palavra terminou no meio de um salto "
                    encontrou = true;
                fimDaBusca = true;
            }
            else {
                char letra = palavra.charAt(atual.posicao - 1);
                atual = atual.ponteiros[letra - 'a'];
            }
        }
        return encontrou;
    }

    // LETRA D
    public void exibirNivelANivel() {
        if (this.raiz == null)
            System.out.println("Árvore vazia");
        else {

            Fila fila = new Fila();
            fila.enqueue(this.raiz, 1);

            while (!fila.isEmpty()) {
                NoFila noRemovido = fila.dequeue();
                NoPatricia atual = noRemovido.getInfo();
                int nivelAtual = noRemovido.getNivel();

                System.out.print("Nível " + nivelAtual + " | Posição: " + atual.posicao + " | Letras: [ ");

                int i = 0;
                while (i < 26) {
                    if (atual.ponteiros[i] != null) {
                        System.out.print(atual.letras[i] + " ");
                        fila.enqueue(atual.ponteiros[i], nivelAtual + 1);
                    }
                    i++;
                }
                System.out.print("]");

                if (atual.palavraFinal != null)
                    System.out.print(" | Palavra: " + atual.palavraFinal);

                System.out.println();
            }
        }
    }

}