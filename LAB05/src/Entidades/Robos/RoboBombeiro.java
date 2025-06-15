package LAB05.src.Entidades.Robos;
import java.util.Iterator;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.ComunicadorCentral;
import LAB05.src.Entidades.Interfaces.Comunicavel;
import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Entidades.Obstaculos.Obstaculo.TipoObstaculo;
import LAB05.src.Exceptions.*;

public class RoboBombeiro extends Robo implements Comunicavel {
    private int altitudeMaxima;
    private int peso_max; // peso maximo que o robo suporta;
    private int reservatorio; // litros de agua no reservatorio
    private int raio_de_cessar_fogo; // raio para ter uma distancia segura para apagar o fogo
    private Obstaculo Ultimo_incendio = null; // ultimo incendio apagado pelo robo, para evitar apagar o mesmo incendio mais de uma vez
    private boolean aprimorado = false; // variável para verificar se o robô foi aprimorado
    private Obstaculo incendio_proximo = null;
    private ComunicadorCentral central;
    // Construtor
    public RoboBombeiro(String id, int pos_x, int pos_y, int pos_z, char representacao, int reservatorio,
                        Ambiente ambiente, int altitudeMaxima, int peso_max, int raio_de_cessar_fogo) {
        super("Bombeiro_"+id, pos_x, pos_y, pos_z, representacao, ambiente);
        this.altitudeMaxima = altitudeMaxima;
        this.peso_max = peso_max;
        this.raio_de_cessar_fogo = raio_de_cessar_fogo;
        this.reservatorio = reservatorio; // litros de agua no reservatorio (começa vazio)
        central = new ComunicadorCentral(47, 47, ambiente); // vai ficar sempre no meio do mapa
    }
    
    public void adicionar_agua() throws ErrorAbastecimentoException {
        // Verifica se o robô está dentro de um lago
        boolean dentro_lago = false;
        for (Entidade e : getAmbiente().getEntidades()) {
            if (e.getTipo() == TipoEntidade.LOCAL){
                Obstaculo o = (Obstaculo) e;
                if (o.getTipoObstaculo().getNome() == "Lago" && o.getX_1() <= getX_1() && o.getY_1() <= getY_1() &&
                o.getX_2() >= getX_1() && o.getY_2() >= getY_1()) {
                    if (getZ_1() != 1) {
                        throw new ErrorAbastecimentoException(getId() + " está voando, desça para altura 1 para abastecer água!\n");  // Se o robô estiver voando, não pode abastecer
                    }
                    System.out.println(getId() + " está em um lago e pode abastecer água.");
                    dentro_lago = true;
                    break;
                }
            }
        }
        
        if (!dentro_lago) {
            throw new ErrorAbastecimentoException(getId() + " não está em um lago e não pode abastecer água!\n"); 
        }
        if (getEstado() == EstadoRobo.OFF) {
            throw new ErrorAbastecimentoException("Não foi possível abastecer o robô: " + getId() + " pois ele está desligado!\n");
        }
        
        reservatorio = peso_max; // Abastece o reservatório até o máximo
        System.out.println(getId() + " foi abastecido com sucesso.");
        System.out.println("Reservatorio possui " + reservatorio + " litros de um máximo de: " + peso_max + " litros.\n");
    }
    

