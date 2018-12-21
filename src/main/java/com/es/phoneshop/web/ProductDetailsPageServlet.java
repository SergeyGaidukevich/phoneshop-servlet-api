package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.exception.ArrayListProductDaoException;
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

import javax.servlet.ServletContext;
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
            Product product = productDao.getProduct(id);
            request.setAttribute(PRODUCT, product);

            addProductsToViewed(request, product);
            increaseProductPopularity(product);

            request.getRequestDispatcher(PRODUCT_JSP).forward(request, response);
        } catch (ArrayListProductDaoException e) {
            response.sendError(404, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = getCart(request);

        Long productId = getProductId(request);
        try {
            Product product = productDao.getProduct(productId);
            request.setAttribute(PRODUCT, product);

            boolean isErrorInStockCount = true;
            int quantity = 0;
            try {
                quantity = Integer.valueOf(request.getParameter(QUANTITY));
                try {
                    cartService.addProductToCart(cart, product, quantity);
                    isErrorInStockCount = false;
                } catch (IllegalArgumentException e) {
                    request.setAttribute(QUANTITY_ERROR, "Not enough quantity in stock");
                }
            } catch (NumberFormatException e) {
                request.setAttribute(QUANTITY_ERROR, "Not a number");
            }

            if (isErrorInStockCount) {
                request.getRequestDispatcher(PRODUCT_JSP).forward(request, response);
            } else {
                response.sendRedirect(request.getRequestURI() +
                        String.format("?message=add %d product(s) to cart Successfully", quantity));
            }
        } catch (ArrayListProductDaoException e) {
            response.sendError(404, e.getMessage());
        }
    }

    private void increaseProductPopularity(Product product) {
        ServletContext context = getServletContext();
        PopularProducts popularProducts = (PopularProducts) context.getAttribute(MOST_POPULAR_PRODUCTS);
        if (popularProducts == null) {
            popularProducts = new PopularProducts();
        }

        popularProductService.increaseProductPopularity(popularProducts, product);
        List<Product> arrayMostPopularProducts = popularProductService.getMostPopularProducts(popularProducts);
        context.setAttribute(ARRAY_POPULAR_PRODUCTS, arrayMostPopularProducts);
        context.setAttribute(MOST_POPULAR_PRODUCTS, popularProducts);
    }

    private void addProductsToViewed(HttpServletRequest request, Product product) {
        HttpSession session = request.getSession();
        ViewedProducts viewedProducts = (ViewedProducts) session.getAttribute(VIEWED_PRODUCTS);
        if (viewedProducts == null) {
            viewedProducts = new ViewedProducts();
        }

        viewedProductsService.addProductsToViewed(viewedProducts, product);
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
