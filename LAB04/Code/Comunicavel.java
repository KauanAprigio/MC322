package LAB04.Code;

public interface Comunicavel {
    public void enviarMensagem(Comunicavel destinatario, String mensagem);
    public void receberMensagem(String mensagem);
}

//só fazer os override disso para as entidades necessarias(possivelmente somente robos)