    public void apagar_fogo(Comunicavel Central) throws ErrorApagarFogoException, ErroComunicacaoException, RoboDesligadoException {
        if (getEstado() == EstadoRobo.OFF){ // condicional caso o robo esteja desligado
            String msg = "O robô" + getId() + " não pode combater nenhum fogo, pois está desligado!\n";
            throw new RoboDesligadoException(msg);
        }
        int litros_necessarios = 0; // quantidade de agua para apagar o fogo
        boolean encontrou_fogo = false; // flag para verificar se encontrou algum fogo
        // Verifica se o robô está dentro de um incêndio
        Iterator<Entidade> iterator = getAmbiente().getEntidades().iterator();
        while (iterator.hasNext()) {
            Entidade e = iterator.next();
            if (e.getTipo() == TipoEntidade.FOGO){
                Obstaculo fogo = (Obstaculo) e;
                
                // Nesse bloco aqui vejo se ele está perto o suficiente de algum obstaculo que tenha fogo
                int Xmaisproximo = Math.max(fogo.getX_1(), Math.min(getX_1(), fogo.getX_2()));
                int Ymaisproximo = Math.max(fogo.getY_1(), Math.min(getY_1(), fogo.getY_2()));
                double distancia = Math.sqrt(Math.pow(Xmaisproximo - getX_1(), 2) + Math.pow(Ymaisproximo - getY_1(), 2));
                
                if (distancia <= raio_de_cessar_fogo) { // Condional caso o obstaculo detectado seja um fogo
                    if (getZ_1() >= fogo.getZ_2()){ // condicional para ver se está na altura do fogo
                        System.out.println(getId() + " está próximo de um incêndio.");

                        if (fogo.getTipoObstaculo() == TipoObstaculo.FOGO) litros_necessarios = 100; // quantidade de agua para apagar o fogo
                        else if (fogo.getTipoObstaculo() == TipoObstaculo.PREDIOEMCHAMAS) litros_necessarios = 1000; // quantidade de agua para apagar o predio em chamas
                        
                        if (reservatorio < litros_necessarios){ // condicional caso nao tenha agua o suficiente
                            int deficit = litros_necessarios - reservatorio; // quanto ira faltar de agua para apagar o fogo
                            String msg = getId() + " precisa de " + deficit + " litros a mais para apagar o incêndio!\n";
                            throw new ErrorApagarFogoException(msg);
                        } else {
                            reservatorio -= litros_necessarios;
                            if (fogo.getTipoObstaculo() == TipoObstaculo.FOGO) {
                                System.out.println("Incêndio apagado com sucesso.");
                                // esse print está duplicado, pois quero que apareça tudo do robo primeiro depois da remoção do objeto,
                                // por isso nao botei depois, a fim de englobar os dois casos
                                System.out.println("Foram usados " + litros_necessarios + " litros para apagar o incêndio.");
                                System.out.println("O reservatório está atualmente com " + reservatorio + " litros.\n"); 
                                try{
                                    Ultimo_incendio = fogo; // guarda o ultimo incendio apagado pelo robo
                                    getAmbiente().removerEntidade(fogo,false);
                                } catch (EntidadeNaoEncontradaException e1) {
                                    // Nunca deveria acontecer, pois o obstaculo é um obstaculo que ja existe
                                    System.out.println("Erro Inesperado: " + e1.getMessage());
                                }
                            } else if (fogo.getTipoObstaculo() == TipoObstaculo.PREDIOEMCHAMAS) {
                                System.out.println("Prédio não mais está em chamas.");
                                System.out.println("Foram usados " + litros_necessarios + " litros para apagar o incêndio.");
                                System.out.println("O reservatório está atualmente com " + reservatorio + " litros.\n");
                                Obstaculo novo_Predinho = new Obstaculo(fogo.getX_1(), fogo.getY_1(), TipoObstaculo.PREDIO, getAmbiente(), TipoEntidade.LOCAL);
                                try {
                                    Ultimo_incendio = fogo; // guarda o ultimo incendio apagado pelo robo
                                    getAmbiente().removerEntidade(fogo, false);
                                    getAmbiente().adicionarEntidade(novo_Predinho, false);
                                } catch (LocalOcupadoException | ForaDosLimitesException | EntidadeNaoEncontradaException e1) {
                                    // Nunca deveria acontecer, pois o predio é um obstaculo que ja existe
                                    // e o ambiente ja foi verificado para nao ter obstaculos
                                    // nesse local, mas vou deixar aqui para evitar erros futuros
                                    System.out.println("Erro Inesperado: " + e1.getMessage());
                                }    
                            }
                            enviarMensagem(Central, "Incêndio apagado");
                            encontrou_fogo = true; // marca que encontrou um fogo para apagar
                            break; // Apaga apenas 1 fogo por vez, se quiser apagar mais, precisa chamar o método novamente
                        }                         
                    } else {
                        int falta_altura = fogo.getZ_2() - getZ_1();
                        String msg = getId() + " não está na altura do fogo, suba " + falta_altura + " metros para apagar o fogo!\n";
                        throw new ErrorApagarFogoException(msg);
                    }
                }
            }
        }
        if (!encontrou_fogo) {
            String msg = getId() + " não encontrou nenhum incêndio próximo para apagar.\n";
            throw new ErrorApagarFogoException(msg);
        }
    }

