package LAB04.Code.Interfaces;


import LAB04.Code.Ambiente;
import LAB04.Code.Exceptions.RoboDesligadoException;

public interface Entidade {
    public int getX();
    public int getY();
    public int getZ();

    public TipoEntidade getTipo();

    public String getDescricao();

    public char getRepresentacao();
    
    public void mover(int deltaX, int deltaY, int deltaZ) throws RoboDesligadoException;
    public int getLarguraX ();
    public int getLarguraY ();
    public int getAltura();
    public String getId();
    public Ambiente getAmbiente();
    public default boolean isComunicavel() {
        return false; // Por padrão, Entidade não é comunicável, sobrescrever em subclasses se necessário
    }


    //se precisar criar metodos cria depois, pois a principio nao vejo a necessidade, no caso no enum de tipoentidade
    public static enum TipoEntidade{
        VAZIO,
        ROBO,
        OBSTACULO,
        DESCONHECIDO,
        COMUNICADOR;
    }
}


