package net.zenoc.gallium;

import net.zenoc.gallium.api.entity.Player;
import net.zenoc.gallium.exceptions.GalliumDatabaseException;

import java.sql.*;

public class Database {
    private Connection conn;
    String connectionString;

    private PreparedStatement insertPlayer;
    private PreparedStatement getPlayer;
    public Database(String connectionString) {
        this.connectionString = connectionString;
    }

    public void open() throws SQLException {
        conn = DriverManager.getConnection(connectionString);

        insertPlayer = conn.prepareStatement("INSERT INTO players (uuid) VALUES (?)");
        getPlayer = conn.prepareStatement("SELECT * FROM players WHERE uuid = ?");
    }

    public void insertPlayer(Player player) {
        try {
            insertPlayer.setString(1, player.getUUID());
            insertPlayer.execute();
        } catch (SQLException e) {
            throw new GalliumDatabaseException(e);
        }
    }

    public boolean playerExists(Player player) {
        try {
            getPlayer.setString(1, player.getUUID());
            ResultSet rs = getPlayer.executeQuery();
            return rs.first();
        } catch (SQLException e) {
            throw new GalliumDatabaseException(e);
        }
    }
}
