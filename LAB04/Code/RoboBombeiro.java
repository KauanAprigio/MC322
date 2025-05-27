package LAB04.Code;

import java.util.Iterator;
import LAB04.Code.Obstaculo.TipoObstaculo;
import LAB04.Code.AbstractClasses.Robo;
import LAB04.Code.Exceptions.ErrorAbastecimentoException;
import LAB04.Code.Exceptions.ForaDosLimitesException;
import LAB04.Code.Exceptions.LocalOcupadoException;
import LAB04.Code.Interfaces.Aprimoravel;
import LAB04.Code.Interfaces.Comunicavel;
import LAB04.Code.Interfaces.Entidade;
import LAB04.Code.Interfaces.FogoZero;
import LAB04.Code.Exceptions.ErrorApagarFogoException;
import LAB04.Code.Exceptions.ErrorAprimoramentoException;

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

public class RoboBombeiro extends Robo implements FogoZero, Comunicavel, Aprimoravel {
    private int altitudeMaxima;
    private int altitude = 0; // altitude do robô, começa no chão
    private int peso_max; // peso maximo que o robo suporta;
    private int reservatorio; // litros de agua no reservatorio
    private int raio_de_cessar_fogo; // raio para ter uma distancia segura para apagar o fogo
    
    // Construtor
    public RoboBombeiro(String id, EstadoRobo estado, int pos_x, int pos_y, int altitude, 
                        Ambiente ambiente, int altitudeMaxima, int peso_max, int raio_de_cessar_fogo) {
        super("Bombeiro_"+id, estado, pos_x, pos_y, altitude, ambiente);
        this.altitudeMaxima = altitudeMaxima;
        this.peso_max = peso_max;
        this.raio_de_cessar_fogo = raio_de_cessar_fogo;
        this.reservatorio = peso_max / 2; // litros de agua no reservatorio (começa na metade)
    }
    
