package hello.itemservice.domain.item;


import lombok.Data;

@Data // 롬복 Data를 핵심 도메인에 사용하는 위험. getter, setter정도는 ㄱㅊ/
// dto 경우 data를 사용해도 무방하지만,
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item(){

    }
    public Item( String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
