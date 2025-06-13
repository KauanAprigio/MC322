package LAB05.src.Missoes;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Exceptions.*;

public interface Missao {
    public void executar(Robo r, Ambiente a) throws MissaoInvalidaException;
    public String getDetalhes();
}
