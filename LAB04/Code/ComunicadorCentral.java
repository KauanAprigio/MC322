package LAB04.Code;

import java.util.ArrayList;

import LAB04.Code.Exceptions.RoboDesligadoException;

public class ComunicadorCentral extends CentralComunicacao implements Entidade, Comunicavel{
    private TipoEntidade tipo;
    private int pos_x;
    private int pos_y;
    private int pos_z;
    private Ambiente ambiente;
    private final int larguraX = 0;
    private final int larguraY = 0;
    private final int altura = 0;

    //Listas de Obstaculos
    ArrayList<Obstaculo> fogos;
    //ArrayList<Obstaculo> lixos;

    //Construtor
    ComunicadorCentral(TipoEntidade tipo, int pos_x, int pos_y, int pos_z, Ambiente ambiente){
        this.tipo = tipo;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.pos_z = pos_z;
        this.ambiente = ambiente;
        fogos = new ArrayList<Obstaculo>();
        //lixos = new ArrayList<Obstaculo>();
    }
    
    // Metodos sobrescritos da Comunicavel
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem){
        destinatario.receberMensagem(mensagem);
    }

    @Override
    public void receberMensagem(String mensagem){
        System.out.println(mensagem);
    }

    // Metodos sobrescritos da Entidade
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
        String descricao = "QUALQUER COISA, SÓ PARA TER ALGO";
        return descricao;
    }

    @Override
    public char getRepresentacao() { char representacao = 'c'; return representacao; }

    @Override
    public void mover(int deltaX, int deltaY, int deltaZ,  TipoEntidade[][][] mapa,
        char[][] planoXY) throws RoboDesligadoException {
    }

    @Override
    public int getLarguraX () { return altura; }

    @Override
    public int getLarguraY () { return larguraY; }

    @Override
    public int getAltura(){ return larguraX; }


    //Getters e Setters
    public Ambiente getAmbiente() { return ambiente; }
}
