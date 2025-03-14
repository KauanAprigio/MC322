public class Ambiente {

    private int largura;
    private int altura;
    private String nome;
    
    public Ambiente(int larguraX, int alturaY, String nome) {
        this.largura = larguraX;
        this.altura = alturaY;
        this.nome = nome;
    }

    public boolean dentroDosLimites(int x, int y) {
        if ((0 <= x && x < largura) && (0 <= y && y < altura)) return true;
        return false;
    }  
    public String getNome() {
        return nome;
    }
}