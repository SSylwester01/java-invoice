package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {

    private static int counter = 1;
    private final int number;


    private Map<Product, Integer> products = new HashMap<Product, Integer>();

    public Invoice() {
        this.number = counter++;
    }


    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {

        if (product == null) {

            throw new IllegalArgumentException("Product cannot be null");

        }

        if (quantity == null || quantity <= 0) {

            throw new IllegalArgumentException("Quantity must be positive");

        }

        if (products.containsKey(product)) {

            Integer currentQuantity = products.get(product);

            products.put(product, currentQuantity + quantity);

        } else {

            products.put(product, quantity);

        }

    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice nr ").append(number).append("\n");
        int count = 0;
        for (Product product : products.keySet()) {
            Integer quantity = products.get(product);
            sb.append(product.getName())
                    .append(", ")
                    .append(quantity)
                    .append(", ")
                    .append(product.getPrice())
                    .append("\n");
            count++;
        }
        sb.append("Liczba pozycji: ").append(count);
        return sb.toString();
    }




    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }


    public int getNumber() {
        return number;
    }

}
