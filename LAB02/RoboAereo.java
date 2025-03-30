package LAB02;

public class RoboAereo extends Robo {
    //Atributos adicionais
    private int altitude;
    private int altitudeMaxima;


    // Construtor
    public RoboAereo(String nome, int posicaoX, int posicaoY, int altitudeMaxima) {
        super(nome, posicaoX, posicaoY);
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


    // Getters e Setters
    public int getAltitude() { return altitude; }
    public int getAltitudeMaxima() { return altitudeMaxima; }
    public void setAltitudeMaxima(int altitudeMaxima) { this.altitudeMaxima = altitudeMaxima; }
    public void setAltitude(int altitude) { this.altitude = altitude; }
}

class RoboBombeiro extends RoboAereo {
    //Atributos adicionais
    private int peso_max; // peso maximo que o robo suporta;
    private int peso_tripulantes = 0; // peso da tripulaçao presente no exato momento 
    private int reservatorio = 0; // litros de agua no reservatorio, lembre-se que 1l = 1kg de agua
    private int altitude = 0; // ele começa no solo, logo a altitude ja é inicializada como sendo 0
    

    // Construtor
    public RoboBombeiro(String nome, int posicaoX, int posicaoY, int altitudeMaxima, int peso_max) {
        super(nome, posicaoX, posicaoY, altitudeMaxima);   
        this.peso_max = peso_max;
    }

    
    // Metodos
    public void encher_reservatorio(int litros){
        int auxiliar = reservatorio + peso_tripulantes + litros; // variavel auxiliar para o if, representa somatorio dos pesos
        if (auxiliar > peso_max){
            int excedente = auxiliar - peso_max;
            System.out.println("A quantidade " + litros + " excede " + excedente + " kilos da capacidade máxima de " + getNome() + "!");
        } else {
            reservatorio += litros;
            System.out.println(getNome() + " foi abastecido com sucesso!");
        }
    }

    public void apagar_fogo(int litros_necessarios){
        if (reservatorio < litros_necessarios){
            int deficit = litros_necessarios - reservatorio; // quanto ira faltar de agua para apagar o fogo
            System.out.println(getNome() + " precisa de " + deficit + " litros a mais para apagar o incêndio!");
        } else {
            reservatorio -= litros_necessarios;
            System.out.println(getNome() + " apagou o incêndio com sucesso!");
        }
    }

    public void resgate(int peso_tripulacao){
        int auxiliar = reservatorio + peso_tripulantes + peso_tripulacao; // variavel auxiliar que representa o somatorio das cargas
        if (auxiliar > peso_max ){
            int excedente = auxiliar - peso_max;
            System.out.println("O peso " + peso_tripulacao + " excede " + excedente + " kilos da capacidade máxima de " + getNome() + "!");
        } else {
            peso_tripulantes += peso_tripulacao;
            System.out.println(getNome() + " conseguiu suportar todos a bordo!");
        }
    }

    public void aprimora(int novo_peso){
        if (altitude == 0){ // isso quer dizer que ele ainda nao esta indo socorrer ninguem, por estar na base pode ser aprimorado
            peso_max = novo_peso;
            System.out.println(getNome() + " aumentou sua capacidade máxima para " + novo_peso + " kilos!");
        } else {
            System.out.println(getNome() + " deve estar na base para ser aprimorado");
        }
    }

    
}
