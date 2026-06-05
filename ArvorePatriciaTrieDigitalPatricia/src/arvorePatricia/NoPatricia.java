package arvorePatricia;

public class NoPatricia {
    public char[] letras; // índices de 0..25
    public int posicao; // corresponde à posição da letra na string
    public NoPatricia[] ponteiros; // índices de 0..25
    public String palavraFinal; // guarda a string se for final de palavra

    public NoPatricia() {
        this.letras = new char[26];
        this.ponteiros = new NoPatricia[26];
        this.posicao = -1; // -1 indica que a posição ainda não foi definida
        this.palavraFinal = null;

        // Inicializa o vetor de letras com espaços em branco (ou outro caractere nulo)
        // para facilitar a verificação se um índice está vazio depois.
        for (int i = 0; i < 26; i++) {
            this.letras[i] = ' ';
        }
    }
}