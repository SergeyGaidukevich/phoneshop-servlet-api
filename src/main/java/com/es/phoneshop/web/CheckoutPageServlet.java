package com.es.phoneshop.web;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Order;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.CartServiceImpl;
import com.es.phoneshop.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckoutPageServlet extends HttpServlet {
    private static final String CHECKOUT_JSP = "/WEB-INF/pages/checkout.jsp";
    private static final String CART = "cart";
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String DELIVERY_ADDRESS = "deliveryAddress";
    private static final String ORDER_OVERVIEW = "/orderOverview/";
    private static final String ERROR_MESSAGE = "errorMessage";

    private OrderService orderService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();

        cartService = CartServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(CHECKOUT_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART);
        String name = request.getParameter(NAME);
        String deliveryAddress = request.getParameter(DELIVERY_ADDRESS);
        String phone = request.getParameter(PHONE);

        try {
            Order order = orderService.placeOrder(cart, name, deliveryAddress, phone);
            cartService.clearCart(cart);
            response.sendRedirect(request.getContextPath() + ORDER_OVERVIEW + order.getId());
        } catch (IllegalArgumentException e) {
            request.setAttribute(ERROR_MESSAGE, "Field is not valid");
            request.getRequestDispatcher(CHECKOUT_JSP).forward(request, response);
        }

    }
}
