/*aUTHOR @MO CODER
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author MO CODER
 */
public class DIFCSS extends Application implements MapComponentInitializedListener {
    public String imageforeinsicJSON;
    public String processed_imagePath;
    public String _imageName_real;
    //public String image_path;
    String[] colors_data;
    //GLobal String
    String json_result;
    //variable for Displying the Map 
    GoogleMapView mapView;//MapView
    GoogleMap map;//Map
    //Class Properties
    Boolean _controlLading;
        private TabPane _caseResultTabPane;//For Holding Case Detail Tab Pane
        private SplitPane _mainSplitPane;//FOr Holding Both  Case Detail and Case Result Details
        //private Accordion _holdingTitledPans;
        private final BorderPane _mainBorderPane;//The Main Borderane
       // private TitledPane _caseDetailsTitledPane;//For Holding the Case Details
        //private TitledPane _caseAnalysisResultTitledPane;//For Holding the Case Details
        private final GraphicsDevice _screen_size=GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        //Properties for Top Menu
        
        //Properties for Left Menu Left BorderPane
        
//                Button _btnImageForeinsic;//For Opening the Image foresic content
//                Button _btnVideoForeinsic;//For Opening the Video foresic content
//                Button _btnAudioForeinsic;//For Opening the Audio foresic content
                Label _imageForensicLabe;//For Displaying the Image Foreinsic Label
                Label _videoForensicLabe;//For Displaying the Image Foreinsic Label
                Label _audioForensicLabe;//For Displaying the Image Foreinsic Label
                Label _softwareCopyrightLabe;//For Displaying the Software copyright
                GridPane _leftMenuHolder;//For Holdding the left Menu
                HBox _imageHBox;//For Holding the Image and Text for Image 
                HBox _videoHBox;//For Holding the Image and Text for Image 
                HBox _audioHBox;//For Holding the Image and Text for Image 
                HBox _copyRightinfo;//For Holding the Copyright Information
                ImageView _imageIcon;//For Holding Image Foreisic Incon
                ImageView _videoIcon;//For Holding Image Foreisic Incon
                ImageView _audioIcon;//For Holding Image Foreisic Incon
                VBox _holdImageIcon;//FOr Holding Image Icon
                VBox _holdVideoIcon;//For Holding Video Icon
                VBox _holdAudioIcon;//For Holding Audio Icon
                BorderPane _leftMenuBorderPane;//For Holding the GridPane and the Bottom Version Test and Copyright
                 //Constant Values for Left Menu
                    final String _imageIconPath="Icons/camera.png";//Store the Image Icon Path
                    final String _videoIconPath="Icons/video.png";//Store the Video Icon Path
                    final String _audioIconPath="Icons/audio.png";//Store the Audion Icon Path
                    final float _leftMenuGrid_LeftMargin=15;//For Gridane Left Margin
                    final float _leftMenuGrid_TopMargin=22;//For Gridane Top Margin
                    final float _leftMenuGrid_RightMargin=0;//For Gridane Right Margin
                    final float _leftMenuGrid_ImageButtonLeftMargin=10;//For Image in Button Left Margin
                    final float _leftMenuGrid_ImageButtonRightMargin=50;//For Image in Button Right Margin
                    final float _verticalGap=1;//For GridPane Left Margin
                    final float _leftMenuGrid_CopyRightP_BottomMargin=0;//For Copyright Bottom Margin
                //End of Constant Values
        
        //Properties for Top Titled Pane Case Details
                    GridPane _caseDetailgridPane;//For Holding the CaseDetail Titlepane Content
                    HBox _selectImageButton;//For Selecting iMage Button
                    HBox _analyzeImageButton;//For Analyse Button Action
                    VBox _imageViewerContaerner;//For Holding the Image Viewer of the Image to be Analyzed
                    VBox _imageDetails;//Hold the Image to Be Analyzed
                    TextField _caseIdTextFiled;//For Entering Case Details
                    TextField _caseTitleTextFiled;//For Entering Case Title Details
                    TextArea _caseIdDescriptionFiled;//For Entering Case Description Details
                    Label _caseIdLabel;//For Case Id Label
                    Label _caseTitleLabel;//For Case Title Label
                    Label _caseDescriptionLabel;//For Case Description Label
                    Label _selectImageLabel;//Hold the Text Select Image
                    Label _analyzeImageLabel;//Hold the Text Analyze Image
                    ImageView _imageToAnalyze;//Hold the Image to Analyze
                        //Image Details
                    String _imageDimesion;//Store the Image Diemesion
                    String _imageWidth;//Store Image Width
                    String _imageBitDepth;//Store Image Depth
                    String _imageName;//Store Image Name
                    String _imageType;//Store Image Type
                    String _ImagePath;//Store Image Path
                    Long _imageSize;//Store Image Size
        //Properties for Bottom Titled Pane
    
    //Class Cosntructor
    
