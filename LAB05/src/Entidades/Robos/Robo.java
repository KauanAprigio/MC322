package LAB05.src.Entidades.Robos;
import LAB05.src.Entidades.Entidade;
import LAB05.src.Ambiente.Ambiente;


public class Robo implements Entidade {
    private int x_1;
    private int y_1;
    private int z_1;
    private String id;
    private String descricao;
    private final int larguraX = 0;
    private final int larguraY = 0;
    private final int altura = 0;
    private char representacao; // Cada tipo de robô terá uma representação diferente no ambiente
    private Ambiente ambiente;
    private final TipoEntidade tipo = TipoEntidade.ROBO; // Definindo o tipo como ROBO por padrão
    private EstadoRobo estado = EstadoRobo.OFF; // Estado inicial do robô é desligado

    // Construtor
    public Robo(String id, String descricao, int x_1, int y_1, int z_1, char representacao, Ambiente ambiente) {
        this.id = id;
        this.descricao = descricao;
        this.x_1 = x_1;
        this.y_1 = y_1;
        this.z_1 = z_1;
        this.representacao = representacao;
        this.ambiente = ambiente;
    }
    

    // getters e setters para os atributos do implemento da interface Entidade
    @Override
    public int getX_1() { return x_1; }
    public int getY_1() { return y_1; }
    public int getZ_1() { return z_1; }
    public String getId() { return id; }
    public String getDescricao() { return descricao; }
    public int getLarguraX() { return larguraX; }
    public int getLarguraY() { return larguraY; }
    public int getAltura() { return altura; }
    public char getRepresentacao() { return representacao; }
    public Ambiente getAmbiente() { return ambiente; }
    public TipoEntidade getTipo() { return tipo; }
    public EstadoRobo getEstado() { return estado; }

    public enum EstadoRobo {
        ON, // ligado
        OFF; // desligado
    }

}
