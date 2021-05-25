/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dlc.holaworldwideweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author philip
 */
@WebServlet(name = "ContadorCookie", urlPatterns = {"/visitascookies"})
public class ContadorCookieServlet extends HttpServlet
{
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
       
        response.setContentType("text/html;charset=UTF-8");
        int contador;
        try (PrintWriter out = response.getWriter())
        {
            /* TODO output your page here. You may use following sample code. */
            // Usando las cookies
            Cookie[] cookies = request.getCookies();
            Optional<Cookie> optional = Arrays.stream(cookies)
                                   .filter(c -> "cuenta".equals(c.getName()))
                                   .findFirst();

            if(optional.isPresent()) 
                contador = Integer.parseInt(optional.get().getValue());
            else
            {
                ServletContext contexto = getServletContext();
                contador = Integer.parseInt(contexto.getInitParameter("contador-inicial"));
            }
            contador++;
            
            Cookie respCookie = new Cookie("cuenta", "" + contador);
            response.addCookie(respCookie);

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ContadorServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>Servlet ContadorServlet at " + request.getContextPath() + "</h3>");
            out.println("<h1>" + contador + " visitas</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

    @Override
    public void destroy()
    {
        System.err.println("Ejecutando destroy().......................................");
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
