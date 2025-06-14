package LAB05.src.Missoes;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Entidades.Robos.RoboLimpador;
import LAB05.src.Exceptions.MissaoInvalidaException;


public class MissaoMoverProximo implements Missao {
    @Override
    public void executar(Robo r, Ambiente a) throws MissaoInvalidaException {
        if (!(r instanceof RoboLimpador)){
            throw new MissaoInvalidaException("O robô deve ser um limpador para fazer esta missão!");
        }
        RoboLimpador limpador = (RoboLimpador) r; //aqui faço um casting e uso de um ponteiro para facilitar a busca
        double menorDistancia = Double.MAX_VALUE; // Inicializa com o maior valor possível
        Obstaculo maisProximo = null; // Inicializa como null para verificar se encontrou algum lixo
        for (Obstaculo lixo : limpador.getLixos()) {
            // Calcula a distância entre a entidade e o lixo
            int DistanciaX = Math.max(limpador.getX_1(), lixo.getX_1()) - Math.min (limpador.getX_1(), lixo.getX_1());
            int DistanciaY = Math.max(limpador.getY_1(), lixo.getY_1()) - Math.min (limpador.getY_1(), lixo.getY_1());
            double distancia = Math.sqrt(Math.pow(DistanciaX, 2) + Math.pow(DistanciaY, 2));

            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                maisProximo = lixo; // Atualiza o lixo mais próximo
            }
        }
        try { // devo melhorar isso, por exemplo eu devo 
            limpador.getAmbiente().moverRobo(limpador, maisProximo.getX_1() + 1, maisProximo.getY_1() + 1, 0);
        } catch (Exception e) {
            System.out.println("ERRO! Nunca deve cair aqui" + e.getMessage()); // 
        }
    }

    

    @Override
    public String getDetalhes() {
        String msg = "Faz a movimentação para o lixo mais próximo.";
        return msg;
    }
    
}
