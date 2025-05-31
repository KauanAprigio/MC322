package LAB04.Code.Interfaces;
import LAB04.Code.Exceptions.ErroComunicacaoException;
import LAB04.Code.Exceptions.ErrorAbastecimentoException;
import LAB04.Code.Exceptions.ErrorApagarFogoException;
import LAB04.Code.Exceptions.RoboDesligadoException;

/**
 * Interface FogoZero:
 * Define o contrato para entidades que podem apagar fogo.
 * Contém os métodos adicionar_agua e apagar_fogo.
 * As entidades que implementam essa interface devem fornecer a lógica para adicionar água e apagar fogo.
 * Apenas robôs bombeiros implementam essa interface.
 */
public interface FogoZero {
    /**
     * Método adicionar_agua:
     *   Permite adicionar água ao tanque do robô bombeiro.
     *   Pode lançar uma exceção ErrorAbastecimentoException se o abastecimento falhar.
     *   Abastecimento pode falhar se o robô estiver fora de uma estação de abastecimento ou se a quantidade de água for inválida.
     *   Alternativamente, o abastecimento pode falhar se o tanque for enchido acima do peso max ou se a quantidade de água for negativa.
     *
     * @param litros Quantidade de água a ser adicionada em litros.
     * @throws ErrorAbastecimentoException Se o abastecimento falhar.
     */
    public void adicionar_agua(int litros) throws ErrorAbastecimentoException;


    /**
     * Método apagar_fogo:
     *   Permite ao robô bombeiro apagar fogo.
     *   Pode lançar uma exceção ErrorApagarFogoException se o apagamento falhar.
     *   O apagamento pode falhar se o robô não tiver água suficiente no tanque ou se o fogo estiver muito distante.
     *
     * @throws ErrorApagarFogoException Se o apagamento falhar.
     * @param Central A entidade comunicável que representa o centro de controle ou a central de comando.
     * @throws ErroComunicacaoException Se houver um erro de comunicação com a central.
     */
    public void apagar_fogo(Comunicavel Central) throws ErrorApagarFogoException, ErroComunicacaoException, RoboDesligadoException;
}
