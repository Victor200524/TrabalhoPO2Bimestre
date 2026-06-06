import arvorePatricia.Patricia;

import java.util.Scanner;

public class Main {

    private static String Menu(){
        String op;
        Scanner scanner = new Scanner(System.in);
        System.out.println("=====================================");
        System.out.println("[A] -> Exibir Palavras Inseridas");
        System.out.println("[B] -> Exibir Palavras por Nivel");
        System.out.println("[C] -> Buscar Palavra");
        System.out.println("[Z] -> Sair");
        op = String.valueOf(scanner.next().toUpperCase().charAt(0));
        return op;
    }

    public static void main(String[] args) {
        Patricia arvore = new Patricia();
        String[] palavras = {
                "galo", "sola", "gel", "solo", "sol",
                "abacate", "abacaxi", "banana", "barco", "computador",
                "computacao", "dados", "dado", "estrutura", "estudar",
                "faculdade", "faca", "java", "jarra", "zumbi", "zebra"
        };

        for (int i = 0; i < palavras.length; i++)
            arvore.inserir(palavras[i]);

        System.out.println("Palavras inseridas");
        String op;
        do {
            switch (op = Menu()){
                case "A":
                    System.out.println("=== Palavras Inseridas ===");
                    arvore.exibirPalavras();
                    break;
                case "B":
                    System.out.println("=== Palavras em seu respectivo nivel ===");
                    arvore.exibirNivelANivel();
                    break;
                case "C":
                    System.out.println("=== Buscar Palavra ===");
                    String palavra;
                    System.out.println("Digite a palavra: ");
                    Scanner scanner = new Scanner(System.in);
                    palavra = String.valueOf(scanner.nextLine());
                    System.out.println("Palavra " + palavra + " = " + arvore.buscar(palavra));
                    break;
            }
        }while (!op.equals("Z"));
    }
}