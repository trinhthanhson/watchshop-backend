package ptithcm.tttn.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptithcm.tttn.entity.Brand;
import ptithcm.tttn.entity.Category;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.BrandService;

import java.util.List;

@RestController
@RequestMapping("/api/user/brand")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<Brand>> getAllBrand() {
        ListEntityResponse<Brand> res = new ListEntityResponse<>();
        try {
            List<Brand> allBrand = brandService.findAll();
            res.setData(allBrand);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
            res.setMessage("Success");
        } catch (Exception e) {
            res.setData(null);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            res.setMessage("Error: " +e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @GetMapping("/{id}/find")
    public ResponseEntity<EntityResponse<Brand>> getBrandById(@PathVariable Long id){
        EntityResponse<Brand> res = new EntityResponse<>();
        try{
            Brand brand = brandService.findById(id);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("Success");
            res.setData(brand);
            res.setStatus(HttpStatus.OK);
        }catch (Exception e){
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("Error: " + e.getMessage());
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res,res.getStatus());
    }
}
