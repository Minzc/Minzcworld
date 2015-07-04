package com.yeezhao.wbcrawler.tools;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yeezhao.wbcrawler.base.CrawlerConsts;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author congzicun
 * @since 2015-06-12 10:55 PM
 */
public class SportCheckParser {
    Log LOG = LogFactory.getLog(SportCheckParser.class);

    private DefaultHttpClient client;

    private String username;
    private String password;
    private String cookie;
    private String requestKey;

    public SportCheckParser() {
        BasicCookieStore cookieStore = new BasicCookieStore();
        client = new DefaultHttpClient();
        client.setCookieStore(cookieStore);
        client.getParams().setParameter("http.protocol.cookie-policy",
                CookiePolicy.BROWSER_COMPATIBILITY);
        client.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 5000);
        client.setRedirectStrategy(new LaxRedirectStrategy());
        Configuration conf = HBaseConfiguration.create();
        conf.addResource(CrawlerConsts.CRAWLER_CONF_FILE);
        username = conf.get(CrawlerConsts.CHAMPS_USERNAME_CONF);
        password = conf.get(CrawlerConsts.CHAMPS_PASSWORD_CONF);
        cookie = conf.get(CrawlerConsts.CHAMPS_COOKIE_CONF);
        requestKey = conf.get(CrawlerConsts.CHAMPS_REQKEY_CONF);
    }


    public boolean login() throws UnsupportedEncodingException {
        try {
            HttpPost post = new HttpPost(
                    "https://www.champssports.com/login/login_action.cfm?secured=false&bv_RR_enabled=false&bv_AA_enabled=false&bv_JS_enabled=true&ignorebv=false");
            post.setHeader("Host", "www.champssports.com");
            post.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:32.0) Gecko/20100101 Firefox/32.0");
            post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            post.setHeader("Accept-Encoding", "gzip, deflate");
            post.setHeader("Cookie", cookie);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("login_email", username));
            nvps.add(new BasicNameValuePair("login_password", password));
            nvps.add(new BasicNameValuePair("requestKey", requestKey));
            nvps.add(new BasicNameValuePair("co_cd", "20"));


            post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

            HttpResponse response = client.execute(post);
            System.out.println("Number of Cookies" + client.getCookieStore().getCookies().size());
            for (Cookie c : client.getCookieStore().getCookies()) {
                System.out.println(c.getName() + "=" + c.getValue());
            }
            System.out.println(response.getStatusLine().getStatusCode());
            post.releaseConnection();
            return response.getStatusLine().getStatusCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkOut() throws IOException {
        String baseUrl = "https://www.footlocker.ca/en-CA/account/default.cfm?action=accountSummary";
        String url = String.format(baseUrl, requestKey);
        System.out.println(url);
        HttpGet getMethod = new HttpGet(url);
        cookie = "SSLC=web%2D50; TID=5555%2D29041502004529020743320%2D0; TRACK_USER_P=84710170415015017010789006; DOTOMI_SESSION=1; LOCALE=en%5FCA; CHOSEN_BANNER=1; USER_PROFILE=i59ExPpVtusd5ZTCXz%2BUelSScmPPaWL3qehFqdo7JX3zBXsOfRNQmieubD0txkp9Waf5Nsh77dPYEj8RvIThMNIPe3sTETP3dRbkAIQ%2B%2BSFv5crSlUzgWThHovjq3SVVfEkjQ7TMC8Olb26pLQ1uJGpGXSVAfiMHWBRSGD92N29gBA1ieSfp%2B%2FH7aHB9GpUikOBkgq1IXWurqQLNRUnT5OcCborKLJ9vf%2BCDrV%2FD2jeW3G7QK8lOgv9rznKcEXl5gv8VRlAQpPvBw7CrtNhIa0C99pfkS8Tc; SECURE_USER_PROFILE=RplQFHVMEOlHxzITDviQp4jo8EaOrRtTq9UDFM5Ja6FlSJs917jrz4Lv1zyJHZXzYVkZ0RPWFHyC0qqt6TF69cJJIfk%2F79IcQL32l%2BRLxNw%3D; BROWSER_SESSION=i59ExPpVtuvm%2F6qnRJINVD4jpHfiLZCLOnibuwVzoTTxlkl%2B%2FKSvGQZprj8YX0OnnXb0k5IrAwN0Zy39D8kb6qJlZvdxyG9H1lmgGyNgCmTIb1QfwMgBT6M2%2FUNpDZJPcPX0%2BSX8aFbyhauwTqJ0mnJ2nA3TItZ8DIOfjaDmuSY63zYh8ja0OlvcmuKzhKNYxw08g1IpV11yoh44faIJe%2Ff6Cj9fXPYsVdv%2BHy3Y%2FT4aZ4Ezvn2DOw%3D%3D; mdr_browser=desktop; optimizelySegments=%7B%22590160201%22%3A%22ff%22%2C%22594010114%22%3A%22direct%22%2C%22594010115%22%3A%22false%22%2C%22594510077%22%3A%22none%22%7D; optimizelyEndUserId=oeu1435992619701r0.5927517521386682; optimizelyBuckets=%7B%7D; CHOSEN_BANNER_ID=Free%20Shipping%20Over%20%2499; cmTPSet=Y; CoreID6=18053168842414359926200&ci=90409327; 90409327_clogin=v=1&l=1435992620&e=1435995028823; BAZAARVOICEUSER=51BF709BFB3E2C390462E9D082D9DEF6646174653D323031352D30372D3034267573657269643D31323435393126656D61696C616464726573733D636F6E677A6963756E40676D61696C2E636F6D; INLINECARTSUMMARY=0%2C0; SESSION=Mj4CmMnaNaHkd0g1dlFI0WaI9ACw9cTjGVT%2FOOVjJw6yoJHiFhWFmZEzqD1mLuPOafQy5%2Fl9gWdHwQn9TIlym%2BfqNUDosp1CgCyiDz1domg%3D";
        getMethod.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:32.0) Gecko/20100101 Firefox/32.0");
        getMethod.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        getMethod.setHeader("Connection", "keep-alive");
        getMethod.setHeader("Cookie", cookie);
        HttpResponse response = client.execute(getMethod);
        String entity = EntityUtils.toString(response.getEntity());
