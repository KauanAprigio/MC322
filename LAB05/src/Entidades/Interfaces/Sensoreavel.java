package LAB05.src.Entidades.Interfaces;

import LAB05.src.Exceptions.RoboDesligadoException;
/**
 * Interface Sensoreavel
 * <p>
 * Define o método que entidades sensoreáveis devem implementar.
 * As entidades que implementam esta interface podem executar sensores
 * para coletar informações sobre o ambiente ou outras entidades.
 * </p>
 *
 * <ul>
 *   <li><b>executarSensores()</b>: Método que executa os sensores da entidade.</li>
 * </ul>
 */
public interface Sensoreavel {
    public void executarSensores() throws RoboDesligadoException;
}
