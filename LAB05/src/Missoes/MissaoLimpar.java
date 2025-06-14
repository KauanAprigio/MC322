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
        if (!(r instanceof RoboLimpador)){
            throw new MissaoInvalidaException("O robô deve ser um limpador para fazer esta missão!");
        }

        RoboLimpador limpador = (RoboLimpador) r; //aqui faço um casting e uso de um ponteiro para facilitar a busca
        Iterator<Obstaculo> iterator = limpador.getLixos().iterator();

        while (iterator.hasNext()) {
            Obstaculo lixo = iterator.next();              
            int distancia = (int) Math.sqrt(Math.pow(lixo.getX_1() - limpador.getX_1(), 2) + Math.pow(lixo.getY_1() - limpador.getY_1(), 2));
            if (distancia < limpador.getRaioLimpeza()) { 
                limpador.getLixos().remove(lixo);
                System.out.println(limpador.getId() + " limpou " + lixo.getTipoObstaculo().getNome() + ".");
            }
        }
    }

    @Override
    public String getDetalhes() {
        String msg = "Faz a limpeza dos lixos próximos";
        return msg;
    }
    
}