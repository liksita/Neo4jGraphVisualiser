package neo4jGraphVisualiser;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.Pair;
import org.neo4j.server.CommunityBootstrapper;
import org.neo4j.server.NeoServer;
import org.neo4j.server.ServerBootstrapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws IOException {
        // Wherever the Neo4J storage location is.
        File storeDir = new File("/home/diana/Dokumente/test");
        HashMap<String, String> map = new HashMap<>();
        map.put("dbms.connector.http.address", "127.0.0.1:7474");
        map.put("dbms.connector.http.enabled", "true");
        map.put("dbms.connector.bolt.enabled", "true");
        map.put("dbms.shell.enabled", "true");
        map.put("dbms.shell.host", "127.0.0.1");
        map.put("dbms.shell.port", "1337");
        
        ServerBootstrapper serverBootstrapper = new CommunityBootstrapper();
//        serverBootstrapper.start(
//            storeDir,
//            Optional.empty(), // omit configfile, properties follow
//            Pair.of("dbms.connector.http.address","127.0.0.1:7474"),
//            Pair.of("dbms.connector.http.enabled", "true"),
//            Pair.of("dbms.connector.bolt.enabled", "true"),
//
//            // allow the shell connections via port 1337 (default)
//            Pair.of("dbms.shell.enabled", "true"),
//            Pair.of("dbms.shell.host", "127.0.0.1"),
//            Pair.of("dbms.shell.port", "1337")
//        );
        
        serverBootstrapper.start(
                storeDir,
                Optional.empty(), // omit configfile, properties follow
                map
            );
        // ^^ serverBootstrapper.start() also registered shutdown hook!

        NeoServer neoServer = serverBootstrapper.getServer();
        GraphDatabaseService gdb = neoServer.getDatabase().getGraph();

        /* Some transactional code */
        try(Transaction tx = gdb.beginTx()) {
            gdb.getAllNodes().forEach(
                n -> System.out.println(n)
            );
            tx.success();
        }

        System.out.println("Press ENTER to quit.");
        System.in.read();
        

        System.exit(0);
    }
}
