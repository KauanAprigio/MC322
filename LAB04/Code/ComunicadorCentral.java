package LAB04.Code;

import java.util.ArrayList;

import LAB04.Code.AbstractClasses.CentralComunicacao;
import LAB04.Code.Exceptions.ErroComunicacaoException;
import LAB04.Code.Interfaces.Comunicavel;
import LAB04.Code.Interfaces.Entidade;

public class ComunicadorCentral extends CentralComunicacao implements Entidade, Comunicavel{
    private final TipoEntidade tipo = TipoEntidade.COMUNICADOR; // Definindo o tipo como COMUNICADOR por padrão
    private int pos_x;
    private int pos_y;
    private int pos_z;
    private Ambiente ambiente;
    private final int larguraX = 5;
    private final int larguraY = 5;

    //Listas de Obstaculos
    ArrayList<Obstaculo> fogos;
    //ArrayList<Obstaculo> lixos;

    //Construtor
    public ComunicadorCentral(int pos_x, int pos_y, int pos_z, Ambiente ambiente){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.pos_z = pos_z;
        this.ambiente = ambiente;
        fogos = new ArrayList<Obstaculo>();
        // Adicionar os fogos do ambiente à lista de fogos
        for(Entidade e : getAmbiente().getEntidades()){
            if (e.getTipo() == TipoEntidade.OBSTACULO){
                Obstaculo o = (Obstaculo) e;
                if (o.getTipoObstaculo().isFogo()){
                    fogos.add(o);
                }
            }
        }
    }

    // Métodos
    /**
     * Método listarFogos:
     *  Método para listar todos os fogos no ambiente.
     *  Se não houver fogos, informa que não há fogos no ambiente.
     */
    public void listarFogos(){
        if (fogos.isEmpty()) {
            System.out.println("Não há fogos no ambiente.");
        } else {
            System.out.println("Lista de fogos no ambiente:");
            for (Obstaculo fogo : fogos) {
                System.out.println("Fogo do tipo: " + fogo.getTipoObstaculo() + " localizado em (" + fogo.getX() + ", " + fogo.getY() + ")");
            }
        }
        System.out.println("Total de fogos: " + fogos.size() + ".\n");
    }

    /**
     * Método avisoFogoProximo:
     *  Método para avisar uma entidade sobre o fogo mais próximo.
     *  Apenas avisa se a entidade for comunicável e estiver disponível.
     * 
     * 
     * @param e Entidade que receberá a mensagem.
     * @throws ErroComunicacaoException Se a entidade não for comunicável ou estiver indisponível.
     */
    public void avisoFogoProximo(Entidade e) throws ErroComunicacaoException {
        if (!e.isComunicavel()) throw new ErroComunicacaoException("Erro de comunicação: Entidade " + e.getId() + " não tem a capacidade de receber mensagens ou está indisponível no momento.\n");
        double menorDistancia = Double.MAX_VALUE; // Inicializa com o maior valor possível
        Obstaculo fogoMaisProximo = null; // Inicializa como null para verificar se encontrou algum fogo
        for (Obstaculo fogo : fogos) {
            // Calcula a distância entre a entidade e o fogo
            int DistanciaX = Math.max(e.getX(), fogo.getX()) - Math.min (e.getX() + e.getLarguraX(), fogo.getX() + fogo.getLarguraX());
            if (DistanciaX <= 0) { DistanciaX = 0; } // se a distancia for negativa, significa que o fogo está tocando a entidade
            int DistanciaY = Math.max(e.getY(), fogo.getY()) - Math.min (e.getY() + e.getLarguraY(), fogo.getY() + fogo.getLarguraY());
            if (DistanciaY <= 0) { DistanciaY = 0; } // se a distancia for negativa, significa que o fogo está tocando a entidade
            int DistanciaZ = Math.max (e.getZ(),fogo.getZ()) - Math.min(e.getZ(), fogo.getZ());
            if (DistanciaZ <= 0) { DistanciaZ = 0; } // se a distancia for negativa, significa que o fogo está tocando a entidade
            double distancia = Math.sqrt(Math.pow(DistanciaX, 2) + Math.pow(DistanciaY, 2) + Math.pow(DistanciaZ, 2));

            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                fogoMaisProximo = fogo; // Atualiza o fogo mais próximo
            }
        }
        if (fogoMaisProximo != null) {
            Comunicavel d = (Comunicavel) e;
            String mensagem = "O fogo mais próximo de " + e.getId() + " está a uma distância de "
            + menorDistancia + " metros e está localizado na posição (" 
            + fogoMaisProximo.getX() + ", " + fogoMaisProximo.getY() + ", " + fogoMaisProximo.getZ() + ").\n";
            enviarMensagem(d, mensagem);
        } else {
            System.out.println("Comunicador Central: Não há fogos próximos de " + e.getId() + ".\n");
        }
    }

    public void adicionarFogo(Obstaculo fogo) {
        fogos.add(fogo);
        System.out.println("Comunicador Central: Fogo na posição (" + fogo.getX() + ", " + fogo.getY() + "). Registrado pelo comunicador central\n");
    }

    // Metodos sobrescritos da interface Comunicavel
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws ErroComunicacaoException {
        destinatario.receberMensagem(mensagem, destinatario);
        registrarMensagem(getId(), mensagem);
    }

    @Override
    public void receberMensagem(String mensagem, Comunicavel remetente) throws ErroComunicacaoException {
        Entidade e = (Entidade) remetente;
        registrarMensagem(e.getId(), mensagem);
        if (remetente instanceof RoboBombeiro) {
            avisoFogoProximo(e);
        }
    }

    // Metodos sobrescritos da interface Entidade
    @Override
    public int getX() { return pos_x; }

    @Override
    public int getY() { return pos_y; }

    @Override
    public int getZ() { return pos_z; }

    @Override
    public TipoEntidade getTipo() { return tipo; }

    @Override
    public String getDescricao() { 
        String descricao = "Comunicador Central: responsável por gerenciar a comunicação entre robôs, localiza e indica os fogos mais próximos a uma dada entidade quando ativado.";
        return descricao;
    }

    @Override
    public char getRepresentacao() { char representacao = 'c'; return representacao; }

    @Override
    public void mover(int deltaX, int deltaY, int deltaZ) {
        // Não é possível mover o Comunicador Central, pois ele é fixo no ambiente.
        System.out.println("Comunicador Central não pode ser movido.");
    }

    @Override
    public int getLarguraX () { return larguraX; }

    @Override
    public int getLarguraY () { return larguraY; }

    @Override
    public String getId() { return "ComunicadorCentral"; }

    @Override
    public boolean isComunicavel() { return true; } // O Comunicador Central é comunicável

    @Override
    public Ambiente getAmbiente() { return ambiente; }
}
