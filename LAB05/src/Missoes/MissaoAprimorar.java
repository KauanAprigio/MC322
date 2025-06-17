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
        //faço o laço para encontrar as dimensões da oficina mais proxima e mover para lá
        for (Entidade e : r.getAmbiente().getEntidades()){
            if (e.getTipo() == TipoEntidade.LOCAL){
                Obstaculo oficina = (Obstaculo) e;
                if (oficina.getTipoObstaculo() == TipoObstaculo.OFICINA){
                    int Xmaisproximo = Math.max(oficina.getX_1(), Math.min(r.getX_1(), oficina.getX_2()));
                    int Ymaisproximo = Math.max(oficina.getX_1(), Math.min(r.getY_1(), oficina.getX_2()));
                    try {
                        r.getAmbiente().moverRobo(r, Ymaisproximo, Xmaisproximo, 0);
                    } catch (Exception message) {
                        System.out.println("Não conseguiu mover por conta do seguinte erro:" + message);
                    }
                }
            }
        }
        RoboLimpador robo = (RoboLimpador) r; // faço o casting para ter o estahAprimorado
        if (!(robo.estahAprimorado())){
            int raio = robo.getRaioLimpeza() + 15; // adiciono 15m ao raio atual
            robo.setRaioLimpeza(raio); //atualizo o raio
            robo.setAprimorar(true); // marca que o robô foi aprimorado
            System.out.println(robo.getId() + " teve seu raio de limpeza aumentado para " + raio + ".\n");
            robo.getComunicador().registrarMensagem(robo.getId(), "Aprimoramento foi concluído com sucesso.");  
        } else {
            throw new MissaoInvalidaException("O robô já foi aprimorado anteriormente.");
        }
    }

    @Override
    public String getDetalhes(){
        String msg = "Procura a Oficina no ambiente e move o robô para lá, além de fazer o aprimoramento dele.";
        return msg;
    }
}
