package br.com.zenon;

public class DBMain {

    void main(){
        ConnectionFactory.getConnection();
        IO.println("Conexão com BD criada !:");

        var repository = new TransactionSQLRepository();

        repository.findByOriginName("C1000001")
                .ifPresentOrElse(IO::println, () -> IO.println("Transacao nao encontrada para: C1000001"));


        repository.findByOriginName("C123456")
                .ifPresentOrElse(IO::println, () -> IO.println("Transacao nao encontrada para: C123456"));
    }
}
