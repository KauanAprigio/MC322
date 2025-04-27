package LAB03.Code;

import LAB03.Code.Obstaculo.TipoObstaculo;

/*
 * Classe criada RoboBombeiro
 *  
 * Atributos:
 * - peso_max
 * - reservatorio
 * 
 * Métodos:
 * - adicionar_agua(int litros)
 * - apagar_fogo(int litros_necessarios)
 * - aprimora(int peso_adicional)
 */
// Subclasse de RoboAereo
// resgata civis, apaga fogo e carrega agua
// e tem um peso maximo que suporta
public class RoboBombeiro extends RoboAereo {
    //Atributos adicionais
    private int peso_max; // peso maximo que o robo suporta;
    private int reservatorio = 0; // litros de agua no reservatorio
    private SensorDeFogo sensorDeFogo; // sensor de fogo com raio 10
    private int raio_de_cessar_fogo; // raio para ter uma distancia segura para apagar o fogo
    
    // Construtor
    public RoboBombeiro(String nome, int posicaoX, int posicaoY, int altitudeMaxima, 
                                double raiosensor, Ambiente ambiente, int peso_max, int raio_de_cessar_fogo) {
        super(nome, posicaoX, posicaoY, altitudeMaxima, raiosensor, ambiente);
        this.sensorDeFogo = new SensorDeFogo(raiosensor); // sensor de fogo com raio 10
        this.peso_max = peso_max;
        this.raio_de_cessar_fogo = raio_de_cessar_fogo;
    }
    
    // Metodos
    public void adicionar_agua(int litros){
        // Verifica se o robô está dentro de um lago
        boolean dentro_lago = false;
        for (Obstaculo o : getAmbiente().getObstaculos()) {
            if (o.getTipo().getNome() == "Lago" && o.getPosicaoX1() <= getX() && o.getPosicaoY1() <= getY() &&
                o.getPosicaoX2() >= getX() && o.getPosicaoY2() >= getY()) {
                System.out.println(getNome() + " está em um lago e pode abastecer água.");
                dentro_lago = true;
                break;
            }
        }
        if (!dentro_lago) {
            System.out.println(getNome() + " não está em um lago e não pode abastecer água!\n");
            return;
        }
            int peso_total = reservatorio + litros; // somatorio dos pesos
            if (peso_total > peso_max){
                int excedente = peso_total - peso_max;
                System.out.println("A quantidade " + litros + " litros excede " + excedente + " litros da capacidade máxima de " + peso_max + " litros do " + getNome() + "!\n");
            } else {
                reservatorio += litros;
                System.out.println(getNome() + " foi abastecido com sucesso.");
                System.out.println("Reservatorio possui " + reservatorio + " litros.\n");
            }
    }

    public void apagar_fogo(int litros_necessarios){
        // Verifica se o robô está dentro de um incêndio
        for (Obstaculo o : getAmbiente().getObstaculos()) {

            // Nesse bloco aqui vejo se ele está perto o suficiente de algum obstpaculo que tenha fogo
            int Xmaisproximo = Math.max(o.getPosicaoX1(), Math.min(getX(), o.getPosicaoX2()));
            int Ymaisproximo = Math.max(o.getPosicaoY1(), Math.min(getY(), o.getPosicaoY2()));
            double distancia = Math.sqrt(Math.pow(Xmaisproximo - getX(), 2) + Math.pow(Ymaisproximo - getY(), 2));

            if (o.getTipo().isFogo() && distancia <= raio_de_cessar_fogo) { // Condional caso o obstaculo detectado seja um fogo
                if (o.getAltura() <= getAltitude()){ // condicional para ver se está na altura do fogo
                    System.out.println(getNome() + " está próximo de um incêndio.");
                    if (reservatorio < litros_necessarios){ // condicional caso nao tenha agua o suficiente
                        int deficit = litros_necessarios - reservatorio; // quanto ira faltar de agua para apagar o fogo
                        System.out.println(getNome() + " precisa de " + deficit + " litros a mais para apagar o incêndio!\n");
                    } else {
                        reservatorio -= litros_necessarios;
                        if (o.getTipo() == TipoObstaculo.FOGO) {
                            System.out.println("Incêndio apagado com sucesso.");
                            System.out.println("O reservatório está atualmente com " + reservatorio + " litros.\n"); // esse print está duplicado, pois quero que apareça tudo do robo primeiro depois da remoção do objeto, por isso nao botei depois, a fim de englobar os dois casos
                            getAmbiente().removerObstaculo(o); // remove o fogo do ambiente
                        } else if (o.getTipo() == TipoObstaculo.PREDIOEMCHAMAS) {
                            System.out.println("Prédio não mais está em chamas.");
                            System.out.println("O reservatório está atualmente com " + reservatorio + " litros.\n");
                            getAmbiente().adicionarObstaculo(new Obstaculo(o.getPosicaoX1(), o.getPosicaoY1(), TipoObstaculo.PREDIO)); // agora o predio nao esta mais em chamas
                            getAmbiente().removerObstaculo(o); // muda para somente um predio, nao um predio em chamas
                        }
                    }
                    break;
                } else {
                    int falta_altura = o.getAltura() - getAltitude();
                    System.out.println(getNome() + " não está na altura do fogo, suba " + falta_altura + " metros para apagar o fogo!\n");
                }
            }
        }
    }

    public void indentificar_fogo(){
        sensorDeFogo.monitorar(getX(), getY(), getAltitude(), getAmbiente());
    }

    public void aprimorar(int peso_adicional){
        //verifica se o robô esta dentro de uma oficina
        for (Obstaculo o : getAmbiente().getObstaculos()) {
            if (o.getTipo() == TipoObstaculo.OFICINA) {
                if (getX() <= o.getPosicaoX2() && getX() >= o.getPosicaoX1() && getY() <= o.getPosicaoY2() && getY() >= o.getPosicaoY1()) {
                    System.out.println(getNome() + " está dentro da oficina e pode ser aprimorado.");
                    peso_max += peso_adicional;
                    System.out.println("Reservatório máximo do " + getNome() + " agora é de " + peso_max + " litros.\n");
                } else {
                    System.out.println(getNome() + " não está dentro da oficina e não pode ser aprimorado!\n");
                }
            }
        }
    }


    // Getters e Setters
    public int getCapacidade() { return peso_max; }
    public int getReservatorio() { return reservatorio; }
    
}
