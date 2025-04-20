package LAB03.Code;


/*
 * Classe obrigatória Robo
 *  
 * Atributos:
 * - nome
 * - direcao
 * - posicaoX
 * - posicaoY
 * 
 * Métodos:
 * - mover(int deltaX, int deltaY)
 * - identificarObstaculo()
 * - exibirPosicao()
 * 
 */
public class Robo {
    private String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;

    // Construtor
    public Robo(String nome, String direcao, int posicaoX, int posicaoY) {
        this.nome = nome;
        this.direcao = direcao;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
    }

    // Métodos
    public void mover(int deltaX, int deltaY) {
        this.posicaoX += deltaX;
        this.posicaoY += deltaY;
    }

    public void identificarObstaculo() {
        // Implementar lógica para identificar obstáculos
    }

    public void exibirPosicao() {
        System.out.println("Posição do robô " + nome + ": (" + posicaoX + ", " + posicaoY + ")");
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public String getDirecao() { return direcao; }
    public int getX() { return posicaoX; }
    public int getY() { return posicaoY; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDirecao(String direcao) { this.direcao = direcao; }

}
