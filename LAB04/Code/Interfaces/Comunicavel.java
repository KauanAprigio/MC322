package LAB04.Code.Interfaces;

import LAB04.Code.Exceptions.ErroComunicacaoException;

/*
 * Interface Comunicavel:
 *  Define o contrato para entidades que podem se comunicar.
 *  Contém os métodos enviarMensagem e receberMensagem.
 *  As entidades que implementam essa interface devem fornecer a lógica para enviar e receber mensagens.
 *  Apenas robôs bombeiros e comunicadores implementam essa interface.
 *  As mensagens são enviadas de uma entidade para outra, e a recepção é tratada pela entidade destinatária.
 */
public interface Comunicavel {
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws ErroComunicacaoException;
    public void receberMensagem(String mensagem, Comunicavel remetente) throws ErroComunicacaoException;
}