package LAB05.src.Missoes;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import java.util.Iterator;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Entidades.Robos.RoboLimpador;
import LAB05.src.Exceptions.MissaoInvalidaException;


public class MissaoLimpar implements Missao {
    @Override
    public void executar(Robo r, Ambiente a) throws MissaoInvalidaException {
        r.getAmbiente().getLogger().inicializarMissao(this, r.getId());
        if (!(r instanceof RoboLimpador)){
            r.getAmbiente().getLogger().logErr("Não foi possível finalizar a missão, pois o robô não é um roboLimpador!\n");
            throw new MissaoInvalidaException("O robô deve ser um limpador para fazer esta missão!");
        }

        RoboLimpador limpador = (RoboLimpador) r; //aqui faço um casting e uso de um ponteiro para facilitar a busca
        Iterator<Obstaculo> iterator = limpador.getLixos().iterator();

        while (iterator.hasNext()) {
            Obstaculo lixo = iterator.next();              
            int distancia = (int) Math.sqrt(Math.pow(lixo.getX_1() - limpador.getX_1(), 2) + Math.pow(lixo.getY_1() - limpador.getY_1(), 2));
            if (distancia < limpador.getRaioLimpeza()) { 
                try {
                    limpador.getAmbiente().getLogger().logAcao("Verificando distância para efetuar a limpeza.");
                    limpador.getAmbiente().removerEntidade(lixo,false); // remove do ambiente
                    iterator.remove(); // remove do arraylist<Obstaculo> lixos usando o iterator
                    limpador.getComunicador().registrarMensagem(limpador.getId(), "Limpeza de " + lixo.getId() + " foi concluída com sucesso.");
                    
                    limpador.getAmbiente().getLogger().logAcao(limpador.getId() + " limpou " + lixo.getTipoObstaculo().getNome() + ".");
                    limpador.getAmbiente().getLogger().finalizarMissao("Missão de limpeza finalizada com sucesso.\n");
                } catch (Exception e){
                    limpador.getAmbiente().getLogger().logErr(e);
                }
            }
        }

    }

    @Override
    public String getDetalhes() {
        String msg = "Faz a limpeza dos lixos próximos";
        return msg;
    }
    
}