package sample;

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

/**
 * Created by joshy on 5/9/15.
 *
 * Build and validate URLs
 *
 */

public class Location {
    /**
     * @return valid URL
     *
     * @param str contains URL
     *
     * */

     public static URI assign(String str) {
        try {
            if(!str.startsWith("http")) return new URI("http://" + str);
            else return new URI(str);
        } catch(URISyntaxException e) {
            Console.log(Logger.ERROR, "@Location.assign -> Whoops! Perhaps a malformed URL");
        }
        return null;
    }

    /**
     * This method adds path to an already existing URI
     *
     * @param uri contains a valid URI
     * @param str contains path to add to existing uri
     *
     * */
    public static URI addPath(URI uri, String str) {
        try {
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
            Console.log(Logger.ERROR, "@Location.addPath -> Whoops! Perhaps a something wrong with the URL");
        }

        return null;
    }

    public static HttpEntity addParam(Map<String, String> args) {
        return addParam(args, true);
    }

    public static HttpEntity addParam(Map<String, String> args, boolean withEntity) {

        // TODO: proper handler -> MultipartEntity, URLEncodedFormEntity, withoutEntity

        List<NameValuePair> params = new ArrayList<>();
        if(withEntity) {
            for(String key : args.keySet()) {
                String value = args.get(key);
                params.add(new BasicNameValuePair(key, value));
            }
            return new UrlEncodedFormEntity(params, Consts.UTF_8);
        } else {
            return null;
        }
    }


}
