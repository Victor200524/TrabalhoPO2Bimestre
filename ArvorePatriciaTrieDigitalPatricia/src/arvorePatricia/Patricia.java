package arvorePatricia;

import fila.Fila;
import fila.NoFila;
import pilha.Pilha;

public class Patricia {
    private NoPatricia raiz;

    public Patricia() {
        this.raiz = null;
    }

    // LETRA A - Método Iterativo de Inserção Completo (Sem break, continue ou return)
    public void inserir(String palavra) {
        if (this.raiz == null) {
            this.raiz = new NoPatricia();
            this.raiz.posicao = 1;

            int indiceLetra = palavra.charAt(0) - 'a';
            this.raiz.letras[indiceLetra] = palavra.charAt(0);

            NoPatricia primeiraFolha = new NoPatricia();
            primeiraFolha.palavraFinal = palavra;
            this.raiz.ponteiros[indiceLetra] = primeiraFolha;

        } else {
            boolean inserido = false;
            NoPatricia atual = this.raiz;
            NoPatricia pai = null;
            char letraCaminho = ' ';

            while (!inserido) {
                // CASO 1: Batemos de frente com uma FOLHA PURA
                if (atual.posicao == -1) {
                    int diffIdx = 0;
                    // Encontra o índice onde as duas palavras diferem
                    while (diffIdx < palavra.length() && diffIdx < atual.palavraFinal.length() &&
                            palavra.charAt(diffIdx) == atual.palavraFinal.charAt(diffIdx)) {
                        diffIdx++;
                    }

                    if (diffIdx == palavra.length() && diffIdx == atual.palavraFinal.length()) {
                        inserido = true; // Palavra duplicada, não faz nada
                    } else {
                        // Cria o nó que vai separar as duas palavras (Diagrama B)
                        int posDivergencia = diffIdx + 1;
                        NoPatricia diverge = new NoPatricia();
                        diverge.posicao = posDivergencia;

                        // Se a folha atual for prefixo da nova palavra ou vice-versa
                        if (diffIdx == palavra.length() || diffIdx == atual.palavraFinal.length()) {
                            if (diffIdx == palavra.length()) {
                                diverge.palavraFinal = palavra;
                                char charFolha = atual.palavraFinal.charAt(diffIdx);
                                diverge.letras[charFolha - 'a'] = charFolha;
                                diverge.ponteiros[charFolha - 'a'] = atual;
                            } else {
                                diverge.palavraFinal = atual.palavraFinal;
                                char charPalavra = palavra.charAt(diffIdx);
                                NoPatricia novaFolha = new NoPatricia();
                                novaFolha.palavraFinal = palavra;
                                diverge.letras[charPalavra - 'a'] = charPalavra;
                                diverge.ponteiros[charPalavra - 'a'] = novaFolha;
                            }

                            // Regra do Diagrama C: É obrigatório criar um nó com a última letra do prefixo!
                            if (pai != null && diffIdx > pai.posicao) {
                                NoPatricia quebra = new NoPatricia();
                                quebra.posicao = diffIdx;
                                char charQuebra = palavra.charAt(diffIdx - 1);
                                quebra.letras[charQuebra - 'a'] = charQuebra;
                                quebra.ponteiros[charQuebra - 'a'] = diverge;
                                pai.ponteiros[letraCaminho - 'a'] = quebra;
                            } else if (pai != null) {
                                pai.ponteiros[letraCaminho - 'a'] = diverge;
                            }
                        } else {
                            // Divergência normal no meio das palavras (Ex: "galo" e "gel")
                            char charFolha = atual.palavraFinal.charAt(diffIdx);
                            diverge.letras[charFolha - 'a'] = charFolha;
                            diverge.ponteiros[charFolha - 'a'] = atual;

                            char charPalavra = palavra.charAt(diffIdx);
                            NoPatricia novaFolha = new NoPatricia();
                            novaFolha.palavraFinal = palavra;
                            diverge.letras[charPalavra - 'a'] = charPalavra;
                            diverge.ponteiros[charPalavra - 'a'] = novaFolha;

                            if (pai != null) {
                                pai.ponteiros[letraCaminho - 'a'] = diverge;
                            }
                        }
                        inserido = true;
                    }
                }
                // CASO 2: A palavra terminou no meio de um salto comprimido (Diagrama C)
                else if (palavra.length() < atual.posicao) {
                    NoPatricia quebra = new NoPatricia();
                    quebra.posicao = palavra.length();
                    char charQuebra = palavra.charAt(palavra.length() - 1);
                    quebra.letras[charQuebra - 'a'] = charQuebra;
                    quebra.ponteiros[charQuebra - 'a'] = atual;

                    // Quando o último caractere é inserido, um novo nodo é colocado logo abaixo deste, contendo a palavra.
                    atual.palavraFinal = palavra;

                    if (pai != null) {
                        pai.ponteiros[letraCaminho - 'a'] = quebra;
                    }
                    inserido = true;
                }
                // CASO 3: Navegação normal descendo a árvore
                else {
                    char letraAtual = palavra.charAt(atual.posicao - 1);
                    int indicePonteiro = letraAtual - 'a';

                    if (atual.ponteiros[indicePonteiro] == null) {
                        atual.letras[indicePonteiro] = letraAtual;
                        NoPatricia novaFolha = new NoPatricia();
                        novaFolha.palavraFinal = palavra;
                        atual.ponteiros[indicePonteiro] = novaFolha;
                        inserido = true;
                    } else {
                        pai = atual;
                        letraCaminho = letraAtual;
                        atual = atual.ponteiros[indicePonteiro];
                    }
                }
            }
        }
    }

