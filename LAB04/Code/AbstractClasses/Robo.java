package LAB04.Code.AbstractClasses;

import LAB04.Code.Ambiente;
import LAB04.Code.Exceptions.RoboDesligadoException;
import LAB04.Code.Interfaces.Entidade;

public abstract class Robo implements Entidade {
    // Atributos
    private String id;
    private EstadoRobo estado;
    private final TipoEntidade tipo = TipoEntidade.ROBO; // Definindo o tipo como ROBO por padrão
    private int pos_x;
    private int pos_y;
    private int pos_z;
    private final int larguraX = 0;
    private final int larguraY = 0;
    private final int altura = 0;
    private Ambiente ambiente; // Ambiente onde o robô está operando


    //Construtor
    public Robo(String id, EstadoRobo estado, int pos_x, int pos_y, int pos_z, Ambiente ambiente) {
        this.ambiente = ambiente;
        this.id = id;
        this.estado = estado;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.pos_z = pos_z; 
    }


    //Metodos
    // Quando mover o robo, é necessário utilizar a função moverEntidade do Ambiente!
    // Isso é feito para manter o código padronizado com os Obstáculos.
    public void mover(int deltaX, int deltaY, int deltaZ) throws RoboDesligadoException {
        if (getEstado() == EstadoRobo.OFF) 
            throw new RoboDesligadoException("Não foi possível mover o robô: " + getId() + " pois ele está desligado!\n");
        pos_x += deltaX;
        pos_y += deltaY;
        pos_z += deltaZ;
    }
    
    public void ligar(){
        this.estado = EstadoRobo.ON;
        System.out.println("O robô de id:" + this.getId() + " está ligado.\n");
    }

    public void desligar(){
        this.estado = EstadoRobo.OFF;
        System.out.println("O robô de id:" + this.getId() + " está desligado.\n");
    }

    // Getters e Setters
    public EstadoRobo getEstado() { return estado; }

    public void setEstado(EstadoRobo novoEstado){ this.estado = novoEstado; }
    

    // usarei o enum para me referir ao estado do robo
    public enum EstadoRobo {
        ON, // ligado
        OFF; // desligado
    }

    @Override
    public Ambiente getAmbiente() { return ambiente; }

    @Override
    public int getAltura() { return altura; }

    @Override
    public int getLarguraX() { return larguraX; }

    @Override
    public int getLarguraY() { return larguraY; }

    @Override
    public String getId(){ return id; }

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
        String descricao = "Robô básico, sem nenhuma ação especial além de poder se mover pelo ambiente, porém é Base para os robôs limpadores e bombeiros.";
        return descricao;
    }

    @Override
    public char getRepresentacao() { char representacao = 'r'; return representacao; }

}
