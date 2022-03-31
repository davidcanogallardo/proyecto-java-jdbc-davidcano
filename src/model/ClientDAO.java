package model;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;


public class ClientDAO implements Persistable<Client>, Serializable {
    private HashMap<Integer, Client> hashMap = new HashMap<>();
	private Connection con;

    public ClientDAO(Connection con) {
        this.con = con;
    }
    
    public Client add(Client obj) {
        if (hashMap.containsKey(obj.getId())) {
            return null;
        } else {
            try {
                PreparedStatement stmt = null;
                String sql = "INSERT INTO client VALUES(?,?,?,?,?,?,?,?)";
                stmt = con.prepareStatement(sql);
                int i = 1;
                stmt.setInt(i++, obj.getId());
                stmt.setString(i++, obj.getDni());
                stmt.setString(i++, obj.getName());
                stmt.setString(i++, obj.getSurname());
                stmt.setDate(i++, null); // birthdate
                stmt.setString(i++, ""); // email
                stmt.setArray(i++, con.createArrayOf("VARCHAR", obj.getFullAddress().getArray()));
                stmt.setArray(i++, con.createArrayOf("VARCHAR", obj.getPhonesAsArray()));
                
                stmt.executeUpdate();
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
                PreparedStatement stmt = null;
                if (this.get(obj.getId()) != null){
                    String sql = "DELETE FROM client WHERE id = ?";
                    stmt = con.prepareStatement(sql);
                    int i = 1;
                    stmt.setInt(i++, obj.getId());
                }
                stmt.executeUpdate();
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

    public HashMap<Integer, Client> getPackMap() {
        return new HashMap<>(hashMap);
    }

    public void modify(Client obj) {
        hashMap.replace(obj.getId(), get(obj.getId()), obj);

        try {
            String sql = "UPDATE client SET dni=?,name=?,lastname=?,address=?,phone=? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            int i = 1;
                stmt.setString(i++, obj.getDni());
                stmt.setString(i++, obj.getName());
                stmt.setString(i++, obj.getSurname());
                stmt.setArray(i++, con.createArrayOf("VARCHAR", obj.getFullAddress().getArray()));
                stmt.setArray(i++, con.createArrayOf("VARCHAR", obj.getPhonesAsArray()));
                stmt.setInt(i++, obj.getId());
                stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save() {
    }

    public void load() throws IOException {
        System.out.println("cargando....");
		try (ResultSet result = con.createStatement().executeQuery("SELECT * FROM client")) {
			while (result.next()) {
				int id = result.getInt("id");
				String dni = result.getString("dni");
				String name = result.getString("name");
				String surname = result.getString("lastname");
				Array address = result.getArray("address");

				String[] addressArr = (String[]) address.getArray();

				Address fullAddress = new Address(addressArr[0], addressArr[1], addressArr[2], addressArr[3]);

				Array phoneArrSql = result.getArray("phone");
				String[] phoneArr = (String[]) phoneArrSql.getArray();
				LinkedHashSet<String> phoneNumber = new LinkedHashSet<>(Arrays.asList(phoneArr));

				Client client = new Client(id, dni, name, surname, fullAddress, phoneNumber);
                hashMap.put(client.getId(), client);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    }

}
