package LAB05.src.Entidades.Robos;
import LAB04.Code.Exceptions.ErrorAbastecimentoException;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.ComunicadorCentral;
import LAB05.src.Entidades.Interfaces.Aprimoravel;
import LAB05.src.Entidades.Interfaces.Comunicavel;
import LAB05.src.Exceptions.*;

public class RoboBombeiro extends AgenteInteligente implements Comunicavel, Aprimoravel {
    private int altitudeMaxima;
    private int peso_max; // peso maximo que o robo suporta;
    private int reservatorio; // litros de agua no reservatorio
    private int raio_de_cessar_fogo; // raio para ter uma distancia segura para apagar o fogo
    private boolean aprimorado = false; // variável para verificar se o robô foi aprimorado
    private ComunicadorCentral Central;
    private int[] FogoMaisProx = null;

    public RoboBombeiro(String id, String descricao, int x_1, int y_1, int z_1, char representacao, Ambiente ambiente) {
        super(id, descricao, x_1, y_1, z_1, representacao, ambiente);
    }
    
    @Override
    public void executarMissao(Ambiente a) throws SemMissaoException, MissaoInvalidaException {
        if (temMissao()) {
            missao.executar(this, a);
        } else {
            throw new SemMissaoException("Nenhuma missão atribuída ao robô bombeiro.");
        }
    }
    
    public void apagarFogo() {
        boolean incendioApagado = false;
        // Localiza o fogo mais próximo
        ComunicarComCentral("AJUDA");
        // Agora FogoMaisPróximo possui as coordenadas e litros necessários do fogo mais próximo

        // IMPLEMENTAR LÓGICA PARA SE MOVER AUTOMATICAMENTE E APAGAR O FOGO
        // É necessário garantir que o Robô apague o fogo indentificado pela central

        //
        if (incendioApagado)
            ComunicarComCentral("INCÊNDIO APAGADO");
    }

    @Override
    public void aprimorar() throws ErrorAprimoramentoException {
        //verifica se o robô esta dentro de uma oficina  
        if (getLocalAtualRep() == 'o'){
            if (estahAprimorado()) {
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

    public void adicionar_agua() throws ErrorAbastecimentoException {
        // Verifica se o robô está dentro de um lago        
        if (getZ_1() != 1) {
            throw new ErrorAbastecimentoException(getId() + " está muito alto, desça para altura 1 para abastecer água!\n");  // Se o robô estiver voando, não pode abastecer
        }
        
        if (getLocalAtualRep() != 'l') {
            throw new ErrorAbastecimentoException(getId() + " não está em um lago e não pode abastecer água!\n"); 
        }
        if (getEstado() == EstadoRobo.OFF) {
            throw new ErrorAbastecimentoException("Não foi possível abastecer o robô: " + getId() + " pois ele está desligado!\n");
        }
        reservatorio = peso_max; // Abastece o reservatório até o máximo
        System.out.println(getId() + " foi abastecido com sucesso.");
        System.out.println("Reservatorio possui " + reservatorio + " litros de um máximo de: " + peso_max + " litros.\n");
    }
  
    @Override
    public boolean estahAprimorado() { return aprimorado; }

    @Override
    public String getDescricao() {
        String descricao = "Robô bombeiro: Capaz de voar e apagar fogos, o único porém é que existe uma capacidade" +
         " máxima de peso de água que ele pode armazenar, aprimore sua capacidade na oficina e reabasteça nos lagos para apagar todos os fogos do ambiente e salvar a todos!";
        return descricao;
    }


    public void ComunicarComCentral (String msg) {
        EnviarMensagem(msg, Central);
    }

    @Override
    public void EnviarMensagem(String msg, Comunicavel remetente){
        remetente.receberMensagem(msg);
    }
    @Override
    public void receberMensagem(String msg) {
        if (msg == null) { return; }
        String[] message = msg.split(msg);
        if (message[0].toUpperCase() == "FOGO"){
            FogoMaisProx[0] = Integer.parseInt(message[1]);
            FogoMaisProx[1] = Integer.parseInt(message[2]);
            FogoMaisProx[2] = Integer.parseInt(message[3]);
            // Litros nescessários:
            FogoMaisProx[3] = Integer.parseInt(message[4]);
        }        
    }
}
