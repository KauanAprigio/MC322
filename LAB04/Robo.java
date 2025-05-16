package LAB04;


public class Robo implements Entidade {
    // Atributos
    String id;
    EstadoRobo estado;
    TipoEntidade tipo;
    int pos_x;
    int pos_y;
    int pos_z;

    // Atributo adicional para deixar o moverPara ficar mais fácil
    Ambiente ambiente;


    //Construtor
    Robo(String id, EstadoRobo estado, TipoEntidade tipo,  int pos_x, int pos_y, int pos_z, Ambiente ambiente){
        this.id = id;
        this.estado = estado;
        this.tipo = tipo;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.pos_z = pos_z;
        this.ambiente = ambiente;
    }


    //Metodos
    public void moverPara(int deltaX, int deltaY) {
        // Verifica se a nova posição está dentro dos limites do ambiente
        if (ambiente.dentroDosLimites(pos_x + deltaX, pos_y + deltaY, 0)) {
            this.pos_x += deltaX;
            this.pos_y += deltaY;
            System.out.println("Robo de id:" + id + " moveu para (" + pos_x + ", " + pos_y + ").\n");
        } else {
            System.out.println("Movimento inválido! Posição nova fora dos limites do ambiente atual!\n");
        }
    }

    public void ligar(){
        this.estado = EstadoRobo.ON;
        System.out.println("O robô de id:" + this.getId() + " está ligado.");
    }

    public void desligar(){
        this.estado = EstadoRobo.OFF;
        System.out.println("O robô de id:" + this.getId() + " está desligado.");
    }

    public void executar(){
        //posso deixar enxuto e desenvolver por meio de sobrecarga de método nas subclasses do robo
    }


    // Getters e Setters
    public String getId(){ return id; }
    public EstadoRobo getEstado() { return estado; }
    public Ambiente getAmbiente() { return ambiente; }

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
