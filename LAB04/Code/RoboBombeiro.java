package LAB04.Code;

import java.util.Iterator;
import LAB04.Code.Obstaculo.TipoObstaculo;
import LAB04.Code.Exceptions.ForaDosLimitesException;
import LAB04.Code.Exceptions.LocalOcupadoException;

/*
 * SubClasse de RoboAereo
 *  
 * Atributos:
 * - int peso_max
 * - int reservatorio
 * - SensorDeFogo sensorDeFogo
 * - int raio_de_cessar_fogo
 * 
 * Métodos:
 * - adicionar_agua(int litros)
 * - apagar_fogo()
 * - identificar_fogo()
 * - aprimora(int peso_adicional)
 */

public class RoboBombeiro extends Robo implements Sensoreavel, FogoZero {
    private int altitudeMaxima;
    private int altitude;
    private int peso_max; // peso maximo que o robo suporta;
    private int reservatorio; // litros de agua no reservatorio
    private SensorDeFogo sensorDeFogo; // sensor de fogo com raio 10
    private int raio_de_cessar_fogo; // raio para ter uma distancia segura para apagar o fogo
    
    // Construtor
    public RoboBombeiro(String id, EstadoRobo estado, TipoEntidade tipo, int pos_x, int pos_y, int altitude, 
                        Ambiente ambiente, int altitudeMaxima, double raiosensor, int peso_max, int raio_de_cessar_fogo) {
        super(id, estado, tipo, pos_x, pos_y, altitude, ambiente);
        this.altitudeMaxima = altitudeMaxima;
        this.sensorDeFogo = new SensorDeFogo(raiosensor); // sensor de fogo com raio 10
        this.peso_max = peso_max;
        this.raio_de_cessar_fogo = raio_de_cessar_fogo;
        this.reservatorio = peso_max; // litros de agua no reservatorio (começa cheio)
    }
    
    // Metodos

    public void subir(int deltaZ) {
        // Verifica se a nova altitude está dentro dos limites do ambiente
        if (!getAmbiente().dentroDosLimites(getX(), getY(), getZ() + deltaZ)){
            System.out.println(getId() + " não pode subir para essa altitude, pois está fora dos limites do ambiente!\n");
            return;
        }
        else if (getZ() + deltaZ <= altitudeMaxima) {
            altitude += deltaZ;
            System.out.println(getId() + " subiu para " + getZ() + " metros de altitude.\n");
        } 
        else if (altitude + deltaZ > altitudeMaxima) {
            System.out.println(getId() + " não pode subir acima da altitude máxima de " + altitudeMaxima + " metros!\n");
        }
    }

    public void descer(int deltaZ) {
        if (altitude - deltaZ >= 0) {
            altitude -= deltaZ;
            if (altitude == 0) { // mensagem caso volte para o chão
                System.out.println(getId() + " retornou ao solo.\n");
            } else { // se descer para qualquer outra altitude
                System.out.println(getId() + " desceu para " + getZ() + " metros de altitude.\n");
            }
        } else { // condicional caso tente "entrar" na terra
            System.out.println(getId() + " não pode descer abaixo do nível do solo!\n");
        }
    }

    @Override
    public void adicionar_agua(int litros){
        // Verifica se o robô está dentro de um lago
        boolean dentro_lago = false;
        for (Entidade e : getAmbiente().getEntidades()) {
            if (e.getTipo() == TipoEntidade.OBSTACULO){
                Obstaculo o = (Obstaculo) e;
                if (o.getTipoObstaculo().getNome() == "Lago" && o.getX() <= getX() && o.getY() <= getY() &&
                o.getPosicaoX2() >= getX() && o.getPosicaoY2() >= getY()) {
                    System.out.println(getId() + " está em um lago e pode abastecer água.");
                    dentro_lago = true;
                    break;
                }
            }
        }
        if (!dentro_lago) {
            System.out.println(getId() + " não está em um lago e não pode abastecer água!\n");
            return;
        }
        int peso_total = reservatorio + litros; // somatorio dos pesos
        if (peso_total > peso_max){
            int excedente = peso_total - peso_max;
            System.out.println("A quantidade " + litros + " litros excede " + excedente + " litros da capacidade máxima de " + peso_max + " litros do " + getId() + "!\n");
        } else {
            reservatorio += litros;
            System.out.println(getId() + " foi abastecido com sucesso.");
            System.out.println("Reservatorio possui " + reservatorio + " litros.\n");
        }
    }

