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
    ArrayList<Obstaculo> obstaculos;
    ArrayList<Robo> robos;


    // Construtor
    public Ambiente(int larguraX, int alturaY) {
        this.largura = larguraX;
        this.altura = alturaY;
        obstaculos = new ArrayList<Obstaculo>();
        robos = new ArrayList<Robo>();
    }
    // Metodos
    public void adicionarRobo(Robo r) { //adiciona um robo r no arraylist
        robos.add(r);
        System.out.println(r.getNome() + " foi adicionado ao ambiente.");
    }
    public void removerRobo(Robo r) { //remove um robo r do arraylist
        robos.remove(r);
        System.out.println(r.getNome() + " foi removido do ambiente.");
    }
    public void detectarColisoes() { //verifica se o robo colidiu com algum obstaculo
        for (Robo r : robos) {
            for (Obstaculo o : obstaculos) {
                if (r.getX() >= o.getPosicaoX1() && r.getX() <= o.getPosicaoX2() && r.getY() >= o.getPosicaoY1() && r.getY() <= o.getPosicaoY2()) {
                    System.out.println("Colisão detectada entre " + r.getNome() + " e " + o.getTipo());
                }
            }
        }
    }
    public void adicionarObstaculo(Obstaculo o) { //adiciona um obstaculo o no arraylist
        obstaculos.add(o);
        System.out.println("Obstáculo " + o.getTipo() + " adicionado ao ambiente.");
    }
    public void removerObstaculo(Obstaculo o) { //remove um obstaculo o do arraylist
        obstaculos.remove(o);
        System.out.println("Obstáculo " + o.getTipo() + " removido do ambiente.");
    }
    public boolean dentroDosLimites(int x, int y, int altitude) { // ve se esta dentro dos limites de x,y e altitude, caso contrário retorna false
        if ((0 <= x && x < largura) && (0 <= y && y < altura) && (0 <= altitude)) return true;
        return false;
    }
    //Getters e Setters
    public int getLargura() { return largura; }
    public int getAltura() { return altura; }
    public ArrayList<Obstaculo> getObstaculos() { return obstaculos; }
    public void setObstaculos(ArrayList<Obstaculo> obstaculos) { this.obstaculos = obstaculos; }
    public ArrayList<Robo> getRobos() { return robos; }
    public void setRobos(ArrayList<Robo> robos) { this.robos = robos; }
    public void setLargura(int largura) { this.largura = largura; }
    public void setAltura(int altura) { this.altura = altura; }
    
}
