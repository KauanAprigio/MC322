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

    public void exibirPosicao() {
        System.out.println(getNome() + " está na posição (" + getPosicaoX() + ", " + getPosicaoY() + ", " +getAltitude() +")");
    }

    // Getters e Setters
    public int getAltitude() { return altitude; }
    public int getAltitudeMaxima() { return altitudeMaxima; }
    public void setAltitudeMaxima(int altitudeMaxima) { this.altitudeMaxima = altitudeMaxima; }
    public void setAltitude(int altitude) { this.altitude = altitude; }
}



// Subclasse de RoboAereo
class RoboBombeiro extends RoboAereo {
    //Atributos adicionais
    private int peso_max; // peso maximo que o robo suporta;
    private int peso_tripulantes = 0; // peso da tripulaçao presente no exato momento 
    private int reservatorio = 0; // litros de agua no reservatorio
    
    // Construtor
    public RoboBombeiro(String nome, int posicaoX, int posicaoY, int altitudeMaxima, int peso_max) {
        super(nome, posicaoX, posicaoY, altitudeMaxima);  
        this.peso_max = peso_max;
    }
    
    // Metodos
    public void adicionar_agua(int litros){
        int peso_total = reservatorio + peso_tripulantes + litros; // somatorio dos pesos
        if (peso_total > peso_max){
            int excedente = peso_total - peso_max;
            System.out.println("A quantidade " + litros + " excede " + excedente + " kilos da capacidade máxima de " + peso_max + "do" + getNome());
        } else {
            reservatorio += litros;
            System.out.println(getNome() + " foi abastecido com sucesso");
            System.out.println("Reservatorio possui " + reservatorio + " litros");
        }
    }

    public void apagar_fogo(int litros_necessarios){
        if (reservatorio < litros_necessarios){
            int deficit = litros_necessarios - reservatorio; // quanto ira faltar de agua para apagar o fogo
            System.out.println(getNome() + " precisa de " + deficit + " litros a mais para apagar o incêndio");
        } else {
            reservatorio -= litros_necessarios;
            System.out.println(getNome() + " apagou o incêndio com sucesso");
        }
    }

    public void resgate(int peso_civis){
        int peso_total = reservatorio + peso_tripulantes + peso_civis; // variavel auxiliar que representa o somatorio das cargas
        if (peso_total > peso_max ){
            int excedente = peso_total - peso_max;
            if (reservatorio >= excedente){
                reservatorio -= excedente;
                System.out.println(getNome() + " irá liberar " + excedente + " litros de água do reservatório para poder acomodar os tripulantes");
            } else {
                System.out.println(getNome() + " não suporta todos os tripulantes, libere os que estão a bordo primeiro");
            }
        } else {
            peso_tripulantes += peso_civis; // adiciona o peso dos civis ao peso da tripulaçao
            System.out.println(getNome() + " conseguiu suportar todos a bordo");
        }
    }

    public void liberar_tripulantes(){
        if (getAltitude() == 0){ // quer dizer que esta no chao num lugar seguro para deixar os tripulantes em lugar seguro
            peso_tripulantes = 0;
            System.out.println(getNome() + " liberou os tripulantes com sucesso");
        } else {
            System.out.println(getNome() + " deve estar em um lugar seguro para liberar os tripulantes");
        }
    }

    public void aprimora(int peso_adicional){
        if (getAltitude() == 0){ // isso quer dizer que ele ainda nao esta indo socorrer ninguem, por estar na base pode ser aprimorado
            peso_max += peso_adicional;
            System.out.println(getNome() + " aumentou sua capacidade máxima para " + peso_max + " kilos");
        } else {
            System.out.println(getNome() + " deve estar na base para ser aprimorado");
        }
    }

    // Getters e Setters
    public int getCapacidade() { return peso_max; }
    public int getReservatorio() { return reservatorio; }
    public int getTripulacao() { return peso_tripulantes; }
}



// SubClasse de RoboAereo

class RoboLetreiro extends RoboAereo {
    //Atributos adicionais
    private int max_caracteres; 
    private String visor; // a mensagem que irá aparecer no letreiro


    // Construtor
    public RoboLetreiro(String nome, int posicaoX, int posicaoY, int altitudeMaxima, int max_caracteres){
        super(nome, posicaoX, posicaoY, altitudeMaxima);
        this.max_caracteres = max_caracteres;
    }

    // Metodos
    public void escrever_visor(String texto){
        if (texto.length() <= max_caracteres){ // ve se pode escrever a mensagem desejada, ou seja, o texto
            visor = texto;
            System.out.println(getNome() + " tem no seu visor a seguinte mensagem: " + texto);
        } else {
            int caracteres_excedentes = texto.length() - max_caracteres;
            System.out.println("A mensagem excede " + caracteres_excedentes + " caracteres");
        }
    }

    public void limpar_visor() { visor = ""; }; // deixa o visor vazio, ou seja, lenght = 0

    public void aumentar_caracteres(int novo_limite) { max_caracteres = novo_limite; } // caso precise aumentar o tamanho do robo/letreiro


    // Getters e Setters
    
    public String getVisor() { return visor; }
    public int getMaximoCaracteres() { return max_caracteres; }





    
}