    public void aprimorar() throws ErrorAprimoramentoException {
        //verifica se o robô esta dentro de uma oficina
        for (Entidade e : getAmbiente().getEntidades()) {
            if(e.getTipo() == TipoEntidade.LOCAL){
                Obstaculo o = (Obstaculo) e;
                if (o.getTipoObstaculo() == TipoObstaculo.OFICINA) {
                    if (getX_1() <= o.getX_2() && getX_1() >= o.getX_1() && getY_1() <= o.getY_2() && getY_1() >= o.getY_1()) {
                        if (aprimorado) {
                            String msg = getId() + " já está aprimorado e não pode ser aprimorado novamente!\n";
                            throw new ErrorAprimoramentoException(msg);
                        }
                        System.out.println(getId() + " está dentro da oficina e pode ser aprimorado.");
                        peso_max += 1500; // aumenta a capacidade máxima de peso em 1500 litros
                        reservatorio = peso_max; // atualiza o reservatório para a nova capacidade máxima
                        aprimorado = true; // marca que o robô foi aprimorado
                        System.out.println("Reservatório máximo do " + getId() + " agora é de " + peso_max + " litros.");
                        System.out.println(getId() + " está com o reservatório cheio.\n");
                    } else {
                        String msg = getId() + " não está dentro da oficina e não pode ser aprimorado!\n";
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
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws ErroComunicacaoException {
        Entidade destino = (Entidade) destinatario;
        if (!getAmbiente().getEntidades().contains(destino) || destinatario == null) {
            throw new ErroComunicacaoException("Erro de comunicação: Destinatário não existe!\n");
        }
        destinatario.receberMensagem(mensagem, this);
    }

    @Override
    public void receberMensagem(String mensagem, Comunicavel remetente){
        // Aqui o robô bombeiro recebe a mensagem e pode processá-la ou exibi-la
        System.out.println(getId() + " recebeu a mensagem: " + mensagem);
        // Poderia implementar lógica adicional para processar a mensagem, se necessário
    }

    @Override
    public boolean isComunicavel() { 
        if (getEstado() == EstadoRobo.OFF) {
            return false; // O robô bombeiro não é comunicável se estiver desligado
        }
        return true; 
    } // O robô bombeiro é comunicável quando ligado

    public boolean estahAprimorado() {
        // Verifica se o robô está aprimorado
        return aprimorado;
    }
    

    // Getters e Setters
    public int getAltitudeMaxima() { return altitudeMaxima; }
    public int getCapacidade() { return peso_max; }
    public int getReservatorio() { return reservatorio; }
    public int getRaioDeCessarFogo() { return raio_de_cessar_fogo; }
    public Obstaculo getUltimoIncendio() { return Ultimo_incendio; }
    public Obstaculo incendioProximo() { return incendio_proximo; }

    public void setAltitudeMaxima(int altitudeMaxima) { this.altitudeMaxima = altitudeMaxima; }
    public void setUltimoIncendio(Obstaculo ultimo_incendio) { this.Ultimo_incendio = ultimo_incendio; }
    public void setIncendioProximo (Obstaculo incencio) { this.incendio_proximo = incencio; }
}