    public DIFCSS(){
        _controlLading=false;
        this.colors_data = new String[]{
            "#330033",
            "#000066",
            "#006600",
            "#333333",
            "#93B514",
            "#93B514",
            "#FF2F88",
            "#FF3C41"
        };
      imageforeinsicJSON="";
      //image_path="";
     _mainSplitPane=new SplitPane();//FOr Holding Both  Case Detail and Case Result Details
     _mainSplitPane.setOrientation(Orientation.VERTICAL);
     _mainSplitPane.setPadding(new Insets(20,0,0,0));
    //_holdingTitledPans=new Accordion();
    _mainBorderPane=new BorderPane();//The Main Borderane
    //_caseDetailsTitledPane=new TitledPane();//For Holding the Case Details
    //_caseAnalysisResultTitledPane=new TitledPane();//For Holding the Case Details
    //For LEFT SIDE MENU
//        _btnImageForeinsic=new Button();//For Opening the Image foresic content
//        _btnVideoForeinsic=new Button();//For Opening the Video foresic content
//         _btnAudioForeinsic=new Button();//For Opening the Audio foresic content
         _leftMenuHolder=new GridPane();//For Holdding the left Menu
         _leftMenuHolder.setStyle("-fx-background-color:whitesmoke;");
         _leftMenuHolder.setOpacity(0.9);
        _imageHBox=new HBox();//For Holding the Image and Text for Image 
        _imageHBox.setId("imageHBoxStyle");
        _videoHBox=new HBox();//For Holding the Image and Text for Image 
        _audioHBox=new HBox();//For Holding the Image and Text for Image 
       _holdImageIcon=new VBox();//FOr Holding Image Icon
       _holdVideoIcon=new VBox();//For Holding Video Icon
       _holdAudioIcon=new VBox();//For Holding Audio Icon
       _copyRightinfo=new HBox();//For Holding the Copyright Information
       _leftMenuBorderPane=new BorderPane();//For Holding the GridPane and the Bottom Version Test and Copyright
       _imageIcon=new ImageView(new Image(_imageIconPath));//For Holding Image Foreisic Icon
       _videoIcon=new ImageView(new Image(_videoIconPath));//For Holding Image Foreisic Icon
       _audioIcon=new ImageView(new Image(_audioIconPath));//For Holding Image Foreisic Icon
      _imageForensicLabe=new Label("Image");//For Displaying the Image Foreinsic Label
      _videoForensicLabe=new Label("Video");//For Displaying the Image Foreinsic Label
      _audioForensicLabe=new Label("Audio");//For Displaying the Image Foreinsic Label
      _softwareCopyrightLabe=new Label("Copy Right DIF 2015");//For Copyright Label
            
    //For END LEFT SIDE MENU
    
      //Start of the Top TitledPane Content
       _caseDetailgridPane=new GridPane();//For Holding the CaseDetail Titlepane Content
       _caseDetailgridPane.setPadding(new Insets(0,0,0,10));
       _selectImageButton=new HBox();//For Selecting iMage Button
       _selectImageButton.setMinHeight(40);
       _analyzeImageButton=new HBox();//For Analyse Button Action
       _analyzeImageButton.setMaxHeight(50);
       _imageViewerContaerner=new VBox();//For Holding the Image Viewer of the Image to be Analyzed
       _imageViewerContaerner.setMaxHeight(299);
        _imageViewerContaerner.setMaxWidth(400);
       _imageViewerContaerner.setFillWidth(true);
        _imageDetails=new VBox();//Hold the Image to Be Analyzed
        _imageDetails.setMaxWidth(150);
        _caseIdTextFiled=new TextField();//For Entering Case Details
        _caseIdTextFiled.setStyle("-fx-border-color:#d0d0d0;");
        //_caseIdTextFiled.setMinHeight(15);
        _caseTitleTextFiled=new TextField();//For Entering Case Title Details
        _caseTitleTextFiled.setStyle("-fx-border-color:#d0d0d0;");
        //_caseTitleTextFiled.setMinHeight(15);
        _caseIdDescriptionFiled=new TextArea();//For Entering Case Description Details
        _caseIdDescriptionFiled.setStyle("-fx-border-color:#d0d0d0;");
        _caseIdDescriptionFiled.setMaxHeight(100);
        _caseIdDescriptionFiled.setMaxWidth(400);
        _selectImageLabel=new Label("Select Image");//Hold the Text Select Image
        _selectImageLabel.setId("selectImage");
        _analyzeImageLabel=new Label("Analayze");//Hold the Text Analyze Image
        _analyzeImageLabel.setId("analyzeLabel");
        _caseIdLabel=new Label("Case ID");//For Case Id Label
        _caseTitleLabel=new Label("Case Title");//For Case Title Label
        _caseDescriptionLabel=new Label("Description");//For Case Description Label
        _imageToAnalyze=new ImageView(new Image("Images/owls-rango.jpg"));//Hold the Image to Analyze
        _imageToAnalyze.setFitHeight(299);
        _imageToAnalyze.setFitWidth(400);
       
      //End of the Top TitledPane Content
        
        //For Case Resuts
        _caseResultTabPane=new TabPane();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage _mainWindow) throws IOException, URISyntaxException{
        
       
        
        
        //Adding content to the BorderPane .Left
        
         /**
         * FOR LEFT MENU
                
        */
            //=====FOR VIDEO BUTTON======
                _videoHBox.setAlignment(Pos.CENTER);//Algning the Content At the Center of HBox
                _videoHBox.setMinWidth(250);
                _videoHBox.setMinHeight(50);
                
                /*Setting Visible false*/
                _videoHBox.setVisible(false);
                
                /*End Setting Visible*/
                _videoHBox.setStyle("-fx-background-color:#dddddd;");
                _holdVideoIcon.setAlignment(Pos.CENTER);//Align the Image Icon
                _holdVideoIcon.setPadding(new Insets(0,20,0,0));
                //VBox.setMargin(_holdVideoIcon, new Insets(0,_leftMenuGrid_ImageButtonRightMargin,0,_leftMenuGrid_ImageButtonLeftMargin));
                        //Setting the Click Event for the Video Button
                _videoHBox.setOnMouseClicked((MouseEvent event) -> {
                    if(event.getButton()==MouseButton.PRIMARY){
                        
                        //Applyinh Effect to the HBOX containg the Textand Image
                        _videoHBox.setStyle("-fx-border-color:ffffff;");
                        _imageHBox.setStyle("-fx-background-color:dddddd;");
                        _audioHBox.setStyle("-fx-background-color:dddddd;");
                        _mainBorderPane.setCenter(null);
                        
                    }
                });
                //Mouse Enter Hover Effect Video Button
                    _videoHBox.setOnMouseEntered(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        _videoHBox.setOpacity(0.5);
                        }
                        });
                    
                    
                //Mouse Leave Event for Video Button
                    
                    _videoHBox.setOnMouseExited(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        _videoHBox.setOpacity(1);
                        }
                        });
                _holdVideoIcon.getChildren().add(_videoIcon);//Adding the Icon to VBox
                _videoHBox.getChildren().addAll(_holdVideoIcon,_videoForensicLabe);//Adding the Image and the TextFiledd to the Horizontal
            
            //==========FOR IMAGE BUTTON====================================
                _imageHBox.setAlignment(Pos.CENTER);//Algning the Content At the Center of HBox
                _imageHBox.setMinWidth(250);
                _imageHBox.setMinHeight(50);
                _imageHBox.setStyle("-fx-background-color:#dddddd;");
                _holdImageIcon.setAlignment(Pos.CENTER);//Align the Image Icon
                _holdImageIcon.setPadding(new Insets(0,20,0,0));
                //VBox.setMargin(_holdImageIcon, new Insets(0,_leftMenuGrid_ImageButtonRightMargin,0,_leftMenuGrid_ImageButtonLeftMargin));
                    //Setting the Click Event for the Image Button
                _imageHBox.setOnMouseClicked(new EventHandler<MouseEvent>(){

                                 @Override
                                public void handle(MouseEvent event) {
                                    if(event.getButton()==MouseButton.PRIMARY){
                                        _imageHBox.setStyle("-fx-border-color:#ffffff;");
                                        _videoHBox.setStyle("-fx-background-color:#dddddd;");
                                        _audioHBox.setStyle("-fx-background-color:#dddddd;");
                                    
                                        
                                        //Calling the Method for Fill the BorderPane.Center
                                        //_stillImageForeinsic(_mainWindow);
                                        
                                        
                                        _mainBorderPane.setCenter(_stillImageForeinsic(_mainWindow));
                     
                        }  } });
                
                //Mouse Enter Hover Effect
                    _imageHBox.setOnMouseEntered(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        _imageHBox.setOpacity(0.5);
                        }
                        });
                    
                    
                //Mouse Leave Event
                    
                    _imageHBox.setOnMouseExited(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        _imageHBox.setOpacity(1);
                        }
                        });
                
                _holdImageIcon.getChildren().add(_imageIcon);//Adding the Icon to VBox
                _imageHBox.getChildren().addAll(_holdImageIcon,_imageForensicLabe);//Adding the Image and the TextFiledd to the Horizontal
            //For Audion Button
                _audioHBox.setAlignment(Pos.CENTER);//Algning the Content At the Center of HBox
                _audioHBox.setMinWidth(250);
                _audioHBox.setMinHeight(50);
                
                /*Setting in not Visible*/
                _audioHBox.setVisible(false);
                _audioHBox.setStyle("-fx-background-color:dddddd;");
                _holdAudioIcon.setAlignment(Pos.CENTER);//Align the Image Icon
                _holdAudioIcon.setPadding(new Insets(0,20,0,0));
                
                
                //Setting the Click Event for the Image Button
                _audioHBox.setOnMouseClicked(new EventHandler<MouseEvent>(){

                                 @Override
                                public void handle(MouseEvent event) {
                                    if(event.getButton()==MouseButton.PRIMARY){
                                    
                                        _audioHBox.setStyle("-fx-border-color:#ffffff;");
                                        _videoHBox.setStyle("-fx-background-color:dddddd;");
                                        _imageHBox.setStyle("-fx-background-color:dddddd;");
                                   
                                    
                                    
                                    } }});
                
                 //Mouse Enter Hover Effect
                    _audioHBox.setOnMouseEntered(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        _audioHBox.setOpacity(0.5);
                        }
                        });
                    
                    
                //Mouse Leave Event
                    
                    _audioHBox.setOnMouseExited(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        _audioHBox.setOpacity(1);
                        }
                        });
                
                
                
                
                //VBox.setMargin(_holdAudioIcon, new Insets(0,_leftMenuGrid_ImageButtonRightMargin,0,_leftMenuGrid_ImageButtonLeftMargin));
                _holdAudioIcon.getChildren().add(_audioIcon);//Adding the Icon to VBox
                _audioHBox.getChildren().addAll(_holdAudioIcon,_audioForensicLabe);//Adding the Image and the TextFiledd to the Horizontal
                
                
                //Grid View For Holding the Menues
                _leftMenuHolder.setVgap(_verticalGap);
                _leftMenuHolder.add(_imageHBox,0, 0,100,1);//Adding the Menu Content to the Grid Image menu
                _leftMenuHolder.add(_videoHBox,0, 1, 100, 1);//Adding the Menu Content to the Grid Video menu
                _leftMenuHolder.add(_audioHBox, 0, 2, 100, 1);//Adding the Menu Content to the Grid Audio menu
                _leftMenuBorderPane.setCenter(_leftMenuHolder);//Adding The Left Menu content to the Pane
                //BorderPane.setMargin(_leftMenuHolder,new Insets());
                _leftMenuBorderPane.setPadding(new Insets(_leftMenuGrid_TopMargin,_leftMenuGrid_RightMargin,25,_leftMenuGrid_LeftMargin));
                    //For Copyright Labe
                _copyRightinfo.setAlignment(Pos.CENTER);//Aling the Copyright Horizontal Box Content
                //HBox.setMargin(_copyRightinfo, new Insets(0,0,0,0));//Setting the Marginh for Copyright Horizontal Box
                _copyRightinfo.getChildren().add(_softwareCopyrightLabe);//Adding the Label to the Copyright Horizontal Box
                _leftMenuBorderPane.setBottom(_copyRightinfo);
                //Final Adding the _leftMenuBorderPane that Contain the left Menu to MainBorder Pane to left
                    _mainBorderPane.setLeft(_leftMenuBorderPane);
                    
        /**
         * eND fOR tHE lEFT mENE cODES
         */
        
        
         /**
          * For Top Menu Codes
          */
                    MenuBar _topMenuBar=new MenuBar();//Creating Menu Bar Object
                    _topMenuBar.setMinHeight(20);//Setting the Height of the Menu 
                    _topMenuBar.setStyle("-fx-background-color:#d0d0d0;");
                        //File Menu
                            Menu _fileMenu=new Menu("File");
                            MenuItem _startCase = new MenuItem("Start Case");
                            MenuItem _saveAsMenuItem = new MenuItem("Save As");
                            MenuItem _exitMenuItem = new MenuItem("Exit");
                            _exitMenuItem.setOnAction(actionEvent -> Platform.exit());//Exit The Application
                            _fileMenu.getItems().addAll(_startCase,_saveAsMenuItem,new SeparatorMenuItem(),_exitMenuItem);
                            _topMenuBar.getMenus().add(_fileMenu);
                            
                         //Edit Case Menu
                            Menu _editMenu=new Menu("Edit");
                            MenuItem _edit_Case = new MenuItem("Start Case");
                            MenuItem _edit_CaseReport = new MenuItem("View Report");
                            _editMenu.getItems().addAll(_edit_Case,_edit_CaseReport);
                            _topMenuBar.getMenus().add(_editMenu);
                         //Start Case Menu
                            Menu _startMenu=new Menu("Start Case");
                            MenuItem _start_Case = new MenuItem("Start Case");
                            MenuItem _view_CaseReport = new MenuItem("View Report");
                            _startMenu.getItems().addAll(_start_Case,_view_CaseReport);
                            _topMenuBar.getMenus().add(_startMenu);
                        //View Case Menu
                            Menu _caseMenu=new Menu("View Case");
                            MenuItem _viewCase = new MenuItem("View Case List");
                           _viewCase.setOnAction(new EventHandler<ActionEvent>(){

                           @Override
                            public void handle(ActionEvent event) {
                                                      
                               try {
                                   getListOfCases("");
                               } catch (IOException | URISyntaxException ex) {
                                   Logger.getLogger(DIFCSS.class.getName()).log(Level.SEVERE, null, ex);
                               }
                                
                                    }

                                });
                            MenuItem _viewCaseReport = new MenuItem("View Report");
                            _viewCaseReport.setOnAction(new EventHandler<ActionEvent>(){

                           @Override
                            public void handle(ActionEvent event) {
                                                      
                                displayCurrentSelectedCaseReport("http://192.168.43.80/reports/");
                                
                                    }

                                });
                            
                            
                            _caseMenu.getItems().addAll(_viewCase,_viewCaseReport);
                            _topMenuBar.getMenus().add(_caseMenu);
                        //Help Menu
                            Menu _helpMenu=new Menu("Help");
                            MenuItem _checkUpdateCase = new MenuItem("Check Updates");
                            MenuItem _versionHelp = new MenuItem("Version");
                            _helpMenu.getItems().addAll(_checkUpdateCase,_versionHelp);
                            _topMenuBar.getMenus().add(_helpMenu);
                            
                        //Setting Menu
                         Menu _settingMenu=new Menu("Setting");
                         MenuItem _insert_api_key = new MenuItem("Insert API Key");
                         MenuItem _update_api_key = new MenuItem("Update API Key");
                         
                         _settingMenu.getItems().addAll(_insert_api_key,_update_api_key);
                     _insert_api_key.setOnAction(new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent event)
                    {
                     TextInputDialog _api_key = new TextInputDialog("API Key");
                     _api_key.setTitle("API Key");
                     _api_key.setHeaderText("You Must Insert Valid API Key");
                     _api_key.setContentText("Insert Your API Key");
                     /*Getting the Response of the Diloag*/
                     Optional<String> result_dialog = _api_key.showAndWait();
                     
                     /*Cheking API key is ther in Text Box*/
                     if(result_dialog.isPresent())
                     {
                         try {
                             /*Creating SettingAPi Class Object*/
                             // SettingAPI set_api = new SettingAPI();
                             // set_api.setAPIKey(result_dialog.get());
                             Hedwig.saveSettings(result_dialog.get());
                         } catch (IOException ex) {
                             Logger.getLogger(DIFCSS.class.getName()).log(Level.SEVERE, null, ex);
                         }
                     }
                    }
                });
                     
               _update_api_key.setOnAction(new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent event)
                    {
                     TextInputDialog _api_key = new TextInputDialog("New API Key");
                     _api_key.setTitle("Update API Key");
                     _api_key.setHeaderText("You Must Update Valid API Key");
                     _api_key.setContentText("Update Your API Key");
                     /*Getting the Response of the Diloag*/
                     Optional<String> result_dialog = _api_key.showAndWait();
                     
                     /*Cheking API key is ther in Text Box*/
                     if(result_dialog.isPresent())
                     {
                        /*Creating SettingAPi Class Object*/
//                        SettingAPI set_api = new SettingAPI();
//                        set_api.updateAPIKey(result_dialog.get());
                         
                     }
                    }
                });
                 _topMenuBar.getMenus().add(_settingMenu);
                //Adding the Menu to the MainBorder Pane
                   _mainBorderPane.setTop(_topMenuBar);
            /**
             * End Of the Top Menu Codes
             */
        
       
        
        Scene _mainContentContainer = new Scene(_mainBorderPane,_screen_size.getDisplayMode().getWidth(),_screen_size.getDisplayMode().getHeight());
        _mainContentContainer.setFill(Color.rgb(255, 255, 255));
        _mainWindow.setTitle("DIF");//Setting the Windows Title
        _mainWindow.setScene(_mainContentContainer);//Adding the Scene to the Primary Stage
        _mainWindow.setMinWidth(_screen_size.getDisplayMode().getWidth());//Set the Minimum Windows width
        _mainWindow.setMinHeight(_screen_size.getDisplayMode().getHeight());//Set the Minimum Windows Heigth
        _mainWindow.setMaximized(true);//Enable Windows Maxmize Mode
        _mainContentContainer.getStylesheets().add(DIFCSS.class.getResource("main.css").toExternalForm());
        _mainWindow.show();
    }

    //Implemetation of the Map Method
    
    @Override
