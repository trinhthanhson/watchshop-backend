package ptithcm.tttn.service;

import ptithcm.tttn.entity.Update_price;

public interface UpdatePriceService {
    Update_price updatePriceProduct(String id, Update_price priceUpdateDetail, String jwt) throws Exception;

}
