package ru.akirakozov.sd.refactoring.servlet;

import dao.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static html.ProductHTML.printProductsHTML;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractProductServlet {

    public GetProductsServlet(ProductDao productDao) {
        super(productDao);
    }

    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        printProductsHTML(productDao.getProducts(), response.getWriter());
    }
}
