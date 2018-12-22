package com.es.phoneshop.web;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.impl.ArrayListOrderDaoImpl;
import com.es.phoneshop.model.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    private static final String ORDER = "order";
    private static final String OVERVIEW_JSP = "/WEB-INF/pages/orderOverview.jsp";
    private OrderDao orderDao;

    @Override
    public void init() throws ServletException {
        super.init();

        orderDao = ArrayListOrderDaoImpl.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer uri = request.getRequestURL();
        String stringId = uri.substring(uri.lastIndexOf("/") + 1);
        Long id = Long.parseLong(stringId);
        Order order = orderDao.getOrder(id);

        request.setAttribute(ORDER, order);
        request.getRequestDispatcher(OVERVIEW_JSP).forward(request, response);
    }
}
