package arvorePatricia;

public class NoPatricia {
    public char[] letras;
    public int posicao;
    public NoPatricia[] ponteiros;
    public String palavraFinal;

    public NoPatricia() {
        this.letras = new char[26];
        this.ponteiros = new NoPatricia[26];
        this.posicao = -1;
        this.palavraFinal = null;

        for (int i = 0; i < 26; i++) {
            this.letras[i] = ' ';
        }
    }
}