import arvoreBmais.Bmais;

public class Main {
    public static void main(String[] args) {
        // ==========================================
        // TESTE 1: Árvore B+ com Ordem Par (N = 4)
        // ==========================================
        System.out.println("=====================================");
        System.out.println("      TESTANDO ÁRVORE COM N = 4      ");
        System.out.println("=====================================");
        Bmais arvoreN4 = new Bmais(4);

        System.out.println("Inserindo 500 chaves (1 a 500)...");
        int i = 1;
        while (i <= 500) {
            arvoreN4.inserir(i);
            i++;
        }

        arvoreN4.exibirFolhas();
        // Descomente a linha abaixo se quiser ver a árvore toda impressa (pode poluir bastante o terminal!)
        // arvoreN4.emOrdem(); 

        // ==========================================
        // TESTE 2: Árvore B+ com Ordem Ímpar (N = 5)
        // ==========================================
        System.out.println("=====================================");
        System.out.println("      TESTANDO ÁRVORE COM N = 5      ");
        System.out.println("=====================================");
        Bmais arvoreN5 = new Bmais(5);

        System.out.println("Inserindo 500 chaves (1 a 500)...");
        int j = 1;
        while (j <= 500) {
            arvoreN5.inserir(j);
            j++;
        }

        arvoreN5.exibirFolhas();
    }
}