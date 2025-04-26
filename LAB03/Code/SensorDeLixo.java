package LAB03.Code;

public class SensorDeLixo extends Sensor {
    // Construtor
    public SensorDeLixo(double raio) {
        super(raio);
    }

    // Método para monitorar lixo
    @Override
    public void monitorar(int x, int y, int altura, Ambiente ambiente) {
        int N_lixos = 0;
        for (Obstaculo obstaculo : ambiente.getObstaculos()) {
            if (obstaculo.getTipo().isLixo()) {
                
                int Xmaisproximo = Math.max(obstaculo.getPosicaoX1(), Math.min(x, obstaculo.getPosicaoX2()));
                int Ymaisproximo = Math.max(obstaculo.getPosicaoY1(), Math.min(y, obstaculo.getPosicaoY2()));
                int Zmaisproximo = Math.max(0, Math.min(altura, obstaculo.getAltura()));
                double distancia = Math.sqrt(Math.pow(Xmaisproximo - x, 2) + Math.pow(Ymaisproximo - y, 2) + Math.pow(Zmaisproximo - altura, 2));
                
                if (distancia <= getRaio()) {
                    System.out.println("Lixo detectado na posição: " + obstaculo.getPosicaoX1() + ", " + obstaculo.getPosicaoY1() + " a uma distância de " + distancia + " metros.");
                    System.out.println("Tipo de lixo: " + obstaculo.getTipo() );
                    N_lixos++;
                }
            }
        }
        if (N_lixos > 0) {
            System.out.println("Sensor de lixo detectou " + N_lixos + " lixos próximos.\n");
        } else {
            System.out.println("Sensor de lixo não detectou lixos próximos!\n");
        }
    }
    
}
