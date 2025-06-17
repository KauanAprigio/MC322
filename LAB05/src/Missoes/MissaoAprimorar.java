package LAB05.src.Missoes;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Entidades.Interfaces.Entidade.TipoEntidade;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Entidades.Obstaculos.Obstaculo.TipoObstaculo;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Entidades.Robos.RoboLimpador;
import LAB05.src.Exceptions.MissaoInvalidaException;

public class MissaoAprimorar implements Missao{
    @Override
    public void executar(Robo r, Ambiente a) throws MissaoInvalidaException{
        r.getAmbiente().getLogger().inicializarMissao(this, r.getId());
        //faço o laço para encontrar as dimensões da oficina mais proxima e mover para lá

        r.getAmbiente().getLogger().logAcao("Está procurando a oficina...");
        for (Entidade e : r.getAmbiente().getEntidades()){
            if (e.getTipo() == TipoEntidade.LOCAL){
                Obstaculo oficina = (Obstaculo) e;
                if (oficina.getTipoObstaculo() == TipoObstaculo.OFICINA){
                    r.getAmbiente().getLogger().logAcao("Está procurando a melhor rota até a oficina...");
                    int Xmaisproximo = Math.max(oficina.getX_1(), Math.min(r.getX_1(), oficina.getX_2()));
                    int Ymaisproximo = Math.max(oficina.getX_1(), Math.min(r.getY_1(), oficina.getX_2()));
                    try {
                        r.getAmbiente().moverRobo(r, Ymaisproximo, Xmaisproximo, 0);
                    } catch (Exception message) {
                        r.getAmbiente().getLogger().logErr(message);
                    }
                }
            }
        }
        RoboLimpador robo = (RoboLimpador) r; // faço o casting para ter o estahAprimorado
        if (!(robo.estahAprimorado())){
            robo.getAmbiente().getLogger().logAcao("Está na oficina...");
            int raio = robo.getRaioLimpeza() + 15; // adiciono 15m ao raio atual
            robo.setRaioLimpeza(raio); //atualizo o raio
            robo.setAprimorar(true); // marca que o robô foi aprimorado
            
            robo.getComunicador().registrarMensagem(robo.getId(), "Aprimoramento foi concluído com sucesso.");  
            
            robo.getAmbiente().getLogger().logAcao(robo.getId() + " teve seu raio de limpeza aumentado para " + raio + ".\n");
            robo.getAmbiente().getLogger().finalizarMissao("O agente " + robo.getId() + " finalizou sua missão de aprimoramento com sucesso.\n");
        } else {
            throw new MissaoInvalidaException("O robô já foi aprimorado anteriormente.\n");
        }
    }

    @Override
    public String getDetalhes(){ 
        return "Procurar a Oficina no ambiente, mover o robô para lá e fazer o aprimoramento dele.";
    }
}
