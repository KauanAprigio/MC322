package LAB05.src.Missoes;

import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Entidades.Robos.RoboLimpador;
import LAB05.src.Exceptions.MissaoInvalidaException;
/**
 * Classe MissaoLimpezaAuto:
 * <p>
 * Limpa o ambiente completamente, removendo todos os lixos encontrados.
 * Chama várias outras Missões intermediárias, como MissaoLimpezaProxima,
 * até que não haja mais lixos a serem removidos.
 * </p>
 * <ul>
 */
public class MissaoLimpezaAuto implements Missao {
    @Override
    public void executar(Robo r, Ambiente a) throws MissaoInvalidaException{
        r.getAmbiente().getLogger().inicializarMissao(this, r.getId());
        
        MissaoLimpezaProxima limpeza_proxima = new MissaoLimpezaProxima();

        if (!(r instanceof RoboLimpador)){
            throw new MissaoInvalidaException("O robô deve ser um limpador para fazer esta missão!\n");
        }
        
        RoboLimpador limpador = (RoboLimpador) r;
        
        if (limpador.getLixos() == null){
            throw new MissaoInvalidaException("O robô deve executar o seu sensor para fazer a missão!\n");
        }

        try {
            while (!(limpador.getLixos().isEmpty())){
                limpador.setMissao(limpeza_proxima);
                limpador.executarMissao(limpador.getAmbiente());

                limpador.limpar();
            }
            System.out.println(r.getId() + " limpou todo o ambiente.\n");
            limpador.getAmbiente().getLogger().finalizarMissao("Missão de limpezaAuto finalizada com sucesso.");
        } catch (Exception e){
            limpador.getAmbiente().getLogger().logErr(e);
        }
    }

    @Override
    public String getDetalhes(){
        return "Limpeza completa do ambiente";
    }
}
