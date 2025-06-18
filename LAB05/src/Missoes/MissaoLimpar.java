package LAB05.src.Missoes;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import java.util.Iterator;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Entidades.Robos.RoboLimpador;
import LAB05.src.Entidades.Robos.Robo.EstadoRobo;
import LAB05.src.Exceptions.MissaoInvalidaException;


public class MissaoLimpar implements Missao {
    @Override
    public void executar(Robo r, Ambiente a) throws MissaoInvalidaException {
        r.getAmbiente().getLogger().inicializarMissao(this, r.getId());
        if (!(r instanceof RoboLimpador)){
            throw new MissaoInvalidaException("O robô deve ser um limpador para fazer esta missão!");
        }

        RoboLimpador limpador = (RoboLimpador) r; //aqui faço um casting e uso de um ponteiro para facilitar a busca

        if (limpador.getEstado() == EstadoRobo.OFF){
            String message = "Não foi possível limpar o ambiente, pois o robô encontra-se desligado!\n";
            throw new MissaoInvalidaException(message);
        }
        
        if (limpador.getLixos() == null){
            String message = "Não foi possível limpar, pois o robô " + limpador.getId() + " não executou o seu sensor de varredura!\n";
            throw new MissaoInvalidaException(message);
        }

        if (limpador.getLixos().isEmpty()){
            String message = "Não foi possivel limpar, pois não há mais lixos no ambiente!\n";
            throw new MissaoInvalidaException(message);
        }


        Iterator<Obstaculo> iterator = limpador.getLixos().iterator();

        limpador.getAmbiente().getLogger().logAcao("Verificando o ambiente ao redor para efetuar a limpeza.");
        while (iterator.hasNext()) {
            Obstaculo lixo = iterator.next();              
            int distancia = (int) Math.sqrt(Math.pow(lixo.getX_1() - limpador.getX_1(), 2) + Math.pow(lixo.getY_1() - limpador.getY_1(), 2));
            if (distancia < limpador.getRaioLimpeza()) { 
                try {
                    limpador.getAmbiente().removerEntidade(lixo,false); // remove do ambiente
                    iterator.remove(); // remove do arraylist<Obstaculo> lixos usando o iterator
                    limpador.getComunicador().registrarMensagem(limpador.getId(), "Limpeza de " + lixo.getId() + " foi concluída com sucesso.");
                    limpador.getAmbiente().getLogger().logAcao(limpador.getId() + " limpou " + lixo.getTipoObstaculo().getNome() + ".");
                } catch (Exception e){
                    limpador.getAmbiente().getLogger().logErr(e);
                }
            }
        }
        limpador.getAmbiente().getLogger().finalizarMissao("Missão de limpeza finalizada com sucesso.\n");

    }

    @Override
    public String getDetalhes() {
        return "Limpeza dos lixos próximos";
    }
    
}