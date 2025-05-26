package LAB04.Code;
import LAB04.Code.Exceptions.ForaDosLimitesException;
import LAB04.Code.Exceptions.LocalOcupadoException;

//basicamente irei usar essa interface para fazer as funcoes para apagar o fogo do roboBombeiro
public interface FogoZero {
    public void adicionar_agua(int litros);
    public void apagar_fogo() throws ForaDosLimitesException, LocalOcupadoException;
}
