package LAB05.src.Entidades;

import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Entidades.Interfaces.Entidade.TipoEntidade;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Entidades.Robos.AgenteInteligente;
import LAB05.src.Entidades.Robos.RoboLimpador;

public class SensorDeVarredura {
    public void varreruda(AgenteInteligente agente){
        agente.getAmbiente().getLogger().inicializarAcao("varredura", "Sensor de varredura");
        if (agente instanceof RoboLimpador){
            RoboLimpador robo = (RoboLimpador) agente;
            agente.getAmbiente().getLogger().logAcao("Começará a varredura para encontrar todos os lixos.");
            for (Entidade e : agente.getAmbiente().getEntidades()){
                if (e.getTipo() == TipoEntidade.LIXO){
                    Obstaculo lixo = (Obstaculo) e;
                    robo.getLixos().add(lixo);
                }
            }
            agente.getAmbiente().getLogger().finalizarAcao("Varredura finalizada com sucesso.\n");
        }       
    }
}
