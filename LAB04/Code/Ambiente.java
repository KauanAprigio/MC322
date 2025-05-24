package LAB04.Code;
import java.util.ArrayList;

import LAB04.Code.Entidade.TipoEntidade;
import LAB04.Code.Exceptions.EntidadeNaoEncontradaException;
import LAB04.Code.Exceptions.ForaDosLimitesException;
import LAB04.Code.Exceptions.LocalOcupadoException;

// VALE RESSALTAR QUE SE EU FOR USAR O VERIFICAR COLISOES PARA BOTAR UM OBSTACULO EM UM LUGAR QUE NÃO TENHA OUTRO DEVO REVER O CODIGO

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
                planoXY[x][y] = 'v'; // aqui irei fazer a representação do plano xy no z = 0. O 'v' é a representação para o vazio
                for (int z = 0; z < altura; z++){
                    mapa[x][y][z] = TipoEntidade.VAZIO;
                }
            }
        }
    }
    public void adicionarEntidade(Entidade e, boolean printar) throws ForaDosLimitesException, LocalOcupadoException{
        if (e.getTipo() == TipoEntidade.ROBO){
            Robo r = (Robo) e; // aqui usarei o casting para ter acesso ao ID do robo
            if (!dentroDosLimites(r.getX(), r.getY(), 0)) throw new ForaDosLimitesException("O local do ROBO "+ r.getId() +" está fora dos limites do ambiente!\n");
            if (estaOcupado(r.getX(), r.getY(), r.getZ())) throw new LocalOcupadoException("O local do ROBO " + r.getId() +" está ocupado!\n");
            // aqui significa que está nos limites e o local esta desocupado, logo ira adicionar e o local esta desocupado.
        
            //vou deixar separado do resto para melhor visualização 
            mapa[e.getX()][e.getY()][e.getZ()] = TipoEntidade.ROBO; // adiciona na representação do ambiente
            planoXY[e.getX()][e.getY()] = r.getRepresentacao(); // adiciona no planoXY, ou seja, representaçaõ 2d do ambiente
            entidades.add(e);
            if (printar) {
                System.out.println("A entidade ROBO, de ID" + r.getId() + ", foi adicionado ao ambiente.");
                System.out.println("Posição: (" + r.getX() + ", " + r.getY() + ", " + 0 + ")\n");
            }
        
        } else if (e.getTipo() == TipoEntidade.OBSTACULO){
            Obstaculo o = (Obstaculo) e; // aqui usarei o casting para ter acesso aos metodos getPosx2 e y2
            if (!dentroDosLimites(o.getX(), o.getY(), o.getZ()) || !dentroDosLimites(o.getPosicaoX2(), o.getPosicaoY2(), o.getZ())) 
                throw new ForaDosLimitesException("A regiao ocupada pelo OBSTACULO do tipo "+ o.getTipoObstaculo() +" está fora dos limites do ambiente!\n");
            
            verificarColisoes(o);
            //Aqui irei colocar a entidade OBJETO na representação 2d e 3d do ambiente
            for (int x = o.getX(); x < o.getPosicaoX2(); x++) {
                for (int y = o.getY(); y < o.getPosicaoY2(); y++) {
                    planoXY[x][y] = o.getRepresentacao(); // Representação no plano XY (z=0)
                    
                    if (o.getZ() > altitudeMinima) { // Preencherá as camadas no eixo z se o objeto tiver altura
                        for (int z = 0; z < o.getZ(); z++) {
                            mapa[x][y][z] = TipoEntidade.OBSTACULO;
                        }
                    } else { //Se nao tiver altura preenche somente o z = 0 para deixar o código mais eficiente
                        mapa[x][y][altitudeMinima] = TipoEntidade.OBSTACULO;
                    }
                }
            }

            entidades.add(e);
            if (printar) {
                System.out.println("A entidade OBSTACULO, de tipo" + o.getTipoObstaculo() + ", foi adicionado ao ambiente.");
                System.out.println("Posição inferior esquerda: (" + o.getX() + ", " + o.getY() + ", " + 0 + ")");
                System.out.println("Posição superior direita: (" + o.getPosicaoX2() + ", " + o.getPosicaoY2() + ", " + o.getZ() + ")\n");
            }
        }
    }

    public void removerEntidade(Entidade e, boolean printar) throws EntidadeNaoEncontradaException{ //pode desenvolver uma exception se quiser, no caso se eu for remover algo que nao existe
        if (e.getTipo() == TipoEntidade.ROBO){
            Robo r = (Robo) e; // casting para facilitar as coisas
            if (mapa[r.getX()][r.getY()][r.getZ()] == TipoEntidade.VAZIO) 
                throw new EntidadeNaoEncontradaException("Entidade não está no ambiente e não pode ser removida!\n");
            mapa[r.getX()][r.getY()][r.getZ()] = TipoEntidade.VAZIO; // deixa como vazio o espaço da entidade
            planoXY[r.getX()][r.getY()] = 'v'; // deixa o 'v' de vazio na reprentacao do planoXY, ou seja, representaçaõ 2d do ambiente
        
        } else if (e.getTipo() == TipoEntidade.OBSTACULO){
            Obstaculo o = (Obstaculo) e; // casting para facilitar tambem 
            if (mapa[o.getX()][o.getY()][o.getZ()] == TipoEntidade.VAZIO) 
                throw new EntidadeNaoEncontradaException("Entidade não está no ambiente e não pode ser removida!\n");
            for (int x = o.getX(); x < o.getPosicaoX2(); x++) {
                for (int y = o.getY(); y < o.getPosicaoY2(); y++) {
                    planoXY[x][y] = 'v'; // Deixa como vazio na representação no plano XY (z=0) ambiente 2d
                    
                    if (o.getZ() > altitudeMinima) { // Preencherá com o vazio as camadas no eixo z se o objeto tiver altura
                        for (int z = 0; z < o.getZ(); z++) {
                            mapa[x][y][z] = TipoEntidade.VAZIO;
                        }
                    } else { //Se nao tiver altura preenche com vazio somente o z = 0 para deixar o código mais eficiente
                        mapa[x][y][altitudeMinima] = TipoEntidade.VAZIO;
                    }
                }
            }
        }
        entidades.remove(e);
        if (printar)
            System.out.println("A Entidade do tipo: " + e.getTipo() + " foi removida com sucesso.");
    }

    public boolean dentroDosLimites(int x, int y, int altitude) { // ve se esta dentro dos limites de x,y e altitude, caso contrário retorna false
        if ((origemX <= x && x <= largura) && (origemY <= y && y <= altura) && (altitudeMinima <= altitude && altitude <= altura)) return true;
        return false;
    }

    public boolean estaOcupado(int x, int y, int z){ 
        return mapa[x][y][z] != TipoEntidade.VAZIO;
    }

    public void moverEntidade(Entidade e, int novoX, int novoY, int novoZ ){ // aqui eu nao sei como faria para mover o z, porque um predio por exemplo na pode começar sem ser do 0 + PODE TER UM EXCEPTION SE A ENTIDADE NAO EXISTIR
        try{
            removerEntidade(e, false); // aqui reutilizarei o metodo para remover a entidade e colocar espaços vazios
        } catch (EntidadeNaoEncontradaException exc) {
            System.out.println("Entidade não está no ambiente e não pode ser movida!\n");
        }
        //AQUI BASICAMENTE IREI MOVER A ENTIDADE DA SEGUINTE MANEIRA IREI ADICIONAR ELA EM UM LUGAR E REMOVER A ENTIDADE DE AGORA
        if (e.getTipo() == TipoEntidade.OBSTACULO){
            Obstaculo obstaculo = (Obstaculo) e; // casting para refazer as coordenadas do obstaculo

            // atualizando as coordenadas do obstaculo
            obstaculo.setPosX(novoX);
            obstaculo.setPosY(novoY);
            obstaculo.setPosX2();
            obstaculo.setPosY2();
            try {
                adicionarEntidade(obstaculo, false); // reutilizarei o adicionar para mover a entidade com as novas coordenadas
                System.out.println("A entidade OBSTACULO, de tipo" + obstaculo.getTipoObstaculo() + ", foi movida para a posição: " + "(" + obstaculo.getPosicaoX2() + ", " + obstaculo.getPosicaoY2() + ", " + 0 + ")");
                System.out.println("Posição superior direita: (" + obstaculo.getPosicaoX2() + ", " + obstaculo.getPosicaoY2() + ", " + obstaculo.getZ() + ")\n");
            } catch (ForaDosLimitesException | LocalOcupadoException exc){
                System.out.println("erro: " + exc);
                System.out.println("Obstáculo não foi movido!\n");
                return;
            }
        }
    }

    public void executarSensores(){ // AQUI NAO VEJO MUITO USO PARA ISSO 
       
    }

    public void verificarColisoes(Obstaculo o) throws LocalOcupadoException{ // PODE SER USADO PARA VERIFICAR SE ONDE VOCÊ ESTÁ COLOCANDO UM OBJETO SE TEM UMA COISA QUE IMPEDE ISSO
        for (int x = o.getX(); x < o.getPosicaoX2(); x++) {
            for (int y = o.getY(); y < o.getPosicaoY2(); y++) {
                for (int z = altitudeMinima; z < o.getZ(); z++) {
                    if (estaOcupado(x, y, z)){
                        throw new LocalOcupadoException("A região ocupada pelo obstáculo " + o.getTipoObstaculo() + " está ocupada\n");
                    }
                }
            }
        }
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
