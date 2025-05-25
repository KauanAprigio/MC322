package LAB04.Code;

import LAB04.Code.Entidade.TipoEntidade;

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
        for (Entidade e : ambiente.getEntidades()) {
            if (e.getTipo() == TipoEntidade.OBSTACULO){
                Obstaculo o = (Obstaculo) e;
                if (o.getTipoObstaculo().isFogo()) {
                    int Xmaisproximo = Math.max(o.getX(), Math.min(x, o.getPosicaoX2()));
                    int Ymaisproximo = Math.max(o.getY(), Math.min(y, o.getPosicaoY2()));
                    int Zmaisproximo = Math.max(0, Math.min(altura, o.getAlturinha()));
                    double distancia = Math.sqrt(Math.pow(Xmaisproximo - x, 2) + Math.pow(Ymaisproximo - y, 2) + Math.pow(Zmaisproximo - altura, 2));
    
                    if (distancia <= getRaio()) {
                        System.out.println("Fogo detectado na posição: (" + o.getX() + ", " + o.getY() + ") a uma distância de " + distancia + " metros.");
                        System.out.println("Tipo de fogo: " + e.getTipo() + ".");
                        N_fogos++;
                    }
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
