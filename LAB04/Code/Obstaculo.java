package LAB04.Code;

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
    private int pos_z2;
    private TipoObstaculo tipoObstaculo;
    private TipoEntidade tipo;


    // Construtor
    public Obstaculo(int pos_x, int pos_y, TipoObstaculo tipoObstaculo, TipoEntidade tipo) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.pos_x2 = pos_x + tipoObstaculo.getLarguraX();
        this.pos_y2 = pos_y + tipoObstaculo.getLarguraY();
        this.pos_z2 = tipoObstaculo.getAltura();
        this.tipoObstaculo = tipoObstaculo;
        this.tipo = tipo;
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
    public char getRepresentacao() { char representacao = 'o'; return representacao; }


    // Getters e Setters
    public int getAlturinha() { return pos_z2; } // só para tirar a linha amarela, mas nem sei se vou usar kkkkkkk
    public int getPosicaoX2() { return pos_x2; }
    public int getPosicaoY2() { return pos_y2; }
    public TipoObstaculo getTipoObstaculo() { return tipoObstaculo; }

    public void setPosX(int novo_x) { this.pos_x = novo_x; }
    public void setPosY(int novo_y) { this.pos_y = novo_y; }
    public void setPosX2() { pos_x2 = pos_x + tipoObstaculo.getLarguraX(); }
    public void setPosY2() { pos_y2 = pos_y + tipoObstaculo.getLarguraY(); }


    /* 
     * Enumeração dos tipos de obstáculos
     * - LAGO: Local:reabastecimento de água do robô bombeiro: não removível
     * - FOGO: removível pelo robô bombeiro destroi robos que não são bombeiros
     * - PREDIOEMCHAMAS: Fogo: removível pelo robô bombeiro vira prédio destroi robos que não são bombeiros
     * - PREDIO: Local: não removível
     * - SUJEIRAENCARDIDA: Lixo: removível pelo robô limpador no modo de limpeza pesada
     * - COMIDANOCHAO: Lixo: removível pelo robô limpador no modo de limpeza média
     * - SACOLAPLASTICA: Lixo: removível pelo robô limpador no modo de limpeza leve
     * - OFICINA: Local: melhoria de robôs: não removível
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
        LAGO(30, 30, 0, false, "Lago", false, false, false), 
        FOGO(5, 5, 5, false, "Fogo", true, false, false),
        PREDIOEMCHAMAS(30, 30, 100, true, "Prédio em chamas", true, false,false), 
        PREDIO(30, 30, 100, true, "Prédio", false, false,false),
        SUJEIRAENCARDIDA(0, 0, 0, false, "Sujeira encardida", false, true,false), 
        COMIDANOCHAO(0, 0, 0, false, "Comida no chão", false, true,false), 
        SACOLAPLASTICA(0, 0, 0, false, "Sacola plástica", false, true,false),
        OFICINA(20, 20, 20, true, "Oficina", false, false,true); 


        private final int larguraX;
        private final int larguraY;
        private final int altura;
        private String nome;
        
        // Atributos finais (requisito obrigatório)
        private final boolean local;
        private final boolean fogo;
        private final boolean lixo;
        private final boolean aprimora;

        TipoObstaculo(int larguraX, int larguraY, int altura, boolean Local, String nome,
                            boolean fogo, boolean lixo, boolean aprimora) {
            this.larguraX = larguraX;
            this.larguraY = larguraY;
            this.altura = altura;
            this.nome = nome;
            this.local = Local;
            this.fogo = fogo;
            this.lixo = lixo;
            this.aprimora = aprimora;
        }
        public int getLarguraX() { return larguraX; }
        public int getLarguraY() { return larguraY; }
        public int getAltura() { return altura; }
        public String getNome() { return nome; }
        public boolean isLocal() { return local; }
        public boolean isFogo() { return fogo; }
        public boolean isLixo() { return lixo; }
        public boolean isAprimora() { return aprimora; }
    }
}