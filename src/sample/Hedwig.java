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

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * Contains methods to handle CSS_Client's use cases
 * 
 **/

public class Hedwig {

    private static URI BASE_URI;
    private static URI REMOTE_NAME;
    private static String API_KEY;
    private static String CONFIG_FILE_PATH = "/tmp/mca_config_file.txt";
    private static CloseableHttpClient HEDWIG;
    
    
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
    
    public static void setAPIKey() throws FileNotFoundException, IOException {
        File configFile;
        configFile = new File(CONFIG_FILE_PATH);
        
        BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile));
        API_KEY = bufferedReader.readLine();
        bufferedReader.close();
        
    }
    
    public static void saveSettings(String str) throws FileNotFoundException, IOException {
        File configFile;
        configFile = new File(CONFIG_FILE_PATH);
        
        if(!configFile.exists()) {
            configFile.createNewFile();
        }
        
        Writer writer;
        writer = new FileWriter(configFile);
        writer.write(str);
        writer.close();
    }

    /**
     * Sets base URI,
     * routes packets to appropriate APIs
     *
     * @return 
     * @throws IOException
     *
     * @param HedwigPacketList:
     *      Contains a pair of values (key, value) to be bundled with the http request
     *
     * @param route:
     *      Contains a name of the url to send the packets to
     * @throws URISyntaxException
     **/

    public static String go(Route route, ArrayList<HedwigPacket> HedwigPacketList)
            throws IOException, URISyntaxException {

        // TODO: bundle destination into the HedwigPacket instance

        setBaseURI("http://127.0.0.1:8000/api/");
        setAPIKey();
        
        String response = null;

        Console.log(Logger.INFO, "@Hedwig.go -> @method getBaseURI <" + getBaseURI() + ">");
        Console.log(Logger.INFO, "@Hedwig.go -> @var route <" + route + ">");
        
        // PacketRouting: Dear hedwig, kindly send my packets to...
        switch(route) {
            case ALL_CASES: {
                Console.log(Logger.INFO, "@Hedwig.go @switch -> route switched to <" + route + ">");
                response = gotoCases();
            } break;

            case ALL_IMAGES: {
                Console.log(Logger.INFO, "@Hedwig.go @switch -> route switched to <" + route + ">");
                response = gotoImages(HedwigPacketList.get(0));
            } break;

            case NEW_ANALYSIS: {
                Console.log(Logger.INFO, "@Hedwig.go @switch -> route switched to <" + route + ">");
                response = addAnalysis(HedwigPacketList);
            } break;

            case SHOW_ANALYSIS: {
                Console.log(Logger.INFO, "@Hedwig.go @switch -> route switched to <" + route + ">");
                response = gotoAnal("2");
            } break;

        }

        return response;
    }

    public static String gotoAnal(String pk) throws IOException, URISyntaxException {
        String api = "images/analysis";
        Console.log(Logger.INFO, "@Hedwig.gotoAnal <api: " + api + ">");
        Console.log(Logger.INFO, "@Hedwig.gotoAnal <pk: " + pk + ">");
        Console.log(Logger.INFO, "@Hedwig.gotoAnal <getRemoteName: " + getRemoteName() + ">");

        try {
            HEDWIG = HttpClients.createDefault();
            HttpGet httpGet;

            setRemoteName(Location.addPath(getBaseURI(), api));
            Console.log(Logger.INFO, "@Hedwig.gotoAnal -> requesting analysis <"
                    + getRemoteName().toString() + ">");

            // TODO: a better way to add params to an http request
            setRemoteName(new URIBuilder().setScheme(getRemoteName().getScheme())
                    .setHost(getRemoteName().getHost())
                    .setPort(getRemoteName().getPort())
                    .setPath(getRemoteName().getPath())
                    .setParameter("analysis_id", pk)
                    .build()
            );

            httpGet = new HttpGet(getRemoteName());
            Console.log(Logger.INFO, "@Hedwig.gotoAnal < httpGet: " + httpGet.getURI() + ">");
            

            return HEDWIG.execute(httpGet, responseHandler);

        } finally {
            HEDWIG.close();
        }

    }

    public static String gotoImages(HedwigPacket mHedwigPacket) throws IOException, URISyntaxException {
        String api = "images/";
        Console.log(Logger.INFO, "@Hedwig.gotoImages -> Images index" );

        try{
            HEDWIG = HttpClients.createDefault();
            HttpGet httpGet;

            setRemoteName(Location.addPath(getBaseURI(), api));
            Console.log(Logger.INFO, "@Hedwig.gotoImages -> Images index <" +
                    getRemoteName().toString() + ">");

            // TODO: a better way to add params to an http request
            setRemoteName(new URIBuilder()
                            .setScheme(getRemoteName().getScheme())
                            .setHost(getRemoteName().getHost())
                            .setPort(getRemoteName().getPort())
                            .setPath(getRemoteName().getPath())
                            .setParameter("case_id", mHedwigPacket.getValue())
                            .build()
            );

            httpGet = new HttpGet(getRemoteName());
            Console.log(Logger.INFO, "@Hedwig.gotoImages -> Images index <" +
                    httpGet.getURI() + ">");

            return HEDWIG.execute(httpGet,responseHandler);

        } finally {
            HEDWIG.close();
        }
    }

    public static String gotoCases() throws IOException {
        String api = "cases/";
        Console.log(Logger.INFO, "@Hedwig.gotoCases -> retrieving all analysis cases");

        try{
            HEDWIG = HttpClients.createDefault();
            HttpGet httpGet;

            setRemoteName(Location.addPath(getBaseURI(), api));
            Console.log(Logger.INFO, "@Hedwig.gotoCases -> Cases index <" +
                    getRemoteName().toString() + ">");

            httpGet = new HttpGet(getRemoteName());

            return HEDWIG.execute(httpGet, responseHandler);

        } finally {
            HEDWIG.close();
        }

    }

    public static String addAnalysis(ArrayList<HedwigPacket> HedwigPacketList) throws IOException, URISyntaxException {
        String api = "images/analysis";
        Console.log(Logger.INFO, "@Hedwig.addAnalysis -> api <" + api + ">");

        Map<String, String> args;

        args = new HashMap<>();
        for(HedwigPacket mHedwigPacket : HedwigPacketList) {
            Console.log(Logger.INFO, "@Hedwig.go @listIterator <"
                    + mHedwigPacket.getName() + " : " + mHedwigPacket.getValue() + ">");
            args.put(mHedwigPacket.getName(), mHedwigPacket.getValue());
        }

        String aCase;
        if(Objects.equals(aCase = addCase(args.get("name"), args.get("description")), null)) {
            Console.log(Logger.INFO, "<aCase: " + aCase + ">");
            Console.log(Logger.WARNING, "Whoops! I think you have better things to do...");
            return null;
        }

        String aImage;
        if(Objects.equals(aImage = addImage(aCase, args.get("image")), null)) {
            Console.log(Logger.WARNING, "Whoops! Perhaps not being a jerk would be a great thing right...");
            return null;
        }
        
        try {
            Thread.sleep(5000);                 //1000 milliseconds is one second.
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            Console.log(Logger.WARNING, e.getMessage());
        }   

        String anal;
        if(Objects.equals(anal = gotoAnal(aImage), null)) {
            Console.log(Logger.WARNING, "Cool story bro... it needs more dragon and...");
            return null;
        }

        return anal;
    }

    public static String addCase(String name, String description) throws IOException {
        String api = "cases/new";
        Console.log(Logger.WARNING,getBaseURI().toString());
        Console.log(Logger.INFO, "<api_key: " + API_KEY + ">");
        try {
            HEDWIG = HttpClients.createDefault();
            HttpPost httpPost;

            setRemoteName(Location.addPath(getBaseURI(), api));
            Console.log(Logger.INFO, "@Hedwig.addCase -> adding a new case <"
                    + getRemoteName().toString() + ">");

            Map<String, String> args = new HashMap<>();
            args.put("name", name);
            args.put("description", description);
            args.put("api_key", API_KEY);

            UrlEncodedFormEntity mFormEntity;
            mFormEntity = (UrlEncodedFormEntity) Location.addParam(args);
            Console.log(Logger.INFO, "@Hedwig.addCase -> hedwig params <"
                    + mFormEntity.toString() + ">");

            httpPost = new HttpPost(getRemoteName());
            httpPost.setEntity(mFormEntity);

            return HEDWIG.execute(httpPost, responseHandler);

        } catch (Exception e) {
            Console.log(Logger.ERROR,
                    "Whoops! @Hedwig.addCase -> bad hedwig... bad, bad hedwig\n" + e.getMessage());
        } finally {
            //Console.log(Logger.ERROR, "Baaaaadddd Headwig .... ");
            //hedwig.close();
        }

        return null;
    }

    public static String addImage(String case_id, String image) throws IOException {
        String api = "images/new";

        // TODO: upload multiple images

        try {
            HEDWIG = HttpClients.createDefault();
            HttpPost httpPost;

            setRemoteName(Location.addPath(getBaseURI(), api));
            Console.log(Logger.INFO, "@Hedwig.addImage -> adding a new task <"
                    + getRemoteName().toString() + ">");

            HttpEntity mMultipartEntity;
            try {

                mMultipartEntity = MultipartEntityBuilder.create()
                        .addPart("case_id", new StringBody(case_id, ContentType.TEXT_PLAIN))
                        .addPart("api_key", new StringBody(API_KEY, ContentType.TEXT_PLAIN))
                        .addPart("image", new FileBody(new File(image)))
                        .build();

                Console.log(Logger.INFO, "@Hedwig.addImage -> hedwig params <"
                        + mMultipartEntity.toString() + ">");

                httpPost = new HttpPost(getRemoteName());
                httpPost.setEntity(mMultipartEntity);

                return HEDWIG.execute(httpPost, responseHandler);

            } catch(Exception e) {
                Console.log(Logger.ERROR, "@Hedwig.addImage.mMultipartEntity <Whoops! We got a "
                        + e.getMessage() + ">");
                e.printStackTrace();
            }

        } catch (Exception e) {
            Console.log(Logger.ERROR,
                    "Whoops! @Hedwig.addImage <Whoops! " + e.getMessage() + ">");
        } finally {
            HEDWIG.close();
        }

        return null;
    }
    
    public static void gotoCopyMove(String image) throws IOException {
        String myCommand;
//        myCommand = "python ../scripts/detect.py " + image;
//        myCommand = "../scripts/test.sh";
        
        // Get the fucking root directory
        myCommand = "pwd";
        Console.log(Logger.INFO, myCommand);
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(myCommand);
            p.waitFor();
            BufferedReader reader = 
                new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";			
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
        } catch (IOException | InterruptedException e) {
                e.printStackTrace();
        }
        
        String rootDir = output.toString().trim();
        Console.log(Logger.INFO, "rootDir: " + rootDir);
        
        // Copy-move magic
        // myCommand = "python " + rootDir + "/src/scripts/detect.py " + image + " --blcoldev=0.05 --impalred=30 --blsim=100 --rgsim=2";
        myCommand = "python " + rootDir + "/src/scripts/detect.py " + image;
        
        Console.log(Logger.INFO, "myCommand: " + myCommand);
        
        try {
            p = Runtime.getRuntime().exec(myCommand);
            p.waitFor();
            BufferedReader reader = 
                new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";			
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
        } catch (IOException | InterruptedException e) {
                e.printStackTrace();
        }
        Console.log(Logger.INFO, output.toString());
        
    }

    private static ResponseHandler<String> responseHandler = httpResponse -> {
        int status = httpResponse.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = httpResponse.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        } else {
            return (new ClientProtocolException("Whoops! This table is taken: " + status)).toString();
        }
    };


}
