package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.exception.DaoException;
import com.es.phoneshop.dao.impl.ArrayListProductDaoImpl;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.PopularProducts;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ViewedProducts;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.PopularProductService;
import com.es.phoneshop.service.ViewedProductsService;
import com.es.phoneshop.service.impl.CartServiceImpl;
import com.es.phoneshop.service.impl.PopularProductsServiceImpl;
import com.es.phoneshop.service.impl.ViewedProductsServiceImpl;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProductDetailsPageServlet extends HttpServlet {

    private static final String CART = "cart";
    private static final String PRODUCT_JSP = "/WEB-INF/pages/product.jsp";
    private static final String QUANTITY = "quantity";
    private final static String PRODUCT = "product";
    private static final String QUANTITY_ERROR = "quantityError";
    private static final String VIEWED_PRODUCTS = "viewedProducts";
    private static final String MOST_POPULAR_PRODUCTS = "mostPopularProducts";
    private static final String ARRAY_POPULAR_PRODUCTS = "arrayMostPopularProducts";

    private ProductDao productDao;
    private CartService cartService;
    private ViewedProductsService viewedProductsService;
    private PopularProductService popularProductService;

    @Override
    public void init() throws ServletException {
        super.init();

        productDao = ArrayListProductDaoImpl.getInstance();
        cartService = CartServiceImpl.getInstance();
        viewedProductsService = ViewedProductsServiceImpl.getInstance();
        popularProductService = PopularProductsServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = getProductId(request);
            Product product = productDao.get(id);
            request.setAttribute(PRODUCT, product);

            addProductsToViewed(request, product);
            increaseProductPopularity(product);

            request.getRequestDispatcher(PRODUCT_JSP).forward(request, response);
        } catch (DaoException e) {
            response.sendError(404, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = productDao.get(getProductId(request));
            request.setAttribute(PRODUCT, product);

            if (NumberUtils.isNumber(request.getParameter(QUANTITY))) {
                addProductToCart(request, response, product);
            } else {
                request.setAttribute(QUANTITY_ERROR, "Not a number");
                request.getRequestDispatcher(PRODUCT_JSP).forward(request, response);
            }
        } catch (DaoException e) {
            response.sendError(404, e.getMessage());
        }
    }

    private void addProductToCart(HttpServletRequest request, HttpServletResponse response, Product product) throws ServletException, IOException {
        int quantity = Integer.valueOf(request.getParameter(QUANTITY));
        try {
            cartService.addProductToCart(getCart(request), product, quantity);

            response.sendRedirect(request.getRequestURI() +
                    String.format("?message=add %d product(s) to cart Successfully", quantity));
        } catch (IllegalArgumentException e) {
            request.setAttribute(QUANTITY_ERROR, e.getMessage());
            request.getRequestDispatcher(PRODUCT_JSP).forward(request, response);
        }
    }

    private void increaseProductPopularity(Product product) {
        PopularProducts popularProducts = (PopularProducts) getServletContext().getAttribute(MOST_POPULAR_PRODUCTS);
        if (popularProducts == null) {
            popularProducts = new PopularProducts();
        }

        popularProductService.increaseProductPopularity(popularProducts, product);
        List<Product> arrayMostPopularProducts = popularProductService.getMostPopularProducts(popularProducts);

        getServletContext().setAttribute(ARRAY_POPULAR_PRODUCTS, arrayMostPopularProducts);
        getServletContext().setAttribute(MOST_POPULAR_PRODUCTS, popularProducts);
    }

    private void addProductsToViewed(HttpServletRequest request, Product product) {
        HttpSession session = request.getSession();
        ViewedProducts viewedProducts = (ViewedProducts) session.getAttribute(VIEWED_PRODUCTS);
        if (viewedProducts == null) {
            viewedProducts = new ViewedProducts();
        }

        viewedProductsService.addProductToViewed(viewedProducts, product);

        session.setAttribute(VIEWED_PRODUCTS, viewedProducts);
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

    private Long getProductId(HttpServletRequest request) {
        StringBuffer uri = request.getRequestURL();
        String stringId = uri.substring(uri.lastIndexOf("/") + 1);

        return Long.parseLong(stringId);
    }
}
