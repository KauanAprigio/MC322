package LAB05.src.Missoes;

import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Entidades.Robos.RoboLimpador;
import LAB05.src.Exceptions.MissaoInvalidaException;

public class MissaoLimpezaAuto implements Missao {
    @Override
    public void executar(Robo r, Ambiente a) throws MissaoInvalidaException{
        r.getAmbiente().getLogger().inicializarMissao(this, r.getId());
        
        MissaoLimpezaProxima mover = new MissaoLimpezaProxima();

        if (!(r instanceof RoboLimpador)){
            throw new MissaoInvalidaException("O robô deve ser um limpador para fazer esta missão!");
        }
        
        RoboLimpador limpador = (RoboLimpador) r;
        
        try {
            while (!(limpador.getLixos().isEmpty())){
                limpador.setMissao(mover);
                limpador.executarMissao(limpador.getAmbiente());

                limpador.limpar();
            }
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
