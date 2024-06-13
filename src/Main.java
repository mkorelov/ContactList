import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.VBox;

class Contact extends HBox implements Comparable<Object> {

    private Label index;
    private TextField contactName;
    private TextField phoneNumber;
    private TextField emailAddress;
    private Button selectButton;

    private boolean selected;
    
    private Button uploadButton;
    
    // To display images
    private ImageView imageView;

    Contact() {
        this.setPrefSize(700, 20); // sets size of contact
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of contact
        selected = false;

        index = new Label();
        index.setText(""); // create index label
        index.setPrefSize(25, 20); // set size of Index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the contact
        this.getChildren().add(index); // add index label to contact
        
        uploadButton = new Button("Upload Image");
        
        imageView = new ImageView();
        imageView.setFitHeight(90);
        imageView.setFitWidth(90);

    	VBox vbox = new VBox(uploadButton, imageView);
    	vbox.setPrefSize(125, 20);
    	vbox.setPrefHeight(Double.MAX_VALUE);
    	vbox.setAlignment(Pos.CENTER);
    	this.getChildren().add(vbox);
        
        contactName = new TextField(); // create contact name text field
        contactName.setPrefSize(175, 20); // set size of text field
        contactName.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        contactName.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(contactName); // add textlabel to contact
        
        phoneNumber = new TextField(); // create phone number text field
        phoneNumber.setPrefSize(125, 20); // set size of text field
        phoneNumber.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        phoneNumber.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(phoneNumber); // add textlabel to phone number
        
        emailAddress = new TextField(); // create phone number text field
        emailAddress.setPrefSize(200, 20); // set size of text field
        emailAddress.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        emailAddress.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(emailAddress); // add textlabel to phone number

        selectButton = new Button("Select"); // creates a button for selecting a contact for deletion
        selectButton.setPrefSize(75, 50);
        selectButton.setPrefHeight(Double.MAX_VALUE);
        selectButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button
        this.getChildren().add(selectButton);
    }

    public void setContactIndex(int num) {
        this.index.setText(num + ""); // num to String
        this.contactName.setPromptText("Name");
        this.phoneNumber.setPromptText("Phone Number");
        this.emailAddress.setPromptText("Email Address");
    }

    public TextField getContactName() {
        return this.contactName;
    }
    
    public TextField getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public TextField getEmailAddress() {
        return this.emailAddress;
    }

    public Button getSelectButton() {
        return this.selectButton;
    }
    
    public Button getUploadButton() {
        return this.uploadButton;
    }
    
    public ImageView getImageView() {
    	return this.imageView;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void toggleSelect() {
        
    	if (this.selected == true) {
    		this.selected = false;
    		this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;");
    		for (int i = 0; i < this.getChildren().size(); i++) {
    			this.getChildren().get(i).setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
    		}
    	}
    	else {
    		this.selected = true;
    		this.setStyle("-fx-background-color: #EB212E; -fx-border-width: 0; -fx-font-weight: bold;"); // change color of contact to green
    		for (int i = 0; i < this.getChildren().size(); i++) {
    			this.getChildren().get(i).setStyle("-fx-background-color: #EB212E; -fx-border-width: 0;"); // change color of contact to green
    		}
    	}
    }
    @Override
    public int compareTo(Object o) {
    	Contact contact = (Contact) o;		
    	return this.getContactName().getText().compareTo(contact.getContactName().getText());
    }
}

class ContactList extends VBox {

    ContactList() {
        this.setSpacing(5); // sets spacing between contacts
        this.setPrefSize(800, 750);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }

    public void updateContactIndices() {
        int index = 1;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Contact) {
                ((Contact) this.getChildren().get(i)).setContactIndex(index);
                index++;
            }
        }
    }

    public void deleteSelectedContacts() {
        this.getChildren().removeIf(contact -> contact instanceof Contact && ((Contact) contact).isSelected());
        this.updateContactIndices();
    }

    /*
     * Save contacts to a csv file
     */
    public void saveContacts(File file) {
    	try {
    		
    		FileWriter output = new FileWriter(file.getPath());
    		
            for (int i = 0; i < this.getChildren().size(); i++) {
            	if (this.getChildren().get(i) instanceof Contact) {
            		
            		Contact contact = ((Contact) this.getChildren().get(i));
            		output.write(contact.getContactName().getText() + ',');
            		output.write(contact.getPhoneNumber().getText() + ',');
            		output.write(contact.getEmailAddress().getText() + '\n');
            	}
            }
    		
    		output.close();
    	}
    	
    	catch (Exception e) {
    		System.out.println("Exception in saveContacts()!");
    	}
    }

    /*
     * Sort the contacts lexicographically
     */
    public void sortContacts() {
    	try {
    		ArrayList<Contact> list = new ArrayList<Contact>();
    		Contact copy;
    		
    		for (int i = 0; i < this.getChildren().size(); i++) {
    			if (this.getChildren().get(i) instanceof Contact) {
    				Contact contact = (Contact) this.getChildren().get(i);
    				copy = new Contact();
    				
    				copy.getContactName().setText(contact.getContactName().getText());
    				copy.getPhoneNumber().setText(contact.getPhoneNumber().getText());
    				copy.getEmailAddress().setText(contact.getEmailAddress().getText());
    				copy.getImageView().setImage(contact.getImageView().getImage());
    				list.add(copy);
    			}
    		}
    		
    		Collections.sort(list);
    		
    		for (int i = 0; i < list.size(); i++) {
            	if (this.getChildren().get(i) instanceof Contact) {
            		Contact contact = (Contact) this.getChildren().get(i);
            		
            		contact.getContactName().setText(list.get(i).getContactName().getText());
            		contact.getPhoneNumber().setText(list.get(i).getPhoneNumber().getText());
            		contact.getEmailAddress().setText(list.get(i).getEmailAddress().getText());
            		contact.getImageView().setImage(list.get(i).getImageView().getImage());
            		contact.getImageView().setFitWidth(90);
            		contact.getImageView().setFitHeight(90);
            	}
    		}
    	}
    	catch (Exception e){
    		System.out.println("Exception in sortContacts()!");
    	}
    }
}

