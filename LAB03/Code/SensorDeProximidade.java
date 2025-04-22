package LAB03.Code;

/*
 * Subclasse de Sensor
 * 
 * Atributos:
 * 
 */
public class SensorDeProximidade extends Sensor {

    // Construtor
    public SensorDeProximidade(double raio) {
        super(raio);
    }

    // Método para monitorar obstáculos
    @Override
    public void monitorar(int x, int y, int altura, Ambiente ambiente) {
        int N_obstaculos = 0;
        for (Obstaculo obstaculo: ambiente.getObstaculos()) {
            int Xmaisproximo = Math.max(obstaculo.getPosicaoX1(), Math.min(x, obstaculo.getPosicaoX2()));
            int Ymaisproximo = Math.max(obstaculo.getPosicaoY1(), Math.min(y, obstaculo.getPosicaoY2()));
            int Zmaisproximo = Math.max(0, Math.min(altura, obstaculo.getAltura()));
            double distancia = Math.sqrt(Math.pow(Xmaisproximo - x, 2) + Math.pow(Ymaisproximo - y, 2) + Math.pow(Zmaisproximo - altura, 2));
            if (distancia <= getRaio()) {
               System.out.println("Obstáculo detectado: " + obstaculo.getTipo() + " a uma distância de " + distancia + "\n");
                N_obstaculos++;
            }
        }
        if (N_obstaculos > 0) {
            System.out.println("Sensor de proximidade detectou " + N_obstaculos + " obstáculos próximos.\n");
        } else {
            System.out.println("Sensor de proximidade não detectou obstáculos próximos.\n");
        }
    }

}
