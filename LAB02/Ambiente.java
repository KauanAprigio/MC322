package LAB02;

import java.util.ArrayList;

public class Ambiente {
    ArrayList<Robo> robos;
    private int largura;
    private int altura;
    private String nome;

    public Ambiente(int larguraX, int alturaY, String nome) {
        this.largura = larguraX;
        this.altura = alturaY;
        this.nome = nome;
        robos = new ArrayList<Robo>();
    }
    public void adicionarRobo(Robo r) {
        robos.add(r);
        System.out.println(r.getNome() + " foi adicionado ao ambiente " + getNome());
    }
    public boolean dentroDosLimites(int x, int y, int altitude, int altitudeMaxima) {
        if ((0 <= x && x < largura) && (0 <= y && y < altura) && (0 <= altitude && altitude <= altitudeMaxima)) return true;
        return false;
    }
    //getters e setters
    public int getLargura() { return largura; }
    public int getAltura() { return altura; }
    public String getNome() { return nome; }
    public void setLargura(int largura) { this.largura = largura; }
    public void setAltura(int altura) { this.altura = altura; }
    public void setNome(String nome) { this.nome = nome; }
    public ArrayList<Robo> getRobos() { return robos; }
    public void setRobos(ArrayList<Robo> robos) { this.robos = robos; }
}