    public void apagar_fogo() throws ForaDosLimitesException, LocalOcupadoException{
        int litros_necessarios = 0; // quantidade de agua para apagar o fogo
        // Verifica se o robô está dentro de um incêndio
        Iterator<Entidade> iterator = getAmbiente().getEntidades().iterator();
        while (iterator.hasNext()) {
            Entidade e = iterator.next();
            if (e.getTipo() == TipoEntidade.OBSTACULO){
                Obstaculo o = (Obstaculo) e;

                // Nesse bloco aqui vejo se ele está perto o suficiente de algum obstpaculo que tenha fogo
                int Xmaisproximo = Math.max(o.getX(), Math.min(getX(), o.getPosicaoX2()));
                int Ymaisproximo = Math.max(o.getX(), Math.min(getY(), o.getPosicaoY2()));
                double distancia = Math.sqrt(Math.pow(Xmaisproximo - getX(), 2) + Math.pow(Ymaisproximo - getY(), 2));
                
                if (o.getTipoObstaculo().isFogo() && distancia <= raio_de_cessar_fogo) { // Condional caso o obstaculo detectado seja um fogo
                    if (o.getAltura() <= getZ()){ // condicional para ver se está na altura do fogo
                        System.out.println(getId() + " está próximo de um incêndio.");

                        if (o.getTipoObstaculo() == TipoObstaculo.FOGO) litros_necessarios = 100; // quantidade de agua para apagar o fogo
                        else if (o.getTipoObstaculo() == TipoObstaculo.PREDIOEMCHAMAS) litros_necessarios = 1000; // quantidade de agua para apagar o predio em chamas
                        
                        if (reservatorio < litros_necessarios){ // condicional caso nao tenha agua o suficiente
                            int deficit = litros_necessarios - reservatorio; // quanto ira faltar de agua para apagar o fogo
                            System.out.println(getId() + " precisa de " + deficit + " litros a mais para apagar o incêndio!\n");
                        } else {
                            reservatorio -= litros_necessarios;
                            if (o.getTipoObstaculo() == TipoObstaculo.FOGO) {
                                System.out.println("Incêndio apagado com sucesso.");
                                // esse print está duplicado, pois quero que apareça tudo do robo primeiro depois da remoção do objeto,
                                // por isso nao botei depois, a fim de englobar os dois casos
                                System.out.println("Foram usados " + litros_necessarios + " litros para apagar o incêndio.");
                                System.out.println("O reservatório está atualmente com " + reservatorio + " litros.\n"); 
                                iterator.remove();
                            } else if (o.getTipoObstaculo() == TipoObstaculo.PREDIOEMCHAMAS) {
                                System.out.println("Prédio não mais está em chamas.");
                                System.out.println("Foram usados " + litros_necessarios + " litros para apagar o incêndio.");
                                System.out.println("O reservatório está atualmente com " + reservatorio + " litros.\n");
                                iterator.remove();
                                Obstaculo novo_Predinho = new Obstaculo(o.getX(), o.getY(), TipoObstaculo.PREDIO, TipoEntidade.OBSTACULO);
                                getAmbiente().adicionarEntidade(novo_Predinho);
                            }
                        }
                        break;
                    } else {
                        int falta_altura = o.getAltura() - getZ();
                        System.out.println(getId() + " não está na altura do fogo, suba " + falta_altura + " metros para apagar o fogo!\n");
                    }
                }
            }
        }
    }

    public void aprimorar(int peso_adicional){
        //verifica se o robô esta dentro de uma oficina
        for (Entidade e : getAmbiente().getEntidades()) {
            if(e.getTipo() == TipoEntidade.OBSTACULO){
                Obstaculo o = (Obstaculo) e;
                if (o.getTipoObstaculo() == TipoObstaculo.OFICINA) {
                    if (getX() <= o.getPosicaoX2() && getX() >= o.getX() && getY() <= o.getPosicaoY2() && getY() >= o.getY()) {
                        System.out.println(getId() + " está dentro da oficina e pode ser aprimorado.");
                        peso_max += peso_adicional;
                        System.out.println("Reservatório máximo do " + getId() + " agora é de " + peso_max + " litros.\n");
                    } else {
                        System.out.println(getId() + " não está dentro da oficina e não pode ser aprimorado!\n");
                    }
                }
            }
        }
    }

    @Override
    public String getDescricao() {
        String descricao = "Robô bombeiro: Capaz de voar e apagar fogos, o único porém é que existe uma capacidade" +
         " máxima de peso de água que ele pode armazenar, aprimore sua capacidade na oficina e reabasteça nos lagos para apagar todos os fogos do ambiente e salvar a todos!";
        return descricao;
    }
    
    //Métodos do sensoreavel
    @Override
    public void acionarSensores(){ // basicamente irá utilizar o sensor de fogo para ver se tem fogo próximo
        sensorDeFogo.monitorar(getX(), getY(), getZ(), getAmbiente());
    }


    // Getters e Setters
    public int getAltitudeMaxima() { return altitudeMaxima; }
    public int getCapacidade() { return peso_max; }
    public int getReservatorio() { return reservatorio; }

    public void setAltitudeMaxima(int altitudeMaxima) { this.altitudeMaxima = altitudeMaxima; }
    
}
