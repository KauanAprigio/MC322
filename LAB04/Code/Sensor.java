package LAB04.Code;

public abstract class Sensor {
    private double raio;

    // Construtor
    public Sensor(double raio) {
        this.raio = raio;
    }
    // Métodos
    public abstract void monitorar(int x, int y, int altura, Ambiente ambiente);

    // Getters e Setters
    public double getRaio() { return raio; }
}
