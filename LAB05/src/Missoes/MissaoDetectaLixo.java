package LAB05.src.Missoes;
import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Entidades.Interfaces.Entidade.TipoEntidade;
import LAB05.src.Entidades.Obstaculos.Obstaculo;
import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Robos.Robo;
import LAB05.src.Entidades.Robos.RoboLimpador;
import LAB05.src.Exceptions.MissaoInvalidaException;




public class MissaoDetectaLixo implements Missao {
    @Override
    public void executar(Robo r, Ambiente a) throws MissaoInvalidaException { 
        if (!(r instanceof RoboLimpador)){
            throw new MissaoInvalidaException("O robô deve ser um limpador para fazer esta missão!");
        }
        //aqui faço um casting e uso de um ponteiro para facilitar a busca
        RoboLimpador limpador = (RoboLimpador) r;
        Ambiente ambiente = limpador.getAmbiente();
        for (Entidade e : ambiente.getEntidades()){
            if (e.getTipo() == TipoEntidade.LIXO){
                Obstaculo lixo = (Obstaculo) e;
                limpador.getLixos().add(lixo);
            }
        }
        System.out.println("Agora o " + r.getId() + " sabe de todos os lixos que estão no ambiente, vamos limpá-los.");
    }
    

    @Override
    public String getDetalhes() {
        String msg = "Verifica quantos lixos tem no ambiente.";
        return msg;
    }
    
}
