package bestworkingconditions.biedaflix.server.config;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private int port;
    @Value("${spring.data.mongodb.database}")
    private String database;
    //@Value("${spring.data.mongodb.username}")
    //private String username;
    //@Value("${spring.data.mongodb.password}")
    //private String password;

    @Override
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(new ConnectionString("mongodb://" + host + ":" + port + "/" + database));
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }
}
