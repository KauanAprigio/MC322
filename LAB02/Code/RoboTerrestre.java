package LAB02.Code;

//Subclasse de Robo, mas também é a "SuperClasse" de RoboLimpador e RoboGarcom
public class RoboTerrestre extends Robo {
    // Atributos adicionais
    private int velocidadeMaxima;

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
}


// Subclasse de RoboTerrestre
class RoboLimpador extends RoboTerrestre {
    // Atributos adicionais
    private int n_pas; // numero de pás limpadoras do robo
    private boolean power = false; // estado que ira dizer se esta ligado = true ou desligado = false
    private int tipo_limpeza = 0; // 0 = limpeza leve, 1 = limpeza pesada, 2 = limpeza muito pesada

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

    void definir_tipo_limpeza(int tipo){
        if (!power) {
            System.out.println(getNome() + " está desligado. Não é possível definir o tipo de limpeza.");
            return;
        }
        // Verifica se o tipo de limpeza é válido
        // 0 = limpeza leve, 1 = limpeza pesada, 2 = limpeza muito pesada
        if (tipo >= 0 && tipo <= 2) {
            tipo_limpeza = tipo;
            switch (tipo_limpeza) {
                case 0:
                    System.out.println(getNome() + " está configurado para limpeza leve.");
                    break;
                case 1:
                    System.out.println(getNome() + " está configurado para limpeza pesada.");
                    break;
                case 2:
                    System.out.println(getNome() + " está configurado para limpeza muito pesada.");
                    break;
            }
        } else {
            System.out.println("Tipo de limpeza inválido. Escolha entre 0 (leve), 1 (pesada) ou 2 (muito pesada).");
        }
    }

    public void mover(int deltaX, int deltaY) {
        if (power) {
            super.mover(deltaX, deltaY);
        } else {
            System.out.println(getNome() + " não pode se mover enquanto desligado.");
        }
    }

    //Getters e Setters
    public boolean getPower() { return power; }
    public int getPas() { return n_pas; }
    public int getTipoLimpeza() { return tipo_limpeza; }
    public void setPas(int n_pas) { this.n_pas = n_pas; }
}

class RoboGarcom extends RoboTerrestre {
    private int estoque = 0; // estoque seria o quanto ele ja esta carregando de comida em peso, por ele começar carregando nada o estoque e 0
    private int carga_maxima; //maximo de comida que ele pode carregar

    // Construtor
    public RoboGarcom(String nome, int posicaoX, int posicaoY, int velocidadeMaxima, int carga_maxima) {
        super(nome, posicaoX, posicaoY, velocidadeMaxima);
        this.carga_maxima = carga_maxima;
    }

    // Metodos
    public void adicionar_estoque(int peso_adicional_comida){
        if (estoque + peso_adicional_comida <= carga_maxima){
            estoque += peso_adicional_comida;
            System.out.println("Foram colocadas " + peso_adicional_comida + " gramas de comida no " + getNome());
            System.out.println(getNome() + " agora tem " + estoque + " gramas de comida");
        } else {
            int peso_disponivel = carga_maxima - estoque;
            System.out.println(getNome() + " não pode carregar mais " + peso_adicional_comida + ", pois o compartimento não aguenta");
            System.out.println(getNome() + " aguenta apenas mais  " + peso_disponivel + " gramas de comida!");
        }
    }

    public void entregar_comida(int comida){
        int estoque_anterior = estoque;
        estoque -= comida;
        if (estoque == 0) { //condicional caso o robo entregue toda sua comida
            System.out.println(getNome() + " entregou " + comida + " gramas de comida");
            System.out.println(getNome() + " não tem mais comida para entregar");
        } else if (estoque > 0) { // caso o robo entregue a comida e ainda tenha comida no estoque
            System.out.println(getNome() + " entregou " + comida + " gramas de comida");
            System.out.println(getNome() + " ainda tem " + estoque + " gramas de comida");
        } else { // caso "falte" comida para entregar
            estoque = 0;
            System.out.println(getNome() + " entregou apenas " + estoque_anterior + " gramas de comida");
            System.out.println(getNome() + " não tem mais comida para entregar");
        }
    }

    public void mudar_carga(int nova_carga){ // metodo para aumentar sua capacidade máxima
        carga_maxima = nova_carga;
        if (carga_maxima < estoque) { // caso a nova carga máxima seja do mesmo tamanho que o estoque atual, impede que ele diminua a carga máxima para menos que o estoque atual
            carga_maxima = estoque;
            System.out.println(getNome() + " teve seu compartimento alterado para " + carga_maxima + " gramas");
            System.out.println(getNome() + " não pode reduzir mais o seu compartimento, pois ele já está cheio");
        }
        else // aqui só modifica a carga_maxima podendo ser tanto para mais quanto para menos
            System.out.println(getNome() + " teve seu compartimento alterado para " + carga_maxima + " gramas");
    }

    //Getters e Setters
    public int getEstoque() { return estoque; }
    public int getCarga_maxima() { return carga_maxima; }
}
