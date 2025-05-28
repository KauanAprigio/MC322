package LAB04.Code;

import LAB04.Code.Interfaces.Entidade;

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
public class Obstaculo implements Entidade { // rever conceito de posZ, pois eu nao sei se deve dar a altura ou o final, pois sempre começa em z = 0
    private int pos_x;
    private int pos_y;
    private final int pos_z = 0;
    private int pos_x2;
    private int pos_y2;
    private TipoObstaculo tipoObstaculo;
    private TipoEntidade tipo; // Definindo o tipo como OBSTACULO por padrão
    private Ambiente ambiente; // Ambiente onde o obstáculo está localizado

    // Construtor
    public Obstaculo(int pos_x, int pos_y, TipoObstaculo tipoObstaculo, Ambiente ambiente, TipoEntidade tipo) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.pos_x2 = pos_x + tipoObstaculo.getLarguraX();
        this.pos_y2 = pos_y + tipoObstaculo.getLarguraY();
        this.tipoObstaculo = tipoObstaculo;
        this.ambiente = ambiente;
        this.tipo = tipo;
    }


    // Metodos sobrescritos da interface
    @Override
    public int getAltura() { return tipoObstaculo.getAltura(); }

    @Override
    public int getX() { return pos_x; }

    @Override
    public int getY() { return pos_y; }

    @Override
    public int getZ() { return pos_z; }

    @Override
    public TipoEntidade getTipo() { return tipo; }

    @Override
    public String getDescricao() { return tipoObstaculo.getDescricao(); }

    @Override
    public int getLarguraX() { return tipoObstaculo.getLarguraX(); }

    @Override
    public int getLarguraY() { return tipoObstaculo.getLarguraY(); }

    @Override
    public char getRepresentacao() { char representacao = getTipoObstaculo().getRepresentacao(); return representacao; }

    @Override
    public String getId() { return getTipoObstaculo().getNome(); }

    @Override
    public void mover(int deltaX, int deltaY, int deltaZ) {
        setPosX(pos_x + deltaX);
        setPosY(pos_y + deltaY);
        for (int x = pos_x; x <= pos_x2; x++) {
            for (int y = pos_y; y <= pos_y2; y++) {
                getAmbiente().getplanoXY()[x][y] = 'o';
                for (int z = 0; z <= getAltura(); z++) {
                    getAmbiente().getMapa()[x][y][z] = TipoEntidade.OBSTACULO;
                }
            }
        } 
    }


    // Getters e Setters
    public int getPosicaoX2() { return pos_x2; }
    public int getPosicaoY2() { return pos_y2; }
    public TipoObstaculo getTipoObstaculo() { return tipoObstaculo; }
    public Ambiente getAmbiente() { return ambiente; }

    public void setPosX(int novo_x) { 
        this.pos_x = novo_x;
        this.pos_x2 = novo_x + getLarguraX(); 
    }
    public void setPosY(int novo_y) { 
        this.pos_y = novo_y;
        this.pos_y2 = novo_y + getLarguraY();
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
        
        LAGO(30, 30, 0, false,
              "Lago", false, false, false, 'l',
               "Lago: Local utilizado para abestecimento dos robôs bombeiros, eles devem estar pousados na água para abastecer o tanque!"), 
        FOGO(5, 5, 5, false, "Fogo",
            true, false, false, 'f', "Fogo! é urgente, Chame um robô bombeiro!"),
        PREDIOEMCHAMAS(30, 30, 100, true,
             "Prédio em chamas", true, false,false, 'F',
              "Prédio em chamas! Local extremamente perigoso e instável! Chame um robô bombeiro!"), 
        PREDIO(30, 30, 100, true, "Prédio",
             false, false,false, 'p',
              "Prédio: uma construção de vários andares, industrial, comercial ou residencial. Em algum ponto podia ter estado em chamas..."),
        SUJEIRAENCARDIDA(0, 0, 0, false,
             "Sujeira encardida", false, true,false, 'S',
              "Sujeira encardida: A mais nojenta entre os tipos de lixo... Um robô limpador consegue remove-la."), 
        COMIDANOCHAO(0, 0, 0, false, 
            "Comida no chão", false, true,false, 'C',
             "Comida no chão: Que desperdício... A regra dos 5 segundos não vale mais. Chame um robô limpador para tirar isso daqui"), 
        SACOLAPLASTICA(0, 0, 0, false,
             "Sacola plástica", false, true,false, 'S',
              "Sácola plástica: Plástico.. O lixo mais poluente do mundo! Faça a coisa certa e chame um robô limpador para tirar isso daqui."),
        OFICINA(20, 20, 20, true,
             "Oficina", false, false,true, 'o',
              "Oficina: Local de aprimoramento dos Robôs, Robôs bombeiros ganham peso máximo adicional e Robôs limpadores ganham um raio de limpeza maior."); 


        private final int larguraX;
        private final int larguraY;
        private final int altura;
        private String nome;
        private final boolean local;
        private final boolean fogo;
        private final boolean lixo;
        private final boolean aprimora;
        private final char representacao; // Descrição do obstáculo
        private final String descricao;


        TipoObstaculo(int larguraX, int larguraY, int altura, boolean Local, String nome,
                            boolean fogo, boolean lixo, boolean aprimora, char representacao, String descricao) {
            this.larguraX = larguraX;
            this.larguraY = larguraY;
            this.altura = altura;
            this.nome = nome;
            this.local = Local;
            this.fogo = fogo;
            this.lixo = lixo;
            this.aprimora = aprimora;
            this.descricao = descricao;
            this.representacao = representacao;
        }

        //Getters e Setters
        public int getLarguraX() { return larguraX; }
        public int getLarguraY() { return larguraY; }
        public int getAltura() { return altura; }
        public String getNome() { return nome; }
        public String getDescricao() { return descricao; }
        public char getRepresentacao() { return representacao; }
        public boolean isLocal() { return local; }
        public boolean isFogo() { return fogo; }
        public boolean isLixo() { return lixo; }
        public boolean isAprimora() { return aprimora; }
    }
}