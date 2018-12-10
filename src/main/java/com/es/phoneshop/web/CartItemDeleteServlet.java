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

public class CartItemDeleteServlet extends HttpServlet {
    private static final String CART = "cart";

    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();

        productDao = ArrayListProductDaoImpl.getInstance();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART);
        Long id = getProductId(request);
        try {
            Product product = productDao.getProduct(id);
            cartService.deleteCartItem(cart, product);

            response.sendRedirect(request.getContextPath() + "/cart?message=Cart item " + product.getCode() + " removed successfully");
        } catch (ArrayListProductDaoException e) {
            response.sendError(404, e.getMessage());
        }
    }

    private Long getProductId(HttpServletRequest request) {
        StringBuffer uri = request.getRequestURL();
        String stringId = uri.substring(uri.lastIndexOf("/") + 1);
        return Long.parseLong(stringId);
    }
}
