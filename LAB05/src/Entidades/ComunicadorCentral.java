package LAB05.src.Entidades;

import LAB05.src.Ambiente.Ambiente;
import LAB05.src.Entidades.Interfaces.Comunicavel;
import LAB05.src.Entidades.Robos.Robo;

public class ComunicadorCentral implements Comunicavel{
    private Ambiente ambiente;

    @Override
    public void EnviarMensagem(String msg, Comunicavel remetente) {
        
    }

    @Override
    public void receberMensagem(String msg) {
        
    }

    public void LocalizarFogoMaisProx (Robo r) {

    }
}
