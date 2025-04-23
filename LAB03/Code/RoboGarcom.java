package LAB03.Code;

/* 
 * Classe criada RoboGarcom
 *  
 * Atributos:
 * - estoque
 * - carga_maxima
 * 
 * Métodos:
 * - adicionar_estoque(int peso_adicional_comida)
 * - entregar_comida(int comida)
 * - mudar_carga(int nova_carga)
 */
// robo que carrega comida e entrega
// SubClasse de RoboTerrestre
class RoboGarcom extends RoboTerrestre {
    private int estoque = 0; // estoque seria o quanto ele ja esta carregando de comida em peso, por ele começar carregando nada o estoque e 0
    private int carga_maxima; //maximo de comida que ele pode carregar

    // Construtor
    public RoboGarcom(String nome, int posicaoX, int posicaoY, int velocidadeMaxima,
                                    Sensor sensor, Ambiente ambiente, int carga_maxima) {
        super(nome, posicaoX, posicaoY, velocidadeMaxima, sensor, ambiente);
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
