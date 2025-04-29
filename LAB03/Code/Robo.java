package LAB03.Code;


/*
 * SuperClasse Robo, é obrigatória
 *  
 * Atributos:
 * - String nome
 * - int posicaoX
 * - int posicaoY
 * - Sensor sensor
 * - Ambiente ambiente
 * 
 * Relações:
 * - Ambiente: pode ter mais de um rôbo, mas um rôbo tem apenas um ambiente
 * - Sensor: depende do robô para existir, logo compõe o robô, o robô deve ter 1 ou mais sensores
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
            System.out.println("Robo " + nome + " moveu para (" + posicaoX + ", " + posicaoY + ").\n");
        } else {
            System.out.println("Movimento inválido! Posição nova fora dos limites do ambiente atual!\n");
        }
    }

    public void identificarObstaculo() {
        // Lógica para identificar obstáculos
        // Aqui é utilizado o sensor para verificar se há obstáculos próximos
        System.out.println("Identificando obstáculos...");
        sensor.monitorar(posicaoX, posicaoY, 0, ambiente);
    }

    public void exibirPosicao() {
        System.out.println("Posição do robô " + nome + ": (" + posicaoX + ", " + posicaoY + ").\n");
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public int getX() { return posicaoX; }
    public int getY() { return posicaoY; }
    public Ambiente getAmbiente() { return ambiente; }
    public Sensor getSensor() { return sensor; }

    public void setNome(String nome) { this.nome = nome; }
    public void setSensor(Sensor sensor) { this.sensor = sensor; }
    public void setAmbiente(Ambiente ambiente) { this.ambiente = ambiente; }
}
