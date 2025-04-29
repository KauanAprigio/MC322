package LAB03.Code;

/*
 * Subclasse de Sensor
 * 
 * Métodos:
 * monitorar(int x, int y, int altura, Ambiente ambiente)
 */

public class SensorDeLixo extends Sensor {
    // Construtor
    public SensorDeLixo(double raio) {
        super(raio);
    }

    // Método para monitorar lixo
    @Override
    public void monitorar(int x, int y, int altura, Ambiente ambiente) {
        int N_lixos = 0;
        for (Obstaculo obstaculo : ambiente.getObstaculos()) { // percorre todos os obstáculos
            if (obstaculo.getTipo().isLixo()) { // condicional caso o obstaculo seja um lixo
                
                // Lógica para calcular o ponto mais próximo dentro dos limites do obstáculo e calcular a distância deste ponto para o robo.
                int Xmaisproximo = Math.max(obstaculo.getPosicaoX1(), Math.min(x, obstaculo.getPosicaoX2()));
                int Ymaisproximo = Math.max(obstaculo.getPosicaoY1(), Math.min(y, obstaculo.getPosicaoY2()));
                int Zmaisproximo = Math.max(0, Math.min(altura, obstaculo.getAltura()));
                double distancia = Math.sqrt(Math.pow(Xmaisproximo - x, 2) + Math.pow(Ymaisproximo - y, 2) + Math.pow(Zmaisproximo - altura, 2));
                
                if (distancia <= getRaio()) { // aqui vê se o lixo está dentro da distancia do raio, se estiver o contador adiciona +1 para contagem de lixos
                    System.out.println("Lixo detectado na posição: " + obstaculo.getPosicaoX1() + ", " + obstaculo.getPosicaoY1() + " a uma distância de " + distancia + " metros.");
                    System.out.println("Tipo de lixo: " + obstaculo.getTipo() + ".");
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
