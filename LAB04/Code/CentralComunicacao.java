package LAB04.Code;
import java.util.ArrayList;

public class CentralComunicacao {
    //Atributos
    private ArrayList<String> mensagens;


    //Construtor
    CentralComunicacao(){
        this.mensagens = new ArrayList<String>();
    }


    //Metodos
    public void registrarMensagem(String remetente, String msg){ // tem que mudar isso uma forma para armazenar o remetente ou destinatario
        //podemos usar aquelas exceções personalizadas se passar:
        mensagens.add(msg);
    }

    public void exibirMensagens(){ // pode fazer algo se o mensagens estiver vazio retornar um exception...
        for(int i = 0; i < mensagens.size(); i++){
            System.out.println(mensagens.get(i));
        }
    }
}
