/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: lab11
* Date: 04/28/2026
* File: DiceSimulator.java
*
* Description: A simulator for dice rolls using javafx.
********************************************************************/

package lab11;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DiceSimulator extends Application 
{
    Image d1Image = new Image("file:Die1.png");
    Image d2Image = new Image("file:Die2.png");
    Image d3Image = new Image("file:Die3.png");
    Image d4Image = new Image("file:Die4.png");
    Image d5Image = new Image("file:Die5.png");
    Image d6Image = new Image("file:Die6.png");
    ArrayList<Image> imageList;
    ImageView leftDieIV;
    ImageView rightDieIV;
    
    class TossDie implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            Random rand = new Random();
            Timeline timer = new Timeline();

            for (int i = 0; i < 11; i++) {
                int frame = i;
                KeyFrame kf = new KeyFrame(Duration.millis(frame * 75), e -> {
                    leftDieIV.setImage(imageList.get(rand.nextInt(imageList.size())));
                    rightDieIV.setImage(imageList.get(rand.nextInt(imageList.size())));
                });
                timer.getKeyFrames().add(kf);
            }

            timer.play();
            
        }
    }
    
    public void start(Stage primaryStage) {
        imageList = new ArrayList<>();
        leftDieIV = new ImageView();
        rightDieIV = new ImageView();

        imageList.add(d1Image);
        imageList.add(d2Image);
        imageList.add(d3Image);
        imageList.add(d4Image);
        imageList.add(d5Image);
        imageList.add(d6Image);

        leftDieIV.setFitHeight(300);
        leftDieIV.setFitWidth(300);

        rightDieIV.setFitHeight(300);
        rightDieIV.setFitWidth(300);

        Button tossButton = new Button("Toss");
        tossButton.setOnAction(new TossDie());

        HBox ivHBox = new HBox(25, leftDieIV, rightDieIV);
        VBox mainVBox = new VBox(25, ivHBox, tossButton);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setPadding(new Insets(15));
        Scene scene = new Scene(mainVBox);

        




        primaryStage.setScene(scene);
        primaryStage.show();
    }    
    
    public static void main(String[] args) {
        launch(args);

    }
}
