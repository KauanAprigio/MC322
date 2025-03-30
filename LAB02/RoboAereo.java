package LAB02;

//SubClasse de Robo, mas é "SuperClasse" de RoboLetreiro e RoboBombeiro
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
// Robo que mostra uma mensagem em uma dada altura em seu visor para servir como placa ou letreiro
class RoboLetreiro extends RoboAereo {
    //Atributos adicionais
    private int max_caracteres;
    private int max_altura; 
    private String visor; // a mensagem que irá aparecer no letreiro


    // Construtor
    public RoboLetreiro(String nome, int posicaoX, int posicaoY, int altitudeMaxima, int max_caracteres){
        super(nome, posicaoX, posicaoY, altitudeMaxima);
        this.max_caracteres = max_caracteres;
    }

    // Metodos
    public void escrever_visor(String texto, int altura){
        if ((texto.length() <= max_caracteres) && (altura <= max_altura)){ // ve se pode escrever a mensagem desejada + se nao fica acima da alturaMaxima do robo
            int diff_atura = altura - getAltitude(); // diferença entre a altura do robo e a altura desejada
            if (diff_atura < 0){ // se o robo estiver acima da altura desejada
                descer(-diff_atura); // desce a altura desejada
            } else if (diff_atura > 0){ // se o robo estiver abaixo da altura desejada
                subir(diff_atura); // sobe a altura desejada
            }
            visor = texto;
            System.out.println(getNome() + " tem no seu visor a seguinte mensagem: " + visor);
        } else if (altura > max_altura) {
            int altura_excendente = altura - max_altura;
            System.out.println(getNome() + " não pode escrever acima da altura máxima de " + max_altura + " metros, excedendo em " + altura_excendente + " metros");
        } else {
            int caracteres_excedentes = texto.length() - max_caracteres;
            System.out.println(getNome() + " não pode escrever mais de " + max_caracteres + " caracteres, excedendo em " + caracteres_excedentes + " caracteres");
        }
    }

    public void limpar_visor() {
        visor = ""; // deixa o visor vazio, ou seja, lenght = 0
        System.out.println(getNome() + " teve seu visor limpo!");
    }

    public void aumentarVisor(int tamanho_adicional) {// caso precise aumentar o tamanho do robo/letreiro
        if (getAltitude() == 0){ // tem que estar no chao para ser atualizado o tamanho do visor, ou seja, quantos caracteres ele suporta
            max_caracteres += tamanho_adicional; 
            System.out.println(getNome() + " teve seu visor aumentado, agora suporta " + max_caracteres + " caracteres");
        } else {
            System.out.println(getNome() + " deve estar no chão para ter seu visor aumentado");
        }
    }
    // Getters e Setters
    public String getVisor() { return visor; }
    public int getMaximoCaracteres() { return max_caracteres; }
    public String getTexto() { return visor; }
}