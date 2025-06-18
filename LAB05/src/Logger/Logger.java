package LAB05.src.Logger;

import java.io.PrintWriter;
import java.util.Stack; 
import LAB05.src.Exceptions.ErrorLoggerException;
import LAB05.src.Missoes.Missao;

/**
 * Classe Logger:
 * <p>
 * Responsável por registrar ações e missões em um arquivo de log.txt.
 * Formata Todas as ações e missões com indentação adequada, permitindo fácil leitura e compreensão do fluxo de execução.
 * </p>
 * <ul>
 * <li><b>inicializarMissao(Missao missao, String agente)</b>: Inicia uma nova missão, registrando detalhes da missão e do agente.</li>
 * <li><b>inicializarAcao(String acao, String origem)</b>: Registra o início de uma ação, atribuindo um ID único.</li>
 * <li><b>logAcao(String texto)</b>: Registra uma mensagem de ação dentro de uma ação ativa.</li>
 * <li><b>finalizarMissao(String resultado)</b>: Finaliza a missão atual, registrando o resultado e o total de ações executadas.</li>
 * <li><b>finalizarAcao(String resultado)</b>: Finaliza a ação atual, registrando o resultado.</li>
 * <li><b>logErr(Exception e)</b>: Registra uma exceção, limpando ações e missões ativas restantes.</li>
 * <li><b>fecharLogger()</b>: Fecha o arquivo de log, garantindo que todos os dados sejam salvos corretamente.</li>
 * </ul>
 * <p>
 * 
 */
public class Logger {
    // Esses contadores agora serão principalmente para atribuir IDs únicos
    private static int contadorIdAcaoGlobal;
    private static int contadorIdAcaoMissao;
    private static int contadorIdMissao;

    // Usa uma pilha para gerenciar os níveis de indentação para as ações.
    // Cada entrada representa uma ação ativa e ajuda a determinar sua indentação.
    private static Stack<Integer> profundidadesAcaoAtivas;
    private static Stack<Integer> idsMissoesAtivas;

    private static PrintWriter impressor;

    public Logger (String nomeArquivo) {
        try {
            Logger.impressor = new PrintWriter(nomeArquivo);
            profundidadesAcaoAtivas = new Stack<>();
            idsMissoesAtivas = new Stack<>();
            contadorIdAcaoGlobal = 0;
            contadorIdAcaoMissao = 0;
            contadorIdMissao = 0;
        } catch (Exception e) {
            System.out.println("Erro na criação do logger: " + e.getMessage());
        }
    }

    // Ativa o modo missão
    public void inicializarMissao(Missao missao, String agente) {
        contadorIdMissao++;
        idsMissoesAtivas.push(contadorIdMissao); // Empilha o ID da missão atual

        imprimirIndentacao(); // Indentação inicial para o bloco da missão
        impressor.printf("------- MISSÃO %d -------\n", contadorIdMissao);
        imprimirIndentacao();
        impressor.printf("Agente %s inicializando missão:\n", agente);
        imprimirIndentacao();
        impressor.println(missao.getDetalhes());
    }

    // Indica que uma ação começou (movimento, apagar fogo, adicionar entidade ao ambiente etc...)
    public void inicializarAcao(String acao, String origem) {
        // Determina o ID baseado se uma missão está ativa ou não
        int idAcaoAtual;
        if (!idsMissoesAtivas.isEmpty()) {
            contadorIdAcaoMissao++;
            idAcaoAtual = contadorIdAcaoMissao;
        } else {
            contadorIdAcaoGlobal++;
            idAcaoAtual = contadorIdAcaoGlobal;
        }
        imprimirIndentacao();
        // Empilha um novo nível de profundidade para esta ação
        profundidadesAcaoAtivas.push(idAcaoAtual);
        impressor.printf("%d - Ação do tipo %s, inicializada por: %s\n", idAcaoAtual, acao, origem);
    }

    public void logAcao(String texto) {
        imprimirIndentacao();
        impressor.println("-> " + texto);
    }

    public void finalizarMissao(String resultado) {
        if (!idsMissoesAtivas.isEmpty()) {
            int idMissaoFinalizada = idsMissoesAtivas.pop(); // Pega o ID da missão que está sendo finalizada

            imprimirIndentacao();
            impressor.printf("------- Fim Missão %d -------\n", idMissaoFinalizada);
            imprimirIndentacao();
            impressor.printf("Relatório da missão: %s\n", resultado);
            imprimirIndentacao();
            impressor.printf("Total de ações executadas: %d\n", contadorIdAcaoMissao);
            
            // Reseta contadores específicos da missão após uma missão ser concluída
            contadorIdAcaoMissao = 0;
        } else{
            // Se idsMissoesAtivas estiver vazia aqui, significa que isso foi chamado sem uma missão ativa
            // Ou foi chamado múltiplas vezes para a mesma missão, o que indica um erro de uso.
            logErr(new ErrorLoggerException("Tentativa de finalizar missão sem missão ativa!")); 
        }
    }

    public void finalizarAcao(String resultado) {
        if (!profundidadesAcaoAtivas.isEmpty()) {
            int idAcaoFinalizada = profundidadesAcaoAtivas.pop(); // Pega o ID da ação que está sendo finalizada
            imprimirIndentacao();
            impressor.printf("-> Ação %d encerrada: %s\n", idAcaoFinalizada, resultado);
        } else {
            // Se profundidadesAcaoAtivas estiver vazia aqui, significa que isso foi chamado sem uma ação ativa
            // ou muitas chamadas a inicializarAcao em comparação com finalizarAcao
            logErr(new ErrorLoggerException("Tentativa de finalizar ação sem ação ativa!"));
        }

    }

    public void logErr(Exception e) {
        // Registra a mensagem de erro imediatamente
        imprimirIndentacao();
        impressor.println("Exceção detectada: " + e.getMessage());

        // Tenta limpar quaisquer ações e missões ativas restantes
        // Isso imprimirá mensagens de "encerrada" para elas.
        while (!profundidadesAcaoAtivas.isEmpty()) {
            finalizarAcao("Erro: " + e.getMessage()); // Passa a mensagem de erro para as ações pendentes
        }
        while (!idsMissoesAtivas.isEmpty()) {
            finalizarMissao("Erro: " + e.getMessage()); // Passa a mensagem de erro para as ações pendentes
        }
    }

    // Método auxiliar para imprimir a indentação correta baseada no tamanho da pilha
    private void imprimirIndentacao() {
        for (int i = 0; i < profundidadesAcaoAtivas.size(); i++) {
            impressor.print("   "); // 3 espaços por nível
        }
    }

    public void fecharLogger() {
        impressor.close();
    }
}