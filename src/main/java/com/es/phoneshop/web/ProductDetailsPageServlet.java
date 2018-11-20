package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.ArrayListProductDaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        super.init();

        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer uri = request.getRequestURL();
        String stringId = uri.substring(uri.lastIndexOf("/") + 1);
        Long id = Long.parseLong(stringId);

        try {
            request.setAttribute("product", productDao.getProduct(id));
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        } catch (ArrayListProductDaoException e){
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }
}
