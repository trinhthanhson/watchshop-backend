package ptithcm.tttn.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Product;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.ProductService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/user/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<Product>> findAllProduct() {

        ListEntityResponse<Product> res = new ListEntityResponse<>();
        try {
            List<Product> allProduct = productService.findAll();
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setData(allProduct);
        } catch (Exception e) {
            res.setMessage("Error: " + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            res.setData(null);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<EntityResponse> findProductById(@PathVariable String id) {
        EntityResponse res = new EntityResponse();
        try {
            Product findById = productService.findById(id);
            res.setMessage("Success");
            res.setData(findById);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            res.setMessage("Error: " + e.getMessage());
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/find")
    public ResponseEntity<ListEntityResponse> getAllProductByDetail(@RequestParam String keyword) {
        ListEntityResponse res = new ListEntityResponse();
        try {
            List<Product> find = productService.findByDetail(keyword);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setData(find);
        } catch (Exception e) {
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
            res.setStatus(HttpStatus.CONFLICT);
            res.setData(null);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }
}
