package LAB03.Code;

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
    public RoboAereo(String nome, int posicaoX, int posicaoY, int altitudeMaxima,
                                            double raiosensor, Ambiente ambiente) {
        // Robos aéreos possuem um sensor de proximidade.
        super(nome, posicaoX, posicaoY, new SensorDeProximidade(raiosensor), ambiente);
        // Todos os robos aéreos começam com altitude 0
        this.altitude = 0;
        this.altitudeMaxima = altitudeMaxima;
    }

    // Métodos
    public void subir(int dz, Ambiente ambiente) {
        // Verifica se a nova altitude está dentro dos limites do ambiente
        if (!ambiente.dentroDosLimites(getX(), getY(), altitude + dz)){
            System.out.println(getNome() + " não pode subir para essa altitude, pois está fora dos limites do ambiente");
            return;
        }
        else if (altitude + dz <= altitudeMaxima) {
            altitude += dz;
            System.out.println(getNome() + " subiu para " + getAltitude() + " metros de altitude");
        } 
        else if (altitude + dz > altitudeMaxima) {
            System.out.println(getNome() + " não pode subir acima da altitude máxima  permitida de " + altitudeMaxima + " metros");
        }
    }

    public void descer(int dz) {
        if (altitude - dz >= 0) {
            altitude -= dz;
            System.out.println(getNome() + " desceu para " + getAltitude() + " metros de altitude");
        } else {
            System.out.println(getNome() + " não pode descer abaixo do nível do solo");
        }
    }

    public void exibirPosicao() {
        System.out.println(getNome() + " está na posição (" + getX() + ", " + getY() + ", " + getAltitude() +")");
    }

    // Getters e Setters
    public int getAltitude() { return altitude; }
    public int getAltitudeMaxima() { return altitudeMaxima; }
    public void setAltitudeMaxima(int altitudeMaxima) { this.altitudeMaxima = altitudeMaxima; }
    public void setAltitude(int altitude) { this.altitude = altitude; }
}

