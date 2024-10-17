package ptithcm.tttn.service;

import ptithcm.tttn.entity.Cart_detail;

import java.util.List;

public interface CartDetailService {

    Cart_detail createCart(Cart_detail cartDetail, String jwt) throws Exception;

    List<Cart_detail> findCartByJwt(String jwt) throws Exception;
}
