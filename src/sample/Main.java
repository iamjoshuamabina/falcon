package sample;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.util.Console;
import sample.util.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

public class Main extends Application implements MapComponentInitializedListener {
	private long imageSize;
	private String imageName, imagePath;
	private boolean canUseExperimentalFeatures = false;

	private final String FIT_HEIGHT = "300";
	private final String FIT_WIDTH = "400";

    private final String[] colors = {
            "#330033", "#000066", "#006600", "#333333",
            "#93B514", "#93B514", "#FF2F88", "#FF3C41"
    };

    private TabPane analysisResultTabPane;
    private String analysisResultJsonString;

    private SplitPane mainSplitPane;

    private GridPane leftGridPane;
    private BorderPane leftBorderPane;
    private VBox imageForensicsIconVBox;
    private HBox imageForensicsLeftGridPaneItemHBox;
    private final Label leftGridPaneItemImageLabel = new Label("Image Forensics");
    private final ImageView cameraIconImageView = new ImageView(new Image("icons/image.png"));

    private VBox imageDetailsVBox;
    private HBox analyzeButtonHBox;
    private HBox fileChooserButtonHBox;
    private VBox analyzeImageHolderVBox;
    private GridPane caseDetailGridPane;
    private TextField caseTitleTextField;
    private TextArea caseDescriptionField;
	private CheckBox canUseExperimentalFeaturesCheckBox;
    private final Label caseTitleLabel = new Label("Title");
    private final Label caseDescriptionLabel = new Label("Description");
    private final Label analyzeImageButtonLabel = new Label("Analyze Image");
    private final Label selectImageFileLabel = new Label("Select Image File");
    private ImageView selectedImageFileImageView = new ImageView(new Image("images/thumb_400x300.png"));

    private GoogleMapView mGoogleMapView;
	private double googleMapViewLatitude, googleMapViewLongitude;

    public Main() {
        mainSplitPane = new SplitPane();
        mainSplitPane.setOrientation(Orientation.VERTICAL);
        mainSplitPane.setPadding(new Insets(20, 0, 0, 0));

        leftGridPane = new GridPane();
        leftGridPane.setStyle("-fx-background-color:whitesmoke;");
        leftGridPane.setOpacity(0.9);

        imageForensicsLeftGridPaneItemHBox = new HBox();
        imageForensicsLeftGridPaneItemHBox.setId("imageHBoxStyle");

        imageForensicsIconVBox = new VBox();
        leftBorderPane = new BorderPane();

        caseDetailGridPane = new GridPane();
        caseDetailGridPane.setPadding(new Insets(0, 0, 0, 10));

        fileChooserButtonHBox = new HBox();
        fileChooserButtonHBox.setMinHeight(40);

        analyzeButtonHBox = new HBox();
        analyzeButtonHBox.setMaxHeight(50);

        analyzeImageHolderVBox = new VBox();
        analyzeImageHolderVBox.setMaxHeight(299);
        analyzeImageHolderVBox.setMaxWidth(400);
        analyzeImageHolderVBox.setFillWidth(true);

        imageDetailsVBox = new VBox();
        imageDetailsVBox.setMaxWidth(150);

        caseTitleTextField = new TextField();
        caseTitleTextField.setStyle("-fx-border-color:#d0d0d0;");

        caseDescriptionField = new TextArea();
        caseDescriptionField.setStyle("-fx-border-color:#d0d0d0;");
        caseDescriptionField.setMaxHeight(100);
        caseDescriptionField.setMaxWidth(400);

        selectImageFileLabel.setId("selectImage");

        analyzeImageButtonLabel.setId("analyzeLabel");

		canUseExperimentalFeaturesCheckBox = new CheckBox("Use experimental features?");

		selectedImageFileImageView.setFitHeight(Double.parseDouble(FIT_HEIGHT));
		selectedImageFileImageView.setFitWidth(Double.parseDouble(FIT_WIDTH));

        analysisResultTabPane = new TabPane();
    }