    @Override
    public void adicionar_agua(int litros) throws ErrorAbastecimentoException {
        // Verifica se o robô está dentro de um lago
        boolean dentro_lago = false;
        for (Entidade e : getAmbiente().getEntidades()) {
            if (e.getTipo() == TipoEntidade.OBSTACULO){
                Obstaculo o = (Obstaculo) e;
                if (o.getTipoObstaculo().getNome() == "Lago" && o.getX() <= getX() && o.getY() <= getY() &&
                o.getPosicaoX2() >= getX() && o.getPosicaoY2() >= getY() && getZ() == 1) {
                    System.out.println(getId() + " está em um lago e pode abastecer água.");
                    dentro_lago = true;
                    break;
                }
            }
        }
        if (!dentro_lago) {
            throw new ErrorAbastecimentoException(getId() + " não está em um lago e não pode abastecer água!"); 
        }
        if (getEstado() == EstadoRobo.OFF) {
            throw new ErrorAbastecimentoException("Não foi possível abastecer o robô: " + getId() + " pois ele está desligado!");
        }
        if (litros <= 0) {
            throw new ErrorAbastecimentoException("Quantidade de litros inválida! Deve ser maior que zero.");
        }
        int peso_total = reservatorio + litros; // somatorio dos pesos
        if (peso_total > peso_max){
            int excedente = peso_total - peso_max;
            String msg = "A quantidade " + litros + " litros excede " + excedente + " litros da capacidade máxima de " + peso_max + " litros do " + getId() + "!\n";
            throw new ErrorAbastecimentoException(msg);
        } else {
            reservatorio += litros;
            System.out.println(getId() + " foi abastecido com sucesso.");
            System.out.println("Reservatorio possui " + reservatorio + " litros de um máximo de: " + peso_max + " litros\n");
        }
    }
    @Override
    public void apagar_fogo() throws ErrorApagarFogoException {
        int litros_necessarios = 0; // quantidade de agua para apagar o fogo
        // Verifica se o robô está dentro de um incêndio
        Iterator<Entidade> iterator = getAmbiente().getEntidades().iterator();
        while (iterator.hasNext()) {
            Entidade e = iterator.next();
            if (e.getTipo() == TipoEntidade.OBSTACULO){
                Obstaculo o = (Obstaculo) e;
                
                // Nesse bloco aqui vejo se ele está perto o suficiente de algum obstaculo que tenha fogo
                int Xmaisproximo = Math.max(o.getX(), Math.min(getX(), o.getPosicaoX2()));
                int Ymaisproximo = Math.max(o.getY(), Math.min(getY(), o.getPosicaoY2()));
                double distancia = Math.sqrt(Math.pow(Xmaisproximo - getX(), 2) + Math.pow(Ymaisproximo - getY(), 2));
                
                if (o.getTipoObstaculo().isFogo() && distancia <= raio_de_cessar_fogo) { // Condional caso o obstaculo detectado seja um fogo
                    if (o.getZ() <= getZ()){ // condicional para ver se está na altura do fogo
                        System.out.println(getId() + " está próximo de um incêndio.");

                        if (o.getTipoObstaculo() == TipoObstaculo.FOGO) litros_necessarios = 100; // quantidade de agua para apagar o fogo
                        else if (o.getTipoObstaculo() == TipoObstaculo.PREDIOEMCHAMAS) litros_necessarios = 1000; // quantidade de agua para apagar o predio em chamas
                        
                        if (reservatorio < litros_necessarios){ // condicional caso nao tenha agua o suficiente
                            int deficit = litros_necessarios - reservatorio; // quanto ira faltar de agua para apagar o fogo
                            String msg = getId() + " precisa de " + deficit + " litros a mais para apagar o incêndio!";
                            throw new ErrorApagarFogoException(msg);
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
                                Obstaculo novo_Predinho = new Obstaculo(o.getX(), o.getY(), TipoObstaculo.PREDIO, getAmbiente(), TipoEntidade.OBSTACULO);
                                try {
                                    getAmbiente().adicionarEntidade(novo_Predinho, false);
                                } catch (LocalOcupadoException | ForaDosLimitesException e1) {
                                    // Nunca deveria acontecer, pois o predio é um obstaculo que ja existe
                                    // e o ambiente ja foi verificado para nao ter obstaculos
                                    // nesse local, mas vou deixar aqui para evitar erros futuros
                                    System.out.println("Erro Inesperado: " + e1.getMessage());
                                }
                            }
                        }                         
                    } else {
                        int falta_altura = o.getZ() - getZ();
                        String msg = getId() + " não está na altura do fogo, suba " + falta_altura + " metros para apagar o fogo!";
                        throw new ErrorApagarFogoException(msg);
                    }
                }
            }
        }
    }

    @Override
    public void aprimorar(int upgrade) throws ErrorAprimoramentoException {
        // Verifica se o upgrade é válido
        if (upgrade <= 0) {
            throw new ErrorAprimoramentoException("Upgrade inválido! O valor deve ser maior que zero.");
        }
        //verifica se o robô esta dentro de uma oficina
        for (Entidade e : getAmbiente().getEntidades()) {
            if(e.getTipo() == TipoEntidade.OBSTACULO){
                Obstaculo o = (Obstaculo) e;
                if (o.getTipoObstaculo() == TipoObstaculo.OFICINA) {
                    if (getX() <= o.getPosicaoX2() && getX() >= o.getX() && getY() <= o.getPosicaoY2() && getY() >= o.getY()) {
                        System.out.println(getId() + " está dentro da oficina e pode ser aprimorado.");
                        peso_max += upgrade;
                        System.out.println("Reservatório máximo do " + getId() + " agora é de " + peso_max + " litros.\n");
                    } else {
                        String msg = getId() + " não está dentro da oficina e não pode ser aprimorado!";
                        throw new ErrorAprimoramentoException(msg);
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

    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem){
        destinatario.receberMensagem(mensagem);
    }

    @Override
    public void receberMensagem(String mensagem){
        System.out.println(mensagem);
    }

    @Override
    public boolean isComunicavel() { 
        if (getEstado() == EstadoRobo.OFF) {
            return false; // O robô bombeiro não é comunicável se estiver desligado
        }
        return true; 
    } // O robô bombeiro é comunicável quando ligado


    // Getters e Setters
    public int getAltitudeMaxima() { return altitudeMaxima; }
    public int getCapacidade() { return peso_max; }
    public int getReservatorio() { return reservatorio; }
    public int getRaioDeCessarFogo() { return raio_de_cessar_fogo; }
    public int getAltitude() { return altitude; }

    public void setAltitudeMaxima(int altitudeMaxima) { this.altitudeMaxima = altitudeMaxima; }
}
