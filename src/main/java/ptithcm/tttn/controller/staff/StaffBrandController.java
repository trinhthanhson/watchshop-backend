package ptithcm.tttn.controller.staff;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Brand;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.service.BrandService;

@RestController
@RequestMapping("/api/staff/brand")
public class StaffBrandController {

    private final BrandService brandService;

    public StaffBrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/add")
    public ResponseEntity<EntityResponse<Brand>> addBrandByManager(@RequestBody Brand brand, @RequestHeader("Authorization") String jwt) throws Exception {
        EntityResponse<Brand> res = new EntityResponse<>();
        try{
            if (brand.getBrand_name().isEmpty()) {
                throw new Exception("Please enter complete information");
            }
            Brand saveBrand = brandService.createBrand(brand,jwt);
            res.setData(saveBrand);
            res.setMessage("Success");
            res.setStatus(HttpStatus.CREATED);
            res.setCode(HttpStatus.CREATED.value());
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("erorr " + e.getMessage());
            res.setData(null);
        }
        return new ResponseEntity<>(res,res.getStatus());
    }



    @PutMapping("/{id}/update")
    public ResponseEntity<EntityResponse<Brand>> updatedBrandByStaff(@RequestBody Brand brand, @RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception{
        EntityResponse<Brand> res = new EntityResponse<>();
        try{
            if (brand.getBrand_name().isEmpty()) {
                throw new Exception("Please enter complete information");
            }
            Brand saveBrand = brandService.updateBrand(id,brand,jwt);
            res.setData(saveBrand);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("Error " + e.getMessage());
            res.setData(null);
        }
        return new ResponseEntity<>(res,res.getStatus());

    }

    @GetMapping("/find")
    public ResponseEntity<EntityResponse<Brand>> findBrandByName(@RequestParam String name,@RequestHeader("Authorization") String jwt) throws Exception {
        EntityResponse<Brand> res = new EntityResponse<>();
        Brand brand = brandService.findByName(name);
        res.setData(brand);
        res.setMessage("Success");
        res.setStatus(HttpStatus.CREATED);
        res.setCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(res,res.getStatus());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteBrandByStaff(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        ApiResponse res = new ApiResponse();
        try{

            Brand saveBrand = brandService.deleteBrand(id,jwt);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

}
