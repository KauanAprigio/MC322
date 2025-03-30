package LAB02;

public class RoboTerrestre extends Robo {
    private int velocidadeMaxima;
    private static int altitudeMaxima = 0;
    private static int altitude = 0;

    
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
    public int getAltitudeMaxima() { return altitudeMaxima; }
    public int getAltitude() { return altitude; }
}

class RoboLimpador extends RoboTerrestre {
    private int n_pas;
    private boolean power = false;


    // Construtor
    public RoboLimpador(String nome, int posicaoX, int posicaoY, int velocidadeMaxima, boolean power, int n_pas) {
        super(nome, posicaoX, posicaoY, velocidadeMaxima);
        this.power = power;
        this.n_pas = n_pas;
    }


    // Metodos
    public boolean ligar() {
        if (!power) {
            power = true;
            System.out.println(getNome() + " foi ligado com sucesso!");
        } else {
            System.out.println(getNome() + " já está ligado.");
        }
        return power;
    }

    public boolean desligar() {
        if (power) {
            power = false;
            System.out.println(getNome() + " foi desligado.");
        } else {
            System.out.println(getNome() + " já está desligado.");
        }
        return power;
    }
    
    public void mudar_pas(int novas_pas){
        n_pas = novas_pas;
    }


    //Getters e Setters
    public boolean getPower() { return power; }
    public int getPas() { return n_pas; }

    
    

    
}
