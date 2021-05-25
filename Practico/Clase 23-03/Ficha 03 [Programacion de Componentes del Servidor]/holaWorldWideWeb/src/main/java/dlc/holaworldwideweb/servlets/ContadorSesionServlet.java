/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dlc.holaworldwideweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author philip
 */
@WebServlet(name = "ContadorSesion", urlPatterns = {"/visitassesion"})
public class ContadorSesionServlet extends HttpServlet
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
            // Usando la Sesion
            HttpSession session = request.getSession();
            if (session.getAttribute("contador") != null)
                contador = ((Integer) session.getAttribute("contador"));
            else
                contador = 10;
            contador++;
            session.setAttribute("contador", contador);
            if (contador >= 20)
                session.invalidate();
//                session = request.getSession();
//                session.removeAttribute("contador");

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ContadorServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>Servlet ContadorServlet at " + request.getContextPath() + "</h3>");
            out.println("<h1>" + contador + " visitas</h1>");
            out.println("<hr/>");
            out.println("<ul>");
            out.println("<li>Id de sesión: ");
            out.println(session.getId());
            out.print("</li><li>Creada: ");
            out.println(session.getCreationTime());
            out.print("</li><li>Ultimo acceso: ");
            out.println(session.getLastAccessedTime());
            out.println("</li>");
            out.println("</ul>");
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
