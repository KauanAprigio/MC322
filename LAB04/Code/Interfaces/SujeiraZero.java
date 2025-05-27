package LAB04.Code.Interfaces;

import LAB04.Code.Exceptions.ErrorLimpezaException;
/**
 * Interface SujeiraZero:
 * Define o contrato para entidades que podem limpar sujeira.
 * Contém os métodos limpar e definir_tipo_limpeza.
 * As entidades que implementam essa interface devem fornecer a lógica para limpeza e definição do tipo de limpeza.
 * Apenas robôs limpadores implementam essa interface.
 */
public interface SujeiraZero {
    /**
     * Método limpar:
     *   Permite ao robô limpador limpar sujeira.
     *   Pode lançar uma exceção ErrorLimpezaException se a limpeza falhar.
     *   A limpeza pode falhar se o robô estiver desligado ou se não houver sujeira para limpar.
     *   Alternativamente, a limpeza pode falhar se o tipo de limpeza não for definido corretamente.
     *
     * @throws ErrorLimpezaException Se a limpeza falhar.
     */
    public void limpar();
    /**
     * Método definir_tipo_limpeza:
     *   Permite definir o tipo de limpeza que o robô limpador deve realizar.
     *   Pode lançar uma exceção ErrorLimpezaException se o tipo de limpeza for inválido.
     *   O tipo de limpeza pode ser um valor inteiro que representa diferentes modos de limpeza.
     *
     * @param tipo Tipo de limpeza a ser definido.
     * @throws ErrorLimpezaException Se o tipo de limpeza for inválido.
     */
    public void definir_tipo_limpeza(int tipo) throws ErrorLimpezaException;
}
