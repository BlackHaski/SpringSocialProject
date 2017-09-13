package base.config;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:config.properties")
public class DataConfig{
    @Autowired
    Environment env;

    @Bean
    public MongoClient mongo(){
        MongoClient mongo = new MongoClient();
        return mongo;
    }
    @Bean
    public Morphia morphia(){
        Morphia morphia = new Morphia();
        morphia.createDatastore(mongo(), env.getProperty("db.name"));
        morphia.mapPackage("/entity/");
        return morphia;
    }
    @Bean
    public Datastore datastore(){
        Datastore datastore = morphia().createDatastore(mongo(), env.getProperty("db.name"));
        return datastore;
    }
}
