package LAB04.Code;


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


    //se precisar criar metodos cria depois, pois a principio nao vejo a necessidade, no caso no enum de tipoentidade
    public static enum TipoEntidade{
        VAZIO,
        ROBO,
        OBSTACULO,
        DESCONHECIDO,
        COMUNICADOR;
    }
}


