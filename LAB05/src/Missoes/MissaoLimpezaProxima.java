package LAB05.src.Missoes;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Entidades.Robos.Robo.EstadoRobo;
import LAB05.src.Entidades.Robos.RoboLimpador;
import LAB05.src.Exceptions.MissaoInvalidaException;


public class MissaoLimpezaProxima implements Missao {
    @Override
    public void executar(Robo robo, Ambiente ambiente) throws MissaoInvalidaException {
        ambiente.getLogger().inicializarMissao(this, robo.getId());
        if (!(robo instanceof RoboLimpador)){
            throw new MissaoInvalidaException("O robô deve ser um limpador para fazer esta missão!");
        }
        RoboLimpador limpador = (RoboLimpador) robo; //aqui faço um casting e uso de um ponteiro para facilitar a busca
        double menorDistancia = Double.MAX_VALUE; // Inicializa com o maior valor possível
        Obstaculo maisProximo = null; // Inicializa como null para verificar se encontrou algum lixo

        if (limpador.getEstado() == EstadoRobo.OFF){
            String message = "Não foi possível mover para o lixo mais próximo, pois o robô encontra-se desligado!\n";
            throw new MissaoInvalidaException(message);
        }
        
        if (limpador.getLixos() == null ){
            String message = "Não foi possível mover para o lixo mais próximo, o robô " + limpador.getId() + " não executou o seu sensor de varredura!\n";
            throw new MissaoInvalidaException(message);
        }

        if (limpador.getLixos().isEmpty()){
            String message = "Não foi possível mover para o lixo mais próximo, pois não há mais lixos no ambiente!\n";
            throw new MissaoInvalidaException(message);
        }

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
        limpador.getAmbiente().getLogger().logAcao("Lixo selecionado e movendo-se até ele...");
        testaOpcao(ambiente, maisProximo, limpador);
        limpador.getComunicador().registrarMensagem(limpador.getId(), "Movimento para o lixo mais próximo foi concluído com sucesso.");
        
        try { // aqui ele irá limpar único lixo, no caso o mais próximo
            limpador.limpar();
        } catch (Exception e){
            ambiente.getLogger().logErr(e);
        }
        limpador.getAmbiente().getLogger().finalizarMissao("Missão limpar o lixo mais próximo foi finalizada com sucesso.\n");
        return; //confirmar que ele só vai limpar 1 lixo APENAS.
    }

    @Override
    public String getDetalhes() {
        return "Limpar o lixo mais próximo.";
    }

    public void testaOpcao(Ambiente ambiente, Obstaculo lixo, Robo agente) {
        // um array que tem deslocamentos para ver se ao redor do lixo é possível o robô se mover
        int[][] deslocamentos = {
            {1, 1}, {0, 1}, {1, 0}, {-1, 0},
            {0, -1},{-1, -1},{-1, 1},{1, -1}
        };

        // vejo se é possível mover-se em algum dos intervalos que criei  
        for (int[]deslocamento : deslocamentos) {
            int novo_X = lixo.getX_1() + deslocamento[0];
            int novo_Y = lixo.getY_1() + deslocamento[1];

            //verifico se a posição está vazia
            if (ambiente.getplanoXY()[novo_X][novo_Y] == 'v') {
                try {
                    ambiente.moverRobo(agente, novo_X, novo_Y, 0);
                    return; // Se moveu com sucesso, saia da função
                } catch (Exception e) {
                    ambiente.getLogger().logErr(e);
                }
            }
        }
    }
}

