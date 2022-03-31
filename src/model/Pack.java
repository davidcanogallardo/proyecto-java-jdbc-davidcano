package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public final class Pack extends Product implements Serializable {
    // private ArrayList<Integer> productList;
    private TreeSet<Product> productList;
    private int discount;

    public Pack(TreeSet<Product> productList, int discount, Integer id, String name, double price,
    LocalDate startCatalog, LocalDate endCatalog, Integer stock) {
        super(id, name, price, stock, startCatalog, endCatalog);
        this.productList = productList;
        this.discount = discount;
        calculateStock();
    }

    public TreeSet<Product> getProductList() {
        return productList;
    }

    public void setProductList(TreeSet<Product> productList) {
        this.productList = productList;
    }

    public void calculateStock() {
        Integer stock = 0;
        int i = 0;
        for (Product product : productList) {
            if (i == 0) {
                stock = product.getStock();
            } else {
                if (stock>product.getStock()) {
                    stock = product.getStock();
                }
            }
            i++;
            // stock += product.getStock();
            // System.out.println(product.getName()+" "+product.getStock()+" "+stock);
        }
        // System.out.println("calculo stock ---"+stock);
        this.stock = stock;
    }

    public TreeSet<Product> getProdList() {
        return productList;
    }

    public void setProdList(TreeSet<Product> idProdList) {
        this.productList = idProdList;
    }

    public Integer getDiscount() {
        return this.discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean deleteProduct(Product id) {
        for (Product i : this.productList) {
            if (i == id) {
                this.productList.remove(id);
                return true;
            }
        }
        return false;
    }

    public boolean addProduct(Product product) {
        for (Product i : this.productList) {
            if (i == product) {
                return false;
            }
        }
        this.productList.add(product);
        return true;
    }

    @Override
    public String toString() {
        String str = super.toString() + "\nProducts [\n";
        for (Product i : this.productList) {
            str += " " + i + ",\n";
        }
        str += "]";
        return str;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pack pack) {
            return this.productList.equals(pack.getProdList());
        } else {
            return false;
        }

    }
}
