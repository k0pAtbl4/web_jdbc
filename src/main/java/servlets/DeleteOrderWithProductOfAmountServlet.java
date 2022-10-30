package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jdbc.dbWorker.Repository;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class DeleteOrderWithProductOfAmountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(
                "forJsp/deleteOrderWithProductOfAmount.jsp");

        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session != null) {
            if (session.getAttribute("type").equals("admin")) {
                requestDispatcher.forward(request, response);
            } else {
                out.println("Only for admin users");
            }
        } else {
            out.println("no session");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String amount = request.getParameter("amount");
        String product = request.getParameter("product");
        PrintWriter out = response.getWriter();

        Repository r = new Repository();
        try {
            r.deleteOrderWithProductOfAmount(product, Integer.parseInt(amount));
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }
}
