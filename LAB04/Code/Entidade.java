package LAB04.Code;


public interface Entidade {
    public int getX();
    public int getY();
    public int getZ();
    public TipoEntidade getTipo();
    public String getDescricao();
    public char getRepresentacao();

    //se precisar criar metodos cria depois, pois a principio nao vejo a necessidade, no caso no enum de tipoentidade
    public static enum TipoEntidade{
        VAZIO,
        ROBO,
        OBSTACULO,
        DESCONHECIDO;

    }
}


