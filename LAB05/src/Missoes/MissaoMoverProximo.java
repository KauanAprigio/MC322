package LAB05.src.Missoes;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Entidades.Robos.RoboLimpador;
import LAB05.src.Exceptions.MissaoInvalidaException;


public class MissaoMoverProximo implements Missao {
    @Override
    public void executar(Robo r, Ambiente a) throws MissaoInvalidaException {
        r.getAmbiente().getLogger().inicializarMissao(this, r.getId());
        if (!(r instanceof RoboLimpador)){
            throw new MissaoInvalidaException("O robô deve ser um limpador para fazer esta missão!");
        }
        RoboLimpador limpador = (RoboLimpador) r; //aqui faço um casting e uso de um ponteiro para facilitar a busca
        double menorDistancia = Double.MAX_VALUE; // Inicializa com o maior valor possível
        Obstaculo maisProximo = null; // Inicializa como null para verificar se encontrou algum lixo
        limpador.getAmbiente().getLogger().logAcao("Procurando o lixo mais próximo...");
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
        limpador.getAmbiente().getLogger().logAcao("Lixo encontrado e movendo-se até ele...");
        try { // devo melhorar isso, por exemplo eu devo 
            limpador.getAmbiente().moverRobo(limpador, maisProximo.getX_1() + 1, maisProximo.getY_1() + 1, 0);
            limpador.getComunicador().registrarMensagem(limpador.getId(), "Movimento para o lixo mais próximo foi concluído com sucesso.");
            limpador.getAmbiente().getLogger().finalizarMissao("Missão de movimentar para próximo do lixo finalizada com sucesso.");
        } catch (Exception e) {
            limpador.getAmbiente().getLogger().logErr(e);
        }
    }

    

    @Override
    public String getDetalhes() {
        return "Ir para o lixo mais próximo.";
    }
    
}
