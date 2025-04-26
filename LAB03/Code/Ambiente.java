package LAB03.Code;
import java.util.ArrayList;

/*
 * Classe obrigatória Ambiente
 * 
 * Atributos:
 * - largura
 * - altura
 * - obstaculos
 * - robos
 * 
 * Métodos:
 * - adicionarRobo(Robo r)
 * - removerRobo(Robo r)
 * - detectarColisoes()
 * - dentroDosLimites(int x, int y, int altitude)
 */
public class Ambiente {
    private int largura;
    private int altura;
    private String nome;
    private int altitudeMaxima;
    private ArrayList<Obstaculo> obstaculos;
    private ArrayList<Robo> robos;
    // variaveis finais (requisito obligatório)
    private final int altitudeMinima = 0;
    private final int origemX = 0;
    private final int origemY = 0;
    


    // Construtor
    public Ambiente(int larguraX, int alturaY, String nome, int altitudeMaxima) {
        this.altitudeMaxima = altitudeMaxima;
        this.nome = nome;
        this.largura = larguraX;
        this.altura = alturaY;
        obstaculos = new ArrayList<Obstaculo>();
        robos = new ArrayList<Robo>();
    }
    // Metodos
    public void adicionarRobo(Robo r) { //adiciona um robo r no arraylist
        // Verifica se o robô está dentro dos limites do ambiente
        // Se o robô estiver fora dos limites, não adiciona e exibe mensagem
        // Se o robô estiver dentro dos limites, adiciona e exibe mensagem
        if (!dentroDosLimites(r.getX(), r.getY(), 0)) {
            System.out.println("Robô fora dos limites do ambiente!");
            System.out.println("Não foi possível adicionar o robô!\n");
            return;
        }
        robos.add(r);
        System.out.println(r.getNome() + " foi adicionado ao ambiente!");
        System.out.println("Posição: (" + r.getX() + ", " + r.getY() + ", " + 0 + ")\n");
    }
    public void removerRobo(Robo r) { //remove um robo r do arraylist
        robos.remove(r);
        System.out.println(r.getNome() + " foi removido do ambiente!\n");
    }
    public void detectarColisoes() { // verifica se houve colisão entre os robos e os obstaculos
        // Se o obstaculo for do tipo FOGO, o robo é destruido a não ser que seja um robo bombeiro
        for (Robo r : robos) {
            for (Obstaculo o : obstaculos) {
                if (r.getX() >= o.getPosicaoX1() && r.getX() <= o.getPosicaoX2() && r.getY() >= o.getPosicaoY1() && r.getY() <= o.getPosicaoY2()) {
                    System.out.println("Colisão detectada entre " + r.getNome() + " e " + o.getTipo() + "\n");
                    if  (o.getTipo() == Obstaculo.TipoObstaculo.FOGO) {
                        if (r instanceof RoboBombeiro) {
                            System.out.println("o robô: "+ r.getNome() + " esta dentro do " + o.getTipo().getNome() + "\n");
                        } else {
                            System.out.println(r.getNome() + " foi destruído pelo fogo\n");
                            removerRobo(r);
                        }
                    } else if (o.getTipo().isLixo()) {
                        System.out.println(r.getNome() + " colidiu com lixo\n");
                    } else {
                        System.out.println(r.getNome() + " esta dentro de " + o.getTipo().getNome() + "\n");
                    }
                }
            }
        }
    }
    public void adicionarObstaculo(Obstaculo o) { //adiciona um obstaculo o no arraylist
        // Verifica se o obstáculo está dentro dos limites do ambiente
        // Se o obstáculo estiver fora dos limites, não adiciona e exibe mensagem
        // Se o obstáculo estiver dentro dos limites, adiciona e exibe mensagem
        if (!dentroDosLimites(o.getPosicaoX1(), o.getPosicaoY1(), o.getAltura()) || !dentroDosLimites(o.getPosicaoX2(), o.getPosicaoY2(), o.getAltura())) {
            System.out.println("Obstáculo fora dos limites do ambiente!");
            System.out.println("Não foi possível adicionar o obstáculo!\n");
            return;
        }
        obstaculos.add(o);
        System.out.println("Obstáculo " + o.getTipo() + " adicionado ao ambiente!");
        System.out.println("Posição inferior esquerda: (" + o.getPosicaoX1() + ", " + o.getPosicaoY1() + ", " + 0 + ")");
        System.out.println("Posição superior direita: (" + o.getPosicaoX2() + ", " + o.getPosicaoY2() + ", " + o.getAltura() + ")\n");
    }
    public void removerObstaculo(Obstaculo o) { //remove um obstaculo o do arraylist
        obstaculos.remove(o);
        System.out.println("Obstáculo " + o.getTipo() + " removido do ambiente!\n");
    }
    public boolean dentroDosLimites(int x, int y, int altitude) { // ve se esta dentro dos limites de x,y e altitude, caso contrário retorna false
        if ((origemX <= x && x <= largura) && (origemY <= y && y <= altura) && (altitudeMinima <= altitude && altitude <= altitudeMaxima)) return true;
        return false;
    }
    //Getters e Setters
    public int getLargura() { return largura; }
    public int getAltura() { return altura; }
    public String getNome() { return nome; }
    public int getAltitudeMaxima() { return altitudeMaxima; }
    public ArrayList<Obstaculo> getObstaculos() { return obstaculos; }
    public ArrayList<Robo> getRobos() { return robos; }

    public void setLargura(int largura) { this.largura = largura; }
    public void setAltura(int altura) { this.altura = altura; }
    public void setNome(String nome) { this.nome = nome; }
    public void setAltitudeMaxima(int altitudeMaxima) { this.altitudeMaxima = altitudeMaxima; }
    public void setObstaculos(ArrayList<Obstaculo> obstaculos) { this.obstaculos = obstaculos; }
    public void setRobos(ArrayList<Robo> robos) { this.robos = robos; }
    
    
}
