package LAB05.src.Entidades.Robos;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.ComunicadorCentral;
import LAB05.src.Entidades.Interfaces.Comunicavel;
import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Entidades.Obstaculos.Obstaculo.TipoObstaculo;
import LAB05.src.Exceptions.*;

public class RoboBombeiro extends Robo implements Comunicavel {
    private int peso_max; // peso maximo que o robo suporta;
    private int reservatorio; // litros de agua no reservatorio
    private int raio_de_cessar_fogo; // raio para ter uma distancia segura para apagar o fogo
    private boolean aprimorado = false; // variável para verificar se o robô foi aprimorado
    private Obstaculo incendio_proximo = null;
    private ComunicadorCentral central;
    // Construtor
    public RoboBombeiro(String id, int pos_x, int pos_y, int pos_z, char representacao, int reservatorio,
                        Ambiente ambiente, int altitudeMaxima, int peso_max, int raio_de_cessar_fogo) {
        super("Bombeiro_"+id, pos_x, pos_y, pos_z, 'B', ambiente);
        this.peso_max = peso_max;
        this.raio_de_cessar_fogo = raio_de_cessar_fogo;
        this.reservatorio = reservatorio; // litros de agua no reservatorio (começa vazio)
    }

    public void apagar_fogo(Comunicavel Central) throws ErrorApagarFogoException, ErroComunicacaoException, RoboDesligadoException {
        getAmbiente().getLogger().inicializarAcao("Apagar fogo", getId());
        if (getEstado() == EstadoRobo.OFF){ // condicional caso o robo esteja desligado
            String msg = "O robô" + getId() + " não pode combater nenhum fogo, pois está desligado!\n";
            throw new RoboDesligadoException(msg);
        }
        int litros_necessarios = 0; // quantidade de agua para apagar o fogo
        getAmbiente().getLogger().logAcao(getId() + " está contactando a central para localizar o próximo fogo...");
        incendio_proximo = central.LocalizarFogoMaisProx(this);
        if (incendio_proximo == null){ // normalmente quando não tiver mais nenhum incêndio
            String msg = getId() + " não há mais incêndios para apagar.\n";
            throw new ErrorApagarFogoException(msg);
        }
        try{
            // move o robo para acima do incendio, z = 101 para ter certeza que estará acima do predio também em 100% dos casos
            getAmbiente().moverRobo(this, incendio_proximo.getX_1(), incendio_proximo.getY_1(), 101); 
        } catch (Exception e){
            getAmbiente().getLogger().logErr(e);
        }
            if (incendio_proximo.getTipoObstaculo() == TipoObstaculo.FOGO) litros_necessarios = 100; // quantidade de agua para apagar o fogo
            else if (incendio_proximo.getTipoObstaculo() == TipoObstaculo.PREDIOEMCHAMAS) litros_necessarios = 1000; // quantidade de agua para apagar o predio em chamas
                        
            if (reservatorio < litros_necessarios){ // condicional caso nao tenha agua o suficiente
                int deficit = litros_necessarios - reservatorio; // quanto ira faltar de agua para apagar o fogo
                String msg = getId() + " precisa de " + deficit + " litros a mais para apagar o incêndio!\n";
                throw new ErrorApagarFogoException(msg);
            } else {
                //irá apagar ou o fogo ou o prédio em chamas
                reservatorio -= litros_necessarios;
                if (incendio_proximo.getTipoObstaculo() == TipoObstaculo.FOGO) {
                    getAmbiente().getLogger().logAcao("Incêndio apagado com sucesso.");
                    getAmbiente().getLogger().logAcao("Foram usados " + litros_necessarios + " litros para apagar o incêndio.");
                    getAmbiente().getLogger().logAcao("O reservatório está atualmente com " + reservatorio + " litros."); 
                    try{
                        getAmbiente().removerEntidade(incendio_proximo,false);
                    } catch (EntidadeNaoEncontradaException e1) {
                        // Nunca deveria acontecer, pois o obstaculo é um obstaculo que ja existe
                        getAmbiente().getLogger().logErr(e1);
                    }
                } else if (incendio_proximo.getTipoObstaculo() == TipoObstaculo.PREDIOEMCHAMAS) {
                    getAmbiente().getLogger().logAcao("Prédio não mais está em chamas.");
                    getAmbiente().getLogger().logAcao("Foram usados " + litros_necessarios + " litros para apagar o incêndio.");
                    getAmbiente().getLogger().logAcao("O reservatório está atualmente com " + reservatorio + " litros.");
                    Obstaculo novo_Predinho = new Obstaculo(incendio_proximo.getX_1(), incendio_proximo.getY_1(), TipoObstaculo.PREDIO, getAmbiente(), TipoEntidade.LOCAL);
                    try {
                        getAmbiente().removerEntidade(incendio_proximo, false);
                        getAmbiente().adicionarEntidade(novo_Predinho, false);
                    } catch (LocalOcupadoException | ForaDosLimitesException | EntidadeNaoEncontradaException e1) {
                        // Nunca deveria acontecer, pois o predio é um obstaculo que ja existe
                        // e o ambiente ja foi verificado para nao ter obstaculos
                        // nesse local, mas vou deixar aqui para evitar erros futuros
                        getAmbiente().getLogger().logErr(e1);
                    }    
                }
                enviarMensagem(Central, "Incêndio apagado"); // aqui irá tirar o fogo do array fogos do ComunicadorCentral
            }                         
        getAmbiente().getLogger().finalizarAcao("Apagar fogo finalizado por " + getId() + "\n");
    }
    
