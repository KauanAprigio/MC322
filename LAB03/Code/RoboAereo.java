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
        super(nome, posicaoX, posicaoY, new SensorPosicaoSegura(raiosensor, ambiente), ambiente);
        // Todos os robos aéreos começam com altitude 0
        this.altitude = 0;
        this.altitudeMaxima = altitudeMaxima;
    }

    // Métodos
    public void subir(int deltaZ) {
        // Verifica se a nova altitude está dentro dos limites do ambiente
        if (!getAmbiente().dentroDosLimites(getX(), getY(), altitude + deltaZ)){
            System.out.println(getNome() + " não pode subir para essa altitude, pois está fora dos limites do ambiente\n");
            return;
        }
        else if (altitude + deltaZ <= altitudeMaxima) {
            altitude += deltaZ;
            System.out.println(getNome() + " subiu para " + getAltitude() + " metros de altitude\n");
        } 
        else if (altitude + deltaZ > altitudeMaxima) {
            System.out.println(getNome() + " não pode subir acima da altitude máxima  permitida de " + altitudeMaxima + " metros\n");
        }
    }

    public void descer(int deltaZ) {
        if (altitude - deltaZ >= 0) {
            altitude -= deltaZ;
            System.out.println(getNome() + " desceu para " + getAltitude() + " metros de altitude\n");
            if (altitude == 0) {
                System.out.println(getNome() + "pousou");
            }
        } else {
            System.out.println(getNome() + " não pode descer abaixo do nível do solo\n");
        }
    }

    public void exibirPosicao() {
        System.out.println(getNome() + " está na posição (" + getX() + ", " + getY() + ", " + getAltitude() +")\n");
    }

    @Override
    public void identificarObstaculo() {
        // Lógica para identificar obstáculos
        // Aqui é utilizado o sensor para verificar se há obstáculos próximos
        System.out.println("Identificando obstáculos...\n");
        getSensor().monitorar(getX(), getY(), altitude, getAmbiente());
    }


    // Getters e Setters
    public int getAltitude() { return altitude; }
    public int getAltitudeMaxima() { return altitudeMaxima; }

    public void setAltitudeMaxima(int altitudeMaxima) { this.altitudeMaxima = altitudeMaxima; }
}

