package LAB04;

import java.util.ArrayList;
import LAB04.Entidade.TipoEntidade;

public class Ambiente {
    //Atributos final
    private final int altitudeMinima = 0;
    private final int origemX = 0;
    private final int origemY = 0;

    //Atributos normais / para o construtor
    int largura; // eixo X
    int profundidade; // eixo Y
    int altura; // eixo Z
    ArrayList<Entidade> entidades;
    TipoEntidade[][][] mapa;

    
    //Construtor
    Ambiente(int largura, int profundidade, int altura, TipoEntidade[][][] mapa){
        this.largura = largura;
        this.profundidade = profundidade;
        this.altura = altura;
        this.mapa = mapa;
        entidades = new ArrayList<Entidade>();
    }


    //Metodos
    public void inicializarMapa(){
        for (int x = 0; x < largura; x++){
            for (int y = 0; y < profundidade; y++){
                for (int z = 0; z < altura; z++){
                    mapa[x][y][z] = TipoEntidade.VAZIO;
                }
            }
        }
    }

    public void adicionarEntidade(Entidade e){
        //desenvolver tmb, aqui tmb pode ter exception
    }

    public void removerEntidade(Entidade e){
        // desenvolver... aqui pode ter exception
    }

    public boolean dentroDosLimites(int x, int y, int altitude) { // ve se esta dentro dos limites de x,y e altitude, caso contrário retorna false
        if ((origemX <= x && x <= largura) && (origemY <= y && y <= altura) && (altitudeMinima <= altitude && altitude <= altura)) return true;
        return false;
    }

    void estaOcupado(int x, int y, int z){
        // a principio acho que seria uma função para ver se tem algo na posicao, da pra fazer como se fosse a ideia do sensor de posicao segura 
    }

    void moverEntidade(Entidade e, int novoX, int novoY, int novoZ ){
        // acho que seria algo para mover obstaculos...
    }

    void executarSensores(){
        
    }

    void verificarColisoes(){
        //pode ser o sensor de proximidade? ou tem q implementar isso?
    }

    void visualizarAmbiente(){
        // imprime o ambiente em 2d, somente eixo (X,Y)
    }


    //Geters e Setters
    public ArrayList<Entidade> getEntidades() { return entidades; }
}
