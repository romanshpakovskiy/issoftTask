package entities;

import java.util.Objects;

public class Product {
    private String id;
    private String name;
    private int pricePerUnit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(int pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,name,pricePerUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Product product = (Product) obj;
        return id == product.id &
                name == product.name &
                pricePerUnit == product.pricePerUnit;
    }

    @Override
    public String toString() {
        return "entities.Product{" +
                "id='" + id + '\'' +
                ", name=" + name +
                ", pricePerUnit=" + pricePerUnit +
                '}';
    }
}
