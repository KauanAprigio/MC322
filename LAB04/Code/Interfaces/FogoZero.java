package LAB04.Code.Interfaces;
import LAB04.Code.Exceptions.ErrorAbastecimentoException;
import LAB04.Code.Exceptions.ErrorApagarFogoException;

//basicamente irei usar essa interface para fazer as funcoes para apagar o fogo do roboBombeiro
public interface FogoZero {
    public void adicionar_agua(int litros) throws ErrorAbastecimentoException;
    public void apagar_fogo() throws ErrorApagarFogoException;
}
