package LAB05.src.Logger;

import java.io.PrintWriter;

import LAB05.src.Missoes.Missao;

public class Logger {
    private static int acaoAtualGLobal = 0;
    private static int acaoProxGLobal = 1;

    private static int acaoAtualMissao = 0;
    private static int acaoProxMissao = 1;

    private static int MissaoAtual = 0;
    private static int MissaoProx = 1;

    private static int MissoesIniciadas = 0;
    private static int acoesIniciada = 0;
    
    private static PrintWriter printer;

    public Logger (String filename) {
        try {
            Logger.printer = new PrintWriter(filename);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        
    } 
    // Ativa o modo missão
    public void inicializarMissao(Missao m, String Agente) {
        MissaoAtual = MissaoProx;
        printer.printf("------- MISSÃO %d -------\n", MissaoAtual);
        printer.printf("Agente %s inicializando missão:\n%s\n", Agente, m.getDetalhes());
        MissaoProx = MissaoAtual+1;
        MissoesIniciadas++;
    }

    // Indica que uma ação começou (movimento, apagar fogo, adicionar entidade ao ambiente etc...)
    public void inicializarAcao(String acao, String Origem) {
        if (MissoesIniciadas != 0){ // Missao ativa {
            acaoAtualMissao = acaoProxMissao;
            printer.printf("%d - Ação do tipo %s, inicializada por: %s\n", acaoAtualMissao, acao, Origem); // Marca o início de uma nova ação
            acaoProxMissao = acaoAtualMissao + 1;
            acoesIniciada++;
        }
        else {
            acaoAtualGLobal = acaoProxGLobal;
            printer.printf("%d - Ação do tipo %s, inicializada por: %s\n", acaoAtualGLobal, acao, Origem); // Marca o início de uma nova ação
            acaoProxGLobal = acaoAtualGLobal + 1;
            acoesIniciada++;
        }
    }

    public void logAcao(String txt) {
        printer.println("   -> " + txt);
    }
    public void finalizarMissao(String Resultado) {
        printer.printf("------- Fim Missão %d -------\n", MissaoAtual);
        printer.printf("Relatório da missão: %s\n", Resultado);
        printer.printf("Total de ações executadas: %d\n", acaoAtualMissao);
        MissaoAtual--;
        MissoesIniciadas--;
    }

    // Indica que a ação ocorrendo finalizou seja por um erro ou porque chegou ao fim
    public void finalizarAcao(String Resultado) {
        if (MissoesIniciadas != 0) {  // Missao ativa
            printer.printf("    -> Ação %d encerrada: %s", acaoAtualMissao, Resultado);
            acaoAtualMissao--;
            acoesIniciada--;
        } else {
            printer.printf("Ação %d encerrada: %s", acaoAtualGLobal, Resultado);
            acaoAtualGLobal--;
            acoesIniciada--;
        }
    }

    // Finaliza a missão/ ação e loga o erro
    // Chamar esse método em todo bloco (try-catch)
    public void logErr(Exception e) {
        if (MissoesIniciadas == 0 && acoesIniciada == 0) {
            printer.println("Exceção detectada: " + e.getMessage());
            return;
        }
        while (acoesIniciada != 0) {
            finalizarAcao(e.getMessage());
        }
        while (MissoesIniciadas != 0) {
            finalizarMissao(e.getMessage());
        } 
    }

    //Após o término do uso. Fechar o logger
    public void fecharLogger() {
        printer.close();
    }
}   


