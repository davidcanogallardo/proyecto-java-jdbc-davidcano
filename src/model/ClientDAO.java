package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import model.Identificable;
import model.Persistable;

public class ClientDAO implements Persistable<Client>, Serializable {
    private HashMap<Integer, Client> hashMap = new HashMap<>();
	private Connection conexionBD;

    public ClientDAO(Connection conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    public Client add(Client obj) {
        if (hashMap.containsKey(obj.getId())) {
            return null;
        } else {
            System.out.println("inserto...");
            try {
                String sql = "";
                PreparedStatement stmt = null;
                sql = "INSERT INTO client VALUES(?,?,?,?,?,?,?,?)";
                stmt = conexionBD.prepareStatement(sql);
                int i = 1;
                // ID
                stmt.setInt(i++, obj.getId());
                stmt.setString(i++, obj.getDni());
                stmt.setString(i++, obj.getName());
                stmt.setString(i++, obj.getSurname());
                // birthdate
                stmt.setDate(i++, null);
                // email
                stmt.setString(i++, "");
                // address
                stmt.setArray(i++, conexionBD.createArrayOf("VARCHAR", obj.getFullAddress().getArray()));
                // phone
                stmt.setArray(i++, conexionBD.createArrayOf("VARCHAR", obj.getArray()));
                int rows = stmt.executeUpdate();
                hashMap.put(obj.getId(), obj);
                return obj;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
    }

    public Client delete(Client obj) {

        if (hashMap.containsKey(obj.getId())) {
            try {
                String sql = "";
                PreparedStatement stmt = null;
                if (this.find(id) != null){
                    sql = "DELETE FROM client WHERE id = ?";
                    stmt = conexionBD.prepareStatement(sql);
                    int i = 1;
                    stmt.setInt(i++, obj.getId());
                }
                int rows = stmt.executeUpdate();
                hashMap.remove(obj.getId());
                return obj;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
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
        // FileInputStream fis = new FileInputStream("client.dat");
        // try {
        //     ObjectInputStream ois = new ObjectInputStream(fis);
        //     try {
        //         this.hashMap = (HashMap<Integer, Client>) ois.readObject();
        //     } catch (ClassNotFoundException e) {
        //         // TODO Auto-generated catch block
        //         e.printStackTrace();
        //     }
        //     ois.close();
        // } catch (Exception EOFException) {
        //     // TODO: handle exception
        // }
		try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM client")) {
			while (result.next()) {
                System.out.println(result.getString("name"));
				int id = result.getInt("id");
				String dni = result.getString("dni");
				String name = result.getString("name");
				String surname = result.getString("lastname");
				Array address = result.getArray("address");

				String[] addressArr = (String[]) address.getArray();
				Address fullAddress = new Address(addressArr[0], addressArr[1], addressArr[2], addressArr[3]);

				Array phone = result.getArray("phone");
				String[] b = (String[]) phone.getArray();
				LinkedHashSet<String> phoneNumber = new LinkedHashSet<>(Arrays.asList(b));
				// LinkedHashSet<String> phoneNumber = new LinkedHashSet<>();

				Client cli2 = new Client(id, dni, name, surname, fullAddress, phoneNumber);
                hashMap.put(cli2.getId(), cli2);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    }

}
