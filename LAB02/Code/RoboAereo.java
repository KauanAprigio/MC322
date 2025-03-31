package LAB02.Code;

/*
 * Classe dada pelo enunciado RoboAereo
 *  
 * Atributos:
 * - altitude
 * - altitudeMaxima
 * 
 * Métodos:
 * - subir(int metros)
 * - descer(int metros)
 * - exibirPosicao() (sobrescreve o método da superclasse para exibir altitude)
 * 
 */
//SubClasse de Robo, mas é "SuperClasse" de RoboLetreiro e RoboBombeiro
public class RoboAereo extends Robo {
    //Atributos adicionais
    private int altitude;
    private int altitudeMaxima;

    // Construtor
    public RoboAereo(String nome, int posicaoX, int posicaoY, int altitudeMaxima) {
        super(nome, posicaoX, posicaoY);
        // Todos os robos aéreos começam com altitude 0
        this.altitude = 0;
        this.altitudeMaxima = altitudeMaxima;
    }

    // Métodos
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

    public void exibirPosicao() {
        System.out.println(getNome() + " está na posição (" + getPosicaoX() + ", " + getPosicaoY() + ", " +getAltitude() +")");
    }

    // Getters e Setters
    public int getAltitude() { return altitude; }
    public int getAltitudeMaxima() { return altitudeMaxima; }
    public void setAltitudeMaxima(int altitudeMaxima) { this.altitudeMaxima = altitudeMaxima; }
    public void setAltitude(int altitude) { this.altitude = altitude; }
}

