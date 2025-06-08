package LAB05.src.Entidades.Robos;
import LAB05.src.Missoes.Missao;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Exceptions.*;

public abstract class AgenteInteligente extends Robo {
    protected Missao missao;

    public AgenteInteligente(String id, String descricao, int x_1, int y_1, int z_1, char representacao, Ambiente ambiente) {
        super(id, descricao, x_1, y_1, z_1, representacao, ambiente);
    }

    public abstract void executarMissao(Ambiente a) throws SemMissaoException, MissaoInvalidaException;
    
    public void setMissao(Missao m) { this.missao = m; }
    public boolean temMissao() { return this.missao != null; }
}
