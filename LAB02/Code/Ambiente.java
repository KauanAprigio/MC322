package LAB02.Code;

import java.util.ArrayList;

/*
 * Classe obrigatória Ambiente
 * 
 * Atributos:
 * - largura
 * - altura
 * - altitudeMaxima
 * - nome
 * 
 * Métodos:
 * - adicionarRobo(Robo r)
 * - dentroDosLimites(int x, int y, int altitude)
 */
public class Ambiente {
    //Atributos da SuperClasse
    ArrayList<Robo> robos;
    private int largura;
    private int altura;
    private int altitudeMaxima;
    private String nome;


    // Construtor
    public Ambiente(int larguraX, int alturaY, String nome, int altitudeMaxima) {
        this.altitudeMaxima = altitudeMaxima;
        this.largura = larguraX;
        this.altura = alturaY;
        this.nome = nome;
        robos = new ArrayList<Robo>();
    }


    // Metodos
    public void adicionarRobo(Robo r) { //adiciona um robo r no arraylist
        robos.add(r);
        System.out.println(r.getNome() + " foi adicionado ao ambiente " + getNome());
    }

    public boolean dentroDosLimites(int x, int y, int altitude) { // ve se esta dentro dos limites de x,y e altitude, caso contrário retorna false
        if ((0 <= x && x < largura) && (0 <= y && y < altura) && (0 <= altitude && altitude <= altitudeMaxima)) return true;
        return false;
    }


    //Getters e Setters
    public int getLargura() { return largura; }
    public int getAltura() { return altura; }
    public String getNome() { return nome; }
    public int getAltitudeMaxima() { return altitudeMaxima; }
    public void setAltitudeMaxima(int altitudeMaxima) { this.altitudeMaxima = altitudeMaxima; }
    public void setLargura(int largura) { this.largura = largura; }
    public void setAltura(int altura) { this.altura = altura; }
    public void setNome(String nome) { this.nome = nome; }
    public ArrayList<Robo> getRobos() { return robos; }
    public void setRobos(ArrayList<Robo> robos) { this.robos = robos; }
}