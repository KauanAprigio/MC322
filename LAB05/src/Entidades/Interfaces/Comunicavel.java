package LAB05.src.Entidades.Interfaces;

import LAB05.src.Exceptions.ErroComunicacaoException;
/**
 * Interface Comunicavel
 * <p>
 * Define métodos para enviar e receber mensagens entre entidades.
 * As entidades que implementam esta interface podem se comunicar entre si,
 * permitindo a troca de informações e coordenação de ações.
 * </p>
 *
 * <ul>
 *   <li><b>enviarMensagem(Comunicavel destinatario, String mensagem)</b>:
 *       Envia uma mensagem para o destinatário especificado.</li>
 *   <li><b>receberMensagem(String mensagem, Comunicavel remetente)</b>:
 *       Recebe uma mensagem de um remetente específico.</li>
 * </ul>
 */
public interface Comunicavel {
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws ErroComunicacaoException;    
    public void receberMensagem(String mensagem, Comunicavel remetente);
} 