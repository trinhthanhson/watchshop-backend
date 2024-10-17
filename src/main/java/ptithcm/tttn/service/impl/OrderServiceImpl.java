package ptithcm.tttn.service.impl;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.*;
import ptithcm.tttn.repository.BillRepo;
import ptithcm.tttn.repository.OrderDetailRepo;
import ptithcm.tttn.repository.OrderRepo;
import ptithcm.tttn.repository.ProductRepo;
import ptithcm.tttn.request.OrderRequest;
import ptithcm.tttn.request.ProductSaleRequest;
import ptithcm.tttn.request.StatisticRequest;
import ptithcm.tttn.service.*;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepo ordersRepo;
    private final UserService userService;
    private final CustomerService customerService;
    private final OrderDetailRepo orderDetailRepo;
    private final CartDetailService cartDetailService;
    private final StaffService staffService;
    private final BillRepo billRepo;
    private final ProductRepo productRepo;

    public OrderServiceImpl(OrderRepo ordersRepo, UserService userService, CustomerService customerService, OrderDetailRepo orderDetailRepo, CartDetailService cartDetailService, StaffService staffService, BillRepo billRepo, ProductRepo productRepo) {
        this.ordersRepo = ordersRepo;
        this.userService = userService;
        this.customerService = customerService;
        this.orderDetailRepo = orderDetailRepo;
        this.cartDetailService = cartDetailService;
        this.staffService = staffService;
        this.billRepo = billRepo;
        this.productRepo = productRepo;
    }

    @Override
    @Transactional
    public Orders orderBuyNow(OrderRequest rq, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        Optional<Product> product = productRepo.findById(rq.getProduct_id());
        Product get = product.get();
        Orders orders = new Orders();
        orders.setAddress(rq.getAddress());
        orders.setStatus("0");
        orders.setCreated_at(LocalDateTime.now());
        orders.setRecipient_name(rq.getRecipient_name());
        orders.setUpdated_at(LocalDateTime.now());
        orders.setCustomer_id(customer.getCustomer_id());
        orders.setNote(rq.getNote());
        orders.setRecipient_phone(rq.getRecipient_phone());
        Orders createOrders = ordersRepo.save(orders);
        if(createOrders != null){
            Order_detail orderDetail = new Order_detail();
            orderDetail.setOrder_id(createOrders.getOrder_id());
            orderDetail.setProduct_id(rq.getProduct_id());
            orderDetail.setPrice(rq.getPrice());
            orderDetail.setQuantity(rq.getQuantity());
            orderDetail.setQuantity(rq.getQuantity());
            get.setQuantity(get.getQuantity()-rq.getQuantity());
            productRepo.save(get);
            Order_detail createDetail = orderDetailRepo.save(orderDetail);
            if(createDetail != null){
                int totalPrice = orderDetailRepo.totalPriceByOrderId(createOrders.getOrder_id());
                int totalQuantity = orderDetailRepo.totalQuantityByOrderId(createOrders.getOrder_id());
                createOrders.setTotal_quantity(totalQuantity);
                createOrders.setTotal_price(totalPrice);
                ordersRepo.save(createOrders);
            }
        }
        return createOrders;
    }

    @Override
    public List<Orders> findByJwtCustomer(String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        return ordersRepo.findByCustomerId(customer.getCustomer_id());
    }

    @Override
    public Orders findById(Long id) throws Exception {
        Optional<Orders> orders = ordersRepo.findById(id);
        if(orders.isPresent()){
            return orders.get();
        }
        throw new Exception("not found order by id " + id);
    }

    @Override
    @Transactional
    public Orders updateStatus(String status,Long id) throws Exception {
        Orders findOrder = findById(id);
        if(findOrder != null && findOrder.getStatus().equals("0")){
            findOrder.setStatus(status);
            return  ordersRepo.save(findOrder);
        }
        throw new Exception("Not found order by id " + id);
    }

    @Override
    public Orders updateStatusPayment(String status, Long id) throws Exception {
        Orders findOrder = findById(id);
        if(status.equals("4")){
            findOrder.setStatus(status);
            Bill bill = new Bill();
            bill.setOrder_id(findOrder.getOrder_id());
            bill.setCreated_at(LocalDateTime.now());
            billRepo.save(bill);
            return  ordersRepo.save(findOrder);
        }
        throw new Exception("Not found order by id " + id);
    }


//    @Override
//    public Orders orderBuyCart(OrderRequest rq, String jwt) throws Exception {
//        User user = userService.findUserByJwt(jwt);
//        Customer customer = customerService.findByUserId(user.getUser_id());
//        Cart cart = cartService.findCartByJwtCustomer(jwt);
//        List<OrderDetail> list = new ArrayList<>();
//        int totalQuantity = 0;
//        for (CartDetail detail : cart.getCartDetails()) {
//            OrderDetail orderDetail = new OrderDetail();
//            // orderDetail.setOder_id(createdOrders.getOrder_id());
//            orderDetail.setPrice(detail.getPrice());
//            orderDetail.setProduct_id(detail.getProduct_id());
//            Optional<Product> find = productRepo.findById(detail.getProduct_id());
//            Product get = find.get();
//            get.setQuantity(get.getQuantity()-detail.getQuantity());
//            productRepo.save(get);
//            orderDetail.setQuantity(detail.getQuantity());
//            OrderDetail createDetail = orderDetailRepo.save(orderDetail);
//            totalQuantity += createDetail.getQuantity();
//            list.add(createDetail);
//        }
//        Orders orders = new Orders();
//        orders.setCreated_at(LocalDateTime.now());
//        orders.setNote(rq.getNote());
//        orders.setAddress(rq.getAddress());
//        orders.setRecipient_name(rq.getRecipient_name());
//        orders.setRecipient_phone(rq.getRecipient_phone());
//        orders.setUpdated_at(LocalDateTime.now());
//        orders.setCreated_by(customer.getCustomer_id());
//        orders.setStatus("0");
//        orders.setTotal_price(rq.getTotal_price());
//        orders.setTotal_quantity(totalQuantity);
//        Orders createdOrders = ordersRepo.save(orders);
//        for (OrderDetail item : list) {
//            item.setOrder_id(createdOrders.getOrder_id());
//            orderDetailRepo.save(item);
//        }
//        cartDetailService.deleteCartDetail(cart.getCart_id());
//        cartService.autoUpdateCart(cart.getCart_id());
//        return createdOrders;
//    }
    @Override
    public List<StatisticRequest> getTotalAmountByMonth(int year){
        List<Object[]> results = ordersRepo.getTotalAmountByMonth(year);
        return results.stream()
                .map(this::mapToStatisticRequest)
                .collect(Collectors.toList());
    }

    @Override
    public List<Orders> findAll() {
        return ordersRepo.findAll();
    }

    @Override
    public List<ProductSaleRequest> getTotalAmountByDate(Date start, Date end){
        List<Object[]> results = ordersRepo.getTotalAmountByDate(start, end);
        return results.stream()
                .map(this::mapToProductSaleRequest)
                .collect(Collectors.toList());

    }

    @Override
    public Orders updateStatusOrderByStaff(String status, Long id, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Orders findOrder = findById(id);
        if(findOrder != null){
            findOrder.setStatus(status);
            findOrder.setUpdated_by(staff.getStaff_id());
            Orders saveOrder = ordersRepo.save(findOrder);
            if(saveOrder.getStatus().equals("5")){
                Bill bill = new Bill();
                bill.setOrder_id(findOrder.getOrder_id());
                bill.setCreated_at(LocalDateTime.now());
                bill.setStaff_id(staff.getStaff_id());
                billRepo.save(bill);
            }
            return saveOrder;
        }
        throw new Exception("Not found order by id " + id);
    }

    @Override
    public Orders orderPaymentBuyNow(OrderRequest rq, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        Optional<Product> product = productRepo.findById(rq.getProduct_id());
        Product getProduct = product.get();
        Orders orders = new Orders();
        orders.setAddress(rq.getAddress());
        orders.setStatus("3");
        orders.setCreated_at(LocalDateTime.now());
        orders.setRecipient_name(rq.getRecipient_name());
        orders.setUpdated_at(LocalDateTime.now());
        orders.setCustomer_id(customer.getCustomer_id());
        orders.setNote(rq.getNote());
        orders.setRecipient_phone(rq.getRecipient_phone());
        Orders createOrders = ordersRepo.save(orders);
        if(createOrders != null){
            Order_detail orderDetail = new Order_detail();
            orderDetail.setOrder_id(createOrders.getOrder_id());
            orderDetail.setProduct_id(rq.getProduct_id());
            orderDetail.setPrice(rq.getPrice());
            orderDetail.setQuantity(rq.getQuantity());
            getProduct.setQuantity(getProduct.getQuantity()-rq.getQuantity());
            productRepo.save(getProduct);
            Order_detail createDetail = orderDetailRepo.save(orderDetail);
            if(createDetail != null){
                int totalPrice = orderDetailRepo.totalPriceByOrderId(createOrders.getOrder_id());
                int totalQuantity = orderDetailRepo.totalQuantityByOrderId(createOrders.getOrder_id());
                createOrders.setTotal_quantity(totalQuantity);
                createOrders.setTotal_price(totalPrice);
                ordersRepo.save(createOrders);
            }

        }
        return createOrders;
    }

