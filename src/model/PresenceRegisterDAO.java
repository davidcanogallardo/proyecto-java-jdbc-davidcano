package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TreeSet;

public class PresenceRegisterDAO {
	private Connection conexionBD;

    public PresenceRegisterDAO(Connection conexionBD) {
        this.conexionBD = conexionBD;
    }
    public TreeSet<Presence> map = new TreeSet<>();

    public TreeSet<Presence> getMap() {
        return map;
    }

    public Presence add(Presence obj) {
        for (Presence presence : map) {
            if (presence.getId() == obj.getId() && presence.getLeaveTime() == null) {
                return null;
            }
        }
        this.map.add(obj);
        return obj;
    }

    public boolean addLeaveTime(int id) {
        for (Presence presence : this.map) {
            if (presence.getId() == id && presence.getLeaveTime() == null) {
                presence.setLeaveTime(LocalDateTime.now());
                return true;
            }
        }
        return false;
    }

    public void list() {
        for (Presence presence : map) {
            System.out.println(presence.toString());
        }
    }

    public void save() throws IOException {
        System.out.println("guardando dao client...");
        FileOutputStream fos = new FileOutputStream("presence.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this.map);
        oos.close();
    }

    public void load() throws IOException {
        System.out.println("cargando....");
        // FileInputStream fis = new FileInputStream("presence.dat");
        // try {
        //     ObjectInputStream ois = new ObjectInputStream(fis);
        //     try {
        //         this.map = (TreeSet<Presence>) ois.readObject();
        //     } catch (ClassNotFoundException e) {
        //         // TODO Auto-generated catch block
        //         e.printStackTrace();
        //     }
        //     ois.close();
        // } catch (Exception EOFException) {
        //     // TODO: handle exception
        // }
		try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM presence")) {
			while (result.next()) {
                // System.out.println(result.getString("name"));
				int id = result.getInt("id");
                Timestamp enter = result.getTimestamp("date_enter");
                Timestamp leave = result.getTimestamp("date_leave");
                // System.out.println(enter);
                // Date leave = result.getDate("date_leave");
                // Timestamp ts = new Timestamp(enter.getTime());
                // Timestamp ts2 = new Timestamp(leave.getTime());
                // LocalDateTime aa = enter.toLocalDateTime
                Presence presence = new Presence(id, enter.toLocalDateTime(),leave.toLocalDateTime());
                
                map.add(presence);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    }
}
