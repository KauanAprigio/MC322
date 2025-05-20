package LAB04;
import LAB04.Entidade.TipoEntidade;

/*
 * SuperClasse obrigatória Sensor
 * 
 * Atributos:
 * - double raio 
 * 
 * Métodos:
 *  Encontra o ponto mais próximo do sensor que pertence a um obstáculo
 *  Se o ponto mais próximo estiver dentro do raio do sensor detecta ele
 * - Monitorar(int X, int Y, int altura, Ambiente ambiente) // detecta se há algum obstáculo na área de monitoramento
 * 
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
        
        //terei de mudar basntate por conta da mudança do ambiente, mas depois vejo isso 
        for (Entidade e: ambiente.getEntidades()) {
            if (e.getTipo() == TipoEntidade.OBSTACULO){
                Obstaculo o = (Obstaculo) e; // casting para que eu possa usar getPosicaox2 e y2 para calcular as distancias
                int Xmaisproximo = Math.max(o.getX(), Math.min(x, o.getPosicaoX2()));
                int Ymaisproximo = Math.max(o.getY(), Math.min(y, o.getPosicaoY2()));
                int Zmaisproximo = Math.max(0, Math.min(altura, o.getZ()));
                double distancia = Math.sqrt(Math.pow(Xmaisproximo - x, 2) + Math.pow(Ymaisproximo - y, 2) + Math.pow(Zmaisproximo - altura, 2));
                if (distancia <= raio) {
                    N_obstaculos++;
                }
            }
        }
        if (N_obstaculos > 0) {
            System.out.println("Sensor detectou " + N_obstaculos + " obstáculos próximos.\n");
        } else {
            System.out.println("Sensor não detectou obstáculos próximos!\n");
        }
    }

    // Getters e Setters
    public double getRaio() { return raio; }
    public void setRaio(double raio) { this.raio = raio; }
}
