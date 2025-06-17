package LAB05.src.Missoes;

import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Entidades.Robos.RoboLimpador;
import LAB05.src.Exceptions.MissaoInvalidaException;

public class MissaoLimpezaAuto implements Missao {
    @Override
    public void executar(Robo r, Ambiente a) throws MissaoInvalidaException{
        MissaoAprimorar aprimora = new MissaoAprimorar();
        MissaoLimpar limpar = new MissaoLimpar();
        MissaoMoverProximo mover = new MissaoMoverProximo();

        if (!(r instanceof RoboLimpador)){
            throw new MissaoInvalidaException("O robô deve ser um limpador para fazer esta missão!");
        }
        
        RoboLimpador limpador = (RoboLimpador) r;
        
        try {
            limpador.setMissao(aprimora);
            limpador.executarMissao(limpador.getAmbiente());

            limpador.executarSensores();

            while (!(limpador.getLixos().isEmpty())){
                limpador.setMissao(mover);
                limpador.executarMissao(limpador.getAmbiente());

                limpador.setMissao(limpar);
                limpador.executarMissao(limpador.getAmbiente());
            }
        } catch (Exception e){
            System.err.println("deu algum B.O que não era para dar, como:" + e);
        }
    }

    @Override
    public String getDetalhes(){
        String msg = "Basicamente vai utilizar de todas as missões para deixar o robô 100% automático enquanto ainda tiver lixos.";
        return msg;
    }
}
