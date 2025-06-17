package LAB05.src.Entidades;


import java.util.ArrayList;

import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Interfaces.Comunicavel;
import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Entidades.Robos.RoboBombeiro;
import LAB05.src.Entidades.Robos.Robo.EstadoRobo;
import LAB05.src.Exceptions.ErroComunicacaoException;


public class ComunicadorCentral extends CentralComunicacao implements Entidade, Comunicavel{

    private final int larguraX = 5;
    private final int larguraY = 5;
    private final int larguraZ = 100;

    private ArrayList<Obstaculo> fogos;
    private final char representacao = 'c';
    private int pos_x;
    private int pos_y;
    private final int pos_z = 0;
    private Ambiente ambiente; // Ambiente onde o obstáculo está localizado
    private Obstaculo oficina;
    private Obstaculo lago;


    public ComunicadorCentral(int pos_x, int pos_y, Ambiente a) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.ambiente = a;
        fogos = new ArrayList<Obstaculo>();
        for (Entidade e : ambiente.getEntidades()){ // loop para armezenar os obstaculos que serão usados futuramente pelo bombeiro
            // para armazenar como Obstaculo irei fazer castings
            if (e.getTipo() == TipoEntidade.FOGO){
                fogos.add( (Obstaculo) e); 
            } else if (e.getTipo() == TipoEntidade.LOCAL){
                if (e.getRepresentacao() == 'o'){
                    oficina = (Obstaculo) e;
                } else if (e.getRepresentacao() == 'l'){
                    lago = (Obstaculo) e;
                }
            }
        }
    }

    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws ErroComunicacaoException {
        getAmbiente().getLogger().inicializarAcao("enviarMensagem", getId());
        
        RoboBombeiro bombeiro = (RoboBombeiro) destinatario;
        if (bombeiro.getEstado() == EstadoRobo.OFF){
            throw new ErroComunicacaoException("Erro de comunicação, o robo " + getId() + " está desligado!");
        }

        if (!getAmbiente().getEntidades().contains(bombeiro) || destinatario == null) {
            throw new ErroComunicacaoException("Erro de comunicação: Destinatário não existe!\n");
        }
        bombeiro.receberMensagem(mensagem, this); // aqui basicamente ele irá enviar uma mensagem
        registrarMensagem(getId(), mensagem);

        getAmbiente().getLogger().finalizarAcao("O mensagem enviada com sucesso.\n");
    }

    @Override
    public void receberMensagem(String mensagem, Comunicavel remetente) {
        getAmbiente().getLogger().inicializarAcao("receberMensagem", getId());
        RoboBombeiro bombeiro = (RoboBombeiro) remetente;
        registrarMensagem((bombeiro.getId()), mensagem);
        if (mensagem.equalsIgnoreCase("INCÊNDIO APAGADO")) {
            try{
                getAmbiente().getLogger().finalizarAcao("Mesagem recebida pela central");
                enviarMensagem(bombeiro, "Atualizando os incêndios que ainda estão no ambiente");
            } catch (ErroComunicacaoException message) {
                getAmbiente().getLogger().logErr(message);
            }
            fogos.remove(bombeiro.getIncendioProximo());
            bombeiro.setIncendioProximo(null);
        } else if (mensagem.equalsIgnoreCase("ABASTECIMENTO")){
            String coordenada = "(" + lago.getX_1() + "," + lago.getY_1() + "," + "1).";
            System.out.println("Você poderá abastecer se for para a posição: " + coordenada);
        } else if (mensagem.equalsIgnoreCase("APRIMORAMENTO")){
            String coordenada = "(" + oficina.getX_1() + "," + oficina.getY_1() + "," + "0).";
            System.out.println("Você poderá aprimorar-se caso for para a posição: " + coordenada);
        }
    }

    public Obstaculo LocalizarFogoMaisProx (Robo robozin) {
        getAmbiente().getLogger().inicializarAcao("localizarFogoPróximo", getId());
        double menorDistancia = Double.MAX_VALUE; // Inicializa com o maior valor possível
        Obstaculo maisProximo = null; // Inicializa como null para verificar se encontrou algum lixo
        for (Obstaculo fogo : fogos) {
            // Calcula a distância entre a entidade e o fogo
            int DistanciaX = Math.max(robozin.getX_1(), fogo.getX_1()) - Math.min (robozin.getX_1(), fogo.getX_1());
            int DistanciaY = Math.max(robozin.getY_1(), fogo.getY_1()) - Math.min (robozin.getY_1(), fogo.getY_1());
            double distancia = Math.sqrt(Math.pow(DistanciaX, 2) + Math.pow(DistanciaY, 2));
            
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                maisProximo = fogo; // Atualiza o fogo mais próximo
            }
        }
        // aqui não irei dar a coordenada do fogo, pois já farei isso quando o robo for mover para o fogo
        getAmbiente().getLogger().logAcao("O fogo " + maisProximo.getId() + "foi localizado");
        getAmbiente().getLogger().finalizarAcao("A localização do fogo mais proximo ao " + robozin.getId() + "foi finalizada com sucesso.\n");
        return maisProximo;
    }

    public ArrayList<Obstaculo> getFogos(){ return fogos; }
    public Obstaculo getOficina() { return oficina; }
    public Obstaculo getLago() { return lago; }

    @Override
    public int getX_1() { 
        return pos_x;
    }
    @Override
    public int getY_1() {
        return pos_y;
    }
    @Override
    public int getZ_1() {
        return pos_z;
    }
    @Override
    public Ambiente getAmbiente() {
        return ambiente;
    }
    @Override
    public String getId() {
        return "Comunicador Central";
    }
    @Override
    public String getDescricao() {
        String descricao = "nn sei ainda";
        return descricao;
    }
    @Override
    public int getLarguraX() {
        return larguraX;
    }
    @Override
    public int getLarguraY() {
        return larguraY;
    }
    @Override
    public int getLarguraZ() {
        return larguraZ;
    }
    @Override
    public char getRepresentacao() {
        return representacao;
    }
    @Override
    public TipoEntidade getTipo() {
        return TipoEntidade.COMUNICADOR;
    }
    @Override
    public int getX_2() {
        return pos_x + getLarguraX();
    }
    @Override
    public int getY_2() {
        return pos_y + getLarguraY();
    }
    @Override
    public int getZ_2() {
        return pos_z + getLarguraZ();
    }
}