    // LETRA B - Exibir todas as palavras de forma iterativa
    public void exibirPalavras() {
        if (this.raiz == null) {
            System.out.println("A árvore está vazia.");
        } else {
            Pilha pilha = new Pilha();
            pilha.push(this.raiz);

            // Laço puro, sem break, sem continue e sem return
            while (!pilha.isEmpty()) {
                NoPatricia atual = pilha.pop();

                // Se o nó atual guarda uma palavra final, nós a imprimimos.
                // Isso resolve tanto as folhas puras (ex: "galo") quanto
                // as palavras que são prefixos de outras (ex: "sol" antes de "solo").
                if (atual.palavraFinal != null) {
                    System.out.println(atual.palavraFinal);
                }

                // Empilha os filhos de trás para frente (25 até 0).
                // Assim, o índice 0 ('a') fica no topo da pilha para ser o próximo processado.
                int i = 25;
                while (i >= 0) {
                    if (atual.ponteiros[i] != null) {
                        pilha.push(atual.ponteiros[i]);
                    }
                    i--; // Decremento clássico para não usar laços complexos
                }
            }
        }
    }

    // LETRA C - Busca Iterativa (apenas um return no final)
    public boolean buscar(String palavra) {
        boolean encontrou = false;
        boolean fimDaBusca = false;
        NoPatricia atual = this.raiz;

        while (!fimDaBusca && atual != null) {
            if (atual.posicao == -1) { // Chegou numa folha pura
                if (palavra.equals(atual.palavraFinal)) {
                    encontrou = true;
                }
                fimDaBusca = true;
            } else if (atual.posicao > palavra.length()) { // A palavra é menor que a posição do nó
                if (palavra.equals(atual.palavraFinal)) { // Verifica se ela parou exatemente nesse nó (Cenário C)
                    encontrou = true;
                }
                fimDaBusca = true;
            } else {
                // Continua descendo na árvore
                char letra = palavra.charAt(atual.posicao - 1);
                atual = atual.ponteiros[letra - 'a'];
            }
        }

        return encontrou; // ÚNICA saída da função
    }

    // LETRA D - Exibir todos os nós e informações nível a nível
    public void exibirNivelANivel() {
        if (this.raiz == null) {
            System.out.println("A árvore está vazia.");
        } else {
            // Importe a sua Fila lá no topo da classe se o IntelliJ não fizer sozinho:
            // import fila.Fila;
            // import fila.NoFila;

            Fila fila = new Fila();
            fila.enqueue(this.raiz, 1); // A raiz começa no nível 1

            // Laço puro, sem break, continue ou return
            while (!fila.isEmpty()) {
                NoFila noRemovido = fila.dequeue();
                NoPatricia atual = noRemovido.getInfo();
                int nivelAtual = noRemovido.getNivel();

                // 1. Imprime o Nível e a Posição (índice de diferenciação)
                System.out.print("Nível " + nivelAtual + " | Posição: " + atual.posicao + " | Letras: [ ");

                // 2. Varre os ponteiros para imprimir as letras ativas e já enfileirar os filhos
                int i = 0;
                while (i < 26) {
                    if (atual.ponteiros[i] != null) {
                        System.out.print(atual.letras[i] + " ");
                        // Enfileira o filho apontando para o próximo nível
                        fila.enqueue(atual.ponteiros[i], nivelAtual + 1);
                    }
                    i++;
                }
                System.out.print("]");

                // 3. Verifica se o nó guarda uma palavra final e a imprime
                if (atual.palavraFinal != null) {
                    System.out.print(" | Palavra: " + atual.palavraFinal);
                }

                System.out.println(); // Quebra a linha para o próximo nó
            }
        }
    }

}