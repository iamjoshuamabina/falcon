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
import sample.utils.Logger;
import sample.utils.Console;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class Hedwig {

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
            return entity != null ? EntityUtils.toString(entity) : null;
        } else {
            return (new ClientProtocolException("Whoops! This table is taken: " + status)).toString();
        }
    };

    public Hedwig() {
    }
    
    public static void setBaseURI(String str) {
        BASE_URI = Location.assign(str);
    }

    public static URI getBaseURI() {
        return Hedwig.BASE_URI;
    }

    public static void setRemoteName(URI uri){
        REMOTE_NAME = uri;
    }

    public static URI getRemoteName() {
        return REMOTE_NAME;
    }

    public static String getEnvPath(String pathString) {
        return System.getenv("HOME") + "/.mca/" + pathString + "/";
    }

    @SuppressWarnings("SuspiciousGetterSetter")
    public static String getAnalysisDir() {
        return ANALYSIS_DIR;
    }
    public static void setAPIKey() throws IOException {
        File configFile;
        configFile = new File(KEY_FILE);
        
        BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile));
        API_KEY = bufferedReader.readLine();
        bufferedReader.close();
        
    }
    
    public static void saveSettings(String str) throws IOException {
        File configFile;
        configFile = new File(KEY_FILE);
        
        if(!configFile.exists()) {
            configFile.createNewFile();
        }
        
        Writer writer;
        writer = new FileWriter(configFile);
        writer.write(str);
        writer.close();
    }

    public static String go(Route route, ArrayList<HedwigPacket> HedwigPacketList)
            throws IOException, URISyntaxException {

        setBaseURI("http://127.0.0.1:8000/api/");
        Console.out(Logger.INFO, "BASE_URI: " + getBaseURI());

        setAPIKey();
        Console.out(Logger.INFO, "API_KEY: " + API_KEY);

        String response = null;

        // PacketRouting: Dear hedwig, kindly send my packets to...
        switch(route) {
            case ALL_CASES: {
                Console.out(Logger.INFO, "@Hedwig.go @switch -> route switched to <" + route + ">");
                response = gotoCases();
            } break;

            case ALL_IMAGES: {
                Console.out(Logger.INFO, "@Hedwig.go @switch -> route switched to <" + route + ">");
                response = gotoImages(HedwigPacketList.get(0));
            } break;

            case NEW_ANALYSIS: {
                Console.out(Logger.INFO, "@Hedwig.go @switch -> route switched to <" + route + ">");
                response = addAnalysis(HedwigPacketList);
            } break;

            case SHOW_ANALYSIS: {
                Console.out(Logger.INFO, "@Hedwig.go @switch -> route switched to <" + route + ">");
                response = getImageAnalysis("2");
            } break;

        }

        return response;
    }

    public static String getImageAnalysis(String pk) throws IOException, URISyntaxException {
        String api = "images/analysis";
        Console.out(Logger.INFO, "@Hedwig.getImageAnalysis <api: " + api + ">");
        Console.out(Logger.INFO, "@Hedwig.getImageAnalysis <pk: " + pk + ">");
        Console.out(Logger.INFO, "@Hedwig.getImageAnalysis <getRemoteName: " + getRemoteName() + ">");

        try {
            CLOSEABLE_HTTP_CLIENT = HttpClients.createDefault();
            HttpGet httpGet;

            setRemoteName(Location.addPath(getBaseURI(), api));
            Console.out(Logger.INFO, "@Hedwig.getImageAnalysis -> requesting analysis <"
                    + getRemoteName().toString() + ">");

            setRemoteName(new URIBuilder().setScheme(getRemoteName().getScheme())
                            .setHost(getRemoteName().getHost())
                            .setPort(getRemoteName().getPort())
                            .setPath(getRemoteName().getPath())
                            .setParameter("analysis_id", pk)
                            .build()
            );

            httpGet = new HttpGet(getRemoteName());
            Console.out(Logger.INFO, "@Hedwig.getImageAnalysis < httpGet: " + httpGet.getURI() + ">");

            return CLOSEABLE_HTTP_CLIENT.execute(httpGet, responseHandler);

        } finally {
            CLOSEABLE_HTTP_CLIENT.close();
        }

    }

    public static String gotoImages(HedwigPacket hedwigPacket) throws IOException, URISyntaxException {
        String api = "images/";
        try{
            CLOSEABLE_HTTP_CLIENT = HttpClients.createDefault();
            HttpGet httpGet;

            setRemoteName(Location.addPath(getBaseURI(), api));
            setRemoteName(new URIBuilder()
                            .setScheme(getRemoteName().getScheme())
                            .setHost(getRemoteName().getHost())
                            .setPort(getRemoteName().getPort())
                            .setPath(getRemoteName().getPath())
                            .setParameter("case_id", hedwigPacket.getValue())
                            .build()
            );

            httpGet = new HttpGet(getRemoteName());
            Console.out(Logger.INFO, "@Hedwig.gotoImages -> Images index <" +
                    httpGet.getURI() + ">");

            return CLOSEABLE_HTTP_CLIENT.execute(httpGet,responseHandler);

        } finally {
            CLOSEABLE_HTTP_CLIENT.close();
        }
    }

    public static String gotoCases() throws IOException {
        String api = "cases/";
        Console.out(Logger.INFO, "@Hedwig.gotoCases -> retrieving all analysis cases");

        try{
            CLOSEABLE_HTTP_CLIENT = HttpClients.createDefault();
            HttpGet httpGet;

            setRemoteName(Location.addPath(getBaseURI(), api));
            Console.out(Logger.INFO, "@Hedwig.gotoCases -> Cases index <" +
                    getRemoteName().toString() + ">");

            httpGet = new HttpGet(getRemoteName());

            return CLOSEABLE_HTTP_CLIENT.execute(httpGet, responseHandler);

        } finally {
            CLOSEABLE_HTTP_CLIENT.close();
        }
    }

    public static String addAnalysis(ArrayList<HedwigPacket> HedwigPacketList) throws IOException, URISyntaxException {
        String api = "images/analysis";
        Console.out(Logger.INFO, "@Hedwig.addAnalysis -> api <" + api + ">");

        Map<String, String> args;

        args = new HashMap<>();
        for(HedwigPacket mHedwigPacket : HedwigPacketList) {
            Console.out(Logger.INFO, "@Hedwig.go @listIterator <"
                    + mHedwigPacket.getName() + " : " + mHedwigPacket.getValue() + ">");
            args.put(mHedwigPacket.getName(), mHedwigPacket.getValue());
        }

        String aCase;
        if(Objects.equals(aCase = addCase(args.get("name"), args.get("description")), null)) {
            Console.out(Logger.INFO, "<aCase: " + aCase + ">");
            Console.out(Logger.WARNING, "Whoops! I think you have better things to do...");
            return null;
        }

        String aImage;
        if(Objects.equals(aImage = addImage(aCase, args.get("image")), null)) {
            Console.out(Logger.WARNING, "Whoops! Perhaps not being a jerk would be a great thing right...");
            return null;
        }
        
        try {
            Thread.sleep(5000);                 //1000 milliseconds is one second.
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            Console.out(Logger.WARNING, e.getMessage());
        }   

        String anal;
        if(Objects.equals(anal = getImageAnalysis(aImage), null)) {
            Console.out(Logger.WARNING, "Cool story bro... it needs more dragon and...");
            return null;
        }

        return anal;
    }

    public static String addCase(String name, String description) throws IOException {
        String api = "cases/new";
        Console.out(Logger.WARNING, getBaseURI().toString());
        Console.out(Logger.INFO, "<api_key: " + API_KEY + ">");
        try {
            CLOSEABLE_HTTP_CLIENT = HttpClients.createDefault();
            HttpPost httpPost;

            setRemoteName(Location.addPath(getBaseURI(), api));
            Console.out(Logger.INFO, "@Hedwig.addCase -> adding a new case <"
                    + getRemoteName().toString() + ">");

            Map<String, String> args = new HashMap<>();
            args.put("name", name);
            args.put("description", description);
            args.put("api_key", API_KEY);

            UrlEncodedFormEntity mFormEntity;
            mFormEntity = (UrlEncodedFormEntity) Location.addParam(args);
            Console.out(Logger.INFO, "@Hedwig.addCase -> hedwig params <"
                    + mFormEntity.toString() + ">");

            httpPost = new HttpPost(getRemoteName());
            httpPost.setEntity(mFormEntity);

            return CLOSEABLE_HTTP_CLIENT.execute(httpPost, responseHandler);

        } catch (Exception e) {
            Console.out(Logger.ERROR,
                    "Whoops! @Hedwig.addCase -> bad hedwig... bad, bad hedwig\n" + e.getMessage());
        }

        return null;
    }

    public static String addImage(String case_id, String image) throws IOException {
        String api = "images/new";

        try {
            CLOSEABLE_HTTP_CLIENT = HttpClients.createDefault();
            HttpPost httpPost;

            setRemoteName(Location.addPath(getBaseURI(), api));
            Console.out(Logger.INFO, "@Hedwig.addImage -> adding a new task <"
                    + getRemoteName().toString() + ">");

            HttpEntity mMultipartEntity;
            try {

                mMultipartEntity = MultipartEntityBuilder.create()
                        .addPart("case_id", new StringBody(case_id, ContentType.TEXT_PLAIN))
                        .addPart("api_key", new StringBody(API_KEY, ContentType.TEXT_PLAIN))
                        .addPart("image", new FileBody(new File(image)))
                        .build();

                Console.out(Logger.INFO, "@Hedwig.addImage -> hedwig params <"
                        + mMultipartEntity.toString() + ">");

                httpPost = new HttpPost(getRemoteName());
                httpPost.setEntity(mMultipartEntity);

                return CLOSEABLE_HTTP_CLIENT.execute(httpPost, responseHandler);

            } catch(Exception e) {
                Console.out(Logger.ERROR, "@Hedwig.addImage.mMultipartEntity <Whoops! We got a "
                        + e.getMessage() + ">");
                e.printStackTrace();
            }

        } catch (Exception e) {
            Console.out(Logger.ERROR,
                    "Whoops! @Hedwig.addImage <Whoops! " + e.getMessage() + ">");
        } finally {
            CLOSEABLE_HTTP_CLIENT.close();
        }

        return null;
    }
    
    public static void gotoCopyMove(String imagePath) throws IOException {

        @SuppressWarnings({"StringConcatenationMissingWhitespace", "SpellCheckingInspection"})
        final String COPY_MOVE_SCRIPT = SCRIPTS_DIR + "copymove.py";
        Console.out(Logger.INFO, "COPY_MOVE_SCRIPT: " + COPY_MOVE_SCRIPT);

        String cmdString = "python " + COPY_MOVE_SCRIPT + " " + imagePath;
        Console.out(Logger.INFO, "cmdString (" + System.currentTimeMillis() + "): "  + cmdString);

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

        Console.out(Logger.INFO, output.toString());

    }
}
