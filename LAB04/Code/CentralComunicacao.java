package LAB04.Code;
import java.util.ArrayList;

public class CentralComunicacao {
    //Atributos
    private ArrayList<String> mensagens;
    private int TotalMensagens = 0;


    //Construtor
    CentralComunicacao(){
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

    public void exibirMensagens(){ // pode fazer algo se o mensagens estiver vazio retornar um exception...
        for(int i = 0; i < mensagens.size(); i++){
            System.out.println(mensagens.get(i));
        }
    }

    public int getTotalMensagens() { return TotalMensagens; }
}
