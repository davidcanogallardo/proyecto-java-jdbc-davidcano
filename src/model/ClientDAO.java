package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Identificable;
import model.Persistable;

public class ClientDAO implements Persistable<Client>, Serializable {
    private HashMap<Integer, Client> hashMap = new HashMap<>();

    public Client add(Client obj) {
        if (hashMap.containsKey(obj.getId())) {
            return null;
        } else {
            hashMap.put(obj.getId(), obj);
            return obj;
        }
    }

    public Client delete(Client obj) {
        if (hashMap.containsKey(obj.getId())) {
            hashMap.remove(obj.getId());
            return obj;
        } else {
            return null;
        }
    }

    public Client get(Integer id) {
        if (hashMap.containsKey(id)) {
            return (Client) hashMap.get(id);
        } else {
            return null;
        }
    }

    public HashMap<Integer, Client> getMap() {
        return new HashMap<>(hashMap);
    }

    public void modify(Client obj) {
        hashMap.replace(obj.getId(), get(obj.getId()), obj);
    }

    public void save() throws IOException {
        System.out.println("guardando dao client...");
        FileOutputStream fos = new FileOutputStream("client.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this.hashMap);
        oos.close();
    }

    public void load() throws IOException {
        System.out.println("cargando....");
        FileInputStream fis = new FileInputStream("client.dat");
        try {
            ObjectInputStream ois = new ObjectInputStream(fis);
            try {
                this.hashMap = (HashMap<Integer, Client>) ois.readObject();
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
