package LAB04;

//ta com erro pq deixei comentado alguns metodos da interface Entidade
public class Robo implements Entidade {
    // Atributos
    String id;
    EstadoRobo estado;
    TipoEntidade tipo;
    int pos_x;
    int pos_y;
    int pos_z;


    //Construtor
    Robo(String id, EstadoRobo estado, TipoEntidade tipo,  int pos_x, int pos_y, int pos_z){
        this.id = id;
        this.estado = estado;
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
        this.estado = EstadoRobo.ON;
        System.out.println("O robô de id:" + this.getNome() + " está ligado.");
    }

    void desligar(){
        this.estado = EstadoRobo.OFF;
        System.out.println("O robô de id:" + this.getNome() + " está desligado.");
    }

    void executar(){
        //posso deixar enxuto e desenvolver por meio de sobrecarga de método nas subclasses do robo
    }


    // Geters e Setters adicionais
    public String getNome(){ return id; }
    public EstadoRobo getEstado() { return estado; }
    public void setEstado(EstadoRobo novoEstado){ this.estado = novoEstado; }
    

    // usarei o enum para me referir ao estado do robo
    public enum EstadoRobo {
        ON, // ligado
        OFF; // desligado
        
        //aqui irei ver se ele está ligado, caso ele esteja ligado ira retornar true, caso o contrario irá falar que é false, logo OFF
        public boolean estaLigado() {
            return this == ON;
        }

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
