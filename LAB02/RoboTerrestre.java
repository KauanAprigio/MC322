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

    //Getters e Setters
    public boolean getPower() { return power; }
    public int getPas() { return n_pas; }
}

class RoboEntregador extends RoboTerrestre {
    private int estoque = 0; // estoque seria o quanto ele ja esta carregando de comida em peso, por ele começar carregando nada o estoque e 0
    private int carga_maxima; 


    // Construtor
    public RoboEntregador(String nome, int posicaoX, int posicaoY, int velocidadeMaxima, int carga_maxima) {
        super(nome, posicaoX, posicaoY, velocidadeMaxima);
        this.carga_maxima = carga_maxima;
    }
    // Metodos
    public void encher_estoque(int peso_adicional_comida){
        if (estoque + peso_adicional_comida <= carga_maxima){
            estoque += peso_adicional_comida;
            System.out.println(getNome() + " foram colocadas " + peso_adicional_comida + " gramas de comida!");
        } else {
            int peso_disponivel = carga_maxima - estoque;
            System.out.println(getNome() + " não pode carregar mais comida, pois o compartimento está cheio!");
            System.out.println(getNome() + "aguenta apenas mais  " + peso_disponivel + " gramas de comida!");
        }
    }

    public void entregar_comida(int comida){
        int estoque_atual = estoque;
        estoque -= comida;
        if (estoque == 0) {
            System.out.println(getNome() + " está entregou " + comida + " gramas de comida");
            System.out.println(getNome() + " não tem mais comida para entregar");
        } else if (estoque > 0) {
            System.out.println(getNome() + " entregou " + comida + " gramas de comida");
            System.out.println(getNome() + " ainda tem " + estoque + " gramas de comida");
        } else {
            estoque = 0;
            System.out.println(getNome() + "entregou apenas " + estoque_atual + " gramas de comida");
            System.out.println(getNome() + " não tem mais comida para entregar");
        }
    }
    public void aumentar_carga(int nova_carga){
        carga_maxima = nova_carga;
        System.out.println(getNome() + " teve seu compartimento expandido para " + carga_maxima + " gramas");
    }
    //Getters e Setters
    public int getEstoque() { return estoque; }
    public int getCompartimento() { return carga_maxima; }
}
