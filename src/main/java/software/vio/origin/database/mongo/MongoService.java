package software.vio.origin.database.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import software.vio.origin.Origin;
import software.vio.origin.util.CC;
import software.vio.origin.util.Msg;

@Getter
public class MongoService {
    
    private final Origin origin;

    private final MongoConfig config;
    private MongoClient connection;
    private MongoDatabase database;
    private MongoCollection<Document> users, punishments;

    public MongoService(Origin origin) {
        this.origin = origin;

        this.config = this.buildConfig();
        this.connect();
    }

    public void disconnect() {
        this.connection.close();
    }

    private void connect() {
        try {
            ConnectionString connectionString = new ConnectionString("mongodb+srv://" + this.config.getUser() + ":" + this.config.getPassword() + "@" + this.config.getHost() + "/" + this.config.getDatabase() + "?retryWrites=true&w=majority");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            this.connection = MongoClients.create(settings);

            this.database = this.connection.getDatabase(this.config.getDatabase());
            this.users = this.database.getCollection("users");
            this.punishments = this.database.getCollection("punishments");

            Msg.log(CC.GOLD + "Origin " + CC.YELLOW + "successfully established a connection to the database.");
        } catch (Exception e) {
            Msg.log(CC.GOLD + "Origin " + CC.YELLOW + "failed to establish a connection to the database. Reason: " + CC.GOLD + e.getMessage());
        }
    }
    
    private MongoConfig buildConfig() {
        return new MongoConfig()
                .setHost(this.origin.getConfig().getString("MONGO.HOST"))
                .setPort(this.origin.getConfig().getInt("MONGO.PORT"))
                .setDatabase(this.origin.getConfig().getString("MONGO.DATABASE"))
                .setUser(this.origin.getConfig().getString("MONGO.USER"))
                .setPassword(this.origin.getConfig().getString("MONGO.PASSWORD"));
    }
}