class Footer extends HBox {

    private Button addButton;
    private Button deleteButton;
    private Button saveButton;
    private Button sortButton;

    Footer() {
        this.setPrefSize(800, 50);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        addButton = new Button("Add Contact"); // text displayed on add button
        addButton.setStyle(defaultButtonStyle); // styling the button
        deleteButton = new Button("Delete Selected"); // text displayed on delete button
        deleteButton.setStyle(defaultButtonStyle);

        saveButton = new Button("Save Contacts");
        saveButton.setStyle(defaultButtonStyle);
        sortButton = new Button("Sort Contacts (By Name)");
        sortButton.setStyle(defaultButtonStyle);
        
        this.getChildren().addAll(addButton, deleteButton, saveButton, sortButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getClearButton() {
        return deleteButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getSortButton() {
        return sortButton;
    }

}

class Header extends HBox {

    Header() {
        this.setPrefSize(800, 50); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text("Contacts"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

class AppFrame extends BorderPane{

    private Header header;
    private Footer footer;
    private ContactList contactList;
    private ScrollPane scrollbar;

    private Button addButton;
    private Button deleteButton;
    private Button saveButton;
    private Button sortButton;
    
    private ImageView imageView;
    
    // To open a file dialog for selecting images
    private FileChooser fileChooser;

    AppFrame()
    {
        // Initialise the header Object
        header = new Header();

        // Create a contactlist Object to hold the contacts
        contactList = new ContactList();
        
        // Initialise the Footer Object
        footer = new Footer();
        

        scrollbar = new ScrollPane(contactList);
        scrollbar.setFitToWidth(true);
        scrollbar.setFitToHeight(true);

        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(scrollbar);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);

        // Initialise Button Variables through the getters in Footer
        addButton = footer.getAddButton();
        deleteButton = footer.getClearButton();
        saveButton = footer.getSaveButton();
        sortButton = footer.getSortButton();

        // Call Event Listeners for the Buttons
        addListeners();
    }

    public void addListeners()
    {

        // Add Contact button functionality
        addButton.setOnAction(e -> {
            // Create a new contact
            Contact contact = new Contact();
            // Add contact to contactList
            contactList.getChildren().add(contact);
            // Add selectButtonToggle to the Select button
            Button selectButton = contact.getSelectButton();
            selectButton.setOnAction(e1 -> {
                // Call toggleSelect on click
                contact.toggleSelect();
            });
            
            Button uploadButton = contact.getUploadButton();
            uploadButton.setOnAction(e2 -> {
            	
                imageView = contact.getImageView();
                fileChooser = new FileChooser();
                
                Stage stage = (Stage) uploadButton.getScene().getWindow();
            	uploadImage(stage); 
            });
            
            // Update contact indices
            contactList.updateContactIndices();
        });
        
        // Delete selected contacts
        deleteButton.setOnAction(e -> {
            contactList.deleteSelectedContacts();
        });
        
        saveButton.setOnAction(e -> {
        	fileChooser = new FileChooser();
        	
        	Stage stage = (Stage) saveButton.getScene().getWindow();
        	
        	fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
        	File selectedFile = fileChooser.showSaveDialog(stage);
        	contactList.saveContacts(selectedFile);
        });
        
        sortButton.setOnAction(e -> {
        	contactList.sortContacts();
        });
    }
    
    private void uploadImage(Stage stage) {

        // Select which extensions are allowed
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(stage);


        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());

            imageView.setImage(image);
            imageView.setFitWidth(90);
            imageView.setFitHeight(90);
        }
    }
}

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setting the Layout of the Window- Should contain a Header, Footer and the ContactList
        AppFrame root = new AppFrame();

        // Set the title of the app
        primaryStage.setTitle("Contact Management App");
        // Create scene of mentioned size with the border pane
        primaryStage.setScene(new Scene(root, 700, 700));
        // Make window non-resizable
        primaryStage.setResizable(false);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}