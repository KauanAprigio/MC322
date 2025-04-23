package LAB03.Code;

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

    // aqui estou usando somente sensor, mas acho que nas subclasses utilizarei de outro tipo de sensor
    public RoboTerrestre(String nome, int posicaoX, int posicaoY, int velocidadeMaxima, Sensor sensor, Ambiente ambiente) { 
        super(nome, posicaoX, posicaoY, sensor, ambiente);
        this.velocidadeMaxima = velocidadeMaxima;
    }

    // Metodos

    // aqui não é necessário fazer mais avaliações de caso, pois ele utiliza os "ifs" da SuperClasse
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


