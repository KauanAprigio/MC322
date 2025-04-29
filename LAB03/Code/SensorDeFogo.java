package LAB03.Code;

/*
 * Subclasse de Sensor
 * 
 * Métodos:
 * monitorar(int x, int y, int altura, Ambiente ambiente)
 */

public class SensorDeFogo extends Sensor {
    // Construtor
    public SensorDeFogo(double raio) {
        super(raio);
    }

    // Método para monitorar fogo, mesma lógica do outro.
    @Override
    public void monitorar(int x, int y, int altura, Ambiente ambiente) {
        int N_fogos = 0;
        for (Obstaculo obstaculo : ambiente.getObstaculos()) {
            if (obstaculo.getTipo().isFogo()) {
                int Xmaisproximo = Math.max(obstaculo.getPosicaoX1(), Math.min(x, obstaculo.getPosicaoX2()));
                int Ymaisproximo = Math.max(obstaculo.getPosicaoY1(), Math.min(y, obstaculo.getPosicaoY2()));
                int Zmaisproximo = Math.max(0, Math.min(altura, obstaculo.getAltura()));
                double distancia = Math.sqrt(Math.pow(Xmaisproximo - x, 2) + Math.pow(Ymaisproximo - y, 2) + Math.pow(Zmaisproximo - altura, 2));

                if (distancia <= getRaio()) {
                    System.out.println("Fogo detectado na posição: (" + obstaculo.getPosicaoX1() + ", " + obstaculo.getPosicaoY1() + ") a uma distância de " + distancia + " metros.");
                    System.out.println("Tipo de fogo: " + obstaculo.getTipo() + ".");
                    N_fogos++;
                }
            }
        }
        if (N_fogos > 0) {
            System.out.println("Sensor de fogo detectou " + N_fogos + " fogos próximos.\n");
        } else {
            System.out.println("Sensor de fogo não detectou fogos próximos!\n");
        }
    }
    
}
