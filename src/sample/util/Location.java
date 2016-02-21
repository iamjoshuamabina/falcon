package sample.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Location {

    public Location(){
    }

     public static URI assign(String str) {
        try {
            if(!str.startsWith("http")) return new URI("http://" + str);
            else return new URI(str);
        } catch(URISyntaxException e) {
            Console.out(Logger.ERROR, "Location.assign() -> " + e.getMessage());
        }
        return null;
    }

    public static URI addPath(URI uri, String str) {
        try {
            //noinspection StringConcatenationMissingWhitespace
            return new URI(
                    new URIBuilder()
                            .setScheme(uri.getScheme())
                            .setHost(uri.getHost())
                            .setPort(uri.getPort())
                            .setPath(uri.getPath() + str)
                            .build()
                            .toString()
            );

        } catch (URISyntaxException e) {
            Console.out(Logger.ERROR, "Location.addPath() -> " + e.getMessage());
        }
        return null;
    }

    public static HttpEntity addParam(Map<String, String> args) {
        return addParam(args, true);
    }

    public static HttpEntity addParam(Map<String, String> args, boolean withEntity) {
        List<NameValuePair> params = new ArrayList<>();
        if(withEntity) {
            for(String key : args.keySet()) {
                String value = args.get(key);
                params.add(new BasicNameValuePair(key, value));
            }
            return new UrlEncodedFormEntity(params, Consts.UTF_8);
        }
        //noinspection ReturnOfNull
        return null;
    }


}