public void mapInitialized() {

    try{
    
    
    MapOptions mapOptions = new MapOptions();

    mapOptions.center(new LatLong(-6.17306,35.7419))
            .mapType(MapTypeIdEnum.ROADMAP)
            .overviewMapControl(false)
            .panControl(false)
            .rotateControl(false)
            .scaleControl(false)
            .streetViewControl(false)
            .zoomControl(false)
            .zoom(12);

    map = mapView.createMap(mapOptions);

    //Add a marker to the map
    MarkerOptions markerOptions = new MarkerOptions();

    markerOptions.position( new LatLong(-6.17306,35.7419) )
                .visible(Boolean.TRUE)
                .title("Photo Taken");

    Marker marker = new Marker( markerOptions );

    map.addMarker(marker);

    }catch(Exception e){
//    Console.log(difcss.Logger.ERROR,"No Internet Connection or Low Signal");
    
    }
    
}
     
  /**
   * /*===================================================START OF THE STILL IMAGE FORENSIC==================================================
   * REMEMBER METHOD FOR DISPLAYING THE STILL IMAGE FOREINSIC INTERFACE
     * @param mainStage
     * @return 
   */
        
public SplitPane _stillImageForeinsic(Stage mainStage) {
           /**
        * For Case Details
        */        
                    _caseResultTabPane.getTabs().clear();
                    _mainSplitPane.getItems().clear();
                    _selectImageButton.getChildren().clear();
                    _analyzeImageButton.getChildren().clear();
                    _imageViewerContaerner.getChildren().clear();
                    _caseDetailgridPane.getChildren().clear();
                    _selectImageButton.setAlignment(Pos.CENTER);
                    _selectImageButton.setStyle("-fx-background-color:#6e6e6e;");
                    _selectImageButton.getChildren().add(_selectImageLabel);
                            // Even for the Select Image Button
                        _selectImageButton.setOnMouseClicked(new EventHandler<MouseEvent>(){

                                 @Override
                                public void handle(MouseEvent event) {
                                    if(event.getButton()==MouseButton.PRIMARY){
                                        FileChooser fileChooser = new FileChooser();
                                        fileChooser.setTitle("Open Image File");
                                       File _imageFile= fileChooser.showOpenDialog(mainStage);
                                       if(_imageFile !=null){
                                           //Image current_image=new Image("/"+_imageFile.getPath());
                                           //_imageDimesion;//Store the Image Diemesion
                                           // _imageWidth;//Store Image Width
                                           //_imageBitDepth=;//Store Image Depth
                                           _imageName=_imageFile.getName();//Store Image Name
                                           //_imageType=_imageFile.;//Store Image Type
                                           _ImagePath=_imageFile.getAbsolutePath();//Store Image Path
                                           _imageSize=_imageFile.length()/1000;//Store Image Size
                                           _imageToAnalyze.setImage(new Image(_imageFile.toURI().toString()));
                                           _imageDetails.getChildren().clear();
                                           _imageDetails.getChildren().add(new Label("Dimesion: 256 x 256 Pixel"));
                                           _imageDetails.getChildren().add(new Label("Width: 256 Pixel"));
                                           _imageDetails.getChildren().add(new Label("Height: 256 Pixel"));
                                           _imageDetails.getChildren().add(new Label("Bit Depth: 32 Bits"));
                                           _imageDetails.getChildren().add(new Label(" "));
                                           _imageDetails.getChildren().add(new Label("Name:" + _imageName));
                                           _imageDetails.getChildren().add(new Label("Item Type: JPG"));
                                           _imageDetails.getChildren().add(new Label("Path:" + _ImagePath));
                                           _imageDetails.getChildren().add(new Label("Size: " + (_imageSize) + "KB"));
                                           
                                           System.out.print(_imageName);
                                           System.out.print(_ImagePath);
                                           System.out.print(_imageFile.getPath());
                                            try {
                                                System.out.print(_imageFile.getCanonicalPath());
                                            } catch (IOException ex) {
                                                Logger.getLogger(DIFCSS.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                       }
                                    
                                   
                        }  } });
                        
                       //Mouse Enter Hover Effect
                    _selectImageButton.setOnMouseEntered(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        _selectImageButton.setOpacity(0.5);
                        }
                        });
                    
                    
                //Mouse Leave Event
                    
                    _selectImageButton.setOnMouseExited(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        _selectImageButton.setOpacity(1);
                        }
                        }); 
                        
                        
                    _analyzeImageButton.setAlignment(Pos.CENTER);
                    _analyzeImageButton.setStyle("-fx-background-color:#3a88ad;");
                    _analyzeImageButton.getChildren().add(_analyzeImageLabel);    
                    
                        //Method for Image Nalyze Button
                    _analyzeImageButton.setOnMouseClicked(new EventHandler<MouseEvent>(){

                        @Override
                        public void handle(MouseEvent event) {
                           if(event.getButton()==MouseButton.PRIMARY){
                           _controlLading=true;
                           
                            try {
                                /*Setting APi Key*/
//                                SettingAPI api_ky = new SettingAPI();
//                                Hedwig.api_key = Hedwig.getAPIKey();
                                
                                Hedwig.gotoCopyMove(_ImagePath);
                            } catch (IOException ex) {
                                Logger.getLogger(DIFCSS.class.getName()).log(Level.SEVERE, null, ex);
                            }
                           
                           ArrayList<HedwigPacket> HedwigPacketArrayList = new ArrayList<>();
                           HedwigPacketArrayList.add(new HedwigPacket("name",_caseTitleTextFiled.getText()));
                           HedwigPacketArrayList.add(new HedwigPacket("description", _caseIdDescriptionFiled.getText()));
                           HedwigPacketArrayList.add(new HedwigPacket("image", _ImagePath));

                               try {
                                   imageforeinsicJSON=Hedwig.go(Route.NEW_ANALYSIS, HedwigPacketArrayList);
                                   _controlLading=false;
                                   _stillImageForeinsic(null);//refreshing the method
                                   System.out.println(imageforeinsicJSON);
                                   
                                   /*Displaying Tabs*/
                    if(imageforeinsicJSON.isEmpty()){
//                     Console.log(difcss.Logger.ERROR,"No JSON Returned Data");
     
                   }else{
                   Tab _Dashboard_Details=new Tab("Dashboard");
                        if(_displayDashBoard("")!=null){
                            _Dashboard_Details.setContent(_displayDashBoard(""));
                            _caseResultTabPane.getTabs().add(_Dashboard_Details);
                        }     
                            
                   Tab _Static_Details=new Tab("Static Analysis");
                        if(_displayStaticData("")!=null){
                            _Static_Details.setContent(_displayStaticData(""));
                            _caseResultTabPane.getTabs().add(_Static_Details);
                        }     
                        
                   Tab _EXIF_Details=new Tab("EXIF Metadata");
                        if(_displayEXIFData("")!=null){
                            _EXIF_Details.setContent(_displayEXIFData(""));
                            _caseResultTabPane.getTabs().add(_EXIF_Details);
                        }           
         
                   Tab _IPTC_Details=new Tab("IPTC Metadata");
                        if(_displayIPTCData("")!=null){
                            _IPTC_Details.setContent(_displayIPTCData(""));
                            _caseResultTabPane.getTabs().add(_IPTC_Details);
                        }     
                                            
                   Tab _XMP_Details=new Tab("XMP Metadata");
                        if(_displayXMPData("")!=null){
                             _XMP_Details.setContent(_displayXMPData(""));
                            _caseResultTabPane.getTabs().add(_XMP_Details);
                        }  
                  
                   Tab _preview_Details=new Tab("Preview Extraction");
                   
                   Tab _Localization_Details=new Tab("Map");
                         if(_displayLocalazationMap_GoogleMap("")!=null){
                           _Localization_Details.setContent(_displayLocalazationMap_GoogleMap(""));
                            _caseResultTabPane.getTabs().add(_Localization_Details);
                        }  
                            
                  
                   Tab _Signature_Check_Details=new Tab("Signature Check");
                         if(_displaySignature("")!=null){
                            _Signature_Check_Details.setContent(_displaySignature(""));
                            _caseResultTabPane.getTabs().add(_Signature_Check_Details);
                        }  
                         
                    Tab _CopyMove_Detection=new Tab("Clone Detection");
                         if(true){
                             System.out.print("This is Blur Blurr Work Now");
                            _CopyMove_Detection.setContent(_displayCopyMoveImage());
                            _caseResultTabPane.getTabs().add(_CopyMove_Detection);
                        } 
                     Tab _Error_Level_Details=new Tab("Error Level Analysis");
                        if(_errorLevelDisplay(null)!=null){
                           _Error_Level_Details.setContent(_errorLevelDisplay(null));
                            _caseResultTabPane.getTabs().add(_Error_Level_Details);
                        }  
                   
                   
                    
                   /**
                    * * 
                    * End of the Case Filled Titled*/ 
                   }
                                   
                                   
                                   /*End Displaying Tabs*/
                     
                                   
                               } catch (IOException | URISyntaxException ex) {
                                   Logger.getLogger(DIFCSS.class.getName()).log(Level.SEVERE, null, ex);
                               }
                               
                               
                          
                           }
                            
                        }
                  
                    });
                    
                    
                    //Mouse Enter Hover Effect
                    _analyzeImageButton.setOnMouseEntered(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        _analyzeImageButton.setOpacity(0.5);
                        }
                        });
                    
                    
                //Mouse Leave Event
                    
                    _analyzeImageButton.setOnMouseExited(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        _analyzeImageButton.setOpacity(1);
                        }
                        }); 
                    
                    _imageViewerContaerner.setAlignment(Pos.CENTER);
                    _imageViewerContaerner.setStyle("-fx-background-color:#6e6e6e;");
                    //_imageViewerContaerner.setMaxHeight(10);
                    //_imageViewerContaerner.setMaxWidth(10);
                    _imageViewerContaerner.getChildren().add(_imageToAnalyze);
                    _imageDetails.setAlignment(Pos.TOP_LEFT);
                    _imageDetails.setStyle("-fx-background-color:white;");
                        //COde for Image Detail Removed From Here

                  // _caseDetailgridPane.setAlignment(Pos.CENTER_LEFT);//Positining the Grid Pane
                   _caseDetailgridPane.setHgap(1);//Adding the Horizontal Gap
                   _caseDetailgridPane.setVgap(15);//Adding the Vertical Gape
                   //_caseDetailgridPane.add(_caseIdLabel, 0, 0);
                   //_caseDetailgridPane.add(_caseIdTextFiled,1,0, 150, 1);
                   _caseDetailgridPane.add(_caseTitleLabel, 0, 0);
                   _caseDetailgridPane.add(_caseTitleTextFiled, 1, 0,150, 1);
                   _caseDetailgridPane.add(_caseDescriptionLabel, 0, 2);
                   _caseDetailgridPane.add(_caseIdDescriptionFiled, 1,2,150,1);
                   _caseDetailgridPane.add(_selectImageButton, 1, 3, 150, 1);
                   _caseDetailgridPane.add(_analyzeImageButton, 1, 4, 150, 1);
                   _caseDetailgridPane.add(_imageViewerContaerner,190, 0,254,5);
                   _caseDetailgridPane.add(_imageDetails,450,0,170,5);
                   //_caseDetailgridPane.add(new Line(),0, 6,258,1);
                  
                   //_caseDetailsTitledPane.setContent(_caseDetailgridPane);
          /**
           * End of the Case Details
           */     
                   
              /**
               * 
               * Start of the Case Detail TITLed Pane
               */
                   
                  
     //For Loading Button
                 //_controlLading=true;
                 while(_controlLading){
                 Tab _Thumbnails_Check_Details=new Tab("Analyzing..........");
                 //ImageView to Hold the 
                 BorderPane _loadingImage=new BorderPane();
                 ImageView _imageloading=new ImageView("Images/best_loading.gif");
                 _imageloading.setSmooth(true);
                 _imageloading.setFitWidth(218.75);
                 _imageloading.setFitHeight(175);
                 _loadingImage.setCenter(_imageloading);
                 _Thumbnails_Check_Details.setContent(_loadingImage);
                 _Thumbnails_Check_Details.setDisable(true);
                 _caseResultTabPane.getTabs().add(_Thumbnails_Check_Details);
                 }
                 
                 
                 
                 
                 
