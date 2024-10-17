package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Cart_detail;
import ptithcm.tttn.entity.Customer;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.CartDetailRepo;
import ptithcm.tttn.service.CartDetailService;
import ptithcm.tttn.service.CustomerService;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CartDetailServiceImpl implements CartDetailService {

    private final CartDetailRepo cartDetailRepo;
    private final UserService userService;
    private final CustomerService customerService;

    public CartDetailServiceImpl(CartDetailRepo cartDetailRepo, UserService userService, CustomerService customerService) {
        this.cartDetailRepo = cartDetailRepo;
        this.userService = userService;
        this.customerService = customerService;
    }

    @Override
    @Transactional
    public Cart_detail createCart(Cart_detail cartDetail, String jwt) throws Exception {
        User find = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(find.getUser_id());
        Cart_detail create = new Cart_detail();
        create.setCustomer_id(customer.getCustomer_id());
        create.setQuantity(cartDetail.getQuantity());
        create.setProduct_id(cartDetail.getProduct_id());
        return  cartDetailRepo.save(create);
    }

    @Override
    public List<Cart_detail> findCartByJwt(String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        return cartDetailRepo.findAllByCustomerId(customer.getCustomer_id());
    }
}
