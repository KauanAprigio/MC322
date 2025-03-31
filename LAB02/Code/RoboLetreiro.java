package LAB02.Code;
// SubClasse de RoboAereo
// Robo que mostra uma mensagem em uma dada altura em seu visor para servir como placa ou letreiro
public class RoboLetreiro extends RoboAereo {
    //Atributos adicionais
    private int max_caracteres;
    private String visor; // a mensagem que irá aparecer no letreiro


    // Construtor
    public RoboLetreiro(String nome, int posicaoX, int posicaoY, int altitudeMaxima, int max_caracteres){
        super(nome, posicaoX, posicaoY, altitudeMaxima);
        this.max_caracteres = max_caracteres;
    }

    // Metodos
    public void escrever_visor(String texto, int altura){
        if ((texto.length() <= max_caracteres) && (altura <= getAltitudeMaxima())){ // ve se pode escrever a mensagem desejada + se nao fica acima da alturaMaxima do robo
            int diff_altura = altura - getAltitude(); // diferença entre a altura do robo e a altura desejada
            if (diff_altura < 0){ // se o robo estiver acima da altura desejada
                descer(-diff_altura); // desce a altura desejada
            } else if (diff_altura > 0){ // se o robo estiver abaixo da altura desejada
                subir(diff_altura); // sobe a altura desejada
            }
            visor = texto;
            System.out.println(getNome() + " tem no seu visor a seguinte mensagem: " + visor);
        } else if (altura > getAltitude()) {
            int altura_excendente = altura - getAltitudeMaxima();
            System.out.println(getNome() + " não pode estar acima da altura máxima de " + getAltitudeMaxima() + " metros, excedendo em " + altura_excendente + " metros");
        } else {
            int caracteres_excedentes = texto.length() - max_caracteres;
            System.out.println(getNome() + " não pode escrever mais de " + max_caracteres + " caracteres, excedendo em " + caracteres_excedentes + " caracteres");
        }
    }

    public void limpar_visor() {
        visor = ""; // deixa o visor vazio, ou seja, lenght = 0
        System.out.println(getNome() + " teve seu visor limpo!");
    }

    public void aumentarVisor(int tamanho_adicional) {// caso precise aumentar o tamanho do robo/letreiro
        if (getAltitude() == 0){ // tem que estar no chao para ser atualizado o tamanho do visor, ou seja, quantos caracteres ele suporta
            max_caracteres += tamanho_adicional; 
            System.out.println(getNome() + " teve seu visor aumentado, agora suporta " + max_caracteres + " caracteres");
        } else {
            System.out.println(getNome() + " deve estar no chão para ter seu visor aumentado");
        }
    }
    // Getters e Setters
    public String getVisor() { return visor; }
    public int getMaximoCaracteres() { return max_caracteres; }
    public String getTexto() { return visor; }
}
