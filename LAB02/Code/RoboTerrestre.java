package LAB02.Code;

/* 
 * Classe dada pelo enunciado RoboTerrestre
 *  
 * Atributos:
 * - velocidadeMaxima
 * 
 * Métodos:
 * - mover(int deltaX, int deltaY)
 * 
 */
//Subclasse de Robo, mas também é a "SuperClasse" de RoboLimpador e RoboGarcom
public class RoboTerrestre extends Robo {
    // Atributos adicionais
    private int velocidadeMaxima;

    // Construtor
    public RoboTerrestre(String nome, int posicaoX, int posicaoY, int velocidadeMaxima) {
        super(nome, posicaoX, posicaoY);
        this.velocidadeMaxima = velocidadeMaxima;
    }

    // Metodos
    public void mover(int deltaX, int deltaY) {
        int velocidade = (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (velocidade <= velocidadeMaxima) {
            super.mover(deltaX, deltaY);
        } else {
            System.out.println(getNome() + ": Velocidade de " + velocidade + " excede o máximo de " + getVelocidadeMaxima() + " permitido");
        }
    }
    

    // Getters e Setters
    public int getVelocidadeMaxima() { return velocidadeMaxima; }
}

