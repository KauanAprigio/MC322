package LAB05.src.Entidades.Obstaculos;
import LAB05.src.Entidades.Interfaces.Entidade;
import LAB05.src.Ambiente.Ambiente;

/**
 * Classe Obstaculo:
 * <p>
 * Representa um obstáculo no ambiente, como fogo, prédios, sujeira, etc.
 * Os obstáculos têm uma posição (x, y) e um tipo que define suas características.
 * </p>
 * <ul>
 *  <li><b>pos_x, pos_y</b>: Posição do obstáculo no plano XY.</li>
 * <li><b>pos_z</b>: Altura do obstáculo, sempre igual à 0 (Obstáculos não saem do chão).</li>
 * <li><b>tipoObstaculo</b>: Tipo do obstáculo, definido pela enumeração TipoObstaculo.</li>
 * <li><b>tipo</b>: Tipo da entidade, definido pela enumeração TipoEntidade.</li>
 * <li><b>ambiente</b>: Ambiente onde o obstáculo está localizado.</li>
 * </ul>
 * <p>
 * Os obstáculos podem ser de diferentes tipos, como fogo, prédios, sujeira, etc.
 * Cada tipo de obstáculo tem suas próprias características, como largura, altura, descrição e representação 2D.
 * </p>
 * <p>
 * A classe implementa a interface Entidade, fornecendo métodos para obter informações sobre a posição, tipo e características do obstáculo.
 * </p>
 * <p>
 */
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
    public int getX_1() { return pos_x; }
    @Override
    public int getY_1() { return pos_y; }
    @Override
    public int getZ_1() { return pos_z; }
    @Override
    public int getX_2() { return pos_x + getLarguraX(); }
    @Override
    public int getY_2() { return pos_y + getLarguraY(); }
    @Override
    public int getZ_2() { return pos_z + getLarguraZ(); }
    @Override
    public TipoEntidade getTipo() { return tipo; }
    @Override
    public String getDescricao() { return tipoObstaculo.descricao; }
    @Override
    public int getLarguraX() { return tipoObstaculo.larguraX; }
    @Override
    public int getLarguraY() { return tipoObstaculo.larguraY; }
    @Override
    public int getLarguraZ() { return tipoObstaculo.larguraZ; }
    @Override
    public char getRepresentacao() { char representacao = tipoObstaculo.representacao; return representacao; }
    @Override
    public String getId() { return tipoObstaculo.nome; }
    @Override
    public Ambiente getAmbiente() { return ambiente; }
    

    // Getters e Setters
    public TipoObstaculo getTipoObstaculo() { return tipoObstaculo; }

    /**
     * Enum TipoObstaculo:
     * <p>
     * Define os tipos de obstáculos que podem existir no ambiente.
     * Cada tipo de obstáculo tem suas próprias características, como largura, altura, descrição e representação 2D.
     * </p>
     * <ul>
     * <li><b>LAGO</b>: Local utilizado para abastecimento dos robôs bombeiros, eles devem estar pousados na água para abastecer o tanque.</li>
     * <li><b>FOGO</b>: Fogo! é urgente, Chame um robô bombeiro!</li>
     * <li><b>PREDIOEMCHAMAS</b>: Prédio em chamas! Local extremamente perigoso e instável! Chame um robô bombeiro!</li>
     * <li><b>PREDIO</b>: Uma construção de vários andares, industrial, comercial ou residencial. Em algum ponto podia ter estado em chamas...</li>
     * <li><b>SUJEIRAENCARDIDA</b>: A mais nojenta entre os tipos de lixo... Um robô limpador consegue removê-la.</li>
     * <li><b>COMIDANOCHAO</b>: Que desperdício... A regra dos 5 segundos não vale mais. Chame um robô limpador para tirar isso daqui.</li>
     * <li><b>SACOLAPLASTICA</b>: Plástico.. O lixo mais poluente do mundo! Faça a coisa certa e chame um robô limpador para tirar isso daqui.</li>
     * <li><b>OFICINA</b>: Local de aprimoramento dos Robôs, Robôs bombeiros ganham peso máximo adicional e Robôs limpadores ganham um raio de limpeza maior.</li>
     * </ul>
     * <p>
     * Cada tipo de obstáculo é representado por um caractere no plano XY do ambiente, facilitando a visualização e interação com os obstáculos.
     * </p>
     * <p>
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

        //getters e setters
        public String getNome() { return nome; }
    }
}