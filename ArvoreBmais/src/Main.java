import arvoreBmais.Bmais;

import java.awt.*;
import java.util.Scanner;

// LETRA C
public class Main {

    private static String Menu(){
        String op;
        Scanner scanner = new Scanner(System.in);
        System.out.println(" === Selecione a opção desejada ===");
        System.out.println("[A] -> Exibir");
        System.out.println("[B] -> Excluir");
        System.out.println("[Z] -> Sair");
        System.out.println("Opção: ");op = String.valueOf(scanner.next().toUpperCase().charAt(0));
        return op;
    }

    public static void main(String[] args) {
        System.out.println("Testando árvore com N = 4");
        Bmais arvoreN4 = new Bmais(4);
        System.out.println("Inserido 1000 chaves");
        for (int i = 0; i < 1000; i++) {
            arvoreN4.inserir(i);
        }
        System.out.println("==============================================================");
        System.out.println("Testando árvore com N = 5");
        Bmais arvoreN5 = new Bmais(5);
        System.out.println("Inserido 1000 chaves");
        for (int i = 0; i < 1000; i++) {
            arvoreN5.inserir(i);
        }
        System.out.println();

        String op;
        do {
            switch (op = Menu()){
                case "A":
                    System.out.println(" ============= Exibir =============");
                    System.out.println("--- N = 4 ---");
                    arvoreN4.exibirFolhas();
                    System.out.println("--- Exibição em ordem de visita ---");
                    arvoreN4.emOrdem();

                    System.out.println();
                    System.out.println("==============================================================");
                    System.out.println();

                    System.out.println("--- N = 4 ---");
                    arvoreN5.exibirFolhas();
                    System.out.println("--- Exibição em ordem de visita ---");
                    arvoreN5.emOrdem();
                    break;
                case "B":
                    System.out.println(" ============= Excluir =============");
                    int num = 0;
                    System.out.println("Digite o numero que deseja excluir: "); num = new Scanner(System.in).nextInt();
                    arvoreN4.excluir(num);
                    arvoreN5.excluir(num);
                    System.out.println("Numero excluido em ambas arvores (N = 4/5): " + num);
                    break;
            }
            System.out.println("==============================================");
            op = Menu();
        }while (!op.equals("Z"));
    }
}