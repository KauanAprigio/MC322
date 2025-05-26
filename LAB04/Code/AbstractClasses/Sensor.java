package LAB04.Code.AbstractClasses;

import LAB04.Code.Ambiente;

public abstract class Sensor {
    private double raio;

    // Construtor
    public Sensor(double raio) {
        this.raio = raio;
    }
    // Métodos
    /**
     * Método monitorar:
     * Deve ser implementado por subclasses para monitorar o ambiente.
     * 
     * 
     * @param x Posição X do sensor
     * @param y Posição Y do sensor
     * @param altura Altitude (pos Z) do sensor
     * @param ambiente Ambiente a ser monitorado
     */
    public abstract void monitorar(int x, int y, int altura, Ambiente ambiente);

    // Getters e Setters
    public double getRaio() { return raio; }
}
