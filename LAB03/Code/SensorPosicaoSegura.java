package LAB03.Code;

/*
 * Subclasse de Sensor
 * 
 * Atributos:
 *  - ambiente (Ambiente)
 * 
 */

public class SensorPosicaoSegura extends Sensor {
    private Ambiente ambiente;

    //Construtor
    public SensorPosicaoSegura(double raio, Ambiente ambiente) {
        super(raio);
        this.ambiente = ambiente;
    }

    //Metodos
    public boolean posicao_segura(int pos_x, int pos_y){
        // aqui vejo todos os obstáculos e caso tenha algum que esteja na posição, a posicao já não é segura
        for(Obstaculo o : ambiente.getObstaculos()){ 
            if (pos_x >= o.getPosicaoX1() && pos_x <= o.getPosicaoX2()
            && pos_y >= o.getPosicaoY1() && pos_y <= o.getPosicaoY2()){
                return false; // se não é segura retorna false
            } 
        }
        
        return true; // passou por todos os obstáculos e nenhum estava na posição, então é seguro
    }
}
