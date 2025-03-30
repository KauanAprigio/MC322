package LAB02;

public class RoboTerrestre extends Robo {
    // Atributos adicionais
    private int velocidadeMaxima;
    private static int altitudeMaxima = 0;
    private static int altitude = 0;


    // Construtor
    public RoboTerrestre(String nome, int posicaoX, int posicaoY, int velocidadeMaxima) {
        super(nome, posicaoX, posicaoY);
        this.velocidadeMaxima = velocidadeMaxima;
    }


    // Metodos
    public void mover(int deltaX, int deltaY) {
        int velocidade = (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (velocidade <= velocidadeMaxima) {
            super.mover(deltaX, deltaY);
        } else {
            System.out.println(getNome() + ": Velocidade de " + velocidade + " excede o máximo de " + getVelocidadeMaxima() + " permitido");
        }
    }
    

    // Getters e Setters
    public int getVelocidadeMaxima() { return velocidadeMaxima; }
    public int getAltitudeMaxima() { return altitudeMaxima; }
    public int getAltitude() { return altitude; }
}


// Subclasse de RoboTerrestre
class RoboLimpador extends RoboTerrestre {
    // Atributos adicionais
    private int n_pas;
    private boolean power = false;


    // Construtor
    public RoboLimpador(String nome, int posicaoX, int posicaoY, int velocidadeMaxima, boolean power, int n_pas) {
        super(nome, posicaoX, posicaoY, velocidadeMaxima);
        this.power = power;
        this.n_pas = n_pas;
    }


    // Metodos
    public boolean ligar() {
        if (!power) {
            power = true;
            System.out.println(getNome() + " foi ligado com sucesso!");
        } else {
            System.out.println(getNome() + " já está ligado.");
        }
        return power;
    }

    public boolean desligar() {
        if (power) {
            power = false;
            System.out.println(getNome() + " foi desligado.");
        } else {
            System.out.println(getNome() + " já está desligado.");
        }
        return power;
    }
    
    public void mudar_pas(int novas_pas){
        n_pas = novas_pas;
    }


    //Getters e Setters
    public boolean getPower() { return power; }
    public int getPas() { return n_pas; }
}

//SubClasse do RoboTerrestre 
class RoboGarcom extends RoboTerrestre {
    // Atributos adicionais
    private int estoque = 0; // estoque seria o quanto ele ja esta carregando de comida, por ele começar carregando nada o estoque e 0
    private int compartimento_maximo;


    // Construtor
    public RoboGarcom(String nome, int posicaoX, int posicaoY, int velocidadeMaxima, int compartimento_maximo) {
        super(nome, posicaoX, posicaoY, velocidadeMaxima);
        this.compartimento_maximo = compartimento_maximo;
    }

    
    // Metodos
    public void encher_estoque(int comida){
        if (estoque + comida <= compartimento_maximo){
            estoque += comida;
            System.out.println(getNome() + " foram colocadas " + comida + " gramas!");
        } else {
            System.out.println(getNome() + " já está na sua capacidade máxima!");
        }
    }

    public void entregar_comida(int comida){
        if (estoque > 0) {
            estoque -= comida;
            System.out.println(getNome() + " está entregando " + comida + " gramas de comida!");
        } else {
            System.out.println(getNome() + " está vazio!");
        }
    }

    public void aumentar_compartimento(int novo_compartimento){
        compartimento_maximo = novo_compartimento;
        System.out.println(getNome() + " teve seu compartimento expandido para " + compartimento_maximo + " gramas!");
    }

    
    //Getters e Setters
    public int getEstoque() { return estoque; }
    public int getCompartimento() { return compartimento_maximo; }
}
