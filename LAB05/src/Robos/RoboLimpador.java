package LAB05.src.Robos;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Exceptions.*;

public class RoboLimpador extends AgenteInteligente {

    @Override
    public void executarMissao(Ambiente a) throws SemMissaoException, MissaoInvalidaException {
        if (temMissao()) {
            missao.executar(this, a);
        } else {
            throw new SemMissaoException("Nenhuma missão atribuída ao RoboLimpador.");
        }
    }
    
}
