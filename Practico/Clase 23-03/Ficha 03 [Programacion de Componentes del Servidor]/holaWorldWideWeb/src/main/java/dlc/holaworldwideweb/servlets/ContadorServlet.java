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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author philip
 */
public class ContadorServlet extends HttpServlet
{
    private int contador;
    
    public ContadorServlet()
    {
        System.err.println("Ejecutando constructor()..........................................");
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        contador = Integer.parseInt(config.getInitParameter("initValue"));
        System.err.println("Ejecutando init()..........................................");

    }
  
   
//    @Override
//    public void init() throws ServletException
//    {
//        contador = 10;
//        System.err.println("Ejecutando init()..........................................");
//    }
   
    
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
       
        System.err.print("Ejecutando service().........................................");
        response.setContentType("text/html;charset=UTF-8");
        contador++;
        try (PrintWriter out = response.getWriter())
        {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ContadorServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>Servlet ContadorServlet at " + request.getContextPath() + "</h3>");
            out.println("<h1>" + contador + " visitas</h1>");
            out.println("<hr/>");
            out.println("<table border=0>");
            Enumeration<String> e = request.getHeaderNames();
            while (e.hasMoreElements()) {
                String headerName = (String)e.nextElement();
                String headerValue = request.getHeader(headerName);
                out.println("<tr><td bgcolor=\"#CCCCCC\">");
                out.println(headerName);
                out.println("</td><td>");
                out.println(headerValue);
                out.println("</td></tr>");
            }
            out.println("</table>");
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