//    @Override
//    public Orders orderPaymentBuyCart(OrderRequest rq, String jwt) throws Exception {
//        User user = userService.findUserByJwt(jwt);
//        Customer customer = customerService.findByUserId(user.getUser_id());
//        Cart cart = cartService.findCartByJwtCustomer(jwt);
//        List<OrderDetail> list = new ArrayList<>();
//        int totalQuantity = 0;
//        for (CartDetail detail : cart.getCartDetails()) {
//            OrderDetail orderDetail = new OrderDetail();
//            // orderDetail.setOder_id(createdOrders.getOrder_id());
//            orderDetail.setPrice(detail.getPrice());
//            orderDetail.setProduct_id(detail.getProduct_id());
//            orderDetail.setQuantity(detail.getQuantity());
//            Optional<Product> find = productRepo.findById(detail.getProduct_id());
//            Product get = find.get();
//            get.setQuantity(get.getQuantity()-detail.getQuantity());
//            productRepo.save(get);
//            OrderDetail createDetail = orderDetailRepo.save(orderDetail);
//            totalQuantity += createDetail.getQuantity();
//            list.add(createDetail);
//        }
//        Orders orders = new Orders();
//        orders.setCreated_at(LocalDateTime.now());
//        orders.setNote(rq.getNote());
//        orders.setAddress(rq.getAddress());
//        orders.setRecipient_name(rq.getRecipient_name());
//        orders.setRecipient_phone(rq.getRecipient_phone());
//        orders.setUpdated_at(LocalDateTime.now());
//        orders.setCreated_by(customer.getCustomer_id());
//        orders.setStatus("3");
//        orders.setTotal_price(rq.getTotal_price());
//        orders.setTotal_quantity(totalQuantity);
//        Orders createdOrders = ordersRepo.save(orders);
//        for (OrderDetail item : list) {
//            item.setOrder_id(createdOrders.getOrder_id());
//            orderDetailRepo.save(item);
//        }
//        cartDetailService.deleteCartDetail(cart.getCart_id());
//        cartService.autoUpdateCart(cart.getCart_id());
//        return createdOrders;
//    }

    @Override
    public List<Orders> allOrderReceiveByStaff(String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        List<Orders> all = findAll();
        List<Orders> allReceive = new ArrayList<>();
        for(Orders od : all){
            if(Objects.equals(od.getUpdated_by(), staff.getStaff_id())){
                allReceive.add(od);
            }
        }
        return allReceive;
    }

    @Override
    public List<StatisticRequest> getTotalPriceByStatus() {
        List<Object[]> results = ordersRepo.findTotalPriceByStatus();

        if (results == null || results.isEmpty()) {
            // Handle the case when no results are found
            return Collections.emptyList();  // Return an empty list instead of null
        }

        return results.stream()
                .filter(Objects::nonNull)  // Filter out null results
                .map(this::mapToStatisticRequestSale)
                .collect(Collectors.toList());
    }

    private ProductSaleRequest mapToProductSaleRequest(Object[] result) {
        // Safely check if result is null
        if (result == null || result.length < 5) {
            throw new IllegalArgumentException("Invalid data in result array");
        }

        String productId = (String) result[0];
        String productName = (String) result[1];
        long totalSoldQuantity = (result[2] != null) ? (long) result[2] : 0L;
        long totalQuantity = (result[3] != null) ? (long) result[3] : 0L;
        Date datePay = (Date) result[4];

        return new ProductSaleRequest(productId, productName, totalSoldQuantity, totalQuantity, datePay);
    }
    private StatisticRequest mapToStatisticRequest(Object[] result) {
        int month = (int) result[0];
        long price = (long) result[1];
        return new StatisticRequest(month,price);
    }

    private StatisticRequest mapToStatisticRequestSale(Object[] result) {
        long price = (long) result[0];
        return new StatisticRequest(price);
    }
}
