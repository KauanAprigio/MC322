package LAB04;


//so para lembrar que aqui primeiro fazer a entidade depois fazer o enum TipoEntidade
public interface Entidade {
    int getX();
    int getY();
    int getZ();
    TipoEntidade getTipo();
    String getDescricao();
    char getRepresentacao();


    public enum TipoEntidade{
    // o enum não será static, por isso nao passei o parametro, só deixei assim para não dar erro, depois irei implementar

    }
}


