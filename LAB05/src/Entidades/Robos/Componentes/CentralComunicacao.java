package LAB05.src.Entidades.Robos.Componentes;
import java.util.ArrayList;

/**
 * Classe CentralComunicacao:
 * <p>
 * Responsável por gerenciar a comunicação entre entidades, registrando e exibindo mensagens.
 * Permite que entidades enviem mensagens entre si, mantendo um registro de todas as comunicações.
 * </p>
 * <ul>
 *  <li><b>registrarMensagem(String remetente, String msg)</b>: Registra uma mensagem enviada por uma entidade.</li>
 * *  <li><b>exibirMensagens()</b>: Exibe todas as mensagens registradas na central de comunicação.</li>
 * <li><b>getTotalMensagens()</b>: Retorna o total de mensagens registradas.</li>
 * </ul>
 * <p>
 * A classe CentralComunicacao é utilizada para facilitar a troca de informações entre entidades no ambiente,
 * permitindo que elas se comuniquem de forma eficiente e organizada.
 * </p>
 * 
 */
public class CentralComunicacao {
    //Atributos
    private ArrayList<String> mensagens; // Lista para armazenar mensagens trocadas entre entidades
    private int TotalMensagens = 0;


    //Construtor
    public CentralComunicacao(){
        this.mensagens = new ArrayList<String>();
    }


    //Metodos
    public void registrarMensagem(String remetente, String msg){ 
        String entrada = "Mensagem de " + remetente + ": " + msg;
        mensagens.add(entrada);
        TotalMensagens++;
        System.out.println("Mensagem registrada: " + entrada);
        System.out.println("Total de mensagens: " + TotalMensagens + ".\n");
    }

    public void exibirMensagens(){ 
        System.out.println("Exibindo todas as mensagens:");
        for(int i = 0; i < mensagens.size(); i++){
            System.out.println(mensagens.get(i));
        }
    }

    public int getTotalMensagens() { return TotalMensagens; }
}
