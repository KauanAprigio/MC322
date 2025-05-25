package LAB04.Code;

import java.util.ArrayList;

import LAB04.Code.Exceptions.ForaDosLimitesException;
import LAB04.Code.Exceptions.LocalOcupadoException;

public interface Entidade {
    public int getX();
    public int getY();
    public int getZ();
    public TipoEntidade getTipo();
    public String getDescricao();
    public char getRepresentacao();
    public void mover(int deltaX, int deltaY, int deltaZ, ArrayList<Entidade> entidades,
        char[][] planoXY) throws LocalOcupadoException, ForaDosLimitesException;
    public int getLarguraX ();
    public int getLarguraY ();
    public int getAltura();


    //se precisar criar metodos cria depois, pois a principio nao vejo a necessidade, no caso no enum de tipoentidade
    public static enum TipoEntidade{
        VAZIO,
        ROBO,
        OBSTACULO,
        DESCONHECIDO;
    }
}