    @Override
    public final void mapInitialized() {
		GoogleMap mGoogleMap;

		try {
			MapOptions mapOptions = new MapOptions();
			mapOptions.center(new LatLong(googleMapViewLatitude, googleMapViewLongitude))
                    .mapType(MapTypeIdEnum.ROADMAP)
                    .overviewMapControl(false)
                    .panControl(false)
                    .rotateControl(false)
                    .scaleControl(false)
                    .streetViewControl(false)
                    .zoomControl(false)
                    .zoom(12);

			mGoogleMap = mGoogleMapView.createMap(mapOptions);

			MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLong(googleMapViewLatitude, googleMapViewLongitude))
                    .visible(Boolean.TRUE)
                    .title("Photo Taken");
            Marker marker = new Marker( markerOptions );
            mGoogleMap.addMarker(marker);

        } catch(Exception e){
            Console.out(Logger.ERROR, "mapInitialized() -> " + e.getMessage());
        }
    }

    private ScrollPane displayCopyMoveScrollPane() {
		File analyzedImageFile = new File(
				Hedwig.getAnalysisDirectory() + "/"+ imageName.split("\\.")[0] +"_analyzed.jpg"
		);

		if(!analyzedImageFile.exists()) {
			return null;
		}

        String analyzedImagePath = analyzedImageFile.toURI().toString();

        ImageView analyzedImageView = new ImageView(new Image(analyzedImagePath));
		analyzedImageView.setFitHeight(Double.parseDouble(FIT_HEIGHT));
		analyzedImageView.setFitWidth(Double.parseDouble(FIT_WIDTH));
        analyzedImageView.setStyle("-fx-padding: 2px");
        analyzedImageView.setStyle("-fx-background-color:white;");

        ScrollPane copymoveDataScrollPane = new ScrollPane();
        copymoveDataScrollPane.setFitToHeight(true);
        copymoveDataScrollPane.setFitToWidth(true);
        copymoveDataScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        copymoveDataScrollPane.setContent(analyzedImageView);

        return copymoveDataScrollPane;
    }

    private ScrollPane displaySignatureScrollPane() {
        GridPane signatureDataGridPane = new GridPane();
        signatureDataGridPane.setVgap(5);
        signatureDataGridPane.setHgap(5);

        try {
            JSONArray signatureJsonArray = new JSONObject(analysisResultJsonString).getJSONArray("signatures");

            for(int row = 0; row < signatureJsonArray.length(); row++) {
                JSONObject tempJsonObject = signatureJsonArray.getJSONObject(row);
                String tempNameString = tempJsonObject.getString("name");
                int severity = tempJsonObject.getInt("severity");

                Button tempButton = new Button();
                tempButton.setAlignment(Pos.CENTER);
                tempButton.setMinWidth(60);

                Label tempNameLabel = new Label();
                tempNameLabel.setPadding(new Insets(1, 10, 0, 10));
                tempNameLabel.setAlignment(Pos.CENTER);

                if(severity == 1){
                    tempButton.setText("Low");
                    tempButton.setStyle("-fx-background-color:#006680");
                    tempButton.setTextFill(Color.web("FFFFFF"));
                    tempNameLabel.setText(tempNameString);
                } else if(severity == 2){
                    tempButton.setText("Medium");
                    tempButton.setStyle("-fx-background-color:#333300");
                    tempButton.setTextFill(Color.web("FFFFFF"));
                    tempNameLabel.setText(tempNameString);
                } else if(severity == 3){
                    tempButton.setText("High");
                    tempButton.setStyle("-fx-background-color:Red;");
                    tempButton.setTextFill(Color.web("FFFFFF"));
                    tempNameLabel.setText(tempNameString);
                }

                HBox tempHBox = new HBox();
                tempHBox.getChildren().addAll(tempButton,tempNameLabel);
                signatureDataGridPane.add(tempHBox, 0, row);
            }


            VBox mainVBox = new VBox();
            mainVBox.setPadding(new Insets(5));
            mainVBox.setStyle("-fx-background-color:white;");
            mainVBox.getChildren().add(signatureDataGridPane);
            mainVBox.setFillWidth(true);

            ScrollPane signatureDataScrollPane = new ScrollPane();
            signatureDataScrollPane.setStyle("-fx-background-color:white;");
            signatureDataScrollPane.setFitToHeight(true);
            signatureDataScrollPane.setFitToWidth(true);
            signatureDataScrollPane.setContent(mainVBox);
            return signatureDataScrollPane;

        } catch(Exception e){
            Console.out(Logger.ERROR, e.getMessage());
            return null;
        }
    }

	private ScrollPane displayStaticDataScrollPane() {
		GridPane staticDataGridPane = new GridPane();
		staticDataGridPane.setVgap(5);
		staticDataGridPane.setHgap(5);

		try{
			Tab fileTypeTab = new Tab("File Type");
			Tab hashInfoTab = new Tab("Hash Information");

			GridPane hashGridPane = new GridPane();
			hashGridPane.setVgap(4);
			hashGridPane.setPadding(new Insets(10, 0, 0, 10));

			JSONObject hashJsonObject = new JSONObject(analysisResultJsonString).getJSONObject("hash");
			Iterator<String> hashKeysStringIterator = hashJsonObject.keys();

			int countNumberOfKeys = 0;
			while(hashKeysStringIterator.hasNext() && countNumberOfKeys != hashJsonObject.length()){

				String tempHashKey = hashKeysStringIterator.next();

				Label tempHashKeyLabel = new Label(tempHashKey.toUpperCase());
				tempHashKeyLabel.setId("hashes_key");
				tempHashKeyLabel.setMinWidth(60);
				tempHashKeyLabel.setTextFill(Color.web("#003333"));

				String tempHashValueString = " " + " : " + hashJsonObject.getString(tempHashKey);
				Label tempHashValueLabel = new Label(tempHashValueString);
				tempHashValueLabel.setFont(new Font(12));
				tempHashValueLabel.setTextFill(Color.web("#003333"));

				HBox tempHashHBox = new HBox();
				tempHashHBox.getChildren().addAll(tempHashKeyLabel, tempHashValueLabel);
				hashGridPane.add(tempHashHBox, 0, countNumberOfKeys);
				countNumberOfKeys++;
			}

			hashInfoTab.setContent(hashGridPane);

			GridPane fileTypeGridPane = new GridPane();
			fileTypeGridPane.setId("grid-pane");
			fileTypeGridPane.setHgap(4);
			fileTypeGridPane.setPadding(new Insets(10, 0, 0, 10));

			hashGridPane.setVgap(4);
			hashGridPane.setPadding(new Insets(10, 0, 0, 10));
			JSONObject fileTypeJsonObject = new JSONObject(analysisResultJsonString);

			Label tempFileTypeLabel = new Label(fileTypeJsonObject.getString("file_type"));
			tempFileTypeLabel.setFont(new Font(12));
			tempFileTypeLabel.setMinWidth(60);
			tempFileTypeLabel.setTextFill(Color.web("#728308"));

			HBox tempFileTypeHBox = new HBox();
			tempFileTypeHBox.getChildren().add(tempFileTypeLabel);

			Label keyLabel = new Label("Image Extension Type");
			keyLabel.setFont(new Font(12));
			keyLabel.setMinWidth(60);
			keyLabel.setTextFill(Color.web("#0076a3"));
			fileTypeGridPane.add(keyLabel, 0, 0);
			fileTypeGridPane.add(tempFileTypeHBox, 1, 0);
			fileTypeTab.setContent(fileTypeGridPane);

			TabPane staticDataTabPane = new TabPane();
			staticDataTabPane.setStyle("-fx-background-color:white;");
			staticDataTabPane.getTabs().addAll(fileTypeTab, hashInfoTab);

			ScrollPane staticDataScrollPane = new ScrollPane();
			staticDataScrollPane.setStyle("-fx-background-color:white;");
			staticDataScrollPane.setFitToHeight(true);
			staticDataScrollPane.setFitToWidth(true);
			staticDataScrollPane.setStyle("-fx-background-color:white;");
			staticDataScrollPane.setContent(staticDataTabPane);

			return staticDataScrollPane;
		} catch(Exception e){
			Console.out(Logger.ERROR, e.getMessage());
			return null;
		}
	}

    private ScrollPane displayExifDataScrollPane() {
        GridPane exifDataGridPane = new GridPane();
        exifDataGridPane.setVgap(5);
        exifDataGridPane.setHgap(5);

        try {
            JSONObject metadataJsonObject = new JSONObject(analysisResultJsonString).getJSONObject("metadata");

			if(metadataJsonObject.has("Exif")) {
				JSONObject exifJsonObject = metadataJsonObject.getJSONObject("Exif");

				int tempLastRowIndex = 0;
				JSONArray exifDataJsonArray = exifJsonObject.names();

				for(int i = 0; i < exifDataJsonArray.length(); i++){
					String tempKeyString = exifDataJsonArray.getString(i);
					JSONObject tempJsonObject = exifJsonObject.getJSONObject(tempKeyString);
					JSONArray tempJsonArray = tempJsonObject.names();

					for(int j = 0; j < tempJsonArray.length(); j++) {

						Label tempExifDataKeyLabel = new Label();
						tempExifDataKeyLabel.setId("value_key_EXIF");
						tempExifDataKeyLabel.setTextFill(Color.web(colors[i]));

						Label tempExifDataValueLabel = new Label();
						tempExifDataValueLabel.setId("value_data_EXIF");
						tempExifDataValueLabel.setTextFill(Color.web(colors[i]));

						String tempExifDataKeyString = tempJsonArray.getString(j);
						tempExifDataKeyLabel.setText(tempExifDataKeyString);

						String tempExifDataValueString = ":" + " " + tempJsonObject.getString(tempExifDataKeyString);
						tempExifDataValueLabel.setText(tempExifDataValueString);

						HBox tempHBox = new HBox();
						tempHBox.getChildren().addAll(tempExifDataKeyLabel, tempExifDataValueLabel);
						exifDataGridPane.add(tempHBox, 15, tempLastRowIndex, 20, 1);
						if(j == tempJsonArray.length()) {
							tempLastRowIndex = j;
						} else {
							tempLastRowIndex++;
						}
					}

					if(tempKeyString.equalsIgnoreCase("photo")){
						exifDataGridPane.add(new Label(tempKeyString.toUpperCase()), 0, tempLastRowIndex, 5, 1);
					} else if(tempKeyString.equalsIgnoreCase("image")){
						exifDataGridPane.add(new Label(tempKeyString.toUpperCase()), 0, tempLastRowIndex, 5, 1);

					} else if(tempKeyString.equalsIgnoreCase("Thumbnail")){
						exifDataGridPane.add(new Label(tempKeyString.toUpperCase()), 0, tempLastRowIndex, 5, 1);

					} else if(tempKeyString.equalsIgnoreCase("iop")){
						exifDataGridPane.add(new Label(tempKeyString.toUpperCase()), 0, tempLastRowIndex, 5, 1);
					}
				}

				VBox mainVBox = new VBox();
				mainVBox.setStyle("-fx-background-color:white;");
				mainVBox.getChildren().add(exifDataGridPane);
				mainVBox.setFillWidth(true);
				mainVBox.setStyle("-fx-background-color:white;");

				ScrollPane exifDataScrollPane = new ScrollPane();
				exifDataScrollPane.setFitToHeight(true);
				exifDataScrollPane.setFitToWidth(true);
				exifDataScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

				exifDataScrollPane.setContent(mainVBox);
				return exifDataScrollPane;

			} else {
				return null;
			}

        } catch(Exception e) {
            Console.out(Logger.ERROR, e.getMessage());
            return null;
        }


	}

    private ScrollPane displayIptcDataScrollPane() {
        GridPane iptcGridPane = new GridPane();
        iptcGridPane.setVgap(5);
        iptcGridPane.setHgap(5);

        try {
            JSONObject metadataJsonObject = new JSONObject(analysisResultJsonString).getJSONObject("metadata");
			if(metadataJsonObject.has("Iptc")) {
				JSONObject iptcJsonObject = metadataJsonObject.getJSONObject("Iptc");

				int tempLastRowIndex = 0;
				JSONArray iptcJsonArray = iptcJsonObject.names();

				for(int i = 0; i < iptcJsonArray.length(); i++) {
					String name = iptcJsonArray.getString(i);
					JSONObject tempJsonObject = iptcJsonObject.getJSONObject(name);

					JSONArray tempJsonArray = tempJsonObject.names();
					for(int j = 0; j < tempJsonArray.length(); j++){
						Label tempIptcDataKeyLabel = new Label();
						tempIptcDataKeyLabel.setId("value_key_IPTC");

						Label tempIptcDataValueLabel = new Label();
						tempIptcDataValueLabel.setId("value_data_IPTC");
						tempIptcDataValueLabel.setTextFill(Color.web(colors[i]));

						String tempIptcDataKeyString = tempJsonArray.getString(j);
						tempIptcDataKeyLabel.setTextFill(Color.web(colors[i]));
						tempIptcDataKeyLabel.setText(tempIptcDataKeyString);

						String tempIptcDataValueString =":" +" " + tempJsonObject.getString(tempIptcDataKeyString);
						tempIptcDataValueLabel.setText(tempIptcDataValueString);

						HBox tempHBox = new HBox();
						tempHBox.getChildren().addAll(tempIptcDataKeyLabel, tempIptcDataValueLabel);
						iptcGridPane.add(tempHBox, 15, tempLastRowIndex, 20, 1);

						if(j == tempJsonArray.length()){
							tempLastRowIndex = j;
						} else {
							tempLastRowIndex++;
						}
					}

					if(name.equalsIgnoreCase("Application2")) {
						iptcGridPane.add(new Label(name.toUpperCase()), 0, tempLastRowIndex - 3, 5, 1);
					} else if(name.equalsIgnoreCase("Envelope")){
						iptcGridPane.add(new Label(name.toUpperCase()), 0, tempLastRowIndex - 1, 5, 1);
					} else {
						iptcGridPane.add(new Label(name.toUpperCase()), 0, tempLastRowIndex - 2, 5, 1);
					}
				}

				VBox mainVBox = new VBox();
				mainVBox.setStyle("-fx-background-color:white;");
				mainVBox.getChildren().add(iptcGridPane);
				mainVBox.setFillWidth(true);

				ScrollPane iptcDataScrollPane = new ScrollPane();
				iptcDataScrollPane.setStyle("-fx-background-color:white;");
				iptcDataScrollPane.setFitToHeight(true);
				iptcDataScrollPane.setFitToWidth(true);
				iptcDataScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
				iptcDataScrollPane.setContent(mainVBox);

				return iptcDataScrollPane;
			} else {
				return null;
			}

        } catch(Exception e){
            return null;
        }
	}

    private ScrollPane displayXmpDataScrollPane() {
        GridPane xmpDataGridPane = new GridPane();
        xmpDataGridPane.setId("XMPGridPane");
        xmpDataGridPane.setVgap(5);
        xmpDataGridPane.setHgap(5);

        try {
            JSONObject metadataJsonObject = new JSONObject(analysisResultJsonString).getJSONObject("metadata");

			if(metadataJsonObject.has("Xmp")) {
				JSONObject xmpDataJsonObject = metadataJsonObject.getJSONObject("Xmp");
				JSONArray xmpDataJsonArray = xmpDataJsonObject.names();

				int tempLastRowIndex = 0;
				for(int i = 0; i < xmpDataJsonArray.length(); i++) {
					String xmpDataKeyString = xmpDataJsonArray.getString(i);

					JSONObject tempXmpDataJsonObject = xmpDataJsonObject.getJSONObject(xmpDataKeyString);
					JSONArray tempXmpDataJsonArray = tempXmpDataJsonObject.names();

					for(int j = 0; j < tempXmpDataJsonArray.length(); j++){

						HBox tempHBox = new HBox();

						Label tempXmpDataKeyLabel = new Label();
						tempXmpDataKeyLabel.setId("key_value");
						tempXmpDataKeyLabel.setTextFill(Color.web(colors[i]));

						Label tempXmpDataValueLabel = new Label();
						tempXmpDataValueLabel.setId("value_data");
						tempXmpDataValueLabel.setTextFill(Color.web(colors[i]));

						String key = tempXmpDataJsonArray.getString(j);
						tempXmpDataKeyLabel.setText(key);
						String value = ":" + " " + tempXmpDataJsonObject.getString(key);
						tempXmpDataValueLabel.setText(value);
						tempHBox.getChildren().addAll(tempXmpDataKeyLabel, tempXmpDataValueLabel);
						xmpDataGridPane.add(tempHBox, 15, tempLastRowIndex, 20, 1);
						if(j==tempXmpDataJsonArray.length()){
							tempLastRowIndex=j;
						} else{
							tempLastRowIndex++;
						}
					}

					if(xmpDataKeyString.equalsIgnoreCase("xmpMM")){
						xmpDataGridPane.add(new Label(xmpDataKeyString.toUpperCase()), 0, tempLastRowIndex - 28, 5, 1);
					} else if(xmpDataKeyString.equalsIgnoreCase("photoshop")){
						xmpDataGridPane.add(new Label(xmpDataKeyString.toUpperCase()), 0, tempLastRowIndex - 3, 5, 1);

					} else if(xmpDataKeyString.equalsIgnoreCase("xmp")){
						xmpDataGridPane.add(new Label(xmpDataKeyString.toUpperCase()), 0, tempLastRowIndex - 3, 5, 1);

					} else if(xmpDataKeyString.equalsIgnoreCase("dc")){
						xmpDataGridPane.add(new Label(xmpDataKeyString.toUpperCase()), 0, tempLastRowIndex - 2, 5, 1);

					} else if(xmpDataKeyString.equalsIgnoreCase("xmp")){
						xmpDataGridPane.add(new Button(xmpDataKeyString.toUpperCase()), 0, tempLastRowIndex - 3, 5, 1);
					}
				}

				VBox mainVBox = new VBox();
				mainVBox.setPadding(new Insets(5));
				mainVBox.setStyle("-fx-background-color:white;");
				mainVBox.getChildren().add(xmpDataGridPane);
				mainVBox.setFillWidth(true);

				ScrollPane xmpDataScrollPane = new ScrollPane();
				xmpDataScrollPane.setStyle("-fx-background-color:white;");
				xmpDataScrollPane.setFitToHeight(true);
				xmpDataScrollPane.setFitToWidth(true);
				xmpDataScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
				xmpDataScrollPane.setContent(mainVBox);

				return xmpDataScrollPane;
			} else {
				return null;
			}

        } catch(Exception e){
            return null;
        }
    }

	private BorderPane displayGpsDataBorderPane() {
		try {
			JSONObject metadataJsonObject = new JSONObject(analysisResultJsonString).getJSONObject("metadata");
			if(metadataJsonObject.has("gps")) {
				JSONObject gpsDataJsonObject = metadataJsonObject.getJSONObject("gps");
				JSONObject posGpsDataJsonObject = gpsDataJsonObject.getJSONObject("pos");

				googleMapViewLatitude = Double.parseDouble(String.valueOf(posGpsDataJsonObject.get("Latitude")));
				googleMapViewLongitude = Double.parseDouble(String.valueOf(posGpsDataJsonObject.get("Longitude")));
				mGoogleMapView = new GoogleMapView();
				mGoogleMapView.addMapInializedListener(this);

				BorderPane borderPane = new BorderPane();
				borderPane.setPadding(new Insets(10));
				borderPane.setCenter(mGoogleMapView);
				borderPane.setStyle("-fx-background-color:whitesmoke;");

				return borderPane;

			} else {
				return null;
			}

		} catch(Exception e){
			Console.out(Logger.ERROR, e.getMessage());
			return null;
		}
	}

    private BorderPane displayElaDataBorderPane(){
        JSONObject elaJsonObject = new JSONObject(analysisResultJsonString).getJSONObject("ela");
        Iterator<String> elaKeysStringIterator = elaJsonObject.keys();

        int countNumberOfKeys = 0;
        BorderPane tempBorderPane = new BorderPane();
        while(elaKeysStringIterator.hasNext() && countNumberOfKeys != elaJsonObject.length()){
            String elaKeyString= elaKeysStringIterator.next();

            if(elaKeyString.equalsIgnoreCase("ela_image")){
                String elaValueString = elaJsonObject.getString(elaKeyString);
                //noinspection StringConcatenationMissingWhitespace
                String elaImagePath
                        = Hedwig.getBaseURI() + "analyses/images/file/" + elaValueString + "/";

                Image elaImageFile = new Image(elaImagePath);
                ImageView elaImageFileImageView = new ImageView(elaImageFile);
				elaImageFileImageView.setFitHeight(Double.parseDouble(FIT_HEIGHT));
				elaImageFileImageView.setFitWidth(Double.parseDouble(FIT_WIDTH));
                tempBorderPane.setCenter(elaImageFileImageView);

                return tempBorderPane;
            }

            countNumberOfKeys++;
        }
        return tempBorderPane;
    }

	private ScrollPane displayDashBoardScrollPane() {
		GridPane dashboardGridPane = new GridPane();
		dashboardGridPane.setVgap(5);
		dashboardGridPane.setHgap(5);

		try{
			JSONObject resultJsonObject = new JSONObject(analysisResultJsonString);
			JSONArray  resultJsonArray = resultJsonObject.names();

			int indexControl = 0;
			for(int i = 0; i < resultJsonArray.length(); i++){
				String currentName = resultJsonArray.getString(i);

				Label tempDataFoundNameLabel;
				Button tempDataFoundButton;
				final String DATA_FOUND_STRING = "FOUND";
				if(currentName.equalsIgnoreCase("signatures")){
					tempDataFoundButton = new Button();
					tempDataFoundButton.setAlignment(Pos.CENTER);
					tempDataFoundButton.setMaxWidth(60);
					tempDataFoundButton.setMinWidth(60);

					tempDataFoundNameLabel = new Label("Signature Data");
					tempDataFoundNameLabel.setId("dashboardValue");
					tempDataFoundNameLabel.setTextFill(Color.web("#006633"));
					tempDataFoundNameLabel.setPadding(new Insets(1, 10, 0, 10));
					tempDataFoundNameLabel.setAlignment(Pos.CENTER);

					HBox tempHorizontalHBox = new HBox();
					tempDataFoundButton.setText(DATA_FOUND_STRING);
					tempDataFoundButton.setStyle("-fx-background-color:#006680");
					tempDataFoundButton.setTextFill(Color.web("FFFFFF"));

					tempHorizontalHBox.getChildren().addAll(tempDataFoundButton, tempDataFoundNameLabel);
					dashboardGridPane.add(tempHorizontalHBox, 0, i + indexControl);

				} else if(currentName.equalsIgnoreCase("metadata")) {
					JSONObject tempMetadataJsonObject = new JSONObject(analysisResultJsonString).getJSONObject("metadata");
					JSONArray  tempMetadataJsonArray = tempMetadataJsonObject.names();

					for(int metadataIndex = 0; metadataIndex < tempMetadataJsonArray.length(); metadataIndex++){
						String metadataName = tempMetadataJsonArray.getString(metadataIndex);
						if(metadataName.equalsIgnoreCase("preview")){
							tempDataFoundButton = new Button(DATA_FOUND_STRING);
							tempDataFoundButton.setAlignment(Pos.CENTER);
							tempDataFoundButton.setMaxWidth(60);
							tempDataFoundButton.setMinWidth(60);

							tempDataFoundNameLabel = new Label("Extraction Data");
							tempDataFoundNameLabel.setId("dashboardValue");
							tempDataFoundNameLabel.setTextFill(Color.web("#006633"));
							tempDataFoundNameLabel.setPadding(new Insets(1, 10, 0, 10));
							tempDataFoundNameLabel.setAlignment(Pos.CENTER);

							HBox tempHBox = new HBox();
							tempDataFoundButton.setStyle("-fx-background-color:#006680");
							tempDataFoundButton.setTextFill(Color.web("FFFFFF"));
							tempHBox.getChildren().addAll(tempDataFoundButton,tempDataFoundNameLabel);
							indexControl = i + metadataIndex;
							dashboardGridPane.add(tempHBox, 0, i + metadataIndex);

						} else if(metadataName.equalsIgnoreCase("exif")){

							tempDataFoundButton = new Button(DATA_FOUND_STRING);
							tempDataFoundButton.setAlignment(Pos.CENTER);
							tempDataFoundButton.setMaxWidth(60);
							tempDataFoundButton.setMinWidth(60);

							Label tempDataLabel = new Label("EXIF Metadata");
							tempDataLabel.setId("dashboardValue");
							tempDataLabel.setTextFill(Color.web("#006633"));
							tempDataLabel.setPadding(new Insets(1, 10, 0, 10));
							tempDataLabel.setAlignment(Pos.CENTER);

							tempDataFoundButton.setStyle("-fx-background-color:#006680");
							tempDataFoundButton.setTextFill(Color.web("FFFFFF"));

							HBox tempHBox = new HBox();
							tempHBox.getChildren().addAll(tempDataFoundButton, tempDataLabel);

							indexControl = i + metadataIndex;
							dashboardGridPane.add(tempHBox, 0, i + metadataIndex);

						} else if(metadataName.equalsIgnoreCase("iptc")){
							tempDataFoundButton = new Button(DATA_FOUND_STRING);
							tempDataFoundButton.setAlignment(Pos.CENTER);
							tempDataFoundButton.setMaxWidth(60);
							tempDataFoundButton.setMinWidth(60);

							tempDataFoundNameLabel = new Label("IPTC Metadata");
							tempDataFoundNameLabel.setId("dashboardValue");
							tempDataFoundNameLabel.setTextFill(Color.web("#006633"));
							tempDataFoundNameLabel.setPadding(new Insets(1, 10, 0, 10));
							tempDataFoundNameLabel.setAlignment(Pos.CENTER);

							tempDataFoundButton.setStyle("-fx-background-color:#006680");
							tempDataFoundButton.setTextFill(Color.web("FFFFFF"));

							HBox tempHBox = new HBox();
							tempHBox.getChildren().addAll(tempDataFoundButton,tempDataFoundNameLabel);

							indexControl = i + metadataIndex;
							dashboardGridPane.add(tempHBox, 0, i + metadataIndex);

						} else if(metadataName.equalsIgnoreCase("xmp")){
							tempDataFoundButton = new Button(DATA_FOUND_STRING);
							tempDataFoundButton.setStyle("-fx-background-color:#006680");
							tempDataFoundButton.setTextFill(Color.web("FFFFFF"));
							tempDataFoundButton.setAlignment(Pos.CENTER);
							tempDataFoundButton.setMaxWidth(60);
							tempDataFoundButton.setMinWidth(60);

							tempDataFoundNameLabel = new Label("XMP Metadata");
							tempDataFoundNameLabel.setId("dashboardValue");
							tempDataFoundNameLabel.setTextFill(Color.web("#006633"));
							tempDataFoundNameLabel.setPadding(new Insets(1, 10, 0, 10));
							tempDataFoundNameLabel.setAlignment(Pos.CENTER);

							HBox tmpHBox = new HBox();
							tmpHBox.getChildren().addAll(tempDataFoundButton,tempDataFoundNameLabel);

							indexControl = i + metadataIndex;
							dashboardGridPane.add(tmpHBox, 0, i + metadataIndex);
						}

					}

				} else if(currentName.equalsIgnoreCase("ela")){
					tempDataFoundButton = new Button(DATA_FOUND_STRING);
					tempDataFoundButton.setStyle("-fx-background-color:#006680");
					tempDataFoundButton.setTextFill(Color.web("FFFFFF"));
					tempDataFoundButton.setAlignment(Pos.CENTER);
					tempDataFoundButton.setMaxWidth(60);
					tempDataFoundButton.setMinWidth(60);

					tempDataFoundNameLabel = new Label("Error Level Analysis Data");
					tempDataFoundNameLabel.setId("dashboardValue");
					tempDataFoundNameLabel.setTextFill(Color.web("#006633"));
					tempDataFoundNameLabel.setPadding(new Insets(1, 10, 0, 10));
					tempDataFoundNameLabel.setAlignment(Pos.CENTER);

					HBox tempHBox = new HBox();
					tempHBox.getChildren().addAll(tempDataFoundButton,tempDataFoundNameLabel);
					dashboardGridPane.add(tempHBox, 0, i + indexControl);

				} else if(currentName.equalsIgnoreCase("file_type")){
					tempDataFoundButton = new Button(DATA_FOUND_STRING);
					tempDataFoundButton.setStyle("-fx-background-color:#006680");
					tempDataFoundButton.setTextFill(Color.web("FFFFFF"));
					tempDataFoundButton.setAlignment(Pos.CENTER);
					tempDataFoundButton.setMaxWidth(60);
					tempDataFoundButton.setMinWidth(60);

					tempDataFoundNameLabel = new Label("Static Analysis Data");
					tempDataFoundNameLabel.setId("dashboardValue");
					tempDataFoundNameLabel.setTextFill(Color.web("#006633"));
					tempDataFoundNameLabel.setPadding(new Insets(1, 10, 0, 10));
					tempDataFoundNameLabel.setAlignment(Pos.CENTER);

					HBox tempHBox = new HBox();
					tempHBox.getChildren().addAll(tempDataFoundButton,tempDataFoundNameLabel);
					dashboardGridPane.add(tempHBox, 0, i + indexControl);
				}
			}

			VBox mainVBox = new VBox();
			mainVBox.setPadding(new Insets(5));
			mainVBox.setStyle("-fx-background-color:white;");
			mainVBox.getChildren().add(dashboardGridPane);
			mainVBox.setFillWidth(true);

			ScrollPane dashBoardDataScrollPane = new ScrollPane();
			dashboardGridPane.setStyle("-fx-background-color:white;");
			dashBoardDataScrollPane.setFitToHeight(true);
			dashBoardDataScrollPane.setFitToWidth(true);
			dashBoardDataScrollPane.setContent(mainVBox);

			return dashBoardDataScrollPane;

		} catch(Exception e) {
			Console.out(Logger.ERROR, e.getMessage());
			return null;
		}
	}

	private SplitPane displaySplitPane(Stage mainStage) {

		analysisResultTabPane.getTabs().clear();
		mainSplitPane.getItems().clear();
		fileChooserButtonHBox.getChildren().clear();
		analyzeButtonHBox.getChildren().clear();
		analyzeImageHolderVBox.getChildren().clear();
		caseDetailGridPane.getChildren().clear();
		fileChooserButtonHBox.setAlignment(Pos.CENTER);
		fileChooserButtonHBox.setStyle("-fx-background-color:#6e6e6e;");
		fileChooserButtonHBox.getChildren().add(selectImageFileLabel);
		fileChooserButtonHBox.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Image File");
				File imageFile = fileChooser.showOpenDialog(mainStage);
				if (imageFile != null) {
					imageName = imageFile.getName();
					imagePath = imageFile.getAbsolutePath();
					imageSize = imageFile.length() / 1000;
					selectedImageFileImageView.setImage(new Image(imageFile.toURI().toString()));
					imageDetailsVBox.getChildren().clear();
					imageDetailsVBox.getChildren().add(new Label("Name:" + imageName));
					imageDetailsVBox.getChildren().add(new Label("Path:" + imagePath));
					imageDetailsVBox.getChildren().add(new Label("Size: " + imageSize + " KB"));
				}
			}
		});

		fileChooserButtonHBox.setOnMouseEntered(event -> fileChooserButtonHBox.setOpacity(0.5));
		fileChooserButtonHBox.setOnMouseExited(event -> fileChooserButtonHBox.setOpacity(1));

		canUseExperimentalFeaturesCheckBox.setOnMouseClicked(event -> {
			if(canUseExperimentalFeaturesCheckBox.isSelected()) {
				canUseExperimentalFeatures = true;
				Console.out("Falcon is using experimental features");
			} else {
				canUseExperimentalFeatures = false;
				Console.out("Falcon is not using experimental features");
			}
		});

		analyzeButtonHBox.setAlignment(Pos.CENTER);
		analyzeButtonHBox.setStyle("-fx-background-color:#3a88ad;");
		analyzeButtonHBox.getChildren().add(analyzeImageButtonLabel);
		analyzeButtonHBox.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {

				if(canUseExperimentalFeatures) {
					try {
						Hedwig.getCopyMoveAnalysisData(imagePath);
					} catch (IOException ex) {
						Console.out(Logger.ERROR, ex.getMessage());
					}
				}

				ArrayList<Hedwig.GhiroBundle> ghiroBundleList = new ArrayList<>();
				ghiroBundleList.add(new Hedwig.GhiroBundle("name", caseTitleTextField.getText()));
				ghiroBundleList.add(new Hedwig.GhiroBundle("description", caseDescriptionField.getText()));
				ghiroBundleList.add(new Hedwig.GhiroBundle("image", imagePath));

				try {
					analysisResultJsonString = Hedwig.analyse(Hedwig.Route.NEW_ANALYSIS, ghiroBundleList);
					displaySplitPane(null);

					if (analysisResultJsonString.isEmpty()) {
						Console.out(Logger.WARNING, "No analysis was returned.");
					} else {
						Tab dashboardTab = new Tab("Analysis Summary");
						if (displayDashBoardScrollPane() != null) {
							dashboardTab.setContent(displayDashBoardScrollPane());
							analysisResultTabPane.getTabs().add(dashboardTab);
						}

						Tab staticDataTab = new Tab("Static Analysis Data");
						if (displayStaticDataScrollPane() != null) {
							staticDataTab.setContent(displayStaticDataScrollPane());
							analysisResultTabPane.getTabs().add(staticDataTab);
						}

						if (displayExifDataScrollPane() != null) {
							Tab exifDataTab = new Tab("EXIF Metadata");
							exifDataTab.setContent(displayExifDataScrollPane());
							analysisResultTabPane.getTabs().add(exifDataTab);
						}

						if (displayIptcDataScrollPane() != null) {
							Tab iptcDataTab = new Tab("IPTC Analysis Data");
							iptcDataTab.setContent(displayIptcDataScrollPane());
							analysisResultTabPane.getTabs().add(iptcDataTab);
						}

						if (displayXmpDataScrollPane() != null) {
							Tab xmpDataTab = new Tab("XMP Analysis Data");
							xmpDataTab.setContent(displayXmpDataScrollPane());
							analysisResultTabPane.getTabs().add(xmpDataTab);
						}

						if (displayGpsDataBorderPane() != null) {
							Tab geolocationDataTab = new Tab("Geolocation Analysis Data");
							geolocationDataTab.setContent(displayGpsDataBorderPane());
							analysisResultTabPane.getTabs().add(geolocationDataTab);
						}

						if (displaySignatureScrollPane() != null) {
							Tab signatureDataTab = new Tab("Signature Analysis Data");
							signatureDataTab.setContent(displaySignatureScrollPane());
							analysisResultTabPane.getTabs().add(signatureDataTab);
						}

						if (displayCopyMoveScrollPane() != null) {
							Tab copymoveDataTab = new Tab("Copymove Analysis Data");
							copymoveDataTab.setContent(displayCopyMoveScrollPane());
							analysisResultTabPane.getTabs().add(copymoveDataTab);
						}

						if (displayElaDataBorderPane() != null) {
							Tab errorLevelAnalysisDataTab = new Tab("Error Level Analysis Data");
							errorLevelAnalysisDataTab.setContent(displayElaDataBorderPane());
							analysisResultTabPane.getTabs().add(errorLevelAnalysisDataTab);
						}
					}

				} catch (IOException | URISyntaxException ex) {
					Console.out(Logger.ERROR, ex.getMessage());
				}
			}

		});

		analyzeButtonHBox.setOnMouseEntered(event -> analyzeButtonHBox.setOpacity(0.5));
		analyzeButtonHBox.setOnMouseExited(event -> analyzeButtonHBox.setOpacity(1));

		analyzeImageHolderVBox.setAlignment(Pos.CENTER);
		analyzeImageHolderVBox.setStyle("-fx-background-color:#6e6e6e;");
		analyzeImageHolderVBox.getChildren().add(selectedImageFileImageView);

		imageDetailsVBox.setAlignment(Pos.TOP_LEFT);
		imageDetailsVBox.setStyle("-fx-background-color:white;");

		caseDetailGridPane.setHgap(1);
		caseDetailGridPane.setVgap(15);
		caseDetailGridPane.add(caseTitleLabel, 0, 0);
		caseDetailGridPane.add(caseTitleTextField, 1, 0, 150, 1);
		caseDetailGridPane.add(caseDescriptionLabel, 0, 2);
		caseDetailGridPane.add(caseDescriptionField, 1, 2, 150, 1);
		caseDetailGridPane.add(fileChooserButtonHBox, 1, 3, 150, 1);
		caseDetailGridPane.add(analyzeButtonHBox, 1, 4, 150, 1);
		caseDetailGridPane.add(canUseExperimentalFeaturesCheckBox, 1, 5);
		caseDetailGridPane.add(analyzeImageHolderVBox, 190, 0, 254, 5);
		caseDetailGridPane.add(imageDetailsVBox, 450, 0, 170, 5);

		mainSplitPane.getItems().addAll(caseDetailGridPane, analysisResultTabPane);

		return mainSplitPane;

	}

    @Override
    @SuppressWarnings("DesignForExtension")
    public void start(Stage primaryStage) throws IOException, URISyntaxException {

        imageForensicsLeftGridPaneItemHBox.setAlignment(Pos.CENTER);
        imageForensicsLeftGridPaneItemHBox.setMinWidth(250);
        imageForensicsLeftGridPaneItemHBox.setMinHeight(50);
        imageForensicsLeftGridPaneItemHBox.setStyle("-fx-background-color:#dddddd;");
        imageForensicsIconVBox.setAlignment(Pos.CENTER);
        imageForensicsIconVBox.setPadding(new Insets(0, 20, 0, 0));

        BorderPane mainBorderPane = new BorderPane();
        imageForensicsLeftGridPaneItemHBox.setStyle("-fx-border-color:#ffffff;");
        mainBorderPane.setCenter(displaySplitPane(primaryStage));

        imageForensicsIconVBox.getChildren().add(cameraIconImageView);
        imageForensicsLeftGridPaneItemHBox.getChildren().addAll(imageForensicsIconVBox, leftGridPaneItemImageLabel);

        final float VERTICAL_GAP = 1;
        leftGridPane.setVgap(VERTICAL_GAP);

        leftGridPane.add(imageForensicsLeftGridPaneItemHBox, 0, 0, 100, 1);
        leftBorderPane.setCenter(leftGridPane);

        final float LEFT_PADDING = 15;
        final float TOP_PADDING = 22;
        final float RIGHT_PADDING = 0;
        final int BOTTOM_PADDING = 25;
        leftBorderPane.setPadding(new Insets(TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING, LEFT_PADDING));

        mainBorderPane.setLeft(leftBorderPane);

        final GraphicsDevice DEFAULT_SCREEN_SIZE
                = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        Scene mainContentScene = new Scene(mainBorderPane, DEFAULT_SCREEN_SIZE.getDisplayMode().getWidth(),
                DEFAULT_SCREEN_SIZE.getDisplayMode().getHeight());
        mainContentScene.setFill(Color.rgb(255, 255, 255));

        primaryStage.setTitle("Digital Image Forensics Software");
        primaryStage.setScene(mainContentScene);
        primaryStage.setMinWidth(DEFAULT_SCREEN_SIZE.getDisplayMode().getWidth());
        primaryStage.setMinHeight(DEFAULT_SCREEN_SIZE.getDisplayMode().getHeight());

        primaryStage.setMaximized(true);
        mainContentScene.getStylesheets().add(getClass().getResource("/css/app.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
