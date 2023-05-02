package dev.teraprath.stats.sql;

public class SQLAuthentication {

    public String host;
    public int port;
    public String database;
    public String user;
    public String password;

    public SQLAuthentication(String host, int port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

}
