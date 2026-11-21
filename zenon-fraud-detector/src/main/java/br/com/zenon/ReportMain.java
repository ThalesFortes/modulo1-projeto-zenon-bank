package br.com.zenon;

import br.com.zenon.TransactionReport.Statistics;

public class ReportMain {
    void main(){
        var transactionReport = new TransactionReport();
        Statistics statisticss = transactionReport.generateReport("data/PS_20174392719_1491204439457_log.csv");
        IO.println("""
                Total de transações: %d
                Total de fraudes: %d
                Valor total: %s
                """.formatted(statisticss.totalTransactions(), statisticss.totalFrauds(), statisticss.totalAmount()));

    }
}
