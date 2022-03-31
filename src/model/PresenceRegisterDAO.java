package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.TreeSet;

public class PresenceRegisterDAO {
	private Connection con;

    public PresenceRegisterDAO(Connection con) {
        this.con = con;
    }
    public TreeSet<Presence> treeSet = new TreeSet<>();

    public TreeSet<Presence> getMap() {
        return treeSet;
    }

    public Presence add(Presence obj) {
        for (Presence presence : treeSet) {
            if (presence.getId() == obj.getId() && presence.getLeaveTime() == null) {
                return null;
            }
        }
        
		try {
            String sql = "INSERT INTO presence VALUES (?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            int i = 1;
            stmt.setInt(i++, obj.getId());
            stmt.setTimestamp(i++, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            this.treeSet.add(obj); 
            return obj;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
            return null;
		}

    }

    public boolean addLeaveTime(int id) {
        for (Presence presence : this.treeSet) {
            if (presence.getId() == id && presence.getLeaveTime() == null) {
                presence.setLeaveTime(LocalDateTime.now());
                try {
                    String sql = "UPDATE presence SET date_leave=? WHERE id=? AND date_leave IS NULL";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    int i = 1;
                    stmt.setTimestamp(i++, Timestamp.valueOf(LocalDateTime.now()));
                    stmt.setInt(i++, id);
                    stmt.executeUpdate();
                    return true;
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
        }
        return false;
    }

    public void list() {
        for (Presence presence : treeSet) {
            System.out.println(presence.toString());
        }
    }

    public void save() {
    }

    public void load() throws IOException {
        System.out.println("cargando....");
		try (ResultSet result = con.createStatement().executeQuery("SELECT * FROM presence")) {
			while (result.next()) {
				int id = result.getInt("id");
                Timestamp enter = result.getTimestamp("date_enter");
                Timestamp leave = result.getTimestamp("date_leave");
                Presence presence;
                if (leave == null) {
                    presence = new Presence(id, enter.toLocalDateTime(),null);
                    
                } else {
                    presence = new Presence(id, enter.toLocalDateTime(),leave.toLocalDateTime());
                }
                
                treeSet.add(presence);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    }
}
