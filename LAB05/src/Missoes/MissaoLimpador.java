package LAB05.src.Missoes;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Robos.Robo;

public class MissaoLimpador implements Missao {
    @Override
    public void executar(Robo r, Ambiente a) {
        // Implementação da missão de limpeza
    }

    @Override
    public String getDetalhes() {
        String msg = "qlqr coisa só pra ter";
        return msg;
    }
    
}
