package LAB04;

//ta com erro pq deixei comentado alguns metodos da interface Entidade
public class Robo implements Entidade {
    // Atributos
    String id;
    // boolean estado; no caso acho que vale deixar aqui que pode ser um boolean o estado, no caso true ligado e false desligado
    TipoEntidade tipo;
    int pos_x;
    int pos_y;
    int pos_z;


    //Construtor
    Robo(String id, TipoEntidade tipo,  int pos_x, int pos_y, int pos_z){ // falta o estado
        this.id = id;
        //this.estado = estado;
        this.tipo = tipo;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.pos_z = pos_z;
    }


    //Metodos
    void moverPara(int x, int y, int z){
        //só desenvolver
    }

    void ligar(){
        // posso botar só para mudar o estado e printar algo 
    }

    void desligar(){
        //mema fita do ligar
    }

    void executar(){
        //posso deixar enxuto e desenvolver por meio de sobrecarga de método nas subclasses do robo
    }


    // Metodos sobrescritos da interface
    @Override
    public int getX() { return pos_x; }

    @Override
    public int getY() { return pos_y; }

    @Override
    public int getZ() { return pos_z; }

    @Override
    public TipoEntidade getTipo() { return tipo; }

    @Override
    public String getDescricao() { 
        String descricao = "QUALQUER COISA, SÓ PARA TER ALGO";
        return descricao;
    }

    @Override
    public char getRepresentacao() { char representacao = 'r'; return representacao; }


    



}
