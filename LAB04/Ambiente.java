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

    public void adicionarEntidade(Entidade e){//pode desenvolver uma exception se quiser ou ate mais se a entidade nao for nenhuma dessas
        if (e.getTipo() == TipoEntidade.ROBO){
            Robo r = (Robo) e; // aqui usarei o casting para ter acesso ao ID do robo
            if (!dentroDosLimites(r.getX(), r.getY(), 0)) { 
                System.out.println("Não foi possível adicionar a entidade do tipo ROBO, pois ela está fora dos limites do ambiente!\n");
                return; // aqui acaba o método caso seja fora dos limites
            }
        // aqui significa que está nos limites, logo ira adicionar
        entidades.add(e);
        System.out.println("A entidade ROBO, de ID" + r.getId() + ", foi adicionado ao ambiente.");
        System.out.println("Posição: (" + r.getX() + ", " + r.getY() + ", " + 0 + ")\n");
        } else if (e.getTipo() == TipoEntidade.OBSTACULO){
            Obstaculo o = (Obstaculo) e; // aqui usarei o casting para ter acesso aos metodos getPosx2 e y2
            if (!dentroDosLimites(o.getX(), o.getY(), o.getZ()) || !dentroDosLimites(o.getPosicaoX2(), o.getPosicaoY2(), o.getZ())) {
                System.out.println("Não foi possível adicionar a entidade do tipo OBSTACULO, pois ela está fora dos limites do ambiente!\n");
                //PODEMOS USAR RECURSAO PARA CHAMAR A FUNÇÃO ATÉ DAR UMA ENTIDADE QUE ESTEJA NOS LIMITES
                return; // aqui acaba o método caso seja fora dos limites
            }
            entidades.add(e);
            System.out.println("A entidade OBSTACULO, de tipo" + o.getTipoObstaculo() + ", foi adicionado ao ambiente.");
            System.out.println("Posição inferior esquerda: (" + o.getX() + ", " + o.getY() + ", " + 0 + ")");
            System.out.println("Posição superior direita: (" + o.getPosicaoX2() + ", " + o.getPosicaoY2() + ", " + o.getZ() + ")\n");
        }
    }

    public void removerEntidade(Entidade e){ //pode desenvolver uma exception se quiser
        
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
