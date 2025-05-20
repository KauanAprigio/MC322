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
    char[][] planoXY; // aqui irei usar esse plano para representar o ambiente 2d, no caso o plano xy com z = 0

    
    //Construtor
    Ambiente(int largura, int profundidade, int altura, TipoEntidade[][][] mapa, char[][] planoXY){
        this.largura = largura;
        this.profundidade = profundidade;
        this.altura = altura;
        this.mapa = mapa;
        this.planoXY = planoXY;
        entidades = new ArrayList<Entidade>();
    }


    //Metodos
    public void inicializarMapa(){
        for (int x = 0; x < largura; x++){
            for (int y = 0; y < profundidade; y++){
                planoXY[x][y] = 'v'; // aqui irei fazer a representação do plano xy no z = 0
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

        //vou deixar separado do resto para melhor visualização 
        mapa[e.getX()][e.getY()][e.getZ()] = TipoEntidade.ROBO; // adiciona na representação do ambiente
        planoXY[e.getX()][e.getY()] = r.getRepresentacao(); // adiciona no planoXY, ou seja, representaçaõ 2d do ambiente
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
            
            //Aqui irei colocar a entidade OBJETO na representação 2d e 3d do ambiente
            for (int x = o.getX(); x < o.getPosicaoX2(); x++) {
                for (int y = o.getY(); y < o.getPosicaoY2(); y++) {
                    planoXY[x][y] = o.getRepresentacao(); // Representação no plano XY (z=0)
                    
                    if (o.getZ() > 0) { // Preencherá as camadas no eixo z se o objeto tiver altura
                        for (int z = 0; z < o.getZ(); z++) {
                            mapa[x][y][z] = TipoEntidade.OBSTACULO;
                        }
                    } else { //Se nao tiver altura preenche somente o z = 0 para deixar o código mais eficiente
                        mapa[x][y][0] = TipoEntidade.OBSTACULO;
                    }
                }
            }


            entidades.add(e);
            System.out.println("A entidade OBSTACULO, de tipo" + o.getTipoObstaculo() + ", foi adicionado ao ambiente.");
            System.out.println("Posição inferior esquerda: (" + o.getX() + ", " + o.getY() + ", " + 0 + ")");
            System.out.println("Posição superior direita: (" + o.getPosicaoX2() + ", " + o.getPosicaoY2() + ", " + o.getZ() + ")\n");
        }
    }

    public void removerEntidade(Entidade e){ //pode desenvolver uma exception se quiser, no caso se eu for remover algo que nao existe
        entidades.remove(e);
        System.out.println("A Entidade " + e.getTipo() + " foi removida com sucesso.");
    }

    public boolean dentroDosLimites(int x, int y, int altitude) { // ve se esta dentro dos limites de x,y e altitude, caso contrário retorna false
        if ((origemX <= x && x <= largura) && (origemY <= y && y <= altura) && (altitudeMinima <= altitude && altitude <= altura)) return true;
        return false;
    }

    public void estaOcupado(int x, int y, int z){
        // a principio acho que seria uma função para ver se tem algo na posicao, da pra fazer como se fosse a ideia do sensor de posicao segura 
    }

    public void moverEntidade(Entidade e, int novoX, int novoY, int novoZ ){
        // acho que seria algo para mover obstaculos...
    }

    public void executarSensores(){
        
    }

    public void verificarColisoes(){
        //pode ser o sensor de proximidade? ou tem q implementar isso?
    }

    public void visualizarAmbiente(){
        for (int x = 0; x < largura; x++){
            for (int y = 0; y < profundidade; y++){
                System.out.println(planoXY[x][y] + " ");// aqui irei printar os caracteres dando um espaço entre eles
            }
        }
        System.err.println(); // uma quebra de linha para deixar dividido a matriz do resto na main
    }


    //Geters e Setters
    public ArrayList<Entidade> getEntidades() { return entidades; }
}
