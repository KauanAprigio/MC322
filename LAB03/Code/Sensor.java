package LAB03.Code;

/*
 * Classe obrigatória Sensor
 * 
 * Atributos:
 * - raio (double)
 * 
 * Métodos:
 *  Encontra o ponto mais próximo do sensor que pertence a um obstáculo
 *  Se o ponto mais próximo estiver dentro do raio do sensor detecta ele
 * - Monitorar(int X, int Y, int altura, Ambiente ambiente) // detecta se há algum obstáculo na área de monitoramento
 * 
 * Relação com a classe Robo: 
 * - Um robô tem 1 ou vários sensores (Composição)
 */
public class Sensor {
    private double raio;

    // Construtor
    public Sensor(double raio) {
        this.raio = raio;
    }
    // Métodos
    public void monitorar(int x, int y, int altura, Ambiente ambiente) {
        int N_obstaculos = 0;
        // Lógica para monitorar o ambiente
        // Conta os obstáculos na área de monitoramento
        for (Obstaculo obstaculo: ambiente.getObstaculos()) {
            int Xmaisproximo = Math.max(obstaculo.getPosicaoX1(), Math.min(x, obstaculo.getPosicaoX2()));
            int Ymaisproximo = Math.max(obstaculo.getPosicaoY1(), Math.min(y, obstaculo.getPosicaoY2()));
            int Zmaisproximo = Math.max(0, Math.min(altura, obstaculo.getAltura()));
            double distancia = Math.sqrt(Math.pow(Xmaisproximo - x, 2) + Math.pow(Ymaisproximo - y, 2) + Math.pow(Zmaisproximo - altura, 2));
            if (distancia <= raio) {
                N_obstaculos++;
            }
        }
        if (N_obstaculos > 0) {
            System.out.println("Sensor detectou " + N_obstaculos + " obstáculos próximos.\n");
        } else {
            System.out.println("Sensor não detectou obstáculos próximos.\n");
        }
    }

    // Getters e Setters
    public double getRaio() { return raio; }
    public void setRaio(double raio) { this.raio = raio; }
}
