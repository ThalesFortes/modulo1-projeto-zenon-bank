package br.com.zenon;

import java.util.List;

public class IngestionMain {

    void main(){
        ConnectionFactory.getConnection();
        IO.println("Conexão com BD criada !:");

        var repository = new TransactionSQLRepository();

        var transactionIngestor = new EfficientTransactionIngestor();

        long startTimeSQL = System.nanoTime();
        transactionIngestor.readAsBatch("data/PS_20174392719_1491204439457_log.csv", repository::saveAll);

        IO.println("Iniciando adicao das transacoes no BD...");

        long endTimeList = System.nanoTime();
        IO.println("Tempo de busca - List (ms): " + (endTimeList - startTimeSQL)/1_000_000.0);

    }
}
