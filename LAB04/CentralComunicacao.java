package LAB04;

import java.util.ArrayList;

public class CentralComunicacao {
    //Atributos
    private ArrayList<String> mensagens;


    //Construtor
    CentralComunicacao(){
        this.mensagens = new ArrayList<String>();
    }


    //Metodos
    void registrarMensagem(String remetente, String msg){
        //podemos usar aquelas exceções personalizadas se passar:
        mensagens.add(msg);
    }

    void exibirMensagens(){ // pode fazer algo se o mensagens estiver vazio retornar um exception...
        for(int i = 0; i < mensagens.size(); i++){
            System.out.println(mensagens.get(i));
        }
    }
}
