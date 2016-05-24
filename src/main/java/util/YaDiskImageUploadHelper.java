package util;

import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by nikita on 24.05.16.
 */
public class YaDiskImageUploadHelper {

    private static final int SUCCESS = 200;
    private static final int SUCCESS_UPLOAD = 201;
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String OAUTH = "OAuth ";

    public static String uploadFile(String name, InputStream image, String token) throws IOException {
        // {@link https://tech.yandex.ru/disk/api/reference/public-docpage/}
        // token can be found https://tech.yandex.ru/disk/poligon/
        CloseableHttpClient aDefault = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://cloud-api.yandex.net/v1/disk/resources/upload?path=" + URLEncoder.encode(name, StandardCharsets.UTF_8.name()) + "&overwrite=true");
        CloseableHttpResponse execute = aDefault.execute(addAuth(request, token));
        checkResponseCode(execute);
        String s = EntityUtils.toString(execute.getEntity());
        JsonParser jsonParser = new JsonParser();
        String asString = jsonParser.parse(s).getAsJsonObject().get("href").getAsString();

        HttpPut request1 = new HttpPut(asString);
        request1.setEntity(new InputStreamEntity(image, ContentType.APPLICATION_OCTET_STREAM));
        CloseableHttpResponse execute1 = aDefault.execute(request1);
        checkResponseCode(execute1, SUCCESS_UPLOAD);
        HttpGet httpGet = new HttpGet("https://cloud-api.yandex.net/v1/disk/resources/last-uploaded?limit=1");
        CloseableHttpResponse execute2 = aDefault.execute(addAuth(httpGet, token));
        checkResponseCode(execute2);
        String pathToPublish = jsonParser.parse(EntityUtils.toString(execute2.getEntity())).getAsJsonObject().get("items").getAsJsonArray().get(0).getAsJsonObject().get("path").getAsString();
        //publishing
        String encodedPathToFile = URLEncoder.encode(pathToPublish, StandardCharsets.UTF_8.name());
        HttpPut publishReq = new HttpPut("https://cloud-api.yandex.net/v1/disk/resources/publish?path=" + encodedPathToFile);


        CloseableHttpResponse execute3 = aDefault.execute(addAuth(publishReq, token));
        checkResponseCode(execute3);
        //get meta
        HttpGet metaReq = new HttpGet("https://cloud-api.yandex.net/v1/disk/resources?path=" + encodedPathToFile);
        CloseableHttpResponse execute4 = aDefault.execute(addAuth(metaReq, token));
        checkResponseCode(execute4);
        String publicUrl = jsonParser.parse(EntityUtils.toString(execute4.getEntity())).getAsJsonObject().get("public_url").getAsString();
        HttpClientUtils.closeQuietly(aDefault);
        return publicUrl;
    }


    private static HttpRequestBase addAuth(HttpRequestBase req, String token) {
        req.addHeader(AUTHORIZATION_HEADER_NAME, OAUTH + token);
        return req;
    }

    private static void checkResponseCode(CloseableHttpResponse response) {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SUCCESS) {
            throw new IllegalStateException("Wrong status code " + statusCode);
        }
    }

    private static void checkResponseCode(CloseableHttpResponse response, int expectedCode) {
        int statusCode = response.getStatusLine().getStatusCode();
        if (response.getStatusLine().getStatusCode() != expectedCode) {
            throw new IllegalStateException("Wrong status code " + statusCode);
        }
    }
}
