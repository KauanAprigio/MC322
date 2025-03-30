package LAB02;

public class RoboAereo extends Robo {
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
            System.out.println(getNome() + " subiu para " + getAltitude() + " metros de altitude");
        } else {
            System.out.println(getNome() + " não pode subir além da altitude máxima de " + getAltitudeMaxima() + " metros");
        }
    }

    public void descer(int metros) {
        if (altitude - metros >= 0) {
            altitude -= metros;
            System.out.println(getNome() + " desceu para " + getAltitude() + " metros de altitude");
        } else {
            System.out.println(getNome() + " não pode descer abaixo do nível do solo");
        }
    }

    // Getters e Setters
    public int getAltitude() { return altitude; }
    public int getAltitudeMaxima() { return altitudeMaxima; }
    public void setAltitudeMaxima(int altitudeMaxima) { this.altitudeMaxima = altitudeMaxima; }
    public void setAltitude(int altitude) { this.altitude = altitude; }
}
