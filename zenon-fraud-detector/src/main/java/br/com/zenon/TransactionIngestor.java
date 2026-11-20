package br.com.zenon;

import javax.xml.validation.Validator;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TransactionIngestor {

    public static final int FRAUD_LIMIT = 100_000;

    public List<Transaction> read(String filename){
        Path path = Path.of(filename);
        try {
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .skip(1)
                    .limit(FRAUD_LIMIT)
                    .map(this::parseTransaction)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } catch (Exception ex){
            throw new RuntimeException("Erro ao ler arquivo " + filename, ex);
        }
    }

    public List<Transaction> readOldSchool(String fileName){

        ArrayList<Transaction> transactions = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(fileName);
             Scanner scanner = new Scanner(fis)){

            int lineCount = 0;

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                lineCount ++;

                if (lineCount == 1){
                    continue;
                }

                if (lineCount > 1001){
                    break;

                }
            }
        } catch (Exception ex){
            throw new RuntimeException("Erro ao ler o arquivo: " + fileName, ex);
        }

        return transactions;
    }

    private Optional<Transaction> parseTransaction(String line) {
        try {
            String[] chunks = line.split(",");
            int step = Integer.parseInt(chunks[0]);
            TransactionType type = TransactionType.valueOf(chunks[1]);

            if((chunks[2] == null || chunks[2].trim().isEmpty()) ||
                    (chunks[4] == null || chunks[4].trim().isEmpty())
                    ||(chunks[5] == null || chunks[5].trim().isEmpty())
                    ||(chunks[7] == null || chunks[7].trim().isEmpty())
                    ||(chunks[8] == null || chunks[8].trim().isEmpty())
                    ) {
                throw new IllegalArgumentException("O valor de amount não pode ser nulo nem vazio");
            }

            BigDecimal amount = new BigDecimal(chunks[2]);


            var origin = new TransactionCustomer(chunks[3], new BigDecimal(chunks[4]), new BigDecimal(chunks[5]));
            var recipient = new TransactionCustomer(chunks[6], new BigDecimal(chunks[7]), new BigDecimal(chunks[8]));


            boolean isFraud = "1".equals(chunks[9]);

            boolean isFlaggedFraud = "1".equals(chunks[10]);


            return Optional.of(new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud));

        } catch (Exception e){
            System.err.println("Erro ao fazer parse: " + line + " | " + e);
            return Optional.empty();
        }
    }

}
