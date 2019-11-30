package by.training.service;

import by.training.appentry.Bean;
import by.training.connection.TransactionManager;

@Bean
public class GameServerServiceImpl extends BaseBeanService implements GameServerService {

    public GameServerServiceImpl(TransactionManager transactionManager) {
        super(transactionManager);
    }

}
