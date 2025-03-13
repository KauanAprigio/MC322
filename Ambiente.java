public class Ambiente {

    int largura = 3;
    int altura = 4;
    String terreno[][] ; 

    
    public void Construtor_Ambiente(int larguraX, int alturaY){
        this.largura = larguraX;
        this.altura = alturaY;
        this.terreno = new String[larguraX][alturaY];

        for (int i = 0; i < larguraX; i++){
            for (int j = 0; j < alturaY; j++){
                terreno[i][j] = "_";
            }
        }
        
    }

    public boolean dentroDosLimites(int x, int y){

        if ((0 <= x && x < largura) && (0 <= y && y < altura)){
            return true;
        } else {
            return false;
        }
    }

    public void imprimirAmbiente() {
        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                System.out.print(terreno[i][j] + " ");
            }
            System.out.println();
        }
    }
}
