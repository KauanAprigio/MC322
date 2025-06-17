package LAB05.src.Logger;

import java.io.PrintWriter;

import LAB05.src.Missoes.Missao;

public class Logger {
    private static int instrucaoAtualGLobal = 1;
    private static int instrucaoAtualMissao = 1;
    private static int MissaoAtual = 1;
    private boolean MissaoIniciada = false;
    private boolean acaoIniciada = false;
    private static PrintWriter printer;

    public Logger () {
        try {
            Logger.printer = new PrintWriter("Log.txt");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        
    } 
    // Ativa o modo missão
    public void inicializarMissao(Missao m, String Agente) {
        printer.printf("---- MISSÃO %d ----\n", MissaoAtual);
        printer.printf("Agente %s inicializando missão....\n %s\n", Agente, m.getDetalhes());
        MissaoIniciada = true;
        instrucaoAtualMissao = 1;
    }

    // Indica que uma ação começou (movimento, apagar fogo, adicionar entidade ao ambiente etc...)
    public void inicializarAcao(String acao, String Origem) {
        printer.printf("%d - Ação do tipo %s, inicializada por: %s\n", instrucaoAtualGLobal, acao, Origem); // Marca o início de uma nova ação
        acaoIniciada = true;
    }

    public void logAcao(String txt) {
        if (MissaoIniciada) {
            logTxtMissao(txt);
            return;
        }
        printer.println("   -> " + txt);
    }
    // Durante a missão cada instrução possuí um índice único daquela missão
    public void logTxtMissao(String txt) {
        printer.printf("   %d -> %s\n", instrucaoAtualMissao, txt);
        instrucaoAtualMissao++;
    }

    
    public void finalizarMissao(String Resultado) {
        MissaoIniciada = false;
        printer.printf("---- Fim Missão %d ----\n", MissaoAtual);
        printer.printf("Relatório da missão: %s", Resultado);
        MissaoAtual++;
        instrucaoAtualMissao = 1;
    }

    // Indica que a ação ocorrendo finalizou seja por um erro ou porque chegou ao fim
    public void finalizarAcao(String Resultado) {
        printer.printf("Ação %d encerrada: %s", instrucaoAtualGLobal, Resultado);
        instrucaoAtualGLobal++;
        acaoIniciada = false;
    }

    // Finaliza a missão/ ação e loga o erro
    // Chamar esse método em todo bloco (try-catch)
    public void logErr(Exception e) {
        if (MissaoIniciada) {
            if (acaoIniciada) finalizarAcao("Erro!");
            finalizarMissao(e.getMessage());
        } else if (acaoIniciada) {
            finalizarAcao(e.getMessage());
        } else {
            printer.println("Exceção detectada: " + e.getMessage());
        }
    }

    // Finaliza a missão/ ação e loga o erro
    //Será usado quando não tem o bloco try-catch, mas nos métodos quando você só tem a exception em si
    public void logErr(String msg) {
    if (MissaoIniciada) {
        if (acaoIniciada) finalizarAcao("Erro!");
        finalizarMissao(msg);
    } else if (acaoIniciada) {
        finalizarAcao(msg);
    } else {
        printer.println("Erro detectado: " + msg);
    }
}

    //Após o término do uso. Fechar o logger
    public void fecharLogger() {
        printer.close();
    }
}   


