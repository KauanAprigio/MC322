package LAB05.src.Robos;
import LAB05.src.Missoes.Missao;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Exceptions.*;

public abstract class AgenteInteligente extends Robo {
    protected Missao missao;

    public abstract void executarMissao(Ambiente a) throws SemMissaoException, MissaoInvalidaException;
    
    public void setMissao(Missao m) { this.missao = m; }
    public boolean temMissao() { return this.missao != null; }
}