    public void aprimorar() throws ErrorAprimoramentoException {
        getAmbiente().getLogger().inicializarAcao("Aprimoramento", getId());

        //robos desligados nao podem ser aprimorados
         if (getEstado() == EstadoRobo.OFF) {
            throw new ErrorAprimoramentoException("Não foi possível aprimorar o robô: " + getId() + " pois ele está desligado!\n");
        }
        
        //confirma se está na oficina
        if (getLocalAtualRep() == 'o'){
            if (estahAprimorado()) { // se já estiver aprimorado não pode fazer o upgrade
                String msg = getId() + " já está aprimorado e não pode ser aprimorado novamente!\n";
                throw new ErrorAprimoramentoException(msg);
            }

            //bloco para ser aprimorado com 1500l no reservatorio
            getAmbiente().getLogger().logAcao(getId() + " está dentro da oficina e pode ser aprimorado.");
            peso_max += 1500; // aumenta a capacidade máxima de peso em 1500 litros
            reservatorio = peso_max; // atualiza o reservatório para a nova capacidade máxima
            aprimorado = true; // marca que o robô foi aprimorado

            central.registrarMensagem(getId(), "Aprimoramento foi concluído com sucesso.");
            
            getAmbiente().getLogger().logAcao("Reservatório máximo do " + getId() + " agora é de " + peso_max + " litros.");
            getAmbiente().getLogger().logAcao(getId() + " está com o reservatório cheio.");
            getAmbiente().getLogger().finalizarAcao("O Aprimoramento de " + getId() + " foi finalizado com sucesso.\n");
        } else { // caso não esteja em um oficina
            String msg = getId() + " não está dentro da oficina e não pode ser aprimorado!\n";
            throw new ErrorAprimoramentoException(msg);
        }
    }

    public void adicionar_agua() throws ErrorAbastecimentoException {
        getAmbiente().getLogger().inicializarAcao("Abastecimento", getId());
        //condicional para ver se o robo está na altura adequada para abastecer
        if (getZ_1() != 1) {
            throw new ErrorAbastecimentoException(getId() + " está muito alto, desça para altura 1 para abastecer água!\n");  // Se o robô estiver voando, não pode abastecer
        }
        
        //robos desligados não podem abastecer agua
        if (getEstado() == EstadoRobo.OFF) {
            throw new ErrorAbastecimentoException("Não foi possível abastecer o robô: " + getId() + " pois ele está desligado!\n");
        }

        //confirmo se está no lago e abasteço o robo
        if (getLocalAtualRep() == 'l'){
            getAmbiente().getLogger().logAcao(getId() + " está em um lago e pode ser abastecido.");
            reservatorio = peso_max; // Abastece o reservatório até o máximoAdd commentMore actions
            
            central.registrarMensagem(getId(), "Abastecimento foi concluído com sucesso.");
            
            getAmbiente().getLogger().logAcao("Reservatório possui " + reservatorio + " litros de um máximo de: " + peso_max + " litros.");
            getAmbiente().getLogger().finalizarAcao("O abastecimento de " + getId() + " foi finalizado com sucesso.\n");   
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
        getAmbiente().getLogger().inicializarAcao("enviarMensagem", getId());
        
        // no caso o enviarMensagem do robo não precisa de uma verificação maior porque ele só comunica-se com a central, que não desliga e sempre é comunicavel
        Entidade destino = (Entidade) destinatario;

        //robo verifica se já tem uma central no ambiente, caso não tenha ele não consegue fazer a comunicação
        if (!getAmbiente().getEntidades().contains(destino) || destinatario == null) {
            throw new ErroComunicacaoException("Erro de comunicação: Destinatário não existe!\n");
        }
        // aqui se enquadra com sensores ativados para o logger
        getAmbiente().getLogger().logAcao(getId() + " está contactando a central para localizar o próximo fogo..."); 
        destinatario.receberMensagem(mensagem, this);
        getAmbiente().getLogger().finalizarAcao("Enviou com sucesso a mensagem.\n");
    }

    @Override
    public void receberMensagem(String mensagem, Comunicavel remetente){
        getAmbiente().getLogger().inicializarAcao("receberMensagem", getId());
        // Aqui o robô bombeiro recebe a mensagem e pode processá-la ou exibi-la
        getAmbiente().getLogger().finalizarAcao(getId() + " recebebeu a mensagem de: " + mensagem + "\n");
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
    public int getCapacidade() { return peso_max; }
    public int getReservatorio() { return reservatorio; }
    public int getRaioDeCessarFogo() { return raio_de_cessar_fogo; }
    public Obstaculo getIncendioProximo() { return incendio_proximo; }

    public void setIncendioProximo (Obstaculo incencio) { this.incendio_proximo = incencio; }
}
