package ptithcm.tttn.controller.staff;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Product;
import ptithcm.tttn.entity.Update_price;
import ptithcm.tttn.request.ProductRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.service.ProductService;
import ptithcm.tttn.service.UpdatePriceService;

@RestController
@RequestMapping("/api/staff/product")
public class StaffProductController {

    private final ProductService productService;
    private final UpdatePriceService updatePriceService;

    public StaffProductController(ProductService productService, UpdatePriceService updatePriceService) {
        this.productService = productService;
        this.updatePriceService = updatePriceService;
    }


    @PostMapping("/add")
    public ResponseEntity<EntityResponse> addProductByStaff(@RequestBody ProductRequest rq, @RequestHeader("Authorization") String jwt) {
        EntityResponse res = new EntityResponse();
        try {
            Product saveProduct = productService.createProduct(rq, jwt);
            res.setData(saveProduct);
            res.setMessage("Success");
            res.setStatus(HttpStatus.CREATED);
            res.setCode(HttpStatus.CREATED.value());
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
            res.setData(null);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<EntityResponse> updateProductByStaff(@RequestBody ProductRequest productRequest, @RequestHeader("Authorization") String jwt, @PathVariable String id) {
        EntityResponse res = new EntityResponse();
        try {
            Product saveProduct = productService.updateProduct(id, productRequest, jwt);
            res.setData(saveProduct);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
            res.setData(null);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PutMapping("/{id}/update/price")
    public ResponseEntity<EntityResponse> updatePriceProductByStaff(@RequestBody Update_price rq, @RequestHeader("Authorization") String jwt, @PathVariable String id) {
        EntityResponse res = new EntityResponse();
        try {
            Update_price savePrice = updatePriceService.updatePriceProduct(id, rq, jwt);
            res.setData(savePrice);
            res.setMessage("Success");
            res.setStatus(HttpStatus.CREATED);
            res.setCode(HttpStatus.CREATED.value());
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
            res.setData(null);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProductByStaff(@RequestHeader("Authorization") String jwt, @PathVariable String productId) {
        ApiResponse res = new ApiResponse();
        try {
            Product delete = productService.deleteProduct(productId, jwt);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }
}
