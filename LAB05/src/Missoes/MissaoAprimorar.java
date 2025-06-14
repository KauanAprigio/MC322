package LAB05.src.Missoes;

import LAB03.Code.Obstaculo;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Entidades.Interfaces.Entidade.TipoEntidade;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Exceptions.MissaoInvalidaException;

public class MissaoAprimorar {
    public void executar(Robo r, Ambiente a) throws MissaoInvalidaException{
        for (Entidade e : r.getAmbiente().getEntidades()){
            if (e.getTipo() == TipoEntidade.LOCAL){
                Obstaculo oficina = (Obstaculo) e;
                int Xmaisproximo = Math.max(oficina.getPosicaoX1(), Math.min(r.getX_1(), oficina.getPosicaoX2()));
                int Ymaisproximo = Math.max(oficina.getPosicaoY1(), Math.min(r.getY_1(), oficina.getPosicaoY2()));
                try {
                    r.getAmbiente().moverRobo(r, Ymaisproximo, Xmaisproximo, 0);
                } catch (Exception message) {
                    System.out.println("Não conseguiu mover por conta do seguinte erro:" + message);
                }
            }
        }

    }
    public String getDetalhes(){
        String msg = "Procura a Oficina no ambiente e move o robô para lá";
        return msg;
    }
}
