package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        //given

        //when

        //then
    }
    @Test
    void findAll(){
        //given
        Item item = new Item("itemA", 10000, 10)


        //when

        //then
    }
    @Test
    void update(){
        //given

        //when

        //then
    }



}
