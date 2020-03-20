package financialanalyzer.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

/**
 *
 * @author pldor
 */
@Component
public class HttpFetcher {

    private static final Logger LOGGER = Logger.getLogger(HttpFetcher.class.getName());
// Create a trust manager that does not validate certificate chains 
    private static final TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        }
    };

    private static SSLSocketFactory trustAllHosts(HttpsURLConnection connection) {
        SSLSocketFactory oldFactory = connection.getSSLSocketFactory();
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory newFactory = sc.getSocketFactory();
            connection.setSSLSocketFactory(newFactory);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        return oldFactory;
    }

    public HTMLPage getResponse(String _url, String _agent, boolean contentTextOnly) {
        try {
            URL urlObj = new URL(_url);
            HTMLPage htmlPage = new HTMLPage();
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            if (_agent != null) {
                conn.addRequestProperty("User-Agent",_agent);
            }
            int responseCode = conn.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            htmlPage.setUrl(_url);
            htmlPage.setStatusCode(responseCode);
            String rawHTML = response.toString();
            Document doc = Jsoup.parse(rawHTML, "https://www.google.com");
            String title = doc.title();
            if (contentTextOnly) {
                htmlPage.setContent(doc.body().text());
            } else {
                htmlPage.setContent(rawHTML);
            }
            htmlPage.setTitle(title);
            return htmlPage;
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }

        return null;
    }

    public HTMLPage getResponse(String _url,boolean contentTextOnly) {
        return this.getResponse(_url, "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0",contentTextOnly);

    }
}
