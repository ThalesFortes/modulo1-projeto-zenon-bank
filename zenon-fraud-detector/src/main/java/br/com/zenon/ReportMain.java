package br.com.zenon;

import br.com.zenon.TransactionReport.Statistics;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportMain {
    void main(String[] args){
        String language = (args.length > 0 ? args[0]: "pt");
        var locale = Locale.of(language);

        var integerFormatter = NumberFormat.getIntegerInstance(locale);
        var currencyFormatter = DecimalFormat.getCurrencyInstance(locale);
        currencyFormatter.setCurrency(Currency.getInstance("USD"));

        var resouceBundle = ResourceBundle.getBundle("report", locale);

        var transactionReport = new TransactionReport();
        Statistics statisticss = transactionReport.generateReport("data/PS_20174392719_1491204439457_log.csv");

        String fmtTotalTransactions = integerFormatter.format(statisticss.totalTransactions());
        String fmtTotalFrauds = integerFormatter.format(statisticss.totalFrauds());
        String fmatTotalAmount = currencyFormatter.format(statisticss.totalAmount());

        String msgTotalTransactions = resouceBundle.getString("label.total.transactions");
        String msgTotalFrauds = resouceBundle.getString("label.total.frauds");
        String msgTotalAmount = resouceBundle.getString("label.total.amount");

        IO.println("""
                %s: %s
                %s: %s
                %s: %s
                """.formatted(
                        msgTotalTransactions,
                        fmtTotalTransactions,
                        msgTotalFrauds,
                        fmtTotalFrauds,
                        msgTotalAmount,
                        fmatTotalAmount
        ));
    }
}
