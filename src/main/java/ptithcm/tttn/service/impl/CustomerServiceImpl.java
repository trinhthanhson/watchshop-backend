package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Customer;
import ptithcm.tttn.repository.CustomerRepo;
import ptithcm.tttn.service.CustomerService;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Customer findByUserId(Long id) throws Exception {
        Customer find = customerRepo.findByUserId(id);
        if(find != null){
            return find;
        }
        throw new Exception("not found customer by user id " + id);
    }

    @Override
    public List<Customer> findAll() throws Exception{
        List<Customer> find = customerRepo.findAll();
        if(find != null){
            return find;
        }
        throw new Exception("Customer is empty");
    }
}
