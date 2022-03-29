package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TreeSet;

public class PresenceRegisterDAO {
    public TreeSet<Presence> map = new TreeSet<>();

    public TreeSet<Presence> getMap() {
        return map;
    }

    public Presence add(Presence obj) {
        for (Presence presence : map) {
            if (presence.equals(obj)) {
                return null;
            }
        }
        this.map.add(obj);
        return obj;
    }

    public boolean addLeaveTime(int id) {
        LocalDate today = LocalDate.now();
        
        for (Presence presence : this.map) {
            System.out.println(presence.toString());
            if (presence.getId() == id && presence.getDate().compareTo(today) == 0 && presence.getLeaveTime() == null) {
                LocalTime now = LocalTime.now();
                presence.setLeaveTime(now);
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
        FileInputStream fis = new FileInputStream("presence.dat");
        try {
            ObjectInputStream ois = new ObjectInputStream(fis);
            try {
                this.map = (TreeSet<Presence>) ois.readObject();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ois.close();
        } catch (Exception EOFException) {
            // TODO: handle exception
        }

    }
}