//Creating the Windoes Scene that Contain the Content
    _mainSplitPane.getItems().addAll(_caseDetailgridPane,_caseResultTabPane);//Adding the Case Detail Content to the Sptli Pane
      return  _mainSplitPane ;
    }
	      
      /**
       * END FOR STILL IMAGE FOREINSOC INTERFACE
       * /*===================================================END OF  STILL IMAGE FOREINSIC===================================================
     * @param result
     * @return 
       */ 
/*===========METHOD FOR DISPLYING SIGNATURE DATA===============================*/
public ScrollPane _displaySignature(String result){
			//Local Variable for holiding data
		
	ScrollPane _SignatureData=new ScrollPane();//The returned object
	VBox _mainVBOX=new VBox();//For holding the all content to ScrollPane
        _mainVBOX.setPadding(new  Insets(5));
	GridPane _containSignatureData=new GridPane();
        
        _containSignatureData.setVgap(5);
        _containSignatureData.setHgap(5); 
                String key_value="signatures";
                
                try{
                    JSONObject signature=new JSONObject(imageforeinsicJSON);
		JSONArray signature_ArrayData = signature.getJSONArray(key_value);
	
		//Processing the JSON Data and Displyong Data
		for(int row=0;row<signature_ArrayData.length();row++){
		
                   JSONObject temp= signature_ArrayData.getJSONObject(row);
                
                String name =temp.getString("name");
                int severity =temp.getInt("severity");
                
						//Temp Data
			Button tempButton =new Button();// for Severity 
                        //tempButton.setPadding(new Insets(5));
                        tempButton.setAlignment(Pos.CENTER);
                        tempButton.setMinWidth(60);
                        Label tempname=new Label();
                        tempname.setPadding(new Insets(1,10,0,10));
                        tempname.setAlignment(Pos.CENTER);
			HBox temphorizontalBox=new HBox();// for holding tempButton and 
                        
                            if(severity==1){
				tempButton.setText("Low");
				tempButton.setStyle("-fx-background-color:#006680");
                                tempButton.setTextFill(Color.web("FFFFFF"));
                                tempname.setText(name);
                            }else if(severity==2){
				tempButton.setText("Medium");
				tempButton.setStyle("-fx-background-color:#333300");
                                tempButton.setTextFill(Color.web("FFFFFF"));
                                tempname.setText(name);
				}else if(severity==3){
				tempButton.setText("High");
				tempButton.setStyle("-fx-background-color:Red;");
                                tempButton.setTextFill(Color.web("FFFFFF"));
                                tempname.setText(name);
										}
                //Adding the Data GridView
                                        temphorizontalBox.getChildren().addAll(tempButton,tempname);
					_containSignatureData.add(temphorizontalBox, 0,row);
                     
			}


	//Adding the _mainVBOX to the SPlitPane
        _mainVBOX.setStyle("-fx-background-color:white;");
	_mainVBOX.getChildren().add(_containSignatureData);
        _mainVBOX.setFillWidth(true);
        _SignatureData.setStyle("-fx-background-color:white;");
        _SignatureData.setFitToHeight(true);
        _SignatureData.setFitToWidth(true);
	_SignatureData.setContent(_mainVBOX);
                return _SignatureData;
                }catch(Exception e){
                
                return null;
                
                }
    
	}

/*========== METHOD FOR DISPLAYING  EXIF METADATA============================*/
public ScrollPane _displayEXIFData(String result){
			//Local Variable for holiding data
		
	ScrollPane _EXIFData=new ScrollPane();//The returned object
	VBox _mainVBOX=new VBox();//For holding the all content to ScrollPane
        _mainVBOX.setPadding(new  Insets(5));
	GridPane _containEXIFData=new GridPane();
        
        _containEXIFData.setVgap(5);
        _containEXIFData.setHgap(5);   
        
        try{
        String key_value="metadata";
                String exif="Exif";
		JSONObject exif_data=new JSONObject(imageforeinsicJSON).getJSONObject(key_value);
		JSONObject exif_object = exif_data.getJSONObject("Exif");
                //JSONArray exif_data_ArrayData = exif_object.getJSONArray(exif);
                //Iterator<String> keys = exif_object.keys();//For Object Iteration
		//Processing the JSON Data and Displyong Data
		//Important Variable for the Displayind data
                int temp_last_row_index=0;
                    
        //for(int row=0;row<exif_object.length();row++){
		
            JSONArray exif_data_ArrayData = exif_object.names();
                for(int rows_arraylist=0;rows_arraylist<exif_data_ArrayData.length();rows_arraylist++){
                    
                    String name=exif_data_ArrayData.getString(rows_arraylist);
                    JSONObject temp_data = exif_object.getJSONObject(name);
                    
                    //Getting the Array list of Value
                    JSONArray array_name = temp_data.names();
                        for(int data_in_name_object=0;data_in_name_object<array_name.length();data_in_name_object++){
                                 //HorizontalBox Dynamic Object
                            HBox temp_box=new HBox();//Holding Key and Label
                            Label temp_key=new Label();//Holding Key id
                                    temp_key.setId("value_key_EXIF");
                                    temp_key.setTextFill(Color.web(colors_data[rows_arraylist]));
                            Label temp_value=new Label();//Holding key value
                                  temp_value.setId("value_data_EXIF");
                                  temp_value.setTextFill(Color.web(colors_data[rows_arraylist]));
                            String key =array_name.getString(data_in_name_object);
				temp_key.setText(key);
                            String value =":" +" " + temp_data.getString(key);
				temp_value.setText(value);
					//Addint key and Value to the Horizontal Box
				temp_box.getChildren().addAll(temp_key,temp_value);
                                //temp_last_row_index=temp_last_row_index+data_in_name_object;
                                _containEXIFData.add(temp_box,15,temp_last_row_index,20,1);
                                            if(data_in_name_object==array_name.length()){
                                                temp_last_row_index=data_in_name_object;
                                            
                                            }else{
                                            temp_last_row_index++;
                                            
                                            }
                                             
                                    }
                        
                        //_containEXIFData.add(new Label("Space"),15,temp_last_row_index+2,20,1);
                                        //Setting the Firts Column Value
				Label temp_first_colimnValue=new Label();
                                temp_first_colimnValue.setText(name.toUpperCase());
				//temp_first_colimnValue.setText(keys .next());
					//Adding to this row
					int tempindex=0;
                                            if(name.equalsIgnoreCase("photo")){
                                              _containEXIFData.add(new Label(name.toUpperCase()),0,temp_last_row_index,5,1);  //kUDA DATA NIMEFUTA                                      
                                            }else if(name.equalsIgnoreCase("image")){
                                                _containEXIFData.add(new Label(name.toUpperCase()),0,temp_last_row_index,5,1);
                                            
                                            }else if(name.equalsIgnoreCase("Thumbnail")){
                                                _containEXIFData.add(new Label(name.toUpperCase()),0,temp_last_row_index,5,1);
                                            
                                            }else if(name.equalsIgnoreCase("iop")){
                                                _containEXIFData.add(new Label(name.toUpperCase()),0,temp_last_row_index,5,1);
                                            
                                            }
                                        
                                        
                                //_containEXIFData.add(new Button(name),0,temp_last_row_index-32,5,1);      
				//_containEXIFData.add(temp_first_colimnValue, 0,tempindex/2,5,1); 
                                
                    
                           
                                }
            
 
                //}
                

//_containEXIFData.setGridLinesVisible(true);
	//Adding the _mainVBOX to the SPlitPane
        _mainVBOX.setStyle("-fx-background-color:white;");
	_mainVBOX.getChildren().add(_containEXIFData);
        _mainVBOX.setFillWidth(true);
        _EXIFData.setStyle("-fx-background-color:white;");
        _EXIFData.setFitToHeight(true);
        _EXIFData.setFitToWidth(true);
        _EXIFData.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	_EXIFData.setContent(_mainVBOX);  
return _EXIFData;
            
        }catch(Exception e){
        
        
        return null;
        }

	}
/*=========METHOD FOR DISPLAYING IPTC DATA===================================*/
public ScrollPane _displayIPTCData(String result){
			//Local Variable for holiding data
		
	ScrollPane _IPTCData=new ScrollPane();//The returned object
	VBox _mainVBOX=new VBox();//For holding the all content to ScrollPane
        _mainVBOX.setPadding(new  Insets(5));
	GridPane _containIPTCData=new GridPane();
        
        _containIPTCData.setVgap(5);
        _containIPTCData.setHgap(5);
                     
        try{
            String key_value="metadata";
                String exif="Iptc";
		JSONObject exif_data=new JSONObject(imageforeinsicJSON).getJSONObject(key_value);
		JSONObject exif_object = exif_data.getJSONObject("Iptc");
                //JSONArray exif_data_ArrayData = exif_object.getJSONArray(exif);
                //Iterator<String> keys = exif_object.keys();//For Object Iteration
		//Processing the JSON Data and Displyong Data
		//Important Variable for the Displayind data
                int temp_last_row_index=0;
                    
        //for(int row=0;row<exif_object.length();row++){
		
            JSONArray exif_data_ArrayData = exif_object.names();
                for(int rows_arraylist=0;rows_arraylist<exif_data_ArrayData.length();rows_arraylist++){
                    
                    String name=exif_data_ArrayData.getString(rows_arraylist);
                    JSONObject temp_data = exif_object.getJSONObject(name);
                    
                    //Getting the Array list of Value
                    JSONArray array_name = temp_data.names();
                        for(int data_in_name_object=0;data_in_name_object<array_name.length();data_in_name_object++){
                                 //HorizontalBox Dynamic Object
                            HBox temp_box=new HBox();//Holding Key and Label
                            Label temp_key=new Label();//Holding Key id
                                temp_key.setId("value_key_IPTC");
                            Label temp_value=new Label();//Holding key value
                                temp_value.setId("value_data_IPTC");
                                temp_value.setTextFill(Color.web(colors_data[rows_arraylist]));
                            String key =array_name.getString(data_in_name_object);
				temp_key.setTextFill(Color.web(colors_data[rows_arraylist]));
                                temp_key.setText(key);
                            String value =":" +" " + temp_data.getString(key);
				temp_value.setText(value);
					//Addint key and Value to the Horizontal Box
				temp_box.getChildren().addAll(temp_key,temp_value);
                                //temp_last_row_index=temp_last_row_index+data_in_name_object;
                                _containIPTCData.add(temp_box,15,temp_last_row_index,20,1);
                                            if(data_in_name_object==array_name.length()){
                                                temp_last_row_index=data_in_name_object;
                                            
                                            }else{
                                            temp_last_row_index++;
                                            
                                            }
                                             
                                    }
                        
                        //_containEXIFData.add(new Label("Space"),15,temp_last_row_index+2,20,1);
                                        //Setting the Firts Column Value
				Label temp_first_colimnValue=new Label();
                                temp_first_colimnValue.setText(name.toUpperCase());
				//temp_first_colimnValue.setText(keys .next());
					//Adding to this row
					int tempindex=0;
                                            if(name.equalsIgnoreCase("Application2")){
                                              _containIPTCData.add(new Label(name.toUpperCase()),0,temp_last_row_index-3,5,1);                                        
                                            }else if(name.equalsIgnoreCase("Envelope")){
                                                _containIPTCData.add(new Label(name.toUpperCase()),0,temp_last_row_index-1,5,1);
                                       
                                            }else{
                                                _containIPTCData.add(new Label(name.toUpperCase()),0,temp_last_row_index-2,5,1);
                                            
                                            }
                                }
	//Adding the _mainVBOX to the SPlitPane
        _mainVBOX.setStyle("-fx-background-color:white;");
	_mainVBOX.getChildren().add(_containIPTCData);
        _mainVBOX.setFillWidth(true);
        _IPTCData.setStyle("-fx-background-color:white;");
        _IPTCData.setFitToHeight(true);
        _IPTCData.setFitToWidth(true);
        _IPTCData.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	_IPTCData.setContent(_mainVBOX);
     
return _IPTCData;
            
        }catch(Exception e){
           return null;
        }
 
	}

/*==============METHOD FOR DISPLAYING XMP METADATA===========================*/
public ScrollPane _displayXMPData(String result){
			//Local Variable for holiding data
		
	ScrollPane _XMPData=new ScrollPane();//The returned object
	VBox _mainVBOX=new VBox();//For holding the all content to ScrollPane
        _mainVBOX.setPadding(new  Insets(5));
	GridPane _containXMPData=new GridPane();
        _containXMPData.setId("XMPGridPane");
        _containXMPData.setVgap(5);
        _containXMPData.setHgap(5);
	   
        try{
        String key_value="metadata";
                String exif="Xmp";
		JSONObject xmp_data=new JSONObject(imageforeinsicJSON).getJSONObject(key_value);
		JSONObject xmp_object = xmp_data.getJSONObject("Xmp");
                //JSONArray exif_data_ArrayData = exif_object.getJSONArray(exif);
                //Iterator<String> keys = exif_object.keys();//For Object Iteration
		//Processing the JSON Data and Displyong Data
		//Important Variable for the Displayind data
                int temp_last_row_index=0;
                    
        //for(int row=0;row<exif_object.length();row++){
		
            JSONArray xmp_data_ArrayData = xmp_object.names();
                for(int rows_arraylist=0;rows_arraylist<xmp_data_ArrayData.length();rows_arraylist++){
                    
                    String name=xmp_data_ArrayData.getString(rows_arraylist);
                    JSONObject temp_data = xmp_object.getJSONObject(name);
                    
                    //Getting the Array list of Value
                    JSONArray array_name = temp_data.names();
                        for(int data_in_name_object=0;data_in_name_object<array_name.length();data_in_name_object++){
                                 //HorizontalBox Dynamic Object
                            HBox temp_box=new HBox();//Holding Key and Label
                            Label temp_key=new Label();//Holding Key id
                            temp_key.setId("key_value");
                            //temp_key.setStyle("-fx-font-weight:Bold");
                            temp_key.setTextFill(Color.web(colors_data[rows_arraylist]));
                            //temp_key.setStyle("-fx-font-size:100px;");
                            //temp_key.setStyle("-fx-font-family:Open Sans");
                            Label temp_value=new Label();//Holding key value
                            temp_value.setId("value_data");
                            temp_value.setTextFill(Color.web(colors_data[rows_arraylist]));
                            //temp_value.setStyle("-fx-font-weight:Bold");
                            //temp_value.setStyle("-fx-font-family:Open Sans");
                            String key =array_name.getString(data_in_name_object);
				temp_key.setText(key);
                            String value =":" +" " + temp_data.getString(key);
				temp_value.setText(value);
					//Addint key and Value to the Horizontal Box
				temp_box.getChildren().addAll(temp_key,temp_value);
                                //temp_last_row_index=temp_last_row_index+data_in_name_object;
                                _containXMPData.add(temp_box,15,temp_last_row_index,20,1);
                                            if(data_in_name_object==array_name.length()){
                                                temp_last_row_index=data_in_name_object;
                                            
                                            }else{
                                            temp_last_row_index++;
                                            
                                            }
                                             
                                    }
                        
                        //_containEXIFData.add(new Label("Space"),15,temp_last_row_index+2,20,1);
                                        //Setting the Firts Column Value
				Label temp_first_colimnValue=new Label();
                                //String toUppername=name.toUpperCase();
                                temp_first_colimnValue.setText(name);
				//temp_first_colimnValue.setText(keys .next());
					//Adding to this row
					int tempindex=0;
                                            if(name.equalsIgnoreCase("xmpMM")){
                                              _containXMPData.add(new Label(name.toUpperCase()),0,temp_last_row_index-28,5,1);                                        
                                            }else if(name.equalsIgnoreCase("photoshop")){
                                                _containXMPData.add(new Label(name.toUpperCase()),0,temp_last_row_index-3,5,1);
                                       
                                            }else if(name.equalsIgnoreCase("xmp")){
                                                _containXMPData.add(new Label(name.toUpperCase()),0,temp_last_row_index-3,5,1);
                                            
                                            }else if(name.equalsIgnoreCase("dc")){
                                                _containXMPData.add(new Label(name.toUpperCase()),0,temp_last_row_index-2,5,1);
                                            
                                            }else if(name.equalsIgnoreCase("xmp")){
                                                _containXMPData.add(new Button(name.toUpperCase()),0,temp_last_row_index-3,5,1);
                                            
                                            }
                                        
                                        
                                //_containEXIFData.add(new Button(name),0,temp_last_row_index-32,5,1);      
				//_containEXIFData.add(temp_first_colimnValue, 0,tempindex/2,5,1); 
                                
                    
                           
                                }
            
 
                //}
                

//_containEXIFData.setGridLinesVisible(true);
	//Adding the _mainVBOX to the SPlitPane
        _mainVBOX.setStyle("-fx-background-color:white;");
	_mainVBOX.getChildren().add(_containXMPData);
        _mainVBOX.setFillWidth(true);
        _XMPData.setStyle("-fx-background-color:white;");
        _XMPData.setFitToHeight(true);
        _XMPData.setFitToWidth(true);
        _XMPData.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	_XMPData.setContent(_mainVBOX);   
return _XMPData;
        }catch(Exception e){
        
        return null;
        
        }
                
 
	}
/*==================METHOD FOR DISPLAYING STATIC METADATA====================*/
public ScrollPane _displayStaticData(String result){
			//Local Variable for holiding data
		
	ScrollPane _staticData=new ScrollPane();//The returned object
	GridPane _containStaticData=new GridPane();
        
        _containStaticData.setVgap(5);
        _containStaticData.setHgap(5);
		
        try{
        
        
		//Creating TABPANE for Static Data
		
		TabPane _holdStaticData=new TabPane();
				//Creating Panes
					Tab static_info=new Tab("Static Info");
					Tab file_type=new Tab("File Type");
					Tab hashes_info=new Tab("Hashes");
     //Hashes Tab JSON Data Processing
      /*========================== HASHES DATA JSON PROCESSING=================*/
    
            GridPane temp_hases=new GridPane();//for Positioning of Data
            temp_hases.setVgap(4);
            temp_hases.setPadding(new Insets(10,0,0,10));
            JSONObject hashes_object=new JSONObject(imageforeinsicJSON).getJSONObject("hash");
            Iterator<String> keys_hashes = hashes_object.keys();
            int number_ofKeys=0;
             while(keys_hashes.hasNext()&& number_ofKeys!=hashes_object.length()){
                 HBox temp_hashkey_value_holder=new HBox();
                 
                 String temp_hashKey=keys_hashes.next();
                     String uppperCase_temp_hashKey=temp_hashKey.toUpperCase();
                    Label lbl_hashKey=new Label(uppperCase_temp_hashKey);
                    lbl_hashKey.setId("hashes_key");
                    //lbl_hashKey.setFont(new Font(12));
                    lbl_hashKey.setMinWidth(60);
                    lbl_hashKey.setTextFill(Color.web("#003333"));
                    //lbl_hashKey.setStyle("-fx-font-weight: bold");
                    //lbl_hashKey.setStyle("-fx-font-family:Open Sans");
                String temp_hashKey_value=" " + " : " +hashes_object.getString(temp_hashKey);
                    Label lbl_hashvalue=new Label(temp_hashKey_value);
                    lbl_hashvalue.setFont(new Font(12));
                    lbl_hashvalue.setTextFill(Color.web("#003333"));
                    //lbl_hashvalue.setStyle("-fx-font-weight:bold");
//                    lbl_hashvalue.setStyle("-fx-font-family:Open Sans");
                 temp_hashkey_value_holder.getChildren().addAll(lbl_hashKey,lbl_hashvalue);
                 
                 //Adding to the GridPane
                 temp_hases.add(temp_hashkey_value_holder,0, number_ofKeys);
                 
                 number_ofKeys++;
              //keys_hashes.next();
             }
            //Adding content to Hashes TabPane
             hashes_info.setContent(temp_hases);
/*==========================END OF HASHES DATA JSON PROCESSING=================*/             
             
             
/*========================== START OF FILE DATA JSON PROCESSING=================*/
            GridPane temp_file_type=new GridPane();//for Positioning of Data
            temp_file_type.setId("grid-pane");
            temp_file_type.setHgap(4);
            temp_file_type.setPadding(new Insets(10,0,0,10));
            HBox temp_filetypeData=new HBox();
            temp_hases.setVgap(4);
            temp_hases.setPadding(new Insets(10,0,0,10));
            JSONObject filetype_object=new JSONObject(imageforeinsicJSON);
            
            Label temp_filetype=new Label(filetype_object.getString("file_type"));
                    temp_filetype.setFont(new Font(12));
                    temp_filetype.setMinWidth(60);
                    temp_filetype.setTextFill(Color.web("#728308"));
            temp_filetypeData.getChildren().add(temp_filetype);
            String key_coulum_text="Image Exetension Type : ";
            Label key_coulum=new Label(key_coulum_text);
                    key_coulum.setFont(new Font(12));
                    key_coulum.setMinWidth(60);
                    key_coulum.setTextFill(Color.web("#0076a3"));
            temp_file_type.add(key_coulum, 0, 0);
            temp_file_type.add(temp_filetypeData, 1, 0);
            file_type.setContent(temp_file_type);
/*========================== END OF FILE DATA JSON PROCESSING=================*/ 
             
             
/*========================== START OF STATIC DATA JSON PROCESSING=================*/
 
/*========================== END OF STATIC DATA JSON PROCESSING=================*/ 
           //Adding Tabs to Tabpane
          _holdStaticData.setStyle("-fx-background-color:white;");
          _holdStaticData.getTabs().addAll(static_info,file_type,hashes_info);
         
        _staticData.setStyle("-fx-background-color:white;");
        _staticData.setFitToHeight(true);
        _staticData.setFitToWidth(true);
        _staticData.setStyle("-fx-background-color:white;");
	_staticData.setContent(_holdStaticData);
        
return _staticData;
        
        }catch(Exception e){
        
        return null;
        }
        
	}

/*================METHOD FOR DISPLAYING LACATIONS=============================*/
      
public BorderPane _displayLocalazationMap_GoogleMap(String result){
          //Create the JavaFX component and set this as a listener so we know when 
    //the map has been initialized, at which point we can then begin manipulating it.
          
          try{
              mapView = new GoogleMapView();
              mapView.addMapInializedListener(this);
              BorderPane _displayMap=new BorderPane();
             _displayMap.setPadding(new Insets(10));
             _displayMap.setCenter(mapView);
             _displayMap.setStyle("-fx-background-color:whitesmoke;");
      return _displayMap;
          }catch(Exception e){
          
          return null;
          }
    
      }
      
 /*====================METHOD FOR DISPLAYING DASHBOARD========================*/    
public ScrollPane _displayDashBoard(String result){
			//Local Variable for holiding data
		
	ScrollPane _dashboardData=new ScrollPane();//The returned object
	VBox _mainVBOX=new VBox();//For holding the all content to ScrollPane
        _mainVBOX.setPadding(new  Insets(5));
	GridPane _containDashboard=new GridPane();
        
        _containDashboard.setVgap(5);
        _containDashboard.setHgap(5);
		try{
                JSONObject json_retuned_object=new JSONObject(imageforeinsicJSON);
		JSONArray  json_retuned_array=json_retuned_object.names();
                int index_control=0;
		//Processing the JSON Data and Displyong Data
	for(int row=0;row<json_retuned_array.length();row++){
                    String current_name=json_retuned_array.getString(row);
                if(current_name.equalsIgnoreCase("signatures")){
			Button tempButton =new Button();// for Severity 
                        //tempButton.setPadding(new Insets(5));
                        tempButton.setAlignment(Pos.CENTER);
                        tempButton.setMaxWidth(60);
                        tempButton.setMinWidth(60);
                        Label tempname=new Label();
                        //tempname.setMinWidth(60);
                        tempname.setId("dashboardValue");
                        tempname.setTextFill(Color.web("#006633"));
                        tempname.setPadding(new Insets(1,10,0,10));
                        tempname.setAlignment(Pos.CENTER);
			HBox temphorizontalBox=new HBox();// for holding tempButton and                                                   
			tempButton.setText("Found");
                        tempButton.setStyle("-fx-background-color:#006680");
                        tempButton.setTextFill(Color.web("FFFFFF"));
                        tempname.setText("Signatures Status");						
                        temphorizontalBox.getChildren().addAll(tempButton,tempname);
			_containDashboard.add(temphorizontalBox, 0,row + index_control); 
                        
                }else if(current_name.equalsIgnoreCase("metadata")){
                    
                        //Getting the Names of the MetaData Object
                            JSONObject temp_metadata=new JSONObject(imageforeinsicJSON).getJSONObject("metadata");
                            JSONArray  temp_metadata_array=temp_metadata.names();
                        //System.out.println("Metadadadad");
                        
                 for(int metadataIndex=0;metadataIndex<temp_metadata_array.length();metadataIndex++){
                             String metaDataIndex_name=temp_metadata_array.getString(metadataIndex);
                    if(metaDataIndex_name.equalsIgnoreCase("preview")){
                                    Button tempButton =new Button();// for Severity 
                        //tempButton.setPadding(new Insets(5));
                        tempButton.setAlignment(Pos.CENTER);
                        tempButton.setMaxWidth(60);
                        tempButton.setMinWidth(60);
                        Label tempname=new Label();
                        //tempname.setMinWidth(60);
                        tempname.setId("dashboardValue");
                        tempname.setTextFill(Color.web("#006633"));
                        tempname.setPadding(new Insets(1,10,0,10));
                        tempname.setAlignment(Pos.CENTER);
			HBox temphorizontalBox=new HBox();// for holding tempButton and                                                   
			tempButton.setText("Found");
                        tempButton.setStyle("-fx-background-color:#006680");
                        tempButton.setTextFill(Color.web("FFFFFF"));
                        tempname.setText("Preview Extraction");						
                        temphorizontalBox.getChildren().addAll(tempButton,tempname);
                        index_control=row + metadataIndex;
			_containDashboard.add(temphorizontalBox, 0,row + metadataIndex ); 
                                    
                                    
                                
                    }else if(metaDataIndex_name.equalsIgnoreCase("exif")){
                           Button tempButton =new Button();// for Severity 
                        //tempButton.setPadding(new Insets(5));
                        tempButton.setAlignment(Pos.CENTER);
                        tempButton.setMaxWidth(60);
                        tempButton.setMinWidth(60);
                        Label tempname=new Label();
                        //tempname.setMinWidth(60);
                        tempname.setId("dashboardValue");
                        tempname.setTextFill(Color.web("#006633"));
                        tempname.setPadding(new Insets(1,10,0,10));
                        tempname.setAlignment(Pos.CENTER);
			HBox temphorizontalBox=new HBox();// for holding tempButton and                                                   
			tempButton.setText("Found");
                        tempButton.setStyle("-fx-background-color:#006680");
                        tempButton.setTextFill(Color.web("FFFFFF"));
                        tempname.setText("EXIF Metadata Extraction");						
                        temphorizontalBox.getChildren().addAll(tempButton,tempname);
                        index_control=row + metadataIndex;
			_containDashboard.add(temphorizontalBox, 0,row + metadataIndex);      
                                
                                
                    }else if(metaDataIndex_name.equalsIgnoreCase("iptc")){
                        Button tempButton =new Button();// for Severity 
                        //tempButton.setPadding(new Insets(5));
                        tempButton.setAlignment(Pos.CENTER);
                        tempButton.setMaxWidth(60);
                        tempButton.setMinWidth(60);
                        Label tempname=new Label();
                        //tempname.setMinWidth(60);
                        tempname.setId("dashboardValue");
                        tempname.setTextFill(Color.web("#006633"));
                        tempname.setPadding(new Insets(1,10,0,10));
                        tempname.setAlignment(Pos.CENTER);
			HBox temphorizontalBox=new HBox();// for holding tempButton and                                                   
			tempButton.setText("Found");
                        tempButton.setStyle("-fx-background-color:#006680");
                        tempButton.setTextFill(Color.web("FFFFFF"));
                        tempname.setText("IPTC Metadata");						
                        temphorizontalBox.getChildren().addAll(tempButton,tempname);
                        index_control=row + metadataIndex;
			_containDashboard.add(temphorizontalBox, 0,row + metadataIndex);    
                                
                    }else if(metaDataIndex_name.equalsIgnoreCase("xmp")){
                            Button tempButton =new Button();// for Severity 
                        //tempButton.setPadding(new Insets(5));
                        tempButton.setAlignment(Pos.CENTER);
                        tempButton.setMaxWidth(60);
                        tempButton.setMinWidth(60);
                        Label tempname=new Label();
                        //tempname.setMinWidth(60);
                        tempname.setId("dashboardValue");
                        tempname.setTextFill(Color.web("#006633"));
                        tempname.setPadding(new Insets(1,10,0,10));
                        tempname.setAlignment(Pos.CENTER);
			HBox temphorizontalBox=new HBox();// for holding tempButton and                                                   
			tempButton.setText("Found");
                        tempButton.setStyle("-fx-background-color:#006680");
                        tempButton.setTextFill(Color.web("FFFFFF"));
                        tempname.setText("XMP Metadata");						
                        temphorizontalBox.getChildren().addAll(tempButton,tempname);
                        index_control=row + metadataIndex;
			_containDashboard.add(temphorizontalBox, 0,row + metadataIndex);
                                }
                            
                            }
                
                }else if(current_name.equalsIgnoreCase("ela")){
                        Button tempButton =new Button();// for Severity 
                        //tempButton.setPadding(new Insets(5));
                        tempButton.setAlignment(Pos.CENTER);
                        tempButton.setMaxWidth(60);
                        tempButton.setMinWidth(60);
                        Label tempname=new Label();
                        //tempname.setMinWidth(60);
                        tempname.setId("dashboardValue");
                        tempname.setTextFill(Color.web("#006633"));
                        tempname.setPadding(new Insets(1,10,0,10));
                        tempname.setAlignment(Pos.CENTER);
			HBox temphorizontalBox=new HBox();// for holding tempButton and                                                   
			tempButton.setText("Found");
                        tempButton.setStyle("-fx-background-color:#006680");
                        tempButton.setTextFill(Color.web("FFFFFF"));
                        tempname.setText("Error Level Analysis(ELA)");						
                        temphorizontalBox.getChildren().addAll(tempButton,tempname);
			_containDashboard.add(temphorizontalBox, 0,row + index_control);     
                         
                    }else if(current_name.equalsIgnoreCase("file_type")){
                        Button tempButton =new Button();// for Severity 
                        //tempButton.setPadding(new Insets(5));
                        tempButton.setAlignment(Pos.CENTER);
                        tempButton.setMaxWidth(60);
                        tempButton.setMinWidth(60);
                        Label tempname=new Label();
                        //tempname.setMinWidth(60);
                        tempname.setId("dashboardValue");
                        tempname.setTextFill(Color.web("#006633"));
                        tempname.setPadding(new Insets(1,10,0,10));
                        tempname.setAlignment(Pos.CENTER);
			HBox temphorizontalBox=new HBox();// for holding tempButton and                                                   
			tempButton.setText("Found");
                        tempButton.setStyle("-fx-background-color:#006680");
                        tempButton.setTextFill(Color.web("FFFFFF"));
                        tempname.setText("Static Analysis");						
                        temphorizontalBox.getChildren().addAll(tempButton,tempname);
			_containDashboard.add(temphorizontalBox, 0,row + index_control);     
                   
                    }
 
			}
	//Adding the _mainVBOX to the SPlitPane
        _mainVBOX.setStyle("-fx-background-color:white;");
	_mainVBOX.getChildren().add(_containDashboard);
        _mainVBOX.setFillWidth(true);
        _containDashboard.setStyle("-fx-background-color:white;");
        _dashboardData.setFitToHeight(true);
        _dashboardData.setFitToWidth(true);
	_dashboardData.setContent(_mainVBOX);
        
return _dashboardData;
                }catch(Exception e){
                
                return null;
                }                   

		
	}  
/*====================END OF METHOD FOR DISPLAYING DASHBOARD==================*/     
      
/*==========METHOD FOR DISPLAYING ERROR LEVEL =================================*/       
public BorderPane _errorLevelDisplay(String result){
          BorderPane temp_image=new BorderPane();
          JSONObject ela_object=new JSONObject(imageforeinsicJSON).getJSONObject("ela");
          Iterator<String> keys_ela = ela_object.keys();
          int number_ofKeys_ela=0;
          while(keys_ela.hasNext()&& number_ofKeys_ela!=ela_object.length()){
              String temp_ela_key= keys_ela.next();
              /* Getting the Needed Value*/
              
              if(temp_ela_key.equalsIgnoreCase("ela_image")){
                  String temp_elaKey_value= ela_object.getString(temp_ela_key);
                  System.out.print(temp_elaKey_value + " | ");
                  Image current_image = new Image("http://127.0.0.1:8000/analyses/Images/file/"+temp_elaKey_value+"/");
                  ImageView hold_prossed_image = new ImageView(current_image);
                  temp_image.setCenter(hold_prossed_image);
                  return temp_image;
              }
              
              number_ofKeys_ela++;
          }  
        
      return temp_image;
      }

/*================================VIEW LIST OF CASES FOR CURRENT USER==========*/
public boolean getListOfCases(String cases_list_json) throws IOException, URISyntaxException{
        
	ScrollPane temp_caseList=new ScrollPane();
	VBox _mainOutsideHolderVbox=new VBox();
	GridPane _holdCaseDetails=new GridPane();
        _holdCaseDetails.setHgap(5);
        _holdCaseDetails.setVgap(5);
        String jsonCaseList = Hedwig.go(Route.ALL_CASES, null);

        String mStringJSON = "{\"caseList\":" + jsonCaseList + "}";
        
       JSONObject json_caselist=new JSONObject(mStringJSON); 
       
       JSONArray json_caselist_Array=json_caselist.getJSONArray("caseList");
            for(int caseId=0;caseId<json_caselist_Array.length();caseId++){
            
                JSONObject temp_case=json_caselist_Array.getJSONObject(caseId);
                String temp_caseid=temp_case.get("pk").toString();
                JSONObject temp_caseArray=temp_case.getJSONObject("fields");
                //int temp_caseid=temp_caseArray.getInt("pk");
                String temp_caseName="Case Name:" + temp_caseArray.get("name").toString();
                String temp_CaseDateCreated="Date Created:" + temp_caseArray.get("created_at").toString() ;
                String temp_CaseDateUpdated="Date Updated: " + temp_caseArray.get("updated_at").toString();
                String temp_CaseDatedescription="Description: " + temp_caseArray.get("description").toString();
                        //Temp Rectangle
                
                HBox container=new HBox();//Contain Image
                container.setPadding(new Insets(0,0,0,10));
                container.setAlignment(Pos.CENTER_LEFT);
                VBox container_vertical=new VBox();//Contain Image
                container_vertical.setPadding(new Insets(0,0,0,5));
                container_vertical.setAlignment(Pos.CENTER_LEFT);
                StackPane _tempstack=new StackPane();
                Circle _temprect=new Circle(50,Color.WHITESMOKE);
                _temprect.setSmooth(true);
                Image _imgeName=new Image("Images/cases_image.png");
                ImageView img=new ImageView(_imgeName);
                img.setSmooth(true);
                img.setFitHeight(50);
                img.setFitWidth(50);
                _tempstack.getChildren().addAll(_temprect,img);
                Hyperlink case_link=new Hyperlink(temp_caseName);
            container_vertical.getChildren().add(case_link);
                    //Setting userData
                    case_link.setUserData(temp_caseid);
                    
            case_link.setOnMouseClicked(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getButton()==MouseButton.PRIMARY){
                        
                        //System.out.println(case_link.getUserData().toString());
                        String id=case_link.getUserData().toString();
                        _holdCaseDetails.getChildren().clear();
                        
                        ArrayList<HedwigPacket> HedwigPacketList = new ArrayList<>();
                        HedwigPacketList.add(new HedwigPacket("case_id", id));
                        
                            try {
                                //Display the List of Image Iside the Case
                                String caseImageList = Hedwig.go(Route.ALL_IMAGES, HedwigPacketList);
//                                Console.log(difcss.Logger.INFO,caseImageList);
                                
                        //CASE LIST MBWAKA
        VBox _mainOutsideHolderVbox_image=new VBox();
	GridPane picture_case_details_holder=new GridPane();
        picture_case_details_holder.setHgap(5);
        picture_case_details_holder.setVgap(5);
        ScrollPane inner_json_picture_case_objectList=new ScrollPane();
        String inner_mStringJSON_images_of_case = "{\"caseList\":" + caseImageList + "}";
        
       JSONObject inner_json_case_image_list=new JSONObject(inner_mStringJSON_images_of_case); 
       
       JSONArray image_case_inner_json_case_image_list_Array=inner_json_case_image_list.getJSONArray("caseList");
            for(int image_case_id=0;image_case_id<image_case_inner_json_case_image_list_Array.length();image_case_id++){
            
                JSONObject inner_json_picture_case_object=image_case_inner_json_case_image_list_Array.getJSONObject(image_case_id);
                String inner_json_picture_case_objectid=inner_json_picture_case_object.get("pk").toString();
                JSONObject inner_json_picture_case_objectArray=inner_json_picture_case_object.getJSONObject("fields");
                //int inner_json_picture_case_objectid=inner_json_picture_case_objectArray.getInt("pk");
                String inner_json_picture_case_objectName="Case Name:" + inner_json_picture_case_objectArray.get("file_name").toString();
                String temp_CaseDateCreated="Date Created:" + inner_json_picture_case_objectArray.get("created_at").toString() ;
                String temp_CaseDateUpdated="Date Updated: " + inner_json_picture_case_objectArray.get("completed_at").toString();
                //String temp_CaseDatedescription="Description: " + inner_json_picture_case_objectArray.get("description").toString();
                        //Temp Rectangle
                
                HBox inner_horizontal_picture_container=new HBox();//Contain Image
                inner_horizontal_picture_container.setPadding(new Insets(0,0,0,10));
                inner_horizontal_picture_container.setAlignment(Pos.CENTER_LEFT);
                VBox case_picture_inner_horizontal_picture_container_vertical=new VBox();//Contain Image
                case_picture_inner_horizontal_picture_container_vertical.setPadding(new Insets(0,0,0,5));
                case_picture_inner_horizontal_picture_container_vertical.setAlignment(Pos.CENTER_LEFT);
                StackPane _tempstack=new StackPane();
                Circle picture_case_temprect=new Circle(50,Color.WHITESMOKE);
                picture_case_temprect.setSmooth(true);
                Image _case_imageeName=new Image("Images/cases_image.png");
                ImageView case_image=new ImageView(_case_imageeName);
                case_image.setSmooth(true);
                case_image.setFitHeight(50);
                case_image.setFitWidth(50);
                _tempstack.getChildren().addAll(picture_case_temprect,case_image);
                Hyperlink case_link=new Hyperlink(inner_json_picture_case_objectName);
            case_picture_inner_horizontal_picture_container_vertical.getChildren().add(case_link);
                    //Setting userData
                    case_link.setUserData(inner_json_picture_case_objectid);
                    
            case_link.setOnMouseClicked(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getButton()==MouseButton.PRIMARY){
                        
                            //Get Image ID
                            //Call getImageCasesAnalysis() Method
                        
                        }
                    }
                    });
            case_picture_inner_horizontal_picture_container_vertical.getChildren().add(new Label(temp_CaseDateCreated));
            case_picture_inner_horizontal_picture_container_vertical.getChildren().add(new Label(temp_CaseDateUpdated));
            //case_picture_inner_horizontal_picture_container_vertical.getChildren().add(new Label(temp_CaseDatedescription));
            inner_horizontal_picture_container.getChildren().addAll(_tempstack,case_picture_inner_horizontal_picture_container_vertical);
            picture_case_details_holder.add(inner_horizontal_picture_container, 0, image_case_id);
            }
        _mainOutsideHolderVbox_image.setStyle("-fx-background-color:white;");
        _mainOutsideHolderVbox_image.getChildren().add(picture_case_details_holder);
        inner_json_picture_case_objectList.setPadding(new Insets(20,1,1,1));
        inner_json_picture_case_objectList.setFitToHeight(true);
        inner_json_picture_case_objectList.setFitToWidth(true);
        inner_json_picture_case_objectList.setStyle("-fx-background-color:white;");
        inner_json_picture_case_objectList.setContent(_mainOutsideHolderVbox_image);
        _mainBorderPane.setCenter(null);
        _mainBorderPane.setCenter(inner_json_picture_case_objectList);    
                                
                  
                                //CASE LIST MBWAKA
                                
                     
                                
                                //Pass the Case Primary Key
                            } catch (IOException | URISyntaxException ex) {
                                Logger.getLogger(DIFCSS.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        
                        }
                    }
                    });
            container_vertical.getChildren().add(new Label(temp_CaseDateCreated));
            container_vertical.getChildren().add(new Label(temp_CaseDateUpdated));
            container_vertical.getChildren().add(new Label(temp_CaseDatedescription));
            container.getChildren().addAll(_tempstack,container_vertical);
            _holdCaseDetails.add(container, 0, caseId);
            }
        _mainOutsideHolderVbox.setStyle("-fx-background-color:white;");
        _mainOutsideHolderVbox.getChildren().add(_holdCaseDetails);
        temp_caseList.setPadding(new Insets(20,1,1,1));
        temp_caseList.setFitToHeight(true);
        temp_caseList.setFitToWidth(true);
        temp_caseList.setStyle("-fx-background-color:white;");
        temp_caseList.setContent(_mainOutsideHolderVbox);
        _mainBorderPane.setCenter(null);
        _mainBorderPane.setCenter(temp_caseList);
        return true;	
}
/*================================END OF METHOD FOR VIEW LIST OF CASES FOR CURRENT USER=========*/

/**
 * 
 * Getting Current Image Case Analysis
     * @param currentImageCase
     * @return 
 */

public SplitPane getImageCasesAnalysis(String currentImageCase) {
               
//Checkiong if the String is not Null
                    _caseResultTabPane.getTabs().clear();
                    _mainSplitPane.getItems().clear();
              /**
               * 
               * Start of the Case Detail TITLed Pane
               */
                   
                   
                 if(imageforeinsicJSON.isEmpty()){
//                     Console.log(difcss.Logger.ERROR,"No JSON Returned Data");
     
                   }else{
                   Tab _Dashboard_Details=new Tab("Dashboard");
                        if(_displayDashBoard(currentImageCase)!=null){
                            _Dashboard_Details.setContent(_displayDashBoard(currentImageCase));
                            _caseResultTabPane.getTabs().add(_Dashboard_Details);
                        }     
                            
                   Tab _Static_Details=new Tab("Static Analysis");
                        if(_displayStaticData(currentImageCase)!=null){
                            _Static_Details.setContent(_displayStaticData(currentImageCase));
                            _caseResultTabPane.getTabs().add(_Static_Details);
                        }     
                        
                   Tab _EXIF_Details=new Tab("EXIF Metadata");
                        if(_displayEXIFData("")!=null){
                            _EXIF_Details.setContent(_displayEXIFData(currentImageCase));
                            _caseResultTabPane.getTabs().add(_EXIF_Details);
                        }           
         
                   Tab _IPTC_Details=new Tab("IPTC Metadata");
                        if(_displayIPTCData(currentImageCase)!=null){
                            _IPTC_Details.setContent(_displayIPTCData(currentImageCase));
                            _caseResultTabPane.getTabs().add(_IPTC_Details);
                        }     
                                            
                   Tab _XMP_Details=new Tab("XMP Metadata");
                        if(_displayXMPData(currentImageCase)!=null){
                             _XMP_Details.setContent(_displayXMPData(currentImageCase));
                            _caseResultTabPane.getTabs().add(_XMP_Details);
                        }  
                  
                   Tab _preview_Details=new Tab("Preview Extraction");
                   
                   Tab _Localization_Details=new Tab("Map");
                         if(_displayLocalazationMap_GoogleMap(currentImageCase)!=null){
                           _Localization_Details.setContent(_displayLocalazationMap_GoogleMap(currentImageCase));
                            _caseResultTabPane.getTabs().add(_Localization_Details);
                        }  
                            
                   Tab _Error_Level_Details=new Tab("Error Level Analysis");
                        if(_errorLevelDisplay(null)!=null){
                           _Error_Level_Details.setContent(_errorLevelDisplay(null));
                            _caseResultTabPane.getTabs().add(_Error_Level_Details);
                        }  
                   Tab _Signature_Check_Details=new Tab("Signature Check");
                         if(_displaySignature(currentImageCase)!=null){
                            _Signature_Check_Details.setContent(_displaySignature(currentImageCase));
                            _caseResultTabPane.getTabs().add(_Signature_Check_Details);
                        }  
            
                   /**
                    * * 
                    * End of the Case Filled Titled*/ 
                   }
     //For Loading Button
                 while(_controlLading){
                 Tab _Thumbnails_Check_Details=new Tab("Getting Analysis..........");
                 //ImageView to Hold the 
                 BorderPane _loadingImage=new BorderPane();
                 ImageView _imageloading=new ImageView("Images/best_loading.gif");
                 _imageloading.setSmooth(true);
                 _imageloading.setFitWidth(218.75);
                 _imageloading.setFitHeight(175);
                 _loadingImage.setCenter(_imageloading);
                 _Thumbnails_Check_Details.setContent(_loadingImage);
                 _Thumbnails_Check_Details.setDisable(true);
                 _caseResultTabPane.getTabs().add(_Thumbnails_Check_Details);
                 }
                 
//Creating the Windoes Scene that Contain the Content
    _mainSplitPane.getItems().addAll(_caseDetailgridPane,_caseResultTabPane);//Adding the Case Detail Content to the Sptli Pane
      return  _mainSplitPane ;
    }

public BorderPane displayCurrentSelectedCaseReport(String htmlContent){
	BorderPane holdMethodContent=new BorderPane();
	ScrollPane _holdWebView=new ScrollPane(); //For Holding the WebView
        
       _holdWebView.setFitToHeight(true);
        _holdWebView.setFitToWidth(true);
	HBox _holdPrintButton=new HBox();						//For Holding the Print Button
	Rectangle _printButton=new Rectangle();						//Print Button
	//printButton.getChildren().add(new Label("Print Report"));			//Adding the Label to the Rectangle
	//printButton.setOnMouseClick(new EventHandler<MouseEvent>(){});			//Seeting the  Button Event
	//printButton.setId("printButton");						//Setting the Button ID to Be Accesed in CSS
	WebView caseReportViewer=new WebView();						//Holding the Case Report
       
    	WebEngine webEngine = caseReportViewer.getEngine();				//
	try{
		webEngine.load(htmlContent);
		//webEngine.
		holdMethodContent.setCenter(caseReportViewer);	//Set the Content on the Center WebView
                _holdWebView.setContent(holdMethodContent);
                //Clear the Center of the Border Pane
                _mainBorderPane.setCenter(null);
                _mainBorderPane.setCenter(_holdWebView);
	return holdMethodContent;	

	}catch(Exception e){

	return null;
		}
}


public ScrollPane _displayCopyMoveImage(){
    ScrollPane image_processed;
        image_processed = new ScrollPane();
        
        //Getting Real FileName
         _imageName_real= _imageName.split("\\.")[0];
        processed_imagePath = "scripts/"+_imageName_real+"_analyzed.jpg";
        System.out.print(processed_imagePath);
        Image current_image = new Image(processed_imagePath);
        ImageView hold_prossed_image = new ImageView(current_image);
        hold_prossed_image.setStyle("-fx-padding: 2px");
        image_processed.setStyle("-fx-background-color:white;");
        image_processed.setFitToHeight(true);
        image_processed.setFitToWidth(true);
        image_processed.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	image_processed.setContent(hold_prossed_image);
    
    return image_processed;
}

} 
