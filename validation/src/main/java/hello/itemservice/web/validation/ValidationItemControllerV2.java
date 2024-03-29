package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
@Slf4j
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @InitBinder // 콘트롤러에 요청 될때 항상 실행된다!
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(itemValidator);
    }


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    //    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes
            , Model model) {


        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            // model에 담기는 오브젝트, 필드명 , 오류 메시지
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다."));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~  1,000,000 까지 허용됩니다!"));
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 99999까지 허용됩니다!"));
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", "가격 * 수량 10,000원 이상이어야 합니다. 현재 값은 = " + resultPrice));
            }
        }


        // 검증에 실패시 다시 입력 로직으로 가기
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            // binding result는 자동으로 model에 담긴다!
            return "validation/v2/addForm";
        }

        //  성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes
            , Model model) {


        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            // model에 담기는 오브젝트, 필드명 , 오류 메시지
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품 이름은 필수입니다."));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 ~  1,000,000 까지 허용됩니다!"));
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 99999까지 허용됩니다!"));
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", null, null, "가격 * 수량 10,000원 이상이어야 합니다. 현재 값은 = " + resultPrice));
            }
        }


        // 검증에 실패시 다시 입력 로직으로 가기
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            // binding result는 자동으로 model에 담긴다!
            return "validation/v2/addForm";
        }

        //  성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes
            , Model model) {


        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());


        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            // model에 담기는 오브젝트, 필드명 , 오류 메시지
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
            }
        }


        // 검증에 실패시 다시 입력 로직으로 가기
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            // binding result는 자동으로 model에 담긴다!
            return "validation/v2/addForm";
        }

        //  성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


    //    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes
            , Model model) {

        if (bindingResult.hasErrors()) {
            log.info("objectName={}", bindingResult.getObjectName());
            log.info("target={}", bindingResult.getTarget());
            return "validation/v2/addForm";
        }

        // 디테일 먼저 찾고 -> 범용
//            new String[]{"required.item.itemName", "required"};

        // 검증 로직 (공백, 빈칸) 편하게 구현
//        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"itemName","required");

        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            // model에 담기는 오브젝트, 필드명 , 오류 메시지
//            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,new String[]{"required.item.itemName"},null,null));
            bindingResult.rejectValue("itemName", "required"); // codes rejc
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.addError(new FieldError("item","price", item.getPrice(),false,new String[]{"range.item.price"},new Object[]{1000,1000000},null));
            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
//            bindingResult.addError(new FieldError("item","quantity", item.getQuantity(),false,new String[]{"max.item.quantity"},new Object[]{9999},null));
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
//                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"},new Object[]{10000,resultPrice},null));
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }


        // 검증에 실패시 다시 입력 로직으로 가기
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            // binding result는 자동으로 model에 담긴다!
            return "validation/v2/addForm";
        }

        //  성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes
            , Model model) {

        itemValidator.validate(item,bindingResult);


        // 검증에 실패시 다시 입력 로직으로 가기
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            // binding result는 자동으로 model에 담긴다!
            return "validation/v2/addForm";
        }

        //  성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @PostMapping("/add") // @Validated 하나로 검증기 도입 완료!!
    public String addItemV6(@Validated @ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes
            , Model model) {


        // 검증에 실패시 다시 입력 로직으로 가기
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            // binding result는 자동으로 model에 담긴다!
            return "validation/v2/addForm";
        }

        //  성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

