package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.exception.ArrayListProductDaoException;
import com.es.phoneshop.dao.impl.ArrayListProductDaoImpl;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class CartPageServlet extends HttpServlet {
    private static final String CART_JSP = "/WEB-INF/pages/cart.jsp";
    private static final String QUANTITY_ERRORS = "quantityErrors";
    private static final String CART = "cart";
    private static final String QUANTITY = "quantity";
    private static final String PRODUCT_ID = "productId";
    private static final String NOT_ENOUGH_QUANTITY_IN_STOCK = "Not enough quantity in stock";
    private static final String NOT_A_NUMBER = "Not a number";
    private static final String MESSAGE_CART_UPDATE_SUCCESSFULLY = "?message=cart update successfully";
    private static final String PRODUCT_NO_FOUND_IN_CART_RELOAD_PAGE = "Product no found in Cart, reload page!";

    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();

        productDao = ArrayListProductDaoImpl.getInstance();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART);

        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART, cart);
        }

        request.getRequestDispatcher(CART_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART);
        String[] quantities = request.getParameterValues(QUANTITY);
        String[] productIds = request.getParameterValues(PRODUCT_ID);
        Map<Long, String> quantityErrors = new HashMap<>();

        for (int i = 0; i < productIds.length; i++) {
            Long productId = Long.valueOf(productIds[i]);
            try {
                Product product = productDao.getProduct(productId);
                int quantity = Integer.valueOf(quantities[i]);
                try {
                    cartService.updateCart(cart, product, quantity);
                } catch (IllegalArgumentException e) {
                    quantityErrors.put(productId, NOT_ENOUGH_QUANTITY_IN_STOCK);
                } catch (NoSuchElementException e) {
                    quantityErrors.put(productId, PRODUCT_NO_FOUND_IN_CART_RELOAD_PAGE);
                }
            } catch (NumberFormatException e) {
                quantityErrors.put(productId, NOT_A_NUMBER);
            } catch (ArrayListProductDaoException e) {
                response.sendError(404, e.getMessage());
            }
        }

        if (quantityErrors.isEmpty()) {
            response.sendRedirect(request.getRequestURI() + MESSAGE_CART_UPDATE_SUCCESSFULLY);
        } else {
            request.setAttribute(QUANTITY_ERRORS, quantityErrors);
            request.getRequestDispatcher(CART_JSP).forward(request, response);
        }
    }
}
