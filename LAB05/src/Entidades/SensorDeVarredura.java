package LAB05.src.Entidades;

import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Entidades.Interfaces.Entidade.TipoEntidade;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Entidades.Robos.AgenteInteligente;
import LAB05.src.Entidades.Robos.RoboLimpador;

public class SensorDeVarredura {
    public void varreruda(AgenteInteligente agente){
        if (agente instanceof RoboLimpador){
            RoboLimpador robo = (RoboLimpador) agente;
            for (Entidade e : agente.getAmbiente().getEntidades()){
                if (e.getTipo() == TipoEntidade.LIXO){
                    Obstaculo lixo = (Obstaculo) e;
                    robo.getLixos().add(lixo);
                }
            }
            System.out.println("Agora o " + robo.getId() + " sabe de todos os lixos que estão no ambiente, vamos limpá-los.");
        }       
    }
}
