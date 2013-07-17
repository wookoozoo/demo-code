package com.wookoozoo.networkproxy;

import java.util.Properties;
import java.util.Date;
import java.net.URL;
import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//   This   is   a   simple   example   that   shows   how   to   get   HTTP   connections   to   work    
//   with   a   proxy   server.   If   all   goes   well,   we   should   be   able   to   post   some    
//   data   to   a   public   cgi   script   and   get   back   the   resulting   HTML.     If   anyone      
//   knows   some   "gotchas"   when   combining   Java   and   proxies,   I'd   love   to   hear    
//   about   them.     Send   your   thoughts   to   kurr@ctron.com.      

public class ProxyTest {
    public static void main(String argv[]) throws IOException {
        String proxyHostName = "localhost";
        String proxyPort = "7070";
        System.setProperty("socksProxySet", "true");
        System.setProperty("socksProxyHost", proxyHostName);
        System.setProperty("socksProxyPort", proxyPort);

        try {
            HttpClientNoPoolUtil.get("http://www.baidu.com", null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.clearProperty("socksProxySet");
        System.clearProperty("socksProxyHost");
        System.clearProperty("socksProxyPort");
//        System.setProperty("socksProxyHost", proxyHostName);
//        System.setProperty("socksProxyPort", "7079");
        HttpClientNoPoolUtil.get("http://www.soso.com", null, null, null);
    }
}
