package LAB03.Code;

/*
 * Classe criada RoboBombeiro
 *  
 * Atributos:
 * - peso_max
 * - peso_tripulantes
 * - reservatorio
 * 
 * Métodos:
 * - adicionar_agua(int litros)
 * - apagar_fogo(int litros_necessarios)
 * - resgate(int peso_civis)
 * - liberar_tripulantes()
 * - aprimora(int peso_adicional)
 */
// Subclasse de RoboAereo
// resgata civis, apaga fogo e carrega agua
// e tem um peso maximo que suporta
public class RoboBombeiro extends RoboAereo {
    //Atributos adicionais
    private int peso_max; // peso maximo que o robo suporta;
    private int peso_tripulantes = 0; // peso da tripulaçao presente no exato momento 
    private int reservatorio = 0; // litros de agua no reservatorio
    
    // Construtor
    public RoboBombeiro(String nome, int posicaoX, int posicaoY, int altitudeMaxima, 
                                double raiosensor, Ambiente ambiente, int peso_max) {
        super(nome, posicaoX, posicaoY, altitudeMaxima, raiosensor, ambiente);
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
            if (reservatorio >= excedente){ // condicional caso queira tirar agua do reservatorio para dimunuir o peso, assim cabendo os outros tripulantes
                reservatorio -= excedente;
                System.out.println(getNome() + " irá liberar " + excedente + " litros de água do reservatório para poder acomodar os tripulantes");
            } else { // condicional caso nao de para tirar o excedente de peso do reservatorio de agua
                System.out.println(getNome() + " não suporta todos os tripulantes, libere os que estão a bordo primeiro");
            }
        } else {
            peso_tripulantes += peso_civis; // adiciona o peso dos civis ao peso da tripulaçao
            System.out.println(getNome() + " conseguiu suportar todos a bordo");
        }
    }

    public void liberar_tripulantes(){
        if (getAltitude() == 0){ // quer dizer que esta no chao para deixar os tripulantes em lugar seguro
            peso_tripulantes = 0;
            System.out.println(getNome() + " liberou os tripulantes com sucesso");
        } else { // condicional caso nao esteja no chao/lugar seguro
            System.out.println(getNome() + " deve estar em um lugar seguro para liberar os tripulantes");
        }
    }

    public void aprimora(int peso_adicional){
        if (getAltitude() == 0){ // isso quer dizer que ele ainda nao esta indo socorrer ninguem, por estar na base pode ser aprimorado
            peso_max += peso_adicional; // aumenta o limite de peso do robo somando o peso_adicional ao limite atual
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
