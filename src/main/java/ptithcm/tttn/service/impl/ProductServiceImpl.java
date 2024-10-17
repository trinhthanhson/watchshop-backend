package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.*;
import ptithcm.tttn.repository.ProductRepo;
import ptithcm.tttn.repository.UpdatePriceRepo;
import ptithcm.tttn.request.ProductRequest;
import ptithcm.tttn.request.ProductSaleRequest;
import ptithcm.tttn.service.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final UserService userService;
    private final StaffService staffService;
    private final ProductRepo productRepo;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final UpdatePriceRepo updatePriceRepo;

    public ProductServiceImpl(UserService userService, StaffService staffService, ProductRepo productRepo, BrandService brandService, CategoryService categoryService, UpdatePriceRepo updatePriceRepo) {

        this.userService = userService;
        this.staffService = staffService;
        this.productRepo = productRepo;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.updatePriceRepo = updatePriceRepo;
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Product findById(String id) throws Exception {
        Optional<Product> find = productRepo.findById(id);
        if (find.isPresent()) {
            return find.get();
        }
        throw new Exception("Not found product by id " + id);
    }

    @Override
    public List<Product> findByDetail(String desc) {
        return productRepo.searchProducts(desc);
    }

    @Override
    public Product findByName(String name) throws Exception {
        Product find = productRepo.findByName(name);
        if (find != null) {
            return find;
        }
        throw new Exception("not found product by name " + name);
    }

    @Override
    public List<Product> findByCategoryId(Long id) throws Exception {
        List<Product> find = productRepo.findByCategoryId(id);
        if (find != null) {
            return find;
        }
        throw new Exception("Not found product by category id " + id);

    }

    @Override
    public List<Product> findByBrandId(Long id) throws Exception {
        List<Product> find = productRepo.findByBrandId(id);
        if (find != null) {
            return find;
        }
        throw new Exception("Not found product by brand id " + id);
    }

    @Override
    @Transactional
    public Product createProduct(ProductRequest product, String jwt) throws Exception {
        Product create = new Product();
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Brand brand = brandService.findByName(product.getBrand_name());
        Category category = categoryService.findByName(product.getCategory_name());
        String id = generateNewProductId();
        Product save = new Product();
        if (!checkExistProductName(product.getProduct_name())) {
            create.setProduct_name(product.getProduct_name());
            create.setProduct_id(id);
            create.setCreated_at(LocalDateTime.now());
            create.setCreated_by(staff.getStaff_id());
            create.setBrand_id(brand.getBrand_id());
            create.setCategory_id(category.getCategory_id());
            create.setQuantity(product.getQuantity());
            create.setUpdated_at(LocalDateTime.now());
            create.setUpdated_by(staff.getStaff_id());
            create.setStatus("Active");
            create.setBand_material(product.getBand_material());
            create.setBand_width(product.getBand_width());
            create.setCase_diameter(product.getCase_diameter());
            create.setCase_material(product.getCase_material());
            create.setCase_thickness(product.getCase_thickness());
            create.setColor(product.getColor());
            create.setDetail(product.getDetail());
            create.setDial_type(product.getDial_type());
            create.setFunc(product.getFunc());
            create.setGender(product.getGender());
            create.setMachine_movement(product.getMachine_movement());
            create.setModel(product.getModel());
            create.setQuantity(product.getQuantity());
            create.setSeries(product.getSeries());
            create.setWater_resistance(product.getWater_resistance());
            create.setImage(product.getImage());
            save = productRepo.save(create);
            if (save != null) {
                Update_price updatePrice = new Update_price();
                updatePrice.setCreated_at(LocalDateTime.now());
                updatePrice.setProduct_id(save.getProduct_id());
                updatePrice.setUpdated_at(LocalDateTime.now());
                updatePrice.setCreated_by(staff.getStaff_id());
                updatePrice.setUpdated_by(staff.getStaff_id());
                updatePrice.setPrice_new(product.getPrice());
                updatePrice.setPrice_old(product.getPrice());
                updatePriceRepo.save(updatePrice);
            }
        } else {
            throw new Exception("exist product by name " + product.getProduct_name());
        }
        return save;
    }

    @Override
    public Product updateProduct(String id, ProductRequest product, String jwt) throws Exception {
        Product find = findById(id);
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Brand brand = brandService.findByName(product.getBrand_name());
        Category category = categoryService.findByName(product.getCategory_name());
        Product update = new Product();

        if(find != null){
            if(checkExistProductName(product.getProduct_name())) {
                find.setProduct_name(product.getProduct_name());
                find.setCreated_at(LocalDateTime.now());
                find.setCreated_by(staff.getStaff_id());
                find.setBrand_id(brand.getBrand_id());
                find.setCategory_id(category.getCategory_id());
                find.setQuantity(product.getQuantity());
                find.setUpdated_at(LocalDateTime.now());
                find.setUpdated_by(staff.getStaff_id());
                find.setStatus("Active");
                find.setBand_material(product.getBand_material());
                find.setBand_width(product.getBand_width());
                find.setCase_diameter(product.getCase_diameter());
                find.setCase_material(product.getCase_material());
                find.setCase_thickness(product.getCase_thickness());
                find.setColor(product.getColor());
                find.setDetail(product.getDetail());
                find.setDial_type(product.getDial_type());
                find.setFunc(product.getFunc());
                find.setGender(product.getGender());
                find.setMachine_movement(product.getMachine_movement());
                find.setModel(product.getModel());
                find.setQuantity(product.getQuantity());
                find.setSeries(product.getSeries());
                find.setWater_resistance(product.getWater_resistance());
                find.setImage(product.getImage());
                Update_price updatePrice = updatePriceRepo.findByProductId(id);
                if(product.getPrice() != updatePrice.getPrice_new()){
                    updatePrice.setPrice_old(updatePrice.getPrice_new());
                    updatePrice.setPrice_new(product.getPrice());
                    updatePrice.setUpdated_by(staff.getStaff_id());
                    updatePrice.setUpdated_at(LocalDateTime.now());
                    updatePriceRepo.save(updatePrice);
                }
            }else{
                find.setProduct_name(product.getProduct_name());
            }
        }
        return update = productRepo.save(find);
    }
    @Override
    public boolean checkExistProductName(String name) throws Exception {
        Product find = productRepo.findByName(name);
        if(find != null){
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public Product deleteProduct(String product_id, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Product product = findById(product_id);
        product.setStatus("Inactive");
        product.setUpdated_by(staff.getStaff_id());
        return productRepo.save(product);
    }


    @Override
    public List<ProductSaleRequest> getProductSales() {
        List<Object[]> results = productRepo.getProductSales();
        return results.stream()
                .map(this::mapToProductSaleRequest)
                .collect(Collectors.toList());
    }

    private String generateNewProductId() {

        List<Product> products = productRepo.findAll();
        int maxId = 0;
        for (Product p : products) {
            String idStr = p.getProduct_id().substring(2);  // Remove "DH" prefix
            int id = Integer.parseInt(idStr);
            if (id > maxId) {
                maxId = id;
            }
        }
        return String.format("DH%08d", maxId + 1);
    }

    private ProductSaleRequest mapToProductSaleRequest(Object[] result) {
        String productId = (String) result[0];
        String productName = (String) result[1];
        long totalSoldQuantity = (long) result[2];
        long totalQuanity = (long) result[3];
        return new ProductSaleRequest(productId, productName, totalSoldQuantity, totalQuanity, null);
    }
}
