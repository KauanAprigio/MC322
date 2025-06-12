package LAB05.src.Entidades.Interfaces;

import LAB05.src.Exceptions.ErrorAprimoramentoException;

/*
 *
 * Interface Aprimoravel:
*   Define o contrato para entidades que podem ser aprimoradas.
*   Contém o método aprimorar que recebe um valor de upgrade.
*   Lança uma exceção ErrorAprimoramentoException se o aprimoramento falhar.
 * 
 */
public interface Aprimoravel {
    /**
     * Método aprimorar:
     *   Permite aprimorar a entidade com um valor de upgrade.
     *   Pode lançar uma exceção ErrorAprimoramentoException se o aprimoramento falhar.
     *   Aprimoramento pode falhar se o robô estiver fora de uma oficina ou se o valor de upgrade for inválido.
     *
     * 
     * @throws ErrorAprimoramentoException Se o aprimoramento falhar.
     */
    public void aprimorar() throws ErrorAprimoramentoException;

    /**
     * 
     * @return boolean
     *   Retorna true se a entidade está aprimorada, false caso contrário.
     */
    public boolean estahAprimorado();
}
