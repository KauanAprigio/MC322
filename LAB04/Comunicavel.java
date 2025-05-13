package LAB04;

public interface Comunicavel {
    void enviarMensagem(Comunicavel destinatario, String mensagem);
    void receberMensagem(String mensagem);
}

//só fazer os override disso para as entidades necessarias(possivelmente somente robos)