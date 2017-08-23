/**
 * HTTP工具。<br>
 */
package com.hrhg.framework.utils;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HTTP工具。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public final class HttpUtils {
    /**
     * 数据类型_json
     */
    public static final String DATA_TYPE_JSON = "JSON";
    /**
     * 数据类型_xml
     */
    public static final String DATA_TYPE_XML = "XML";
    /**
     * 格式化_Get请求URL
     */
    public static final String FORMAT_GET_URL = "%s?%s";
    /**
     * 响应结果为空
     */
    public static final String EMPTY_RESULT = "响应结果为空！";
    /**
     * HTTP请求响应失败
     */
    public static final String FAIL_RESULT = "HTTP请求响应失败！";
    /**
     * 正则表达式_参数分割符
     */
    public static final String PATTERN_QUERY_SPLIT_PARAM = "[&]";
    /**
     * 正则表达式_KEY&VALUE分割符
     */
    public static final String PATTERN_QUERY_SPLIT_KEYVALUE = "[=]";
    /**
     * URL分隔符
     */
    public static final String URL_SEPARATOR = "/";
    /**
     * 数据类型_json
     */
    public static final String URL_SPLIT_PROTOCOL = "://";
    /**
     * HTTP协议
     */
    public static final String PROTOCOL_HTTP = "http";
    /**
     * HTTPS协议
     */
    public static final String PROTOCOL_HTTPS = "https";
    /**
     * SSL协议版本——TLSv1
     */
    public static final String PROTOCOL_TLS_V1 = "TLSv1";
    /**
     * SSL协议版本——TLSv1.2
     */
    public static final String PROTOCOL_TLS_V1_2 = "TLSv1.2";
    /**
     * SSL协议版本——SSLv2Hello
     */
    public static final String PROTOCOL_SSL_V2 = "SSLv2Hello";
    /**
     * SSL协议版本——SSLv3
     */
    public static final String PROTOCOL_SSL_V3 = "SSLv3";

    /**
     * 默认信任HTTP策略。<br>
     */
    private static class DefaultHttpTrustStrategy implements TrustStrategy {
        /**
         * 信任策略结果。<br>
         *
         * @param x509Certificates x509证书
         * @param s                证书内容
         * @return 信任策略结果
         * @throws CertificateException 认证异常
         */
        @Override
        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            return true;
        }
    }

    /**
     * 按顺序组合URL路径。<br>
     *
     * @param url 主机路径
     * @param uri URI集合
     * @return 生成最终URL
     */
    public static String joinUrlPath(String url, String... uri) {
        if (StringUtils.isEmpty(url)) {
            // 主机为空
            return StringUtils.EMPTY_STRING;
        }

        if (DataUtils.isNullOrEmpty(uri)) {
            // 没有URI的情况
            return url;
        }

        // URL生成器
        StringBuilder sb = new StringBuilder(url.endsWith(URL_SEPARATOR) ? url.substring(0, url.length() - 2) : url);

        for (String path : uri) {
            if (StringUtils.isEmpty(path)) {
                // 当前为空
                continue;
            }

            if (!path.startsWith(URL_SEPARATOR)) {
                // 非分隔符开始
                // 添加分隔符
                sb.append(URL_SEPARATOR);
            }

            // 添加当前路径
            sb.append(path.endsWith(URL_SEPARATOR) ? path.substring(0, path.length() - 2) : path);
        }

        // 获取URL全路径
        return sb.toString();
    }

    /**
     * GET请求，发送请求Bean，获取响应Bean
     *
     * @param url     请求URL地址
     * @param reqBean 请求Bean
     * @return 响应Bean
     * @throws Exception 系统异常
     */
    public static <P extends Object, R extends Object> R getJsonBean(
            String url, P reqBean, Class<R> clazzResult) throws Exception {
        // 生成Get请求参数
        String reqParam = makeGetParameter(reqBean);
        // 向服务器请求
        String respData = getJsonString(url, reqParam);

        if (StringUtils.isEmpty(respData)) {
            // 响应数据时，实例化对象
            return ClazzUtils.create(clazzResult);
        }

        // 获取响应结果
        return JsonUtils.json2Object(respData, clazzResult);
    }

    /**
     * POST请求，发送请求Bean，获取响应Bean
     *
     * @param url     请求URL地址
     * @param reqBean 请求Bean
     * @return 响应Bean
     * @throws Exception 系统异常
     */
    public static <P extends Object, R extends Object> R postJsonBean(
            String url, P reqBean, Class<R> clazzResult) throws Exception {
        // 生成json数据
        String reqData = JsonUtils.object2Json(reqBean);

        // 向服务器请求
        String respData = postJsonString(url, reqData);

        if (StringUtils.isEmpty(respData)) {
            // 响应数据时，实例化对象
            return ClazzUtils.create(clazzResult);
        }

        // 获取响应结果
        return JsonUtils.json2Object(respData, clazzResult);
    }

    /**
     * DELETE请求，发送请求Bean，获取响应Bean
     *
     * @param url 请求URL地址
     * @param uri 请求参数
     * @return 响应Bean
     * @throws Exception 系统异常
     */
    public static <R extends Object> R deleteJsonBean(
            String url, String uri, Class<R> clazzResult) throws Exception {
        // 生成delete请求参数
        String reqParam = uri;
        // 向服务器请求
        String respData = deleteJsonString(url, reqParam);

        if (StringUtils.isEmpty(respData)) {
            // 响应数据时，实例化对象
            return ClazzUtils.create(clazzResult);
        }

        // 获取响应结果
        //return JsonUtils.json2Object(respData, clazzResult);
        return null;
    }

    /**
     * GET请求，发送JSON请求数据，获取响应JSON数据
     *
     * @param url     请求URL地址
     * @param reqData 请求JSON数据
     * @return 响应JSON数据
     * @throws Exception 网路请求各种异常
     */
    public static String getJsonString(String url, String reqData) throws Exception {
        // json格式
        return getString(url, reqData, DATA_TYPE_JSON);
    }

    /**
     * POST请求，发送JSON请求数据，获取响应JSON数据
     *
     * @param url     请求URL地址
     * @param reqData 请求JSON数据
     * @return 响应JSON数据
     * @throws Exception 网路请求各种异常
     */
    public static String postJsonString(String url, String reqData) throws Exception {
        // json格式
        return postString(url, reqData, DATA_TYPE_JSON);
    }

    /**
     * DELETE请求，发送JSON请求数据，获取响应JSON数据
     *
     * @param url     请求URL地址
     * @param reqData 请求JSON数据
     * @return 响应JSON数据
     * @throws Exception 网路请求各种异常
     */
    public static String deleteJsonString(String url, String reqData) throws Exception {
        // json格式
        return deleteString(url, reqData, DATA_TYPE_JSON);
    }

    /**
     * 根据Get请求URI和请求参数bean，生成完整URL。<br>
     *
     * @param uri       请求URI
     * @param paramBean 请求参数bean
     * @param <T>       bean类型
     * @return Get请求完整URL
     * @throws Exception 系统异常
     */
    public static <T extends Object> String makeGetUrl(String uri, T paramBean) throws Exception {
        if (StringUtils.isEmpty(uri)) {
            // 请求资源为空
            return null;
        }

        // 生成Get请求参数
        String reqParam = makeGetParameter(paramBean);

        if (StringUtils.isEmpty(reqParam)) {
            return uri;
        }

        // 获取响应URL
        return String.format(FORMAT_GET_URL, uri, reqParam);
    }

    /**
     * 根据bean对象生成属性值为非空的序列参数。<br>
     *
     * @param paramBean bean参数
     * @param <T>       参数类型
     * @return 返回参数字符串
     */
    public static <T extends Object> String makeGetParameter(T paramBean) throws Exception {
        if (DataUtils.isNullOrEmpty(paramBean)) {
            // 参数结果及为空
            return StringUtils.EMPTY_STRING;
        }

        // Bean获取map集合
        Map<String, Object> beanMaps = BeanUtils.beanToMap(paramBean);

        if (DataUtils.isNullOrEmpty(beanMaps)) {
            // 参数结果及为空
            return StringUtils.EMPTY_STRING;
        }

        // 名称-值对集合
        List<NameValuePair> nameValuePairs = new ArrayList<>();

        for (String key : beanMaps.keySet()) {
            // 逐个生成名称-值对象
            // 获取值对象
            Object value = beanMaps.get(key);

            if (null == value) {
                // 忽略空值对象
                continue;
            }

            // 创建基础名称-值对象
            BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value.toString());
            // 添加到集合
            nameValuePairs.add(basicNameValuePair);
        }

        // 生成Url实体对象
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, StringUtils.ENCODING_UTF8);
        // 生成get请求参数对
        return EntityUtils.toString(urlEncodedFormEntity, StringUtils.ENCODING_UTF8);
    }

    /**
     * 获取自定义HTTP客户端。<br>
     *
     * @return 客户端
     * @throws Exception 异常信息
     */
    private static CloseableHttpClient getHttpClient() throws Exception {
        // 创建SSL上下文构造器
        SSLContextBuilder builder = new SSLContextBuilder();
        // 信任模式,忽略身份验证
        builder.loadTrustMaterial(null, new DefaultHttpTrustStrategy());

        // SSL连接工厂
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(),
                new String[]{PROTOCOL_SSL_V2, PROTOCOL_SSL_V3, PROTOCOL_TLS_V1, PROTOCOL_TLS_V1_2},
                null, NoopHostnameVerifier.INSTANCE);
        // 创建连接工厂
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register(PROTOCOL_HTTP, new PlainConnectionSocketFactory())
                .register(PROTOCOL_HTTPS, sslsf)
                .build();

        // 创建HTTP客户端管理器
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        // 最大连接数
        cm.setMaxTotal(200);

        // 创建客户端对象
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .setConnectionManager(cm)
                .setConnectionManagerShared(true)
                .build();
        return httpClient;
    }

    /**
     * GET请求，发送JSON请求数据，获取响应JSON数据
     *
     * @param url      请求URL地址
     * @param reqParam 请求URL参数
     * @param dataType 数据类型
     * @return 响应字符串数据
     * @throws Exception 网路请求各种异常
     */
    private static String getString(String url, String reqParam, String dataType) throws Exception {
        if (StringUtils.isEmpty(dataType)) {
            // 默认为json
            dataType = DATA_TYPE_JSON;
        }

        // 创建默认的httpClient实例.
        CloseableHttpClient httpClient = getHttpClient();
        // 生成URL参数
        String reqUrl;

        if (StringUtils.isEmpty(reqParam)) {
            // 请求参数为空情况
            reqUrl = url;
        } else {
            // 带参数的情况
            reqUrl = String.format(FORMAT_GET_URL, url, reqParam);
        }

        // 创建httpGet
        HttpGet httpGet = new HttpGet(reqUrl);

        // 设置请求头属性
        if (StringUtils.isEquals(DATA_TYPE_JSON, dataType)) {
            // json类型
            httpGet.addHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON_UTF_8);
        } else if (StringUtils.isEquals(DATA_TYPE_JSON, dataType)) {
            // xml类型
            httpGet.addHeader(HTTP.CONTENT_TYPE, ContentType.TEXT_XML_UTF_8);
        }

        try {
            // 执行Get请求
            CloseableHttpResponse response = httpClient.execute(httpGet);

            // 获取响应字符串结果
            return makeRespStringAndClose(response);
        } catch (Exception e) {
            throw e;
        } finally {
            httpClient.close();
        }
    }

    /**
     * POST请求，发送JSON请求数据，获取响应JSON数据
     *
     * @param url      请求URL地址
     * @param reqData  请求字符串数据
     * @param dataType 数据类型
     * @return 响应字符串数据数据
     * @throws Exception 网路请求各种异常
     */
    private static String postString(String url, String reqData, String dataType) throws Exception {
        if (StringUtils.isEmpty(dataType)) {
            // 默认为json
            dataType = DATA_TYPE_JSON;
        }

        // 创建默认的httpClient实例.
        CloseableHttpClient httpClient = getHttpClient();
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头属性
        if (StringUtils.isEquals(DATA_TYPE_JSON, dataType)) {
            // json类型
            httpPost.addHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON_UTF_8);
        } else if (StringUtils.isEquals(DATA_TYPE_JSON, dataType)) {
            // xml类型
            httpPost.addHeader(HTTP.CONTENT_TYPE, ContentType.TEXT_XML_UTF_8);
        }

        try {
            // 设置请求参数
            StringEntity se = new StringEntity(reqData, StringUtils.ENCODING_UTF8);
            // 设置请求头信息
            if (StringUtils.isEquals(DATA_TYPE_JSON, dataType)) {
                // json类型
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON_UTF_8));
            } else if (StringUtils.isEquals(DATA_TYPE_JSON, dataType)) {
                // xml类型
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, ContentType.TEXT_XML_UTF_8));
            }

            // 设置请求内容
            httpPost.setEntity(se);

            // 执行Post请求
            CloseableHttpResponse response = httpClient.execute(httpPost);

            // 响应编码
            int statusCode = response.getStatusLine().getStatusCode();

            // HTTP请求错误不做处理
            if (HttpStatus.SC_NO_CONTENT == statusCode) {
                // 成功响应，但没有内容时
                return StringUtils.EMPTY_STRING;
            } else if (statusCode >= HttpStatus.SC_OK && statusCode <= HttpStatus.SC_MULTI_STATUS) {
                // 成功或创建内容时
                // 获取响应字符串结果
                return makeRespStringAndClose(response);
            } else if (statusCode >= HttpStatus.SC_BAD_REQUEST && statusCode <= HttpStatus.SC_INSUFFICIENT_STORAGE) {
                // 错误响应结果
                return makeRespStringAndClose(response);
            } else {
                // 客户端错误
                throw new Exception(FAIL_RESULT);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpClient.close();
        }
    }

    /**
     * DELETE请求，发送JSON请求数据，获取响应JSON数据
     *
     * @param url      请求URL地址
     * @param reqParam 请求字符串数据
     * @param dataType 数据类型
     * @return 响应字符串数据数据
     * @throws Exception 网路请求各种异常
     */
    private static String deleteString(String url, String reqParam, String dataType) throws Exception {
        if (StringUtils.isEmpty(dataType)) {
            // 默认为json
            dataType = DATA_TYPE_JSON;
        }

        // 创建默认的httpClient实例.
        CloseableHttpClient httpClient = getHttpClient();
        // 生成URL参数
        String reqUrl;

        if (StringUtils.isEmpty(reqParam)) {
            // 请求参数为空情况
            reqUrl = url;
        } else {
            // 带参数的情况
            reqUrl = joinUrlPath(url, reqParam);
        }

        // 创建httpDelete
        HttpDelete httpDelete = new HttpDelete(reqUrl);

        // 设置请求头属性
        if (StringUtils.isEquals(DATA_TYPE_JSON, dataType)) {
            // json类型
            httpDelete.addHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON_UTF_8);
        } else if (StringUtils.isEquals(DATA_TYPE_JSON, dataType)) {
            // xml类型
            httpDelete.addHeader(HTTP.CONTENT_TYPE, ContentType.TEXT_XML_UTF_8);
        }

        try {
            // 执行Delete请求
            CloseableHttpResponse response = httpClient.execute(httpDelete);

            // 获取响应字符串结果
            //return makeRespStringAndClose(response);
            return null;
        } catch (Exception e) {
            throw e;
        } finally {
            httpClient.close();
        }
    }

    /**
     * 根据HTTP响应对象，获取响应字符串,同时关闭响应对象。<br>
     *
     * @param response 响应对象
     * @return 响应结果字符串
     * @throws Exception 异常信息
     */
    private static String makeRespStringAndClose(CloseableHttpResponse response) throws Exception {
        if (null == response) {
            // 响应对象为空
            return StringUtils.EMPTY_STRING;
        }

        try {
            // 获取响应结果
            HttpEntity entity = response.getEntity();

            if (null == entity) {
                throw new Exception(EMPTY_RESULT);
            } else {
                return EntityUtils.toString(entity, StringUtils.ENCODING_UTF8);
            }
        } finally {
            response.close();
        }
    }

    /**
     * 解析查询参数，生成map集合。<br>
     *
     * @param query 查询参数
     * @return 查询参数map集合
     */
    public static Map<String, String> parseQueryParam(String query) {
        if (StringUtils.isEmpty(query)) {
            // 请求参数为空
            return null;
        }

        // 请求参数集合
        Map<String, String> mapRequestParam = new HashMap<>();

        // 分割查询参数
        String[] splitParam = query.split(PATTERN_QUERY_SPLIT_PARAM);

        for (String split : splitParam) {
            // 逐个参数解析
            String[] params = split.split(PATTERN_QUERY_SPLIT_KEYVALUE);

            //解析出键值
            if (params.length > 1) {
                //正确解析
                mapRequestParam.put(params[0], params[1]);
            } else {
                if (StringUtils.isNotEmpty(params[0])) {
                    //只有参数没有值，不加入
                    mapRequestParam.put(params[0], StringUtils.EMPTY_STRING);
                }
            }
        }

        return mapRequestParam;
    }

    @SuppressWarnings("finally")
    public static String sendXMLPost(String urlStr, String xmlStr) {

        String retXmlStr = "";

        try {

            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            // con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            con.setRequestProperty("Accept-Charset", "utf-8");
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");

            out.write(xmlStr);
            out.flush();
            out.close();

            String sCurrentLine = "";
            InputStream l_urlStream = con.getInputStream();

            InputStreamReader isr = new InputStreamReader(l_urlStream, "UTF-8");
            BufferedReader l_reader = new BufferedReader(isr);

            while ((sCurrentLine = l_reader.readLine()) != null) {
                retXmlStr += sCurrentLine + "\r\n";
            }

        } catch (Exception e) {
            throw e;
        } finally {
            return retXmlStr;
        }
    }

    public String sendClientSSL(String url, String certPath, String wxMCHID, String data) throws Exception {

        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        URL certUrl = getClass().getClassLoader().getResource(certPath);

        if (certUrl != null) {
            throw new Exception("成功读取签名文件");
        }

        InputStream inputStream = certUrl.openStream();

        try {
            keyStore.load(inputStream, wxMCHID.toCharArray());
        } finally {
            inputStream.close();
        }

        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, wxMCHID.toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        try {

            HttpPost httpost = new HttpPost(url); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(data, "UTF-8"));

            CloseableHttpResponse response = httpclient.execute(httpost);

            try {
                HttpEntity entity = response.getEntity();

                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;

            } finally {
                response.close();
            }

        } finally {
            httpclient.close();
        }
    }
}
