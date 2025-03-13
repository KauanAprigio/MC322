<<<<<<< HEAD

public class Robo {
    private String nome;
    private int posicaoX;
    private int posicaoY;

    public Robo(String nome, int posicaoX, int posicaoY) {
        this.nome = nome;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
    }

    public void mover(int deltaX, int deltaY) {
        posicaoX += deltaX;
        posicaoY += deltaY;
    }

    public void exibirPosicao() {
        System.out.printf("Posição atual do robõ %s: (%d, %d)\n", nome, posicaoX, posicaoY);
    }
=======
public class Robo {
    
>>>>>>> 471108a78dcb3ce52a06a0513bae47791d7b676d
}
