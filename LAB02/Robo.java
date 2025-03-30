package LAB02;

public class Robo {
    // Atributos da SuperClasse
    private String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;

    // Construtor
    public Robo(String nome, int posicaoX, int posicaoY) {
        this.nome = nome;
        this.direcao = "Norte";
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
    }

    // Metodos
    public void mover(int deltaX, int deltaY) {
        this.posicaoX += deltaX;
        this.posicaoY += deltaY;
        System.out.println(getNome() + " moveu para (" + getPosicaoX() + ", " + getPosicaoY() + ")");
    }

    public void identificarObstaculo() {
        System.out.println(getNome() + " está identificando obstáculos na direção " + getDirecao());
    }

    public void exibirPosicao() {
        System.out.println(getNome() + " está na posição (" + getPosicaoX() + ", " + getPosicaoY() + ")");
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public String getDirecao() { return direcao; }
    public int getPosicaoX() { return posicaoX; }
    public int getPosicaoY() { return posicaoY; }
    public void setDirecao(String direcao) { this.direcao = direcao; }
    public void setPosicaoX(int x) { this.posicaoX = x; }
    public void setPosicaoY(int y) { this.posicaoY = y; }
}

