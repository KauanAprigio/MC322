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
    public RoboTerrestre(String nome, int posicaoX, int posicaoY, int velocidadeMaxima, Double raio, Ambiente ambiente) {
        super(nome, posicaoX, posicaoY, new SensorPosicaoSegura(raio,ambiente), ambiente);
        this.velocidadeMaxima = velocidadeMaxima;
    }

    // Metodos

    // aqui não é necessário fazer mais avaliações de caso, pois ele utiliza os "ifs" da SuperClasse
    public void mover(int deltaX, int deltaY) {
        int pos_x = getX() + deltaX;
        int pos_y = getY() + deltaY;
        SensorPosicaoSegura sensor = (SensorPosicaoSegura) getSensor();
        if (!sensor.posicao_segura(pos_x, pos_y)){
            System.out.println("A posição desejada não é segura, pois há um obstáculo nela");
        } else {
            int velocidade = (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            if (velocidade <= velocidadeMaxima) {
                super.mover(deltaX, deltaY);
            } else {
                System.out.println(getNome() + ": Velocidade de " + velocidade + " excede o máximo de " + getVelocidadeMaxima() + " permitido");
            }
        }
    }

    // Getters e Setters
    public int getVelocidadeMaxima() { return velocidadeMaxima; }
}


