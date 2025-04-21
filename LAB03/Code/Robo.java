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
    private int posicaoX;
    private int posicaoY;
    private Sensor sensor;
    private Ambiente ambiente;

    // Construtor
    public Robo(String nome, int posicaoX, int posicaoY, Sensor sensor, Ambiente ambiente) {
        this.nome = nome;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.sensor = sensor;
        this.ambiente = ambiente;
    }

    // Métodos
    public void mover(int deltaX, int deltaY) {
        // Verifica se a nova posição está dentro dos limites do ambiente
        if (ambiente.dentroDosLimites(posicaoX + deltaX, posicaoY + deltaY, 0)) {
            this.posicaoX += deltaX;
            this.posicaoY += deltaY;
            System.out.println("Robo " + nome + " moveu para (" + posicaoX + ", " + posicaoY + ")");
        } else {
            System.out.println("Movimento inválido! Posição nova fora dos limites do ambiente atual.");
        }
    }

    public void identificarObstaculo() {
        // Lógica para identificar obstáculos
        // Aqui é utilizado o sensor para verificar se há obstáculos próximos
        System.out.println("Identificando obstáculos...");
        sensor.monitorar(posicaoX, posicaoY, 0, ambiente); // Passar o ambiente correto aqui
    }

    public void exibirPosicao() {
        System.out.println("Posição do robô " + nome + ": (" + posicaoX + ", " + posicaoY + ")");
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public int getX() { return posicaoX; }
    public int getY() { return posicaoY; }
    public void setNome(String nome) { this.nome = nome; }
    public String getAmbiente() { return ambiente.toString(); }
}
