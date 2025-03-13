public class Ambiente {

    int largura;
    int altura;
    String terreno[][] = new String[largura][altura];

    
    public void Construtor_Ambiente(int larguraX, int alturaY, String ambiente[][]){
        for (int i = 0; i < larguraX; i++){
            for (int j = 0; j < alturaY; j++){
                terreno[larguraX][alturaY] = "_";
            }
        }
        
        this.largura = larguraX;
        this.altura = alturaY;
        this.terreno = ambiente;
    }

    public boolean dentroDosLimites(int x, int y){

        if ((0 <= x && x < largura) && (0 <= y && y < altura)){
            return true;
        } else {
            return false;
        }
    }
}
