package sample;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sample.util.Location;
import sample.util.Logger;
import sample.util.Console;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class Ghiro {
    private static URI BASE_URI;
    private static URI REMOTE_NAME;
    private static String API_KEY;
    private static CloseableHttpClient CLOSEABLE_HTTP_CLIENT;

    private final static String SCRIPTS_DIR = getEnvPath("scripts");
    private final static String CONFIG_DIR = getEnvPath("config");
    private final static String ANALYSIS_DIR = getEnvPath("analysis");

    @SuppressWarnings("StringConcatenationMissingWhitespace")
    private final static String KEY_FILE = CONFIG_DIR + "mca.key";

    private static ResponseHandler<String> responseHandler = httpResponse -> {
        int status = httpResponse.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = httpResponse.getEntity();
            //noinspection ReturnOfNull
            return entity != null ? EntityUtils.toString(entity) : null;
        } else {
            return (new ClientProtocolException("Whoops! This table is taken: " + status)).toString();
        }
    };

    public enum Route {
        NEW_ANALYSIS,
        SHOW_ANALYSIS,
    }

    public Ghiro() {
    }

    public static void setBaseURI(String str) {
        BASE_URI = Location.assign(str);
    }

    @SuppressWarnings("SuspiciousGetterSetter")
    public static URI getBaseURI() {
        return Ghiro.BASE_URI;
    }

    @SuppressWarnings("SuspiciousGetterSetter")
    public static void setRemoteName(URI uri){
        REMOTE_NAME = uri;
    }

    @SuppressWarnings("SuspiciousGetterSetter")
    public static URI getRemoteName() {
        return REMOTE_NAME;
    }

    public static String getEnvPath(String pathString) {
        return System.getenv("HOME") + "/.mca/" + pathString + "/";
    }

    @SuppressWarnings("SuspiciousGetterSetter")
    public static String getAnalysisDirectory() {
        return ANALYSIS_DIR;
    }
    public static void setAPIKey() throws IOException {
        File configFile;
        configFile = new File(KEY_FILE);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile));
        API_KEY = bufferedReader.readLine();
        bufferedReader.close();

    }

    public static String analyse(Route route, ArrayList<GhiroBundle> ghiroBundleList)
            throws IOException, URISyntaxException {

        setBaseURI("http://127.0.0.1:8000/api/");

        setAPIKey();

        String response = null;

        switch(route) {
            case NEW_ANALYSIS: {
                response = addAnalysis(ghiroBundleList);
            } break;
		}

        return response;
    }

    public static String getImageAnalysis(String pk) throws IOException, URISyntaxException {
        String api = "images/analysis";

        try {
            setRemoteName(Location.addPath(getBaseURI(), api));
            setRemoteName(new URIBuilder().setScheme(getRemoteName().getScheme())
                            .setHost(getRemoteName().getHost())
                            .setPort(getRemoteName().getPort())
                            .setPath(getRemoteName().getPath())
                            .setParameter("analysis_id", pk)
                            .build()
            );

            HttpGet httpGet;
            httpGet = new HttpGet(getRemoteName());

            CLOSEABLE_HTTP_CLIENT = HttpClients.createDefault();

            return CLOSEABLE_HTTP_CLIENT.execute(httpGet, responseHandler);

        } finally {
            CLOSEABLE_HTTP_CLIENT.close();
        }

    }

    public static String addAnalysis(ArrayList<GhiroBundle> ghiroBundleList) throws IOException, URISyntaxException {
        Map<String, String> args;
        args = new HashMap<>();
        for(GhiroBundle mGhiroBundle : ghiroBundleList) {
            args.put(mGhiroBundle.getName(), mGhiroBundle.getValue());
        }

        String aCase;
        if(Objects.equals(aCase = addCase(args.get("name"),
                args.get("description")), null)) {
            return null;
        }

        String aImage;
        if(Objects.equals(aImage = addImage(aCase, args.get("image")), null)) {
            return null;
        }

        try {
            Thread.sleep(10000);                 // 1000 milliseconds is one second.
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            Console.out(Logger.ERROR, e.getMessage());
        }

        String analysis;
        if(Objects.equals(analysis = getImageAnalysis(aImage), null)) {
            return null;
        }
        return analysis;
    }

    public static String addCase(String name, String description) throws IOException {
        String api = "cases/new";

        try {
            setRemoteName(Location.addPath(getBaseURI(), api));

            Map<String, String> args = new HashMap<>();
            args.put("name", name);
            args.put("description", description);
            args.put("api_key", API_KEY);

            UrlEncodedFormEntity mFormEntity;
            mFormEntity = (UrlEncodedFormEntity) Location.addParam(args);

            HttpPost httpPost;
            httpPost = new HttpPost(getRemoteName());
            httpPost.setEntity(mFormEntity);

            CLOSEABLE_HTTP_CLIENT = HttpClients.createDefault();

            return CLOSEABLE_HTTP_CLIENT.execute(httpPost, responseHandler);

        } catch (Exception e) {
            Console.out(Logger.ERROR, e.getMessage());
        }

        return null;
    }

    public static String addImage(String case_id, String image) throws IOException {
        String api = "images/new";

        try {
            setRemoteName(Location.addPath(getBaseURI(), api));

            HttpPost httpPost;
            HttpEntity mMultipartEntity;
            try {

                mMultipartEntity = MultipartEntityBuilder.create()
                        .addPart("case_id", new StringBody(case_id, ContentType.TEXT_PLAIN))
                        .addPart("api_key", new StringBody(API_KEY, ContentType.TEXT_PLAIN))
                        .addPart("image", new FileBody(new File(image)))
                        .build();

                httpPost = new HttpPost(getRemoteName());
                httpPost.setEntity(mMultipartEntity);

                CLOSEABLE_HTTP_CLIENT = HttpClients.createDefault();

                return CLOSEABLE_HTTP_CLIENT.execute(httpPost, responseHandler);

            } catch(Exception e) {
                Console.out(Logger.ERROR, e.getMessage());
            }

        } catch (Exception e) {
            Console.out(Logger.ERROR, e.getMessage());
        } finally {
            CLOSEABLE_HTTP_CLIENT.close();
        }

        return null;
    }

    public static void getCopyMoveAnalysisData(String imagePath) throws IOException {

        @SuppressWarnings({"StringConcatenationMissingWhitespace", "SpellCheckingInspection"})
        final String COPY_MOVE_SCRIPT = SCRIPTS_DIR + "copymove.py";

        String cmdString = "python " + COPY_MOVE_SCRIPT + " " + imagePath;

        Process process;
        StringBuilder output = new StringBuilder();
        try {
            //noinspection CallToRuntimeExecWithNonConstantString
            process = Runtime.getRuntime().exec(cmdString);
            process.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine())!= null) {
                output.append(line).append("\n");
            }
        } catch (IOException | InterruptedException e) {
            Console.out(Logger.ERROR, e.getMessage());
        }

    }

	@SuppressWarnings("ClassWithoutNoArgConstructor")
    public static class GhiroBundle {
        private String name;
        private String value;

        public GhiroBundle(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public final String getName() {
            return this.name;
        }

        public final String getValue() {
            return this.value;
        }
    }
}
