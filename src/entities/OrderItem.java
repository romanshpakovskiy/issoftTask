package entities;

import java.util.Objects;

public class OrderItem {
    private String orderId;
    private String productId;
    private Integer quantity;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId,productId,quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        OrderItem orderItem = (OrderItem) obj;
        return orderId == orderItem.orderId &
                productId== orderItem.productId &
                quantity == orderItem.quantity;
    }

    @Override
    public String toString() {
        return "entities.OrderItem{" +
                "orderId='" + orderId + '\'' +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
