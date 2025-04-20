package LAB03.Code;
/*
 * Classe obrigatória Obstaculo
 * 
 * Enum:
 *  - Enum TipoObstaculo {ARVORE, PREDIO, BURACO, PAREDE, OUTRO}
 * Atributos:
 * - PosicaoX1, PosicaoY1 (coordenadas do canto inferior esquerdo)
 * - PosicaoX2 = PosicaoX1 + tipo.getLarguraX, PosicaoY2 = PosizaoY1 + tipo.getLarguraY (coordenadas do canto superior direito)
 * - tipo
 * - altura
 * 
 * Relação com a classe Ambiente: 
 * - Um ambiente pode ter vários obstáculos (Composição)
 */

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

}