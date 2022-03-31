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

	private Connection con;

    public ProductDAO(Connection con) {
        this.con = con;
    }

    public Product add(Product obj) {
        if (obj instanceof Pack && packMap.containsKey(obj.getId()) || !(obj instanceof Pack) && prodMap.containsKey(obj.getId())) {
            return null;
        } else if (obj instanceof Pack pack && !packMap.containsKey(obj.getId())) {
            try {
                PreparedStatement stmt = null;
                String sql = "INSERT INTO pack VALUES(?,?,?,?,?,?,?)";
                stmt = con.prepareStatement(sql);
                int i = 1;
                stmt.setInt(i++, pack.getId());
                stmt.setString(i++, pack.getName());
                stmt.setDouble(i++, pack.getPrice());
                stmt.setInt(i++, 1);
                stmt.setDate(i++, Date.valueOf(pack.getStartCatalog()));
                stmt.setDate(i++, Date.valueOf(pack.getEndCatalog()));
                stmt.setDouble(i++, pack.getDiscount());
                
                stmt.executeUpdate();
                prodMap.put(obj.getId(), obj);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }

            for (Product prod : pack.getProdList()) {
                try {
                    String sql = "INSERT INTO pack_products VALUES (?,?)";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    int i = 1;
                    stmt.setInt(i++, pack.getId());
                    stmt.setInt(i++, prod.getId());
                    int rows = stmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    return null;
                }
            }
            return obj;


        } else {
            try {
                PreparedStatement stmt = null;
                String sql = "INSERT INTO product VALUES(?,?,?,?,?,?)";
                stmt = con.prepareStatement(sql);
                int i = 1;
                stmt.setInt(i++, obj.getId());
                stmt.setString(i++, obj.getName());
                stmt.setDouble(i++, obj.getPrice());
                stmt.setInt(i++, obj.getStock());
                stmt.setDate(i++, Date.valueOf(obj.getStartCatalog()));
                stmt.setDate(i++, Date.valueOf(obj.getEndCatalog()));
                
                stmt.executeUpdate();
                prodMap.put(obj.getId(), obj);
                return obj;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
    }

    public Product delete(Product obj) {
        String sql = "DELETE FROM product WHERE id = ?";
        if (obj instanceof Pack) {
            sql = "DELETE FROM pack WHERE id = ?";
        }

        if (obj instanceof Pack && packMap.get(obj.getId())!=null || obj instanceof Product && prodMap.get(obj.getId())!=null) {
            try {
                PreparedStatement stmt = null;
                stmt = con.prepareStatement(sql);
                int i = 1;
                stmt.setInt(i++, obj.getId());
                stmt.executeUpdate();
                // Recargo la lista de packs y productos
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

    public HashMap<Integer, Product> getPackMap() {
        return new HashMap<>(packMap);
    }

    public HashMap<Integer, Product> getProdMap() {
        return new HashMap<>(prodMap);
    }

    public void modify(Product obj) {
        if (obj instanceof Pack pack) {
            // Modifico el pack
            try {
                String sql = "UPDATE pack SET name=?,price=?,date_start=?,date_end=?,discount=? WHERE id = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
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

            // Borro todos los prods del pack
            try {
                String sql = "DELETE FROM pack_products WHERE id_pack = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                int i = 1;
                stmt.setInt(i++, obj.getId());
                int rows = stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // Añado los productos al pack
            // System.out.println(pack.toString());
            for (Product prod : pack.getProdList()) {
                try {
                    String sql = "INSERT INTO pack_products VALUES (?,?)";
                    PreparedStatement stmt = con.prepareStatement(sql);
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
                PreparedStatement stmt = con.prepareStatement(sql);
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

    public void save() {
    }

    public void load() throws IOException {
        System.out.println("cargando....");
        packMap = new HashMap<>();
        prodMap = new HashMap<>();

		try (ResultSet result = con.createStatement().executeQuery("SELECT * FROM product")) {
			while (result.next()) {
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

		try (ResultSet result = con.createStatement().executeQuery("SELECT * FROM pack")) {
			while (result.next()) {
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

        try (ResultSet result = con.createStatement().executeQuery("SELECT * FROM pack_products")) {
            while (result.next()) {
                int id_pack = result.getInt("id_pack");
                int id_producto = result.getInt("id_producto");

                Pack pack =  packMap.get(id_pack);
                Product prod = prodMap.get(id_producto);

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
