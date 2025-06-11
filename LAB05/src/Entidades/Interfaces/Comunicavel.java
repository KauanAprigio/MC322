package LAB05.src.Entidades.Interfaces;

public interface Comunicavel {
    public void EnviarMensagem(String msg, Comunicavel remetente);    
    public void receberMensagem(String msg);
} 