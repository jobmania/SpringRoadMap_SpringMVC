package hello.itemservice.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
/**
 * FAST 빠른배송
 * NORMARL : 일반배송
 * SLOW : 느린배송
* */
@Data
@AllArgsConstructor
public class DeliveryCode {

    private String code;
    private String displayName; // 고객한테 출력
}
