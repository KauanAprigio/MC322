package LAB05.src.Robos;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Exceptions.*;

public class RoboBombeiro extends AgenteInteligente {
    
    @Override
    public void executarMissao(Ambiente a) throws SemMissaoException, MissaoInvalidaException {
        if (temMissao()) {
            missao.executar(this, a);
        } else {
            // Poderia lançar uma exception?
            throw new SemMissaoException("Nenhuma missão atribuída ao robô bombeiro.");
        }
    }
    
}
