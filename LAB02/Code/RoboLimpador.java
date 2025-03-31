package LAB02.Code;


// Subclasse de RoboTerrestre
class RoboLimpador extends RoboTerrestre {
    // Atributos adicionais
    private int n_pas; // numero de pás limpadoras do robo
    private boolean power = false; // estado que ira dizer se esta ligado = true ou desligado = false
    private int tipo_limpeza = 0; // 0 = limpeza leve, 1 = limpeza pesada, 2 = limpeza muito pesada

    // Construtor
    public RoboLimpador(String nome, int posicaoX, int posicaoY, int velocidadeMaxima, boolean power, int n_pas) {
        super(nome, posicaoX, posicaoY, velocidadeMaxima);
        this.power = power;
        this.n_pas = n_pas;
    }

    // Metodos
    public boolean ligar() {
        if (!power) {
            power = true;
            System.out.println(getNome() + " foi ligado com sucesso!");
        } else {
            System.out.println(getNome() + " já está ligado.");
        }
        return power;
    }

    public boolean desligar() {
        if (power) {
            power = false;
            System.out.println(getNome() + " foi desligado.");
        } else {
            System.out.println(getNome() + " já está desligado.");
        }
        return power;
    }

    void definir_tipo_limpeza(int tipo){
        if (!power) {
            System.out.println(getNome() + " está desligado. Não é possível definir o tipo de limpeza.");
            return;
        }
        // Verifica se o tipo de limpeza é válido
        // 0 = limpeza leve, 1 = limpeza pesada, 2 = limpeza muito pesada
        if (tipo >= 0 && tipo <= 2) {
            tipo_limpeza = tipo;
            switch (tipo_limpeza) {
                case 0:
                    System.out.println(getNome() + " está configurado para limpeza leve.");
                    break;
                case 1:
                    System.out.println(getNome() + " está configurado para limpeza pesada.");
                    break;
                case 2:
                    System.out.println(getNome() + " está configurado para limpeza muito pesada.");
                    break;
            }
        } else {
            System.out.println("Tipo de limpeza inválido. Escolha entre 0 (leve), 1 (pesada) ou 2 (muito pesada).");
        }
    }

    public void mover(int deltaX, int deltaY) {
        if (power) {
            super.mover(deltaX, deltaY);
        } else {
            System.out.println(getNome() + " não pode se mover enquanto desligado.");
        }
    }

    //Getters e Setters
    public boolean getPower() { return power; }
    public int getPas() { return n_pas; }
    public int getTipoLimpeza() { return tipo_limpeza; }
    public void setPas(int n_pas) { this.n_pas = n_pas; }
}

