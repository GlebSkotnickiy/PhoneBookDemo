package com.glebskotnickiy.servlets;

import com.glebskotnickiy.phonebook.PhoneBook;
import com.glebskotnickiy.phonebook.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "bookServlet", value = "/book-servlet")
public class PhoneBookServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        PhoneBook phoneBook = (PhoneBook) session.getAttribute("phoneBook");
        if (session.isNew()) {
            phoneBook = new PhoneBook();
        } else {
            phoneBook = (PhoneBook) session.getAttribute("phoneBook");
        }
        out.println("<html>");
        out.println("<title>Phone Book app</title>");

        out.println("<body>");
        out.println("<h1>Book</h1>");
        out.println("<form method='post'>");
        out.println("<input type='text' name='name'>");
        out.println("<input type='text' name='phoneNumber'>");
        out.println("<input type='submit' value='add'>");
        out.println("</form>");
        out.println("<form method='get'>");
        out.println("<input type='submit' name='sortByName' value='sortByName'>");
        out.println("</form>");
        out.println("<form method='get'>");
        out.println("<input type='submit' name='reset' value='resetUsers'>");
        out.println("</form>");

        if (request.getParameter("resetUsers") != null) {
            for (User user : phoneBook.getUsers()) {
                out.println("<li>" + "Name: " + user.getName() + "  " + "Number: " + user.getNumber() + "</li>");
            }
        }
        if (request.getParameter("sortByName") != null) {
            for (User user : phoneBook.getSortedUsersByLetter()) {
                out.println("<li>" + "Name: " + user.getName() + "  " + "Number: " + user.getNumber() + "</li>");
            }
        }
        if (request.getParameter("sortByName") == null) {
            for (User user : phoneBook.getUsers()) {
                out.println("<li>" + "Name: " + user.getName() + "  " + "Number: " + user.getNumber() + "</li>");
            }
        }

        out.println("</body>");
        out.println("</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        PhoneBook phoneBook = (PhoneBook) session.getAttribute("phoneBook");
        if (phoneBook == null) {
            phoneBook = new PhoneBook();
        }

        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phoneNumber");
        User user = new User(name, phoneNumber);
        phoneBook.getUsers().add(user);
        session.setAttribute("phoneBook", phoneBook);

        response.sendRedirect("book-servlet");
    }

}