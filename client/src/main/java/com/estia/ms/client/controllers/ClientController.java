package com.estia.ms.client.controllers;

import com.estia.ms.client.bean.*;
import com.estia.ms.client.proxies.MsCartProxy;
import com.estia.ms.client.proxies.MsOrdersProxy;
import com.estia.ms.client.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.spring5.processor.SpringTextareaFieldTagProcessor;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {
    @Autowired
    private MsProductProxy msProductProxy;

    @Autowired
    private MsCartProxy msCartProxy;

    @Autowired
    private MsOrdersProxy msOrdersProxy;

    private long idCart = 1;
    private long idOrder = 1;

    @RequestMapping("/")
    public String index(Model model){
        List<ProductBean> products = msProductProxy.list();
        CartBean cartBean = new CartBean();
        OrdersBean ordersBean = new OrdersBean();
        List<OrdersBean> listOrdersBean;
        if (!msOrdersProxy.getOrder(idOrder).isPresent()){
            msOrdersProxy.createNewOrder(ordersBean);
        }
        if(!msCartProxy.getCart(idCart).isPresent()){
            msCartProxy.createNewCart(cartBean);
        }
        cartBean = msCartProxy.getCart(idCart).get();
        ordersBean = msOrdersProxy.getOrder(idOrder).get();
        listOrdersBean = msOrdersProxy.list();
        model.addAttribute("panier", cartBean);
        model.addAttribute("listOrders", listOrdersBean);
        model.addAttribute("products", products);
        return "index";
    }

    @RequestMapping("/product-detail/{id}")
    public String productDetail(Model model, @PathVariable Long id){
        Optional<ProductBean> product = msProductProxy.get(id);
        model.addAttribute("product", product.get());
        return "product-detail";
    }

    @RequestMapping("/add-product/{id}")
    public RedirectView addProduct(Model model, @PathVariable Long id){
        Optional<ProductBean> productBean = msProductProxy.get(id);
        CartItemBean cartItemBean = new CartItemBean(id, 1, productBean.get().getName(), productBean.get().getDescription(), productBean.get().getIllustration(), productBean.get().getPrice());
        msCartProxy.addProductToCart(idCart, cartItemBean);
        model.addAttribute("panier", msCartProxy.getCart(idCart));
        return new RedirectView("/");
    }

    @RequestMapping("/show-cart/{id}")
    public String cartDetail(Model model, @PathVariable Long id){
        Optional<CartBean> cart = msCartProxy.getCart(id);
        model.addAttribute("prixTotal", prixTotal(cart.get()));
        model.addAttribute("cart", cart.get().getProducts());
        return "show-cart";
    }

    @RequestMapping("/commande-passer")
    public String commander(Model model){
        Optional<CartBean> cart = msCartProxy.getCart(idCart);
        Optional<OrdersBean> ordersBeanOptional = msOrdersProxy.getOrder(idOrder);
        OrdersBean ordersBean = ordersBeanOptional.get();
        for (int i = 0; i < cart.get().getProducts().size(); i++){
            OrderItemBean orderItemBean = new OrderItemBean();
            orderItemBean.setProductId(cart.get().getProducts().get(i).getProductId());
            orderItemBean.setQuantity(cart.get().getProducts().get(i).getQuantity());
            orderItemBean.setName(cart.get().getProducts().get(i).getName());
            orderItemBean.setDescription(cart.get().getProducts().get(i).getDescription());
            orderItemBean.setIllustration(cart.get().getProducts().get(i).getIllustration());
            orderItemBean.setPrice(cart.get().getProducts().get(i).getPrice());
            ordersBean = msOrdersProxy.addItemToOrder(idOrder, orderItemBean).getBody();
        }
        ordersBean.setPrix(prixTotal(cart.get()));
        msOrdersProxy.updateOrder(idOrder, ordersBean);
        model.addAttribute("prixTotal", ordersBean.getPrix());
        cart.get().cleanPanier();
        msCartProxy.updateCart(idCart, cart.get());
        model.addAttribute("order", ordersBean.getPaniers());
        idOrder++;
        idCart++;
        return "commande-passer";
    }

    @RequestMapping("/list-orders")
    public String listOrders(Model model){
        List<OrdersBean> listOrders = msOrdersProxy.list();
        model.addAttribute("commandes", listOrders);
        return "list-orders";
    }

    @RequestMapping("/commande-detail/{id}")
    public String ordersDetail(Model model, @PathVariable Long id){
        Optional<OrdersBean> orders = msOrdersProxy.getOrder(id);
        model.addAttribute("infoCommande", orders.get());
        return "commande-detail";
    }

    public Double prixTotal(CartBean cartBean){
        Double result = 0.0;
        for (int i = 0; i < cartBean.getProducts().size(); i++){
            result += cartBean.getProducts().get(i).getPrice();
        }
        return result;
    }
}
