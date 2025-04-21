package LAB03.Code;
/*
 * Classe obrigatória Obstaculo
 * 
 * Enum:
 *  - Enum TipoObstaculo {ARVORE, PREDIO, BURACO, PAREDE, OUTRO}
 * Atributos:
 * - PosicaoX1, PosicaoY1 (coordenadas do canto inferior esquerdo)
 * - PosicaoX2 = PosicaoX1 + tipo.getLarguraX (coordenadas do canto superior direito)
 * - PosicaoY2 = PosizaoY1 + tipo.getLarguraY (coordenadas do canto superior direito)
 * - tipo
 * - altura
 * 
 * Relação com a classe Ambiente: 
 * - Um ambiente pode ter vários obstáculos (Composição)
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


    // Enum
    /*
    * Enum TipoObstaculo
    * Objetivo é criar um enum que represente os tipos de obstáculos que podem ser encontrados no ambiente.
    * Os tipos de obstáculos são:
    * - ARVORE
    * - PREDIO
    * - BURACO
    * - PAREDE
    * - OUTRO
    *  Atributos:
    * - larguray (int)
    * - largurax (int)
    * - altura (int)
    * - PrendeORobo (boolean)
    */
    public static enum TipoObstaculo {
        ARVORE(1, 1, 10, false),
        PREDIO(10, 10, 100, false),
        BURACO(1, 1, 0, true),
        PAREDE(10, 1, 10, true), 
        ROCHA(5, 5, 5, false),
        CERCAELETRICA(0, 15, 5, true),
        AREIAMOVEDICA(3, 3, 0, true),
        LAGO(10, 10, 0, true);

        private final int larguraX;
        private final int larguraY;
        private final int altura;
        private final boolean prendeRobo;

        TipoObstaculo(int larguraX, int larguraY, int altura, boolean prendeRobo) {
            this.larguraX = larguraX;
            this.larguraY = larguraY;
            this.altura = altura;
            this.prendeRobo = prendeRobo;
        }
        public int getLarguraX() { return larguraX; }
        public int getLarguraY() { return larguraY; }
        public int getAltura() { return altura; }
        public boolean isPrendeRobo() { return prendeRobo; }
    }
}