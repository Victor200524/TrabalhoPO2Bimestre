import arvorePatricia.Patricia;

public class Main {
    public static void main(String[] args) {
        Patricia arvore = new Patricia();

        // Exatamente como o professor pediu: inserção de dezenas de palavras fixas!
        String[] palavras = {
                "galo", "sola", "gel", "solo", "sol", // Palavras obrigatórias do exemplo A, B e C
                "abacate", "abacaxi", "banana", "barco", "computador",
                "computacao", "dados", "dado", "estrutura", "estudar",
                "faculdade", "faca", "java", "jarra", "zumbi", "zebra"
        };

        System.out.println("--- INSERINDO PALAVRAS ---");
        for (int i = 0; i < palavras.length; i++) {
            arvore.inserir(palavras[i]);
        }
        System.out.println("Dezenas de palavras inseridas com sucesso!\n");

        System.out.println("--- EXIBINDO PALAVRAS (Ordem Alfabética / Iterativo) ---");
        arvore.exibirPalavras();

        System.out.println("\n--- BUSCANDO PALAVRAS ---");
        System.out.println("Busca por 'sol': " + (arvore.buscar("sol") ? "Encontrado" : "Não Encontrado"));
        System.out.println("Busca por 'gel': " + (arvore.buscar("gel") ? "Encontrado" : "Não Encontrado"));
        System.out.println("Busca por 'aviao': " + (arvore.buscar("aviao") ? "Encontrado" : "Não Encontrado"));

        System.out.println("\n--- EXIBINDO ÁRVORE NÍVEL A NÍVEL ---");
        arvore.exibirNivelANivel();
    }
}