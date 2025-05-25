package LAB04.Code;

import java.util.ArrayList;

import LAB04.Code.Exceptions.ForaDosLimitesException;
import LAB04.Code.Exceptions.LocalOcupadoException;

public class Robo implements Entidade {
    // Atributos
    private String id;
    private EstadoRobo estado;
    private TipoEntidade tipo;
    private int pos_x;
    private int pos_y;
    private int pos_z;
    private final int larguraX = 0;
    private final int larguraY = 0;
    private final int altura = 0;

    // Atributo adicional para deixar o moverPara ficar mais fácil
    Ambiente ambiente;


    //Construtor
    Robo(String id, EstadoRobo estado, TipoEntidade tipo, int pos_x, int pos_y, int pos_z, Ambiente ambiente){
        this.id = id;
        this.estado = estado;
        this.tipo = tipo;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.pos_z = pos_z; // duvida se eu coloco no construtor, porque para robos em geral sera 0, menos para o robo aereo, mas ate ele começa no chao, entao rever se nao coloco pos_Z = 0
        this.ambiente = ambiente;
    }


    //Metodos
    // Atualizar o mapa 2D quando mover ROBO!!
    public void mover(int deltaX, int deltaY, int deltaZ, ArrayList<Entidade> entidades,
        char[][] planoXY) throws LocalOcupadoException, ForaDosLimitesException {
        
        
    }
    
    public void ligar(){
        this.estado = EstadoRobo.ON;
        System.out.println("O robô de id:" + this.getId() + " está ligado.");
    }

    public void desligar(){
        this.estado = EstadoRobo.OFF;
        System.out.println("O robô de id:" + this.getId() + " está desligado.");
    }

    public void executar(){
        //posso deixar enxuto e desenvolver por meio de sobrecarga de método nas subclasses do robo
    }


    // Getters e Setters
    public String getId(){ return id; }
    public EstadoRobo getEstado() { return estado; }
    public Ambiente getAmbiente() { return ambiente; }
    public int getLarguraX() { return larguraX; }
    public int getLarguraY() { return larguraY; }
    public int getAltura() { return altura; }

    public void setEstado(EstadoRobo novoEstado){ this.estado = novoEstado; }
    

    // usarei o enum para me referir ao estado do robo
    public enum EstadoRobo {
        ON, // ligado
        OFF; // desligado
        
        //aqui irei ver se ele está ligado, caso ele esteja ligado ira retornar true, caso o contrario irá falar que é false, logo OFF
        public boolean estaLigado() {
            return this == ON;
        }

    }


    // Metodos sobrescritos da interface
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
    public char getRepresentacao() { char representacao = 'r'; return representacao; }


    



}
