package LAB05.src.Missoes;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Exceptions.*;
/**
 * Interface Missao:
 * <p>
 * Define o contrato para missões que podem ser executadas por robôs automaticamente.
 * As missões devem implementar o método executar, que recebe um robô e um ambiente,
 * e o método getDetalhes, que retorna uma descrição da missão.
 * </p>
 *
 * <ul>
 *   <li><b>executar(Robo r, Ambiente a)</b>: Método que executa a missão no ambiente com o robô especificado.</li>
 *   <li><b>getDetalhes()</b>: Método que retorna os detalhes da missão.</li>
 * </ul>
 */
public interface Missao {
    public void executar(Robo r, Ambiente a) throws MissaoInvalidaException;
    public String getDetalhes();
}
