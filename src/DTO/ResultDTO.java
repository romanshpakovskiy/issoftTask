package DTO;

import java.time.LocalDate;
import java.util.Objects;

public class ResultDTO implements Comparable<ResultDTO> {
    private LocalDate localDate;
    private String productName;
    private Integer profit;

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultDTO resultDTO = (ResultDTO) o;
        return localDate.isEqual(resultDTO.localDate) &&
                productName.equals(resultDTO.productName) &&
                profit.equals(resultDTO.profit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localDate, productName, profit);
    }

    @Override
    public String toString() {
        return "ResultDRO{" +
                "localDate=" + localDate + '\'' +
                "productName=" + productName + '\'' +
                ", profit='" + profit +
                '}';
    }

    @Override
    public int compareTo(ResultDTO resultDTO) {
        return this.getProfit() - resultDTO.getProfit();
    }
}
