package LAB05.src.Entidades;

import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Entidades.Interfaces.Entidade.TipoEntidade;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Entidades.Robos.AgenteInteligente;
import LAB05.src.Entidades.Robos.RoboLimpador;
/**
 * Classe SensorDeVarredura:
 * <p>
 * Representa um sensor de varredura que permite a um robô limpador identificar e coletar lixos no ambiente.
 * O sensor percorre o ambiente, detectando entidades do tipo LIXO e adicionando-as à lista de lixos do robô.
 * </p>
 * <ul>
 *  <li><b>varreruda(AgenteInteligente agente)</b>: Método que executa a varredura no ambiente, identificando e coletando lixos.</li>
 * * </ul>
 * <p>
 * A classe é utilizada por robôs limpadores para realizar a limpeza do ambiente, garantindo que todos os lixos sejam detectados e armazenados.
 */
public class SensorDeVarredura {
    public void varreruda(AgenteInteligente agente){
        agente.getAmbiente().getLogger().inicializarAcao("varredura", "Sensor de varredura");
        if (agente instanceof RoboLimpador){
            RoboLimpador robo = (RoboLimpador) agente;
            System.out.println("Executando varredura...");
            agente.getAmbiente().getLogger().logAcao("Começará a varredura para encontrar todos os lixos.");
            for (Entidade e : agente.getAmbiente().getEntidades()){
                if (e.getTipo() == TipoEntidade.LIXO){
                    Obstaculo lixo = (Obstaculo) e;
                    robo.getLixos().add(lixo);
                }
            }
            System.out.println("A varredura foi concluída e agora o " + agente.getId() + " pode limpar tudo.\n");
            agente.getAmbiente().getLogger().finalizarAcao("Varredura finalizada com sucesso.\n");
        }       
    }
}
