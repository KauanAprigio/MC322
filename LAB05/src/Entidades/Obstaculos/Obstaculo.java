package LAB05.src.Entidades.Obstaculos;
import LAB05.src.Entidades.*;
import LAB05.src.Ambiente.Ambiente;

/*
 * Classe obrigatória Obstaculo
 * 
 * Enum:
 *  - Enum TipoObstaculo {ARVORE, PREDIO, BURACO, PAREDE, OUTRO}
 * Atributos:
 * - int pos_x, int PosicaoY1 (coordenadas do canto inferior esquerdo)
 * - int PosicaoX2 = pos_x + tipoObstaculo.getLarguraX (coordenadas do canto superior direito)
 * - int PosicaoY2 = PosizaoY1 + tipoObstaculo.getLarguraY (coordenadas do canto superior direito)
 * - TipoObstaculo tipoObstaculo
 * - int pos_z
 * 
 */
// Obstaculos São aproximados a paralelepipedos para facilitar os calculos de colisão
// e detecção de proximidade
public class Obstaculo implements Entidade { 

    
    private int pos_x;
    private int pos_y;
    private final int pos_z = 0;
    private TipoObstaculo tipoObstaculo;
    private TipoEntidade tipo;
    private Ambiente ambiente; // Ambiente onde o obstáculo está localizado

    // Construtor
    public Obstaculo(int pos_x, int pos_y, TipoObstaculo tipoObstaculo, Ambiente ambiente, TipoEntidade tipo) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.tipoObstaculo = tipoObstaculo;
        this.ambiente = ambiente;
        this.tipo = tipo;
    }


    // Metodos sobrescritos da interface
    @Override
    public int getLarguraZ() { return tipoObstaculo.larguraZ; }
    public int getX_1() { return pos_x; }
    public int getY_1() { return pos_y; }
    public int getZ_1() { return pos_z; }
    public TipoEntidade getTipo() { return tipo; }
    public String getDescricao() { return tipoObstaculo.descricao; }
    public int getLarguraX() { return tipoObstaculo.larguraX; }
    public int getLarguraY() { return tipoObstaculo.larguraY; }
    public char getRepresentacao() { char representacao = tipoObstaculo.representacao; return representacao; }
    public String getId() { return tipoObstaculo.nome; }


    // Getters e Setters
    public int getX_2() { return pos_x + getLarguraX(); }
    public int getY_2() { return pos_y + getLarguraY(); }
    public int getZ_2() { return pos_z + getLarguraZ(); }
    public TipoObstaculo getTipoObstaculo() { return tipoObstaculo; }
    public Ambiente getAmbiente() { return ambiente; }

    public void setPosX(int novo_x) { 
        this.pos_x = novo_x;
    }
    public void setPosY(int novo_y) { 
        this.pos_y = novo_y;
    }
    


    /* 
     * Enumeração dos tipos de obstáculos
     * - LAGO: Local - reabastecimento de água do robô bombeiro: não removível
     * - FOGO: removível pelo robô bombeiro.
     * - PREDIOEMCHAMAS: Fogo - removível pelo robô bombeiro e vira um prédio.
     * - PREDIO: Local - não removível
     * - SUJEIRAENCARDIDA: Lixo - removível pelo robô limpador no modo de limpeza pesada
     * - COMIDANOCHAO: Lixo - removível pelo robô limpador no modo de limpeza média
     * - SACOLAPLASTICA: Lixo - removível pelo robô limpador no modo de limpeza leve
     * - OFICINA: Local - melhoria de robôs: não removível
     * 
     * Atributos:
     * - int larguraX: largurax do obstáculo
     * - int larguraY: larguray do obstáculo
     * - int altura: altura do obstáculo
     * - boolean local: se o obstáculo é um local de reabastecimento ou melhoria
     * - String nome: nome do obstáculo
     * - boolean fogo: se o obstáculo é ou tem fogo
     * - boolean lixo: se o obstáculo é lixo
     * - boolean aprimora: se é um local para aprimoramento
    */
    public static enum TipoObstaculo {
        
        LAGO(30, 30, 0,
              "Lago", 'l',
               "Lago: Local utilizado para abestecimento dos robôs bombeiros, eles devem estar pousados na água para abastecer o tanque!"), 
        FOGO(5, 5, 5, "Fogo",
            'f', "Fogo! é urgente, Chame um robô bombeiro!"),
        PREDIOEMCHAMAS(30, 30, 100,
             "Prédio em chamas", 'F',
              "Prédio em chamas! Local extremamente perigoso e instável! Chame um robô bombeiro!"), 
        PREDIO(30, 30, 100, "Prédio",
                'p',
              "Prédio: uma construção de vários andares, industrial, comercial ou residencial. Em algum ponto podia ter estado em chamas..."),
        SUJEIRAENCARDIDA(0, 0, 0,
             "Sujeira encardida", 'E',
              "Sujeira encardida: A mais nojenta entre os tipos de lixo... Um robô limpador consegue remove-la."), 
        COMIDANOCHAO(0, 0, 0, 
            "Comida no chão", 'C',
             "Comida no chão: Que desperdício... A regra dos 5 segundos não vale mais. Chame um robô limpador para tirar isso daqui"), 
        SACOLAPLASTICA(0, 0, 0,
             "Sacola plástica", 'S',
              "Sácola plástica: Plástico.. O lixo mais poluente do mundo! Faça a coisa certa e chame um robô limpador para tirar isso daqui."),
        OFICINA(20, 20, 20,
             "Oficina", 'o',
              "Oficina: Local de aprimoramento dos Robôs, Robôs bombeiros ganham peso máximo adicional e Robôs limpadores ganham um raio de limpeza maior."); 


        private final int larguraX;
        private final int larguraY;
        private final int larguraZ;
        private String nome;
        private final char representacao; // Descrição do obstáculo
        private final String descricao;


        TipoObstaculo(int larguraX, int larguraY, int larguraZ, String nome, char representacao, String descricao) {
            this.larguraX = larguraX;
            this.larguraY = larguraY;
            this.larguraZ = larguraZ;
            this.nome = nome;
            this.descricao = descricao;
            this.representacao = representacao;
        }
    }
}