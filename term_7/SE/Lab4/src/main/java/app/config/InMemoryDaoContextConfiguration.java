package app.config;

import app.dao.ToDoDao;
import app.dao.ToDoInMemoryDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InMemoryDaoContextConfiguration {

    @Bean
    public ToDoDao todoDao() {
        return new ToDoInMemoryDao();
    }

}
