package LAB05.src.Entidades;


import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Interfaces.Comunicavel;
import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Entidades.Robos.Robo;

public class ComunicadorCentral extends CentralComunicacao implements Entidade, Comunicavel{

    private final int larguraX = 5;
    private final int larguraY = 5;
    private final int larguraZ = 100;

    private final char representacao = 'c';
    private int pos_x;
    private int pos_y;
    private final int pos_z = 0;
    private Ambiente ambiente; // Ambiente onde o obstáculo está localizado

    public ComunicadorCentral(int pos_x, int pos_y, Ambiente a) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.ambiente = a;
    }

    @Override
    public void EnviarMensagem(String msg, Comunicavel remetente) {
        
    }

    @Override
    public void receberMensagem(String msg) {
        
    }

    public void LocalizarFogoMaisProx (Robo r) {

    }

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
