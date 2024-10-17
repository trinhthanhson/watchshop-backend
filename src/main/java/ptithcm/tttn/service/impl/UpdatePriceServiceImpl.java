package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Staff;
import ptithcm.tttn.entity.Update_price;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.UpdatePriceRepo;
import ptithcm.tttn.service.ProductService;
import ptithcm.tttn.service.StaffService;
import ptithcm.tttn.service.UpdatePriceService;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class UpdatePriceServiceImpl implements UpdatePriceService {

    private final UpdatePriceRepo updatePriceRepo;
    private final UserService userService;
    private final StaffService staffService;


    public UpdatePriceServiceImpl( UpdatePriceRepo updatePriceRepo, UserService userService, StaffService staffService) {
        this.updatePriceRepo = updatePriceRepo;
        this.userService = userService;
        this.staffService = staffService;
    }

    @Override
    @Transactional
    public Update_price updatePriceProduct(String id, Update_price priceUpdateDetail, String jwt) throws Exception {
        Update_price update = updatePriceRepo.findByProductId(id);
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        if(priceUpdateDetail.getPrice_new() == update.getPrice_new()){
            return update;
        }else {
            update.setUpdated_by(staff.getStaff_id());
            update.setUpdated_at(LocalDateTime.now());
            update.setPrice_old(update.getPrice_new());
            update.setPrice_new(priceUpdateDetail.getPrice_new());
        }
        return updatePriceRepo.save(update);
    }
}
