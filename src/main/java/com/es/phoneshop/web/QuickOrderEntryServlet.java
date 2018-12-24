package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.exception.DaoException;
import com.es.phoneshop.dao.impl.ArrayListProductDaoImpl;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.CartServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class QuickOrderEntryServlet extends HttpServlet {
    private static final String QUICK_ORDER_ENTRY_JSP = "/WEB-INF/pages/quickOrderEntry.jsp";
    private static final String CART = "cart";
    private static final String CODE = "code";
    private static final String QUANTITY = "quantity";
    private static final String NOT_A_NUMBER = "Not a number";
    private static final String MESSAGE_CART_UPDATE_SUCCESSFULLY = "?message=cart update successfully";
    private static final String QUANTITY_ERRORS = "quantityErrors";
    private static final String CODE_ERRORS = "codeErrors";
    private static final String RELOAD_PAGE = "Product with code = %s no found in Cart, reload page!";

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
        request.getRequestDispatcher(QUICK_ORDER_ENTRY_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] quantities = request.getParameterValues(QUANTITY);
        String[] codes = request.getParameterValues(CODE);
        Map<Integer, String> quantityErrors = new HashMap<>();
        Map<Integer, String> codeErrors = new HashMap<>();
        Cart quickCart = new Cart();

        for (int i = 0; i < codes.length; i++) {
            String code = codes[i];
            if (NumberUtils.isNumber(quantities[i])) {
                try {
                    Product product = productDao.get(code);
                    int quantity = Integer.parseUnsignedInt(quantities[i]);
                    cartService.addProductToCart(quickCart, product, quantity);
                } catch (DaoException e) {
                    codeErrors.put(i, e.getMessage());
                } catch (IllegalArgumentException e) {
                    quantityErrors.put(i, e.getMessage());
                } catch (NoSuchElementException e) {
                    codeErrors.put(i, String.format(RELOAD_PAGE, e.getMessage()));
                }
            } else if (StringUtils.isNotBlank(code)) {
                quantityErrors.put(i, NOT_A_NUMBER);
            }
        }

        if (quantityErrors.isEmpty() && codeErrors.isEmpty()) {
            cartService.addCartItemsToCart(getCart(request), quickCart.getCartItems());
            response.sendRedirect(request.getRequestURI() + MESSAGE_CART_UPDATE_SUCCESSFULLY);
        } else {
            request.setAttribute(QUANTITY_ERRORS, quantityErrors);
            request.setAttribute(CODE_ERRORS, codeErrors);
            request.getRequestDispatcher(QUICK_ORDER_ENTRY_JSP).forward(request, response);
        }
    }

    private Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART);

        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART, cart);
        }

        return cart;
    }
}
