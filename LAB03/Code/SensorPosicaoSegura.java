package LAB03.Code;

/*
 * Subclasse de Sensor
 * 
 * Atributos:
 *  - Ambiente ambiente
 * 
 * Métodos:
 * posicao_segura(int pos_x, int pos_y, int x_atual, int y_atual)
 */

public class SensorPosicaoSegura extends Sensor {
    private Ambiente ambiente;

    //Construtor
    public SensorPosicaoSegura(double raio, Ambiente ambiente) {
        super(raio);
        this.ambiente = ambiente;
    }

    //Metodos
    public boolean posicao_segura(int pos_x, int pos_y, int x_atual, int y_atual) {
        // verifica se a posição está dentro do ambiente e dentro do raio
        if (pos_x < 0 || pos_x > ambiente.getLargura() || pos_y < 0 || pos_y > ambiente.getAltura()){
            System.out.println("Posição fora do ambiente!");
            return false; // se não está dentro do ambiente retorna false
        }
        if (Math.sqrt(Math.pow((pos_x - x_atual), 2) + Math.pow((pos_y - y_atual), 2)) > getRaio()){
            System.out.println("Posição fora do raio do sensor, não podendo saber a segurança da posição!");
            return false; // se não está dentro do raio retorna false
        }
        // aqui vejo todos os obstáculos e caso tenha algum que esteja na posição, a posicao já não é segura
        for(Obstaculo o : ambiente.getObstaculos()){ 
            if (pos_x >= o.getPosicaoX1() && pos_x <= o.getPosicaoX2()
            && pos_y >= o.getPosicaoY1() && pos_y <= o.getPosicaoY2()){
                if (o.getTipo().isFogo()) {
                    System.out.println("Posição não segura, Fogo detectado! Chame um Robô bombeiro!");
                } else if (o.getTipo().isAprimora()){
                    System.out.println("Posição é segura, pois foi detectado um local de aprimoramento.");
                    return true;
                } else {
                    System.out.println("Posição não é segura, pois foi detectado um obstáculo!");
                }
                
                return false; // se não é segura retorna false
            } 
        }
        
        return true; // passou por todos os obstáculos e nenhum estava na posição, então é seguro
    }
}
