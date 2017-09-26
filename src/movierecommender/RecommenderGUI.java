package movierecommender;

import java.util.ArrayList;

/*Artist: Tyler Cabutto; Brandon Little */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RecommenderGUI extends Application implements EventHandler<ActionEvent>{
	String q;
	TextField t1;	
	TextField id;
	TextField recField;
	Label output;
	Label title;
	Label info;
	Label recMe;
	Button search;
	Button show;
	Button recDo;
	ComboBox<String> select;
	ComboBox<String> howMany;
	ComboBox<String> rec;
	ImageView iv1;
	ImageView iv2;
	
	ArrayList<String> query;
	
	public static void main(String[] args){
		launch();
	}
	
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setTitle("Movie Recommender 3000");
		VBox layout = new VBox();
		HBox menu = new HBox();
		Label space = new Label();
		ScrollPane scroll = new ScrollPane();
		scroll.setMinHeight(350);
		HBox picMenu = new HBox();
		HBox recMenu = new HBox();
		
		//directions at the top
		title = new Label();
		title.setText("Search By:");
		title.setStyle("-fx-font: 24 arial;");
		
		
		//select what type of information to show
		select = new ComboBox<String>();
		select.getItems().addAll("Top Movies", "Movie Title", "Genre", "Director", "Actor", "Movies with tag", "Top Directors", "Top Actors", "Ratings by Id", "Movie Tags");
		select.setMinWidth(150);
		select.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e)
			{
				handleComboBoxAction();
			}
		});
		
		//Select how many items to show
		howMany = new ComboBox<String>();
		howMany.getItems().addAll("10", "20", "50");
		howMany.setMinWidth(70);
		howMany.setMaxWidth(70);
		howMany.setVisible(false);
		
		//Search button
		search = new Button();
		search.setText("Search");
		search.setMinWidth(70);
		search.setMaxWidth(70);
		search.setOnAction(this);
		
		//text area for user input
		t1 = new TextField();		
		t1.setPrefWidth(750);
		t1.setVisible(false);	
		
		//shows query output
		output = new Label();
		output.setStyle("-fx-font-family: monospace");
                output.setWrapText(false);
		
		//tells user to input id
		info = new Label();
		info.setText("Input the movie number to see its picture: ");
		info.setStyle("-fx-font: 20 arial;");
		
		//reads in 
		id = new TextField();
		id.setPrefWidth(177);
		
		//show button to display images
		show = new Button();
		show.setText("Show Me the Pictures!");
		show.setMinWidth(250);
		show.setOnAction(this);
		
		//recommendation label
		recMe = new Label();
		recMe.setText("Let us recommend some movies: ");
		recMe.setStyle("-fx-font: 24 arial;");
		
		//recommendation combobox
		rec = new ComboBox<String>();
		rec.getItems().addAll("Genre", "Director");
		rec.setMinWidth(100);
		
		//recommendation input
		recField = new TextField();
		recField.setPrefWidth(700);
		
		//recommendation button
		recDo = new Button();
		recDo.setText("Show me some movies!");
		recDo.setMinWidth(250);
		recDo.setOnAction(this);
		
		
		//construct the view
		scroll.setContent(output);
		menu.getChildren().addAll(select, t1, howMany, search);
		picMenu.getChildren().addAll(info, id, show);
		recMenu.getChildren().addAll(rec, recField, recDo);
		layout.getChildren().addAll(title, menu, scroll, picMenu, space, recMe, recMenu);
		Scene scene = new Scene(layout, 800, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@Override
	public void handle(ActionEvent event) {
		String s = "";
		String in;
		
		//onclick for search
		if(select.getSelectionModel().getSelectedItem() != null){
			s = select.getSelectionModel().getSelectedItem();
		}
		if(s.equals("Top Movies")){
			in = howMany.getSelectionModel().getSelectedItem();
			q = QueryTester.query1(in);
			output.setText(q);
		}
		if(s.equals("Movie Title")){
			in = t1.getText();
			q = QueryTester.query2(in);
			output.setText(q);
		}
		if(s.equals("Genre")){
			in = t1.getText();
			int i = Integer.parseInt(howMany.getSelectionModel().getSelectedItem());
			q = QueryTester.query3(in, i);
			output.setText(q);
		}
		if(s.equals("Director")){
			in = t1.getText();
			q = QueryTester.query4(in);
			output.setText(q);
		}
		if(s.equals("Actor")){
			in = t1.getText();
			q = QueryTester.query5(in);
			output.setText(q);
		}
		if(s.equals("Movies with tag")){
			in = t1.getText();
			q = QueryTester.query6(in);
			output.setText(q);
		}
		if(s.equals("Top Directors")){
			in = t1.getText();
			q = QueryTester.query7(in);
			output.setText(q);
		}
		if(s.equals("Top Actors")){
			in = t1.getText();
			q = QueryTester.query8(in);
			output.setText(q);
		}
		if(s.equals("Ratings by Id")){
			in = t1.getText();
			q = QueryTester.query9(in);
			output.setText(q);
		}
		if(s.equals("Movie Tags")){
			in = t1.getText();
			q = QueryTester.query10(in);
			output.setText(q);
		}
		
		//onclick of recommendation
		if(rec.getSelectionModel().getSelectedItem() != null){
			s = rec.getSelectionModel().getSelectedItem();
		}
		if(event.getSource() == recDo){
			if(s.equals("Genre")){
				in = recField.getText();
				q = QueryTester.query11(in);
				output.setText(q);
			}
			if(s.equals("Director")){
				in = recField.getText();
				q = QueryTester.query12(in);
				output.setText(q);
			}
		}
		//onclick for showing pictures
		if(event.getSource() == show){
	        try {
	        	//take user input and get the index
	        	int i = 0;
	        	if(!id.getText().equals("")){
	        		i = Integer.parseInt(id.getText());
	        		System.out.println(i);
	        		i = i*2-2;
	        		System.out.println(i);
	        		System.out.println(query.size());
		        	String image1 = "http://" + query.get(i);
		        	String image2 = "http://" + query.get(i+1);
		        	System.out.println(query.get(i));
		        	System.out.println(query.get(i+1));
		        	HBox h = new HBox();
		        	
		        	//create the images
		    		iv1 = ImageViewBuilder.create().image(new Image(image1)).build();
		    		iv1.setFitWidth(225);
		    		iv1.setPreserveRatio(true);
		    		iv2 = ImageViewBuilder.create().image(new Image(image2)).build();
		    		iv2.setFitWidth(225);
		    		iv2.setPreserveRatio(true);
		    		
		    		h.getChildren().addAll(iv1, iv2);
		            Stage stage = new Stage();
		            stage.setTitle("Movie Images");
		            stage.setScene(new Scene(h, 450, 450));
		            stage.show();
	        	}
	        	
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		if(!s.equals("")){
			grabURL(q);
                }	
	}
	
	public void handleComboBoxAction(){
		String s = select.getSelectionModel().getSelectedItem();
		if(s.equals("Top Movies")){
			t1.setVisible(false);
			howMany.setVisible(true);
			title.setText("Search by:\tselect how many movies you want to see:");
		}
		if(s.equals("Movie Title")){
			howMany.setVisible(false);
			t1.setVisible(true);
			title.setText("Search by:\tinput what movie you want to see:");
		}
		if(s.equals("Genre")){
			howMany.setVisible(true);
			t1.setVisible(true);
			title.setText("Search by:\tinput the genre you are searching for:");
		}
		if(s.equals("Director")){
			howMany.setVisible(false);
			t1.setVisible(true);
			title.setText("Search by:\tinput the director you are searching for:");
		}
		if(s.equals("Actor")){
			howMany.setVisible(false);
			t1.setVisible(true);
			title.setText("Search by:\tinput the actor you are searching for:");
		}
		if(s.equals("Movies with tag")){
			howMany.setVisible(false);
			t1.setVisible(true);
			title.setText("Search by:\tinput the tag you are searching for:");
		}
		if(s.equals("Top Directors")){
			t1.setVisible(true);
			howMany.setVisible(false);
			title.setText("Search by:\tinput the required number of movies directed:");
		}
		if(s.equals("Top Actors")){
			howMany.setVisible(false);
			t1.setVisible(true);
			title.setText("Search by:\tinput the required number of movies starred in:");
		}
		if(s.equals("Ratings by Id")){
			howMany.setVisible(false);
			t1.setVisible(true);
			title.setText("Search by:\tinput the Id of the user you are searching for:");
		}
		if(s.equals("Movie Tags")){
			howMany.setVisible(false);
			t1.setVisible(true);
			title.setText("Search by:\tinput the movie you want to see the tags for:");
		}
	}
	
	private ArrayList<String> grabURL(String in)
	{
		query = new ArrayList<String>();
		String[] vals = in.split(" ");
		String t = "";
		int i = 0;
		int j = 0;
		for(String s : vals)
		{
			if(s.length()>7)
			{	
				t = s.substring(0, 7);
				if(t.equals("http://"))
				{
					String sub = s.substring(7, s.length()-1);
					
					//first url
					if(j == 0){
						if(!sub.contains("flixster") && !sub.contains("rottentomatoes")){
							query.add("placeholder");
							j = 1;
						}
						query.add(sub);
					}
					
					//second url
					else {
						if(!sub.contains("ia.media")){
							query.add("placeholder");
							j = 0;
						}
						query.add(sub);
					}
					
					//increment
					i++;
					if(j == 0){
						j = 1;
					}
					else {
						j = 0;
					}
				}
			}
		}
		return query;
	}
}