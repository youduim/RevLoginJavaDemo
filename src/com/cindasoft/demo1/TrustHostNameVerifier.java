package com.cindasoft.demo1;


import javax.net.ssl.*;


/**
 * Created by king on 16-8-24.
 */
public class TrustHostNameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String s, SSLSession sslSession) {
        return true;
    }
}
