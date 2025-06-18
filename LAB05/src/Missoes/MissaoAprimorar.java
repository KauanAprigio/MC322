package LAB05.src.Missoes;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Entidades.Interfaces.Entidade.TipoEntidade;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Entidades.Obstaculos.Obstaculo.TipoObstaculo;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Entidades.Robos.RoboLimpador;
import LAB05.src.Entidades.Robos.Robo.EstadoRobo;
import LAB05.src.Exceptions.MissaoInvalidaException;

public class MissaoAprimorar implements Missao{
    @Override
    public void executar(Robo r, Ambiente a) throws MissaoInvalidaException{
        r.getAmbiente().getLogger().inicializarMissao(this, r.getId());
        RoboLimpador robo = (RoboLimpador) r; // faço o casting para ter o estahAprimorado
        
        //faço o laço para encontrar as dimensões da oficina mais proxima e mover para lá
        if (robo.getLocalAtualRep() != 'o'){
            r.getAmbiente().getLogger().logAcao("Está localizando a oficina...");
            for (Entidade e : robo.getAmbiente().getEntidades()){
                if (e.getTipo() == TipoEntidade.LOCAL){
                    Obstaculo oficina = (Obstaculo) e;
                    if (oficina.getTipoObstaculo() == TipoObstaculo.OFICINA){
                        robo.getAmbiente().getLogger().logAcao("Está procurando a melhor rota até a oficina...");
                        int Xmaisproximo = Math.max(oficina.getX_1(), Math.min(robo.getX_1(), oficina.getX_2()));
                        int Ymaisproximo = Math.max(oficina.getY_1(), Math.min(robo.getY_1(), oficina.getY_2()));
                        try {
                            robo.getAmbiente().moverRobo(robo, Xmaisproximo, Ymaisproximo, 0);
                        } catch (Exception message) {
                            robo.getAmbiente().getLogger().logErr(message);
                        }
                        if (robo.getEstado() == EstadoRobo.ON) aprimorar(robo);
                    }
                }
            }
        } else {
            if (!robo.estahAprimorado()){
                if (robo.getEstado() == EstadoRobo.ON) aprimorar(robo);
            } else {
            throw new MissaoInvalidaException("O robô já foi aprimorado anteriormente.\n");
            }
        }
    }

    @Override
    public String getDetalhes(){ 
        return "Procurar a Oficina no ambiente, mover o robô para lá e fazer o aprimoramento dele.";
    }

    public void aprimorar(RoboLimpador robolimpinho){
        robolimpinho.getAmbiente().getLogger().logAcao("Está na oficina...");
        int raio = robolimpinho.getRaioLimpeza() + 15; // adiciono 15m ao raio atual
        robolimpinho.setRaioLimpeza(raio); //atualizo o raio
        robolimpinho.aprimorar();; // marca que o robô foi aprimorado
                
        robolimpinho.getComunicador().registrarMensagem(robolimpinho.getId(), "Aprimoramento foi concluído com sucesso.");  
                
        robolimpinho.getAmbiente().getLogger().logAcao(robolimpinho.getId() + " teve seu raio de limpeza aumentado para " + raio + ".\n");
        robolimpinho.getAmbiente().getLogger().finalizarMissao("O agente " + robolimpinho.getId() + " finalizou sua missão de aprimoramento com sucesso.\n");
    }
}
