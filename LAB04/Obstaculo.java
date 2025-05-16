// coloquei aqui porque acho que irei usar, apenas copiei do lab 03, mas capaz que terá de ter mudanças bruscas
package LAB04;

/*
 * Classe obrigatória Obstaculo
 * 
 * Enum:
 *  - Enum TipoObstaculo {ARVORE, PREDIO, BURACO, PAREDE, OUTRO}
 * Atributos:
 * - int PosicaoX1, int PosicaoY1 (coordenadas do canto inferior esquerdo)
 * - int PosicaoX2 = PosicaoX1 + tipo.getLarguraX (coordenadas do canto superior direito)
 * - int PosicaoY2 = PosizaoY1 + tipo.getLarguraY (coordenadas do canto superior direito)
 * - TipoObstaculo tipo
 * - int altura
 * 
 */
// Obstaculos São aproximados a paralelepipedos para facilitar os calculos de colisão
// e detecção de proximidade
public class Obstaculo {
    private int posicaoX1;
    private int posicaoY1;
    private int posicaoX2;
    private int posicaoY2;
    private TipoObstaculo tipo;
    private int altura;

    // Construtor
    public Obstaculo(int posicaoX1, int posicaoY1, TipoObstaculo tipo) {
        this.posicaoX1 = posicaoX1;
        this.posicaoY1 = posicaoY1;
        this.tipo = tipo;
        this.posicaoX2 = posicaoX1 + tipo.getLarguraX();
        this.posicaoY2 = posicaoY1 + tipo.getLarguraY();
        this.altura = tipo.getAltura();
    }
    // Getters e Setters
    public int getPosicaoX1() { return posicaoX1; }
    public int getPosicaoY1() { return posicaoY1; }
    public int getPosicaoX2() { return posicaoX2; }
    public int getPosicaoY2() { return posicaoY2; }
    public TipoObstaculo getTipo() { return tipo; }
    public int getAltura() { return altura; }


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