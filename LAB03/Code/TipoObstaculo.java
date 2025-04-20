package LAB03.Code;

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
public enum TipoObstaculo {
    ARVORE(3, 3, 70, false),
    PREDIO(20, 20, 100, false),
    BURACO(2, 2, 0, true),
    PAREDE(20,2, 50, true),
    OUTRO(-1, -1, -1, false);

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

    public int getLarguraX() {
        return larguraX;
    }

    public int getLarguraY() {
        return larguraY;
    }

    public int getAltura() {
        return altura;
    }

    public boolean isPrendeRobo() {
        return prendeRobo;
    }
}


    

