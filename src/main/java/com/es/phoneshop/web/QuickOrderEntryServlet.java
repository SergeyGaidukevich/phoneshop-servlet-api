package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDaoImpl;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class QuickOrderEntryServlet extends HttpServlet {
    private static final String QUICK_ORDER_ENTRY_JSP = "/WEB-INF/pages/quickOrderEntry.jsp";
    private static final String CART = "cart";
    private static final String ERROR_MESSAGE = "errorMessage";

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
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART);

        try {
            response.sendRedirect(request.getContextPath());
        } catch (IllegalArgumentException e) {
            request.setAttribute(ERROR_MESSAGE, "Field is not valid");
            request.getRequestDispatcher(QUICK_ORDER_ENTRY_JSP).forward(request, response);
        }

    }
}
