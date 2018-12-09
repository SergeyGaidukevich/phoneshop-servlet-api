package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.exception.ArrayListProductDaoException;
import com.es.phoneshop.dao.impl.ArrayListProductDaoImpl;
import com.es.phoneshop.dao.impl.SortMode;
import com.es.phoneshop.dao.impl.SortProperty;
import com.es.phoneshop.model.Product;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {

    private static final String PRODUCTS_JSP = "/WEB-INF/pages/productList.jsp";
    private static final String PRODUCTS_ATTRIBUTE = "products";
    private static final String SEARCH_FIELD = "search";
    private static final String SORT_PROPERTY = "sortProperty";
    private static final String SORT_MODE = "sortMode";

    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        super.init();

        productDao = ArrayListProductDaoImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String textSearch = request.getParameter(SEARCH_FIELD);
        SortProperty sortProperty = EnumUtils.getEnum(SortProperty.class, request.getParameter(SORT_PROPERTY));
        SortMode sortMode = EnumUtils.getEnum(SortMode.class, request.getParameter(SORT_MODE));

        boolean sorting = sortProperty != null && sortMode != null;
        try {
            List<Product> products;

            if (sorting) {
                if (StringUtils.isNotBlank(textSearch)) {
                    products = productDao.findProducts(textSearch, sortProperty, sortMode);
                } else {
                    products = productDao.findProducts(sortProperty, sortMode);
                }
            } else if (StringUtils.isNotBlank(textSearch)) {
                products = productDao.findProducts(textSearch);
            } else {
                products = productDao.getAll();
            }

            request.setAttribute(PRODUCTS_ATTRIBUTE, products);
            forwardTo(request, response, PRODUCTS_JSP);
        } catch (ArrayListProductDaoException e) {
            response.sendError(404, e.getMessage());
        }
    }

    private void forwardTo(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }
}
