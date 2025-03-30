package LAB02;

public class RoboTerrestre extends Robo {
    private int velocidadeMaxima;
    private static int altitudeMaxima = 0;
    private static int altitude = 0;

    public RoboTerrestre(String nome, int posicaoX, int posicaoY, int velocidadeMaxima) {
        super(nome, posicaoX, posicaoY);
        this.velocidadeMaxima = velocidadeMaxima;
    }
    public void mover(int deltaX, int deltaY) {
        int velocidade = (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (velocidade <= velocidadeMaxima) {
            super.mover(deltaX, deltaY);
        } else {
            System.out.println(getNome() + ": Velocidade de " + velocidade + " excede o máximo de " + getVelocidadeMaxima() + " permitido");
        }
    }
    public int getVelocidadeMaxima() { return velocidadeMaxima; }
    public int getAltitudeMaxima() { return altitudeMaxima; }
    public int getAltitude() { return altitude; }

    // Getters e Setters são os já herdados da Superclasse Robo
}