//        System.out.println(entity);
        return true;
    }
    public boolean addCart(String requestKey, String sku) throws UnsupportedEncodingException {
        try {
            String baseUrl = "http://www.footlocker.ca/catalog/miniAddToCart.cfm?secure=0";
            HttpPost postMethod = new HttpPost(baseUrl);
            cookie = "SSLC=web%2D50; TID=5555%2D29041502004529020743320%2D0; TRACK_USER_P=84710170415015017010789006; DOTOMI_SESSION=1; LOCALE=en%5FCA; CHOSEN_BANNER=1; USER_PROFILE=i59ExPpVtusd5ZTCXz%2BUelSScmPPaWL3qehFqdo7JX3zBXsOfRNQmieubD0txkp9Waf5Nsh77dPYEj8RvIThMNIPe3sTETP3dRbkAIQ%2B%2BSFv5crSlUzgWThHovjq3SVVfEkjQ7TMC8Olb26pLQ1uJGpGXSVAfiMHWBRSGD92N29gBA1ieSfp%2B%2FH7aHB9GpUikOBkgq1IXWurqQLNRUnT5OcCborKLJ9vf%2BCDrV%2FD2jeW3G7QK8lOgv9rznKcEXl5gv8VRlAQpPvBw7CrtNhIa0C99pfkS8Tc; SECURE_USER_PROFILE=RplQFHVMEOlHxzITDviQp4jo8EaOrRtTq9UDFM5Ja6FlSJs917jrz4Lv1zyJHZXzYVkZ0RPWFHyC0qqt6TF69cJJIfk%2F79IcQL32l%2BRLxNw%3D; BROWSER_SESSION=i59ExPpVtuvm%2F6qnRJINVD4jpHfiLZCLOnibuwVzoTTxlkl%2B%2FKSvGQZprj8YX0OnnXb0k5IrAwN0Zy39D8kb6qJlZvdxyG9H1lmgGyNgCmTIb1QfwMgBT6M2%2FUNpDZJPcPX0%2BSX8aFbyhauwTqJ0mnJ2nA3TItZ8DIOfjaDmuSY63zYh8ja0OlvcmuKzhKNYxw08g1IpV11yoh44faIJe%2Ff6Cj9fXPYsVdv%2BHy3Y%2FT4aZ4Ezvn2DOw%3D%3D; mdr_browser=desktop; optimizelySegments=%7B%22590160201%22%3A%22ff%22%2C%22594010114%22%3A%22direct%22%2C%22594010115%22%3A%22false%22%2C%22594510077%22%3A%22none%22%7D; optimizelyEndUserId=oeu1435992619701r0.5927517521386682; optimizelyBuckets=%7B%7D; CHOSEN_BANNER_ID=Free%20Shipping%20Over%20%2499; cmTPSet=Y; CoreID6=18053168842414359926200&ci=90409327; 90409327_clogin=v=1&l=1435992620&e=1435995028823; BAZAARVOICEUSER=51BF709BFB3E2C390462E9D082D9DEF6646174653D323031352D30372D3034267573657269643D31323435393126656D61696C616464726573733D636F6E677A6963756E40676D61696C2E636F6D; INLINECARTSUMMARY=0%2C0; SESSION=Mj4CmMnaNaHkd0g1dlFI0WaI9ACw9cTjGVT%2FOOVjJw6yoJHiFhWFmZEzqD1mLuPOafQy5%2Fl9gWdHwQn9TIlym%2BfqNUDosp1CgCyiDz1domg%3D";
            postMethod.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:32.0) Gecko/20100101 Firefox/32.0");
            postMethod.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            postMethod.setHeader("Connection", "keep-alive");
            postMethod.setHeader("Cookie", cookie);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("requestKey", "A6AA813g9EF410A0"));
            nvps.add(new BasicNameValuePair("qty", "1"));
            nvps.add(new BasicNameValuePair("size", "08.5"));
            nvps.add(new BasicNameValuePair("the_model_nbr", "248364"));
            nvps.add(new BasicNameValuePair("sku", "0501432"));
            nvps.add(new BasicNameValuePair("storeNumber", "00000"));
            nvps.add(new BasicNameValuePair("fulfillmentType", "SHIP_FROM_STORE"));
            nvps.add(new BasicNameValuePair("storeCostOfGoods", "0.00"));
            nvps.add(new BasicNameValuePair("inlineAddToCart", "0"));
            nvps.add(new BasicNameValuePair("coreMetricsCategory", "blank"));
            nvps.add(new BasicNameValuePair("inlineAddToCart", "1"));
            postMethod.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));


            HttpResponse response = client.execute(postMethod);
            String entity = EntityUtils.toString(response.getEntity());
            System.out.println(entity);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getRequestKey() {
        String url = "http://www.champssports.com/shoppingcart/gateway?action=requestKey&_=1435986994189";
        HttpGet getMethod = new HttpGet(url);
        getMethod.setHeader("Cookie", cookie);

        HttpResponse response = null;
        try {
            response = client.execute(getMethod);
            String entity = EntityUtils.toString(response.getEntity());
            System.out.println(entity);
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject)jsonParser.parse(entity);
            return jo.getAsJsonObject("data").get("RequestKey").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static void main(String[] args) throws Exception {
        // 创建 Options 对象

        SportCheckParser webParser = new SportCheckParser();
        webParser.checkOut();
        webParser.addCart("", "");

    }
}

//    public String getFrontPage() throws IOException {
//        String url = "http://www.champssports.com/login/login_form.cfm?secured=false&status=success&errmsg=&bv_RR_enabled=false&bv_AA_enabled=false&bv_JS_enabled=true&ignorebv=false";
//        HttpGet getMethod = new HttpGet(url);
//        getMethod.setHeader("Host", "www.champssports.com");
//        getMethod.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:32.0) Gecko/20100101 Firefox/32.0");
//        getMethod.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        getMethod.setHeader("Accept-Encoding", "gzip, deflate");
//        getMethod.setHeader("Connection", "keep-alive");
//
//        HttpResponse response = client.execute(getMethod);
//        System.out.println("Number of Cookies" + client.getCookieStore().getCookies().size());
//
//        String entity = EntityUtils.toString(response.getEntity());
//        Document doc = Jsoup.parse(entity);
//        Elements links = doc.getElementsByTag("form");
//        String requestKey = "";
//        for (Element link : links) {
//            Elements e = link.select("input#requestKey");
//            requestKey = e.attr("value");
//            System.out.println(e);
//            System.out.println(requestKey + "#");
//        }
//        getMethod.releaseConnection();
//        return requestKey;
//
//    }
