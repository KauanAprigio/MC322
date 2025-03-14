package LAB01;

// considerando x, y >= 0 sempre
// e largura, altura > 0

public class Main {
    public static void main(String[] args){
        Ambiente ambiente = new Ambiente(100, 100, "ambiente1");
        Robo robo1 = new Robo("diego", 0, 0);
        Robo robo2 = new Robo("Kauan", 50, 50);
        robo1.mover(2, 2);
        robo2.mover(400, 50);
        robo1.exibirPosicao();
        robo2.exibirPosicao();
        if (ambiente.dentroDosLimites(robo1.getPosicaoX(), robo1.getPosicaoY())) System.out.printf("Robo %s dentro dos limites do ambiente %s\n", robo1.getNome(), ambiente.getNome());
        else System.out.printf("Robo %s fora dos limites do ambiente %s\n", robo1.getNome(), ambiente.getNome());
        if (ambiente.dentroDosLimites(robo2.getPosicaoX(), robo2.getPosicaoY())) System.out.printf("Robo %s dentro dos limites do ambiente %s\n", robo2.getNome(), ambiente.getNome());
        else System.out.printf("Robo %s fora dos limites do ambiente %s\n", robo2.getNome(), ambiente.getNome());
    }
}

