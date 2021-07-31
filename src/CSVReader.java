import DTO.ResultDTO;
import entities.Order;
import entities.OrderItem;
import entities.Product;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CSVReader {

    private static final String ORDERS_FILE_PATH = "src/resources/orders.csv";
    private static final String ORDER_ITEMS_FILE_PATH = "src/resources/order_items.csv";
    private static final String PRODUCTS_FILE_PATH = "src/resources/products.csv";
    private static final String RESULT_FILE_NAME = "src/resources/result.txt";
    private static final String delimiter = ",";
    private static final LocalDate dateToDefine = LocalDate.of(2021, 1, 21);

    private static List<Order> getOrderList() {
        List<Order> orderList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ORDERS_FILE_PATH))) {
            String row;
            bufferedReader.readLine();
            while ((row = bufferedReader.readLine()) != null) {
                String[] column = row.split(delimiter);
                Order order = new Order();
                order.setId(column[0]);
                order.setDateTime(LocalDateTime.parse(column[1]));
                orderList.add(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    private static List<OrderItem> getOrderItemList() {
        List<OrderItem> orderItemList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ORDER_ITEMS_FILE_PATH))) {
            String row;
            bufferedReader.readLine();
            while ((row = bufferedReader.readLine()) != null) {
                String[] column = row.split(delimiter);
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(column[0]);
                orderItem.setProductId(column[1]);
                orderItem.setQuantity(Integer.parseInt(column[2]));
                orderItemList.add(orderItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderItemList;
    }

    private static List<Product> getProductList() {
        List<Product> productList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PRODUCTS_FILE_PATH))) {
            String row;
            bufferedReader.readLine();
            while ((row = bufferedReader.readLine()) != null) {
                String[] column = row.split(delimiter);
                Product product = new Product();
                product.setId(column[0]);
                product.setName(column[1]);
                product.setPricePerUnit(Integer.parseInt(column[2]));
                productList.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productList;
    }

    private static void fillResultFile(List<ResultDTO> resultDTOList) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(RESULT_FILE_NAME))) {
            String resultFormatLine;
            for (ResultDTO resultDTO : resultDTOList) {
                resultFormatLine = resultDTO.getLocalDate() + ": " +
                        resultDTO.getProductName() + " with " + resultDTO.getProfit() + " profit";
                if (resultDTO.getLocalDate().isEqual(dateToDefine)) {
                    bufferedWriter.write(resultFormatLine);
                }
                System.out.println(resultFormatLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Order> orderList = getOrderList();
        List<OrderItem> orderItemList = getOrderItemList();
        List<Product> productList = getProductList();
        Map<LocalDate, List<String>> dateOrder = new HashMap<>();
        List<LocalDate> localDateList = orderList.stream()
                .map(order -> order.getDateTime().toLocalDate())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        for (LocalDate localDate : localDateList) {
            List<String> orderIdList = new ArrayList<>();
            for (Order order : orderList) {
                if (order.getDateTime().toLocalDate().isEqual(localDate)) {
                    orderIdList.add(order.getId());
                }
            }
            dateOrder.put(localDate, orderIdList);
        }


        Map<LocalDate, Map<String, Integer>> dateProdQuantityMap = new HashMap<>();

        for (Map.Entry<LocalDate, List<String>> localDateListEntry : dateOrder.entrySet()) {
            Map<String, Integer> orderProdMap = new HashMap<>();
            for (OrderItem orderItem : orderItemList) {
                if (orderProdMap.containsKey(orderItem.getProductId())) {
                    orderProdMap.put(orderItem.getProductId(),
                            orderProdMap.get(orderItem.getProductId()) + orderItem.getQuantity());
                } else if (localDateListEntry.getValue().contains(orderItem.getOrderId())) {
                    orderProdMap.put(orderItem.getProductId(), orderItem.getQuantity());
                }
            }
            dateProdQuantityMap.put(localDateListEntry.getKey(), orderProdMap);
        }

        List<ResultDTO> resultDTOList = new ArrayList<>();

        for (Map.Entry<LocalDate, Map<String, Integer>> localDateMapEntry : dateProdQuantityMap.entrySet()) {
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setLocalDate(localDateMapEntry.getKey());
            for (Product product : productList) {
                if (localDateMapEntry.getValue().containsKey(product.getId())) {
                    if (resultDTO.getProductName() == null && resultDTO.getProfit() == null) {
                        resultDTO.setProductName(product.getName());
                        resultDTO.setProfit(product.getPricePerUnit() * localDateMapEntry.getValue().get(product.getId()));
                    } else if (resultDTO.getProfit() < product.getPricePerUnit() * localDateMapEntry.getValue().get(product.getId())) {
                        resultDTO.setProductName(product.getName());
                        resultDTO.setProfit(product.getPricePerUnit() * localDateMapEntry.getValue().get(product.getId()));
                    }
                }
            }
            resultDTOList.add(resultDTO);
        }

        List<ResultDTO> collect = resultDTOList.stream()
                .sorted(Comparator.comparing(ResultDTO::getLocalDate))
                .collect(Collectors.toList());

        fillResultFile(collect);
    }
}