package LAB02;

public class Robo {
    private String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;

    public Robo(String nome, int posicaoX, int posicaoY) {
        this.nome = nome;
        this.direcao = "Norte";
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
    }

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

class RoboTerrestre extends Robo {
    private int velocidadeMaxima;

    public RoboTerrestre(String nome, int posicaoX, int posicaoY, int velocidadeMaxima) {
        super(nome, posicaoX, posicaoY);
        this.velocidadeMaxima = velocidadeMaxima;
    }
    public void mover(int deltaX, int deltaY) {
        int velocidade = (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (velocidade <= velocidadeMaxima) {
            super.mover(deltaX, deltaY);
        } else {
            System.out.println(getNome() + ": Velocidade de " + velocidade + " excede o máximo de " + getVelocidadeMaxima() + " permitido!");
        }
    }
    public int getVelocidadeMaxima() { return velocidadeMaxima; }
}

class RoboAereo extends Robo {
    private int altitude;
    private int altitudeMaxima;

    public RoboAereo(String nome, int posicaoX, int posicaoY, int altitudeMaxima) {
        super(nome, posicaoX, posicaoY);
        this.altitude = 0;
        this.altitudeMaxima = altitudeMaxima;
    }

    public void subir(int metros) {
        if (altitude + metros <= altitudeMaxima) {
            altitude += metros;
            System.out.println(getNome() + " subiu para " + altitude + " metros de altitude");
        } else {
            System.out.println(getNome() + " não pode subir além da altitude máxima de " + altitudeMaxima + " metros!");
        }
    }

    public void descer(int metros) {
        if (altitude - metros >= 0) {
            altitude -= metros;
            System.out.println(getNome() + " desceu para " + altitude + " metros de altitude");
        } else {
            System.out.println(getNome() + " não pode descer abaixo do nível do solo!");
        }
    }

    // Getters e Setters
    public int getAltitude() { return altitude; }
    public int getAltitudeMaxima() { return altitudeMaxima; }
    public void setAltitudeMaxima(int altitudeMaxima) { this.altitudeMaxima = altitudeMaxima; }
}