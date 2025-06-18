package LAB05.src.Entidades.Robos;
import LAB05.src.Missoes.Missao;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Exceptions.*;
/**
 * Classe abstrata AgenteInteligente:
 * <p>
 * Representa um robô inteligente que pode executar missões específicas no ambiente.
 * Herda da classe Robo e implementa a lógica para realizar ações baseadas em missões automatizadas.
 * </p>
 * <ul>
 *  <li><b>executarMissao(Ambiente a)</b>: Método abstrato que deve ser implementado por subclasses
 *      para definir como o agente executa sua missão no ambiente.</li>
 * </ul>
 */
public abstract class AgenteInteligente extends Robo {
    protected Missao missao;

    public AgenteInteligente(String id, int x_1, int y_1, int z_1, char representacao, Ambiente ambiente) {
        super(id, x_1, y_1, z_1, representacao, ambiente);
    }

    public abstract void executarMissao(Ambiente a) throws SemMissaoException, MissaoInvalidaException;
    
    public void setMissao(Missao m) { this.missao = m; }
    public boolean temMissao() { return this.missao != null; }
}
