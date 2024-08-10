package ptithcm.tttn.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ProductSaleRequest {
    private String product_id;
    private String product_name;
    private long total_sold;
    private long total_quantity;
}
