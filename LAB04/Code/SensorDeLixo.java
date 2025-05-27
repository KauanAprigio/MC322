package LAB04.Code;

import LAB04.Code.AbstractClasses.Sensor;
import LAB04.Code.Interfaces.Entidade;
import LAB04.Code.Interfaces.Entidade.TipoEntidade;

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
        for (Entidade e : ambiente.getEntidades()) { // percorre todos os obstáculos
            if (e.getTipo() == TipoEntidade.OBSTACULO){
                Obstaculo o = (Obstaculo) e;
                if (o.getTipoObstaculo().isLixo()) { // condicional caso o obstaculo seja um lixo
                    // Lógica para calcular o ponto mais próximo dentro dos limites do obstáculo e calcular a distância deste ponto para o robo.
                    int Xmaisproximo = Math.max(o.getX(), Math.min(x, o.getPosicaoX2()));
                    int Ymaisproximo = Math.max(o.getY(), Math.min(y, o.getPosicaoY2()));
                    int Zmaisproximo = Math.max(0, Math.min(altura, o.getZ()));
                    double distancia = Math.sqrt(Math.pow(Xmaisproximo - x, 2) + Math.pow(Ymaisproximo - y, 2) + Math.pow(Zmaisproximo - altura, 2));
                    
                    if (distancia <= getRaio()) { // aqui vê se o lixo está dentro da distancia do raio, se estiver o contador adiciona +1 para contagem de lixos
                        System.out.println("Lixo detectado na posição: " + o.getX() + ", " + o.getY() + " a uma distância de " + distancia + " metros.");
                        System.out.println("Tipo de lixo: " + o.getTipo() + ".");
                        N_lixos++;
                    }
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
