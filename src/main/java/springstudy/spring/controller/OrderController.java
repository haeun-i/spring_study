package springstudy.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springstudy.spring.domain.Address;
import springstudy.spring.domain.Order;
import springstudy.spring.domain.User;
import springstudy.spring.service.CartService;

import springstudy.spring.service.OrderService;
import springstudy.spring.service.CustomUserDetailService;


import java.util.List;
@Controller
@RequiredArgsConstructor
public class OrderController {


    private final CustomUserDetailService userService;
    private final OrderService orderService;
    private final CartService cartService;


    @GetMapping(value = "/orders") // 주문내역 전체확인
    public String orderList(Model model, Long userNum, Long num) {
        User user = userService.findByNum(userNum);
        List<Order> orders = orderService.findOrders(user.getUserNum());
        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping(value = "/order") // 주문 실행
    public String order(@RequestParam("userNum") Long userNum,
                        @RequestParam("cartIdList") Long[] cartIdList,
                        @RequestParam("address") Address address,
                        @RequestParam("pay") Long payId) {
        orderService.createOrder(userNum, cartIdList, address, payId);
        return "redirect:/orders";
    }


    @PostMapping(value = "/orders/{orderId}/delete") // 주문 삭제
    public String deleteOrder(@PathVariable("orderId") Long orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/orders";
    }

    @PostMapping(value = "/orders/{orderId}/cancel") // 주문 취소
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}