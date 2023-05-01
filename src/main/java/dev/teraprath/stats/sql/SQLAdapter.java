package dev.teraprath.stats.sql;


import dev.teraprath.stats.Main;

import java.sql.*;

public class SQLAdapter {

    private final SQLAuthentication authentication;
    private Connection con;

    public SQLAdapter(SQLAuthentication authentication) {
        this.authentication = authentication;
    }

    public void connect() {

        try {
            this.con = DriverManager.getConnection("jdbc:mysql://" + authentication.host + ":" + authentication.port + "/" + authentication.database + "?autoReconnect=true", authentication.user, authentication.password);
        } catch (SQLException e) {
            Main.getInstance().getLogger().warning("MySQL-Connection failed. Please check your 'config.yml' in 'plugins/StatsAPI' folder.");
        }
    }

    public void init() {
        connect();
        String sql = "CREATE TABLE IF NOT EXISTS stats_players "
                + "(uuid VARCHAR(36) not NULL, "
                + "kills INT, "
                + "deaths INT, "
                + "wins INT, "
                + "loses INT, "
                + "games_played INT, "
                + "streak INT, "
                + "game_points INT, "
                + "PRIMARY KEY (uuid))";
        update(sql);
        disconnect();
    }

    public void disconnect() {
        try {
            if (this.hasConnection()) {
                this.con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasConnection() {
        return this.con != null;
    }

    public void update(String sql) {
        try {
            PreparedStatement st = this.con.prepareStatement(sql);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String sql) {
        try {
            PreparedStatement st = this.con.prepareStatement(sql);
            return st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
