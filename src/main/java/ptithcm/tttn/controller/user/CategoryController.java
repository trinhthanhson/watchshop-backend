package ptithcm.tttn.controller.user;

import com.google.api.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptithcm.tttn.entity.Category;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/user/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<Category>> getAllCategory(){
        ListEntityResponse<Category> res = new ListEntityResponse<>();
        try{
            List<Category> allCategory = categoryService.findAll();
            res.setData(allCategory);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
            res.setMessage("Success");
        }catch (Exception e) {
            res.setData(null);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            res.setMessage("Error: " +e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @GetMapping("/{id}/find")
    public ResponseEntity<EntityResponse<Category>> getCategoryById(@PathVariable Long id){
        EntityResponse<Category> res = new EntityResponse<>();
        try{
            Category category = categoryService.findById(id);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("Success");
            res.setData(category);
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
