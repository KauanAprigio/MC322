package LAB05.src.Entidades.Robos;
import LAB05.src.Exceptions.RoboDesligadoException;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Interfaces.Entidade;


public class Robo implements Entidade {
    private int pos_x;
    private int pos_y;
    private int pos_z;
    private String id;
    private String descricao;
    private final int larguraX = 0;
    private final int larguraY = 0;
    private final int larguraZ = 0;
    private char representacao; // Cada tipo de robô terá uma representação diferente no ambiente
    private Ambiente ambiente;
    private final TipoEntidade tipo = TipoEntidade.ROBO; // Definindo o tipo como ROBO por padrão
    private EstadoRobo estado = EstadoRobo.OFF; // Estado inicial do robô é desligado

    // Construtor
    public Robo(String id, String descricao, int pos_x, int pos_y, int pos_z, char representacao, Ambiente ambiente) {
        this.id = id;
        this.descricao = descricao;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.pos_z = pos_z;
        this.representacao = representacao;
        this.ambiente = ambiente;
    }
    
    public void mover(int deltaX, int deltaY, int deltaZ) throws RoboDesligadoException {
        if (getEstado() == EstadoRobo.OFF) 
            throw new RoboDesligadoException("Não foi possível mover o robô: " + getId() + " pois ele está desligado!\n");
        pos_x += deltaX;
        pos_y += deltaY;
        pos_z += deltaZ;
    }

    // getters e setters para os atributos do implemento da interface Entidade
    @Override
    public int getX_1() { return pos_x; }
    public int getY_1() { return pos_y; }
    public int getZ_1() { return pos_z; }
    public int getX_2() { return pos_x + getLarguraX(); }
    public int getY_2() { return pos_y + getLarguraY(); }
    public int getZ_2() { return pos_z + getLarguraZ(); }

    
    public String getId() { return id; }
    public String getDescricao() { return descricao; }
    public int getLarguraX() { return larguraX; }
    public int getLarguraY() { return larguraY; }
    public int getLarguraZ() { return larguraZ; }
    public char getRepresentacao() { return representacao; }
    public Ambiente getAmbiente() { return ambiente; }
    public TipoEntidade getTipo() { return tipo; }
    public EstadoRobo getEstado() { return estado; }

    public enum EstadoRobo {
        ON, // ligado
        OFF; // desligado
    }

}
