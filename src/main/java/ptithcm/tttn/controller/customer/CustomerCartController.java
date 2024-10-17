package ptithcm.tttn.controller.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Cart_detail;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.CartDetailService;

import java.util.List;

@RestController
@RequestMapping("/api/customer/cart")
public class CustomerCartController {
    private final CartDetailService cartDetailService;

    public CustomerCartController(CartDetailService cartDetailService) {
        this.cartDetailService = cartDetailService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCartDetail(@RequestHeader("Authorization") String jwt, @RequestBody Cart_detail detail){
        ApiResponse res = new ApiResponse();
        try{
            Cart_detail create = cartDetailService.createCart(detail,jwt);
            res.setCode(HttpStatus.CREATED.value());
            res.setStatus(HttpStatus.CREATED);
            res.setMessage("Add cart success");
        }catch (Exception e){
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            res.setMessage("Add cart fail");
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @GetMapping("")
    public ResponseEntity<ListEntityResponse<Cart_detail>> findCartDetailByCustomerJwt(@RequestHeader("Authorization") String jwt){
        ListEntityResponse<Cart_detail> res = new ListEntityResponse<>();
        try{
            List<Cart_detail> all = cartDetailService.findCartByJwt(jwt);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
            res.setData(all);
            res.setMessage("Success");
        }catch (Exception e){
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            res.setData(null);
            res.setMessage("Error: " + e.getMessage());
        }
        return  new ResponseEntity<>(res, res.getStatus());
    }
}
