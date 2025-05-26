package LAB04.Code.Interfaces;
/*
 * Interface Comunicavel:
 *  Define o contrato para entidades que podem se comunicar.
 *  Contém os métodos enviarMensagem e receberMensagem.
 *  As entidades que implementam essa interface devem fornecer a lógica para enviar e receber mensagens.
 *  Apenas robôs bombeiros e comunicadores implementam essa interface.
 *  As mensagens são enviadas de uma entidade para outra, e a recepção é tratada pela entidade destinatária.
 */
public interface Comunicavel {
    public void enviarMensagem(Comunicavel destinatario, String mensagem);
    public void receberMensagem(String mensagem);
}