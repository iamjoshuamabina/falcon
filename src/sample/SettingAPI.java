/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

/**
 *
 * @author mocoder
 */
public class SettingAPI {
    private static String _API_KEY=null;
    File api_key_file =null;
    String filePath = "/tmp/api_key.txt";
    
    public SettingAPI(){
      api_key_file= new File(filePath);
             
    }
    /*Get API Key*/
    public String getAPIKey() throws FileNotFoundException{
        BufferedReader read_api=null;
        try{
            read_api = new BufferedReader(new FileReader(filePath));
             _API_KEY = read_api.readLine();
     
        }catch(IOException ex){
         Console.log(Logger.ERROR, "Fail to read the file");
        }
        return _API_KEY;
    }
    /*Set APi Key*/
    public void setAPIKey(String apiKey){
         /*Write Value Back to the Text Box*/            
        if(!api_key_file.exists())
            {
                try {
                        api_key_file.createNewFile();
                        FileWriter write_api = new FileWriter(api_key_file);
                        try (BufferedWriter buffer_writer = new BufferedWriter(write_api))
                            {
                               buffer_writer.write(apiKey);
                            }
                    } catch (IOException ex)
                    {
                      java.util.logging.Logger.getLogger(DIFCSS.class.getName()).log(Level.SEVERE, null, ex);
                    }
                            
            }
    
    }
    /*Update APi Key*/
    public void updateAPIKey(String new_apikey){
        /*First Open the Text File And then Delete the content inside it*/
        if(api_key_file.delete())
        {
            /*Call the Method for Creating New Text File and APi Key*/
            this.setAPIKey(new_apikey);
        }
    
    
    }
    
}
