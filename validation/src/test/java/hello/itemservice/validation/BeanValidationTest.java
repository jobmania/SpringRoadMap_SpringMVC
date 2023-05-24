package hello.itemservice.validation;

import hello.itemservice.domain.item.Item;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {

    @Test
    void beanValidate(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Item item = new Item();
        item.setItemName(" "); // 공백
        item.setPrice(0);
        item.setQuantity(9999999);

        Set<ConstraintViolation<Item>> validate = validator.validate(item);

        for (ConstraintViolation<Item> itemConstraintViolation : validate) {
            System.out.println("itemConstraintViolation = " + itemConstraintViolation);
            System.out.println("itemConstraintViolation.MSG = " + itemConstraintViolation.getMessage());


        }

    }
}
