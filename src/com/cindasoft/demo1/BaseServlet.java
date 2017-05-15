package com.cindasoft.demo1;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by king on 16-8-23.
 * 这个Servlet映射的URL即客户端访问路径（从这里进入系统）
 */
public class BaseServlet extends HttpServlet {
    static String ydIdentifyURL="http://192.168.1.88:7443/cgi/identify?token=";
    static  CloseableHttpClient client;
    static ObjectMapper mapper=new ObjectMapper();
    static {
        try {
            SSLContext context= SSLContexts.custom().loadTrustMaterial(
                    new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                            return true;
                        }
                    }
            ).build();
            SSLConnectionSocketFactory factory=new SSLConnectionSocketFactory(context,new TrustHostNameVerifier());
            client= HttpClients.custom().setSSLSocketFactory(factory).build();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 获取客户端访问时携带的token参数并访问有度服务验证该token，获取该token对应的身份信息。
     * 验证通过即可以正式进入系统了
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        if (token==null){
            response.getOutputStream().println("no token");
            return;
        }
        System.out.println(token);

        HttpGet httpget = new HttpGet(ydIdentifyURL+token);
        CloseableHttpResponse rsp= client.execute(httpget);
        if(rsp.getStatusLine().getStatusCode()!=200){
            System.out.println(rsp.getStatusLine());
            response.getOutputStream().println("call auth error");
            return;
        }
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
        rsp.getEntity().writeTo(buffer);
        YouduResponse ret= mapper.readValue(buffer.toByteArray(), YouduResponse.class);
        //as other server side logic,if auth passed,then set up session info or get inner identify token and redirect to system dashboard..
        //return login page on failed.
        /**
        request.getSession().setAttribute("user","user object");
        response.sendRedirect("/index.jsp");
        */
        if (ret.getStatus().getCode()!=0){
            response.getOutputStream().println(ret.getStatus().getMessage());
            rsp.close();
            return;
        }

        String str = new String(buffer.toByteArray(), "UTF-8");
        System.out.println( str);

        response.setHeader("Content-Type","application/json;charset=utf-8");
        buffer.writeTo(response.getOutputStream());
        rsp.close();
    }
}
