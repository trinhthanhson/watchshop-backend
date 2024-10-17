package ptithcm.tttn.service;

import ptithcm.tttn.entity.Brand;

import java.util.List;

public interface BrandService {

    List<Brand> findAll() throws Exception;

    Brand findById(Long id) throws Exception;

    Brand findByName(String name) throws Exception;
    Brand createBrand(Brand brand, String jwt) throws Exception;
    Brand updateBrand(Long id, Brand brand, String jwt) throws Exception;
    boolean checkExistBrand(String name);
    Brand deleteBrand(Long id, String jwt) throws Exception;


}
