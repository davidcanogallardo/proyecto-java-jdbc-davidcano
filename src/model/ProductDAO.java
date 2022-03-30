package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
// import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;

import java.util.TreeSet;

public class ProductDAO implements Persistable<Product>, Serializable {
    private HashMap<Integer, Product> prodMap = new HashMap<>();
    private HashMap<Integer, Pack> packMap = new HashMap<>();


	private Connection conexionBD;

    public ProductDAO(Connection conexionBD) {
        this.conexionBD = conexionBD;
    }
    public Product add(Product obj) {
        if (prodMap.containsKey(obj.getId())) {
            return null;
        } else {
            prodMap.put(obj.getId(), obj);
            return obj;
        }
    }

    public Product delete(Product obj) {
        String sql = "DELETE FROM product WHERE id = ?";
        if (obj instanceof Pack) {
            sql = "DELETE FROM pack WHERE id = ?";
        }

        if (obj instanceof Pack && packMap.get(obj.getId())!=null || obj instanceof Product && prodMap.get(obj.getId())!=null) {
            try {
                // String sql = "";
                PreparedStatement stmt = null;
                // sql = "DELETE FROM product WHERE id = ?";
                stmt = conexionBD.prepareStatement(sql);
                int i = 1;
                stmt.setInt(i++, obj.getId());
                int rows = stmt.executeUpdate();
                this.load();
                if (obj instanceof Pack) {
                    packMap.remove(obj.getId());
                } else {
                    prodMap.remove(obj.getId());
                }
                return obj;
            } catch (SQLException | IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    public Product get(Integer id, boolean isPack) {
        if (isPack) {
            if (packMap.containsKey(id)) {
                return (Product) packMap.get(id);
            } else {
                return null;
            }
            
        } else {
            if (prodMap.containsKey(id)) {
                return (Product) prodMap.get(id);
            } else {
                return null;
            }

        }
    }

    public HashMap<Integer, Product> getMap() {
        return new HashMap<>(packMap);
    }

    public void modify(Product obj) {
        if (obj instanceof Pack pack) {
            try {
                String sql = "UPDATE pack SET name=?,price=?,date_start=?,date_end=?,discount=? WHERE id = ?";
                PreparedStatement stmt = conexionBD.prepareStatement(sql);
                int i = 1;
                    // ID
                    stmt.setInt(i++, obj.getId());
                    stmt.setDouble(i++, obj.getPrice());
                    // stmt.setInt(i++, obj.getStock());
                    stmt.setDate(i++, Date.valueOf(obj.getStartCatalog()));
                    stmt.setDate(i++, Date.valueOf(obj.getEndCatalog()));
                    stmt.setDouble(i++, ((Pack)obj).getDiscount());
                    stmt.setInt(i++, obj.getId());
                    int rows = stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try {
                String sql = "DELETE FROM pack_products WHERE id_pack = ?";
                PreparedStatement stmt = conexionBD.prepareStatement(sql);
                int i = 1;
                stmt.setInt(i++, obj.getId());
                int rows = stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(pack.toString());
            for (Product prod : pack.getProdList()) {
                try {
                    String sql = "INSERT INTO pack_products VALUES (?,?)";
                    PreparedStatement stmt = conexionBD.prepareStatement(sql);
                    int i = 1;
                    stmt.setInt(i++, pack.getId());
                    stmt.setInt(i++, prod.getId());
                    int rows = stmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
    
        } else {
            try {
                String sql = "UPDATE product SET name=?,price=?,stock=?,date_start=?,date_end=? WHERE id = ?";
                PreparedStatement stmt = conexionBD.prepareStatement(sql);
                int i = 1;
                    stmt.setInt(i++, obj.getId());
                    stmt.setDouble(i++, obj.getPrice());
                    stmt.setInt(i++, obj.getStock());
                    stmt.setDate(i++, Date.valueOf(obj.getStartCatalog()));
                    stmt.setDate(i++, Date.valueOf(obj.getEndCatalog()));
                    stmt.setInt(i++, obj.getId());
                    int rows = stmt.executeUpdate();
                    prodMap.replace(obj.getId(), obj);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        
    }

    public void save() throws IOException {
        System.out.println("guardando products dao...");
        FileOutputStream fos = new FileOutputStream("product.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this.prodMap);
        oos.close();
    }

    public void load() throws IOException {
        System.out.println("cargando....");
        packMap = new HashMap<>();
        prodMap = new HashMap<>();

        // FileInputStream fis = new FileInputStream("product.dat");
        // try {
        //     ObjectInputStream ois = new ObjectInputStream(fis);
        //     try {
        //         this.hashMap = (HashMap<Integer, Product>) ois.readObject();
        //     } catch (ClassNotFoundException e) {
        //         // TODO Auto-generated catch block
        //         e.printStackTrace();
        //     }
        //     ois.close();
        // } catch (Exception EOFException) {
        //     // TODO: handle exception
        // }
		try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM product")) {
			while (result.next()) {
                // System.out.println(result.getString("name"));
				int id = result.getInt("id");
				String name = result.getString("name");
				double price = result.getDouble("price");
				int stock = result.getInt("stock");
                Date date_start = result.getDate("date_start");
                Date date_end = result.getDate("date_end");

                LocalDate startCatalog = Instant.ofEpochMilli(date_start.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate endCatalog = Instant.ofEpochMilli(date_end.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                
                Product prod = new Product(id, name, price, stock, startCatalog, endCatalog);
                prodMap.put(prod.getId(), prod);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM pack")) {
			while (result.next()) {
                System.out.println(result.getString("name"));
				int id = result.getInt("id");
				String name = result.getString("name");
				double price = result.getDouble("price");
                Date date_start = result.getDate("date_start");
                Date date_end = result.getDate("date_end");

                LocalDate startCatalog = Instant.ofEpochMilli(date_start.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate endCatalog = Instant.ofEpochMilli(date_end.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
				int discount = result.getInt("discount");
                TreeSet<Product> prodList = new TreeSet<>();

                Pack pack = new Pack(prodList, discount, id, name, price, startCatalog, endCatalog);
                packMap.put(pack.getId(), pack);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

        try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM pack_products")) {
            while (result.next()) {
                int id_pack = result.getInt("id_pack");
                int id_producto = result.getInt("id_producto");
                // System.out.println(id_pack);
                // System.out.println(id_producto);

                Pack pack =  packMap.get(id_pack);
                Product prod = prodMap.get(id_producto);
                // System.out.println(pack.toString());
                // System.out.println(prod.toString());

                pack.getProdList().add(prod);
                packMap.put(pack.getId(), pack);
            }                
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Product> getDiscontinuedProducts(LocalDate date) {
        List<Product> list = new ArrayList<Product>();
        for (Product product : prodMap.values()) {
            if (product.getEndCatalog().isBefore(date)) {
                // System.out.print("Días de diferencia: ");
                // System.out.println(ChronoUnit.DAYS.between(product.getEndCatalog(), date));
                // System.out.println(product.toString() + "\n");
                list.add(product);
            }
        }

        return list;
    }
    @Override
    public Product get(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

}
