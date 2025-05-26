package LAB04.Code.Interfaces;

import LAB04.Code.Exceptions.ErrorLimpezaException;

public interface SujeiraZero {
    public void limpar() throws ErrorLimpezaException;
    public void definir_tipo_limpeza(int tipo) throws ErrorLimpezaException;
}
