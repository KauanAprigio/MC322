package LAB05.src.Entidades.Robos;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Exceptions.*;


public class RoboLimpador extends AgenteInteligente {

    public RoboLimpador(String id, int x_1, int y_1, int z_1, char representacao, Ambiente ambiente) {
        super("RoboLimpador_"+id, x_1, y_1, z_1, representacao, ambiente);
    }

    @Override
    public void executarMissao(Ambiente a) throws SemMissaoException, MissaoInvalidaException {
        if (temMissao()) {
            missao.executar(this, a);
        } else {
            throw new SemMissaoException("Nenhuma missão atribuída ao RoboLimpador.");
        }
    }

    @Override
    public String getDescricao() {
        String descricao = "Robô limpador: Capaz de limpar diversos tipos sujeira (contanto que o tipo de limpeza ativo seja apropriado!)," + 
         " aprimore seu alcance limpeza na oficina e ande pelo ambiente, evitando fogos e limpado tudo de lixo que houver por sua frente! Você está salvando o planeta!";
        return descricao;
    }
    
}
