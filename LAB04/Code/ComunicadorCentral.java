package LAB04.Code;

import java.util.ArrayList;

import LAB04.Code.Exceptions.RoboDesligadoException;
import LAB04.Code.Obstaculo.TipoObstaculo;

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
    
    public void listarFogos(){
        for(Entidade e : getAmbiente().getEntidades()){
            if (e.getTipo() == TipoEntidade.OBSTACULO){
                Obstaculo o = (Obstaculo) e;
                if (o.getTipoObstaculo().isFogo()){
                    fogos.add(o);
                }
            }
        }
    }

    //basicamente ele vai ver em relacao ao robo/entidade onde ele está e com isso onde está o fogo mais próximo
    public void avisoFogoProximo(Entidade e){
        if (e.getTipo() == TipoEntidade.ROBO){ // vê se é um robo, talvez tenha que ver se é RoboBombeiro
            Obstaculo fogoProximo = null;
            double menorDistancia = Double.MAX_VALUE; // Inicializa com o maior valor possível para double
            for (Obstaculo o : fogos) {
                // Calcula a distância euclidiana entre o robô e o obstáculo
                // Assumindo que getX() e getY() do obstáculo retornam um ponto representativo (ex: centro ou canto)
                int Xmaisproximo = Math.max(o.getX(), Math.min(e.getX(), o.getPosicaoX2()));
                int Ymaisproximo = Math.max(o.getY(), Math.min(e.getY(), o.getPosicaoY2()));
                int Zmaisproximo = Math.max(0, Math.min(e.getZ(), o.getAlturinha()));
                double distancia = Math.sqrt(Math.pow(Xmaisproximo - e.getX(), 2) + Math.pow(Ymaisproximo - e.getY(), 2) + Math.pow(Zmaisproximo - e.getZ(), 2));
                    
                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    fogoProximo = o;
                }
            }
            RoboBombeiro robozin = (RoboBombeiro) e;
            String mensagemFogo = "O fogo mais próximo do robo de ID " + robozin.getId() + " é o obstaculo "
                                   + fogoProximo.getTipoObstaculo() + " com coordenadas em (" + fogoProximo.getX() 
                                   + "," + fogoProximo.getY() + ").\n" ;
            Comunicavel robozao = (Comunicavel) robozin;
            enviarMensagem(robozao, mensagemFogo);
        }

        
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
