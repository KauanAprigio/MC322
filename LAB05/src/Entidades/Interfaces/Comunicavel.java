package LAB05.src.Entidades.Interfaces;

import LAB05.src.Exceptions.ErroComunicacaoException;

public interface Comunicavel {
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws ErroComunicacaoException;    
    public void receberMensagem(String mensagem, Comunicavel remetente);
} 