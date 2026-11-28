package br.com.zenon;

import java.math.BigDecimal;
import java.util.List;

public class DBMain {

    void main(){
        ConnectionFactory.getConnection();
        IO.println("Conexão com BD criada !:");

        var repository = new TransactionSQLRepository();

        var transactionIngestor = new TransactionIngestor();

        long startTimeSQL = System.nanoTime();
        List<Transaction> transactions = transactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");
        IO.println(transactions.size());

        IO.println("Iniciando adicao das transacoes no BD...");
        //transactions.forEach(repository::save);
        repository.saveAll(transactions);

        repository.findByOriginName("C1231006815")
                .ifPresentOrElse(IO::println, () -> IO.println("Transacao nao encontrada para: C1231006815"));

        long endTimeList = System.nanoTime();
        IO.println("Tempo de busca - List (ms): " + (endTimeList - startTimeSQL)/1_000_000.0);
    }
}
