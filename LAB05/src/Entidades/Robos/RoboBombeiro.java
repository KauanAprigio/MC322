package LAB05.src.Entidades.Robos;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Exceptions.*;

public class RoboBombeiro extends AgenteInteligente {

    public RoboBombeiro(String id, String descricao, int x_1, int y_1, int z_1, char representacao, Ambiente ambiente) {
        super(id, descricao, x_1, y_1, z_1, representacao, ambiente);
    }
    
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
