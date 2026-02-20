package store;

import java.util.LinkedList;
import java.util.List;

public class InventoryManager extends Employee {

    public InventoryManager(String name, int id, double salary, Store store) {
        super(name, id, salary, store);
    }
    
    // TODO refactor
    public void addProduct(String name, double price, String category, int stock) {
        List<Product> new_inventory = getStore().getInventory();
        new_inventory.add(new Product(name, price, category, stock));
        getStore().setInventory(new_inventory);
        System.out.println("Product " + name + " added to store inventory by " + getName() + "(" + getId() + ")");
    }

    // TODO refactor
    public void removeProduct(String name) {
        List<Product> new_inventory = getStore().getInventory();
        for (Product p : new_inventory) {
            if (p.n.equals(name)) {
                new_inventory.remove(p);
                break;
            }
        }
        getStore().setInventory(new_inventory);
        System.out.println("Product " + name + " removed from store inventory by " + getName() + "(" + getId() + ")");
    }

    // TODO refactor
    public void updateStock(String name, int newStock) {
        List<Product> new_inventory = getStore().getInventory();
        for (Product p : new_inventory) {
            if (p.n.equals(name)) {
                p.s = newStock;
            }
        }
        getStore().setInventory(new_inventory);
        System.out.println("Product " + name + "updated by " + getName() + "(" + getId() + ")");
    }

    // TODO refactor
    public void printInventory() {
        for (Product p : getStore().getInventory()) {
            System.out.println(p.n + " - " + p.c + " - $" + p.p + " - Stock: " + p.s);
        }
    }

    @Override
    public void conductMeeting() {}

    @Override
    public void submitReport() {}

    @Override
    public void approveBudget() {}

    // TODO refactor
    /**
     * Método que recibe una categoría y un booleano. Del inventario va recorriendo los productos con la 
     * categoría indicada (o todos si se declara 'all' como categoría), y si se ha de aplicar un descuento
     * se aplica según el tipo de producto que sea. Tras esto, se imprime por pantalla el stock de dicho
     * producto. Después, se imprime un resumen del stock del inventario completo, incluyendo el precio.
     */
    public void processInventory(String categoryFilter, boolean applyDiscount) {
        System.out.println("Procesando inventario...");
        List<Product> new_inventory = getStore().getInventory();
        for (Product p : new_inventory) {
            if (p.c.equals(categoryFilter) || categoryFilter.equals("all")) {
                if (applyDiscount) {
                    if (p.c.equals("electronics")) {
                        p.p *= 0.9;  // 10% de descuento
                    } else if (p.c.equals("clothing")) {
                        p.p *= 0.85; // 15% de descuento
                    } else {
                        p.p *= 0.95; // 5% de descuento
                    }
                }

                if (p.s == 0) {
                    System.out.println("[AGOTADO] " + p.n);
                } else if (p.s < 5) {
                    System.out.println("[BAJO STOCK] " + p.n + " - Quedan " + p.s);
                } else {
                    System.out.println("[OK] " + p.n + " - Precio: $" + p.p + " - Stock: " + p.s);
                }
            }
        }

        int totalStock = 0;
        double totalValue = 0;

        for (Product p : new_inventory) {
            totalStock += p.s;
            totalValue += p.p * p.s;
        }

        System.out.println("Resumen: " + totalStock + " productos en stock, valor total: $" + totalValue);
        getStore().setInventory(new_inventory);
        System.out.println("Inventory processed by " + getName() + "(" + getId() + ")");
    }

}
