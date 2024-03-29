/*
 * Made by Marcus Uy and Ryan Seto.
 * Game Name: HIYA!
 * Work Cited:
 * - Button Dimming and Button shortcut event handler [.setOnAction(e -> {});]
 *   - http://tutorials.jenkov.com/javafx/button.html
 * - Rectangles, Labels, VBox, TextFields, and Text all used this API
 *   - https://docs.oracle.com/javase/8/javafx/api/toc.htm
 * - FileReader and BufferedReader
 *   - https://docs.oracle.com/javase/10/docs/api/?java/io/FileReader.html
 *   - https://www.youtube.com/watch?v=VwV1VKvtVtI
 * - FileWriter and BufferedWriter
 *   - https://www.youtube.com/watch?v=ymvFVkJ_SDI
 * - TranslateTransion
 *   - http://www.informit.com/articles/article.aspx?p=2359759
 * - Timeline
 *   - https://www.youtube.com/watch?v=t2Bv6hwELsU
 * - JavaFX Colors
 *   - https://docs.oracle.com/javase/8/javafx/api/javafx/scene/paint/class-use/Color.html
 * - Try Catch Statements
 *   - https://www.dummies.com/programming/java/try-statements-in-java/
 * - Setting Fonts
 *   - https://docs.oracle.com/javafx/2/text/jfxpub-text.htm
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.animation.AnimationTimer;

public class Main extends Application {
	// Title Screen Pane and Scene.
	public static Pane rootTitle;
	public static Scene mainScene;

	// Instructions Pane and Scene.
	public static Pane rootI;
	public static Scene sceneI;

	// High Score Pane and Scene.
	public static Pane rootHighScore;
	public static Scene sceneHighScore;

	// Player Name Pane and Scene. This scene is where Players input their names for
	// high score.
	public static Pane rootPlayerName;
	public static Scene scenePlayerName;
	// Player Name Display Labels.
	public static Label p1Name = new Label("");
	public static Label p2Name = new Label("");
	// Player Name Text Fields.
	public static TextField p1TF = new TextField();
	public static TextField p2TF = new TextField();
	// Player Name Input Strings.
	public static String p1NameInput = new String();
	public static String p2NameInput = new String();
	// Player Name Error Labels and Buttons.
	public static Label emptyName;
	public static Label sameName;
	public static Label samePerson;
	public static Button yesSamePerson;
	public static Button noSamePerson;
	public static Label changeName;

	// Game Pane and Scene.
	public static Pane rootGame;
	public static Scene sceneGame;
	// Map for the Game Scene.
	public static Rectangle map = new Rectangle(600, 350);
	// Player 1.
	public static Rectangle p1 = new Rectangle(50, 50);
	// Player 2.
	public static Rectangle p2 = new Rectangle(50, 50);
	// Player Move Boolean
	public static boolean p1MoveUp = false;
	public static boolean p1MoveDown = false;
	public static boolean p2MoveUp = false;
	public static boolean p2MoveDown = false;
	// Player Movement Coordinates
	public static double p1NewY;
	public static double p2NewY;
	// Lives
	public static int p1Lives = 3;
	public static int p2Lives = 3;
	// Lives Icons
	public static Rectangle p1Lives1 = new Rectangle(30, 30, Color.GOLD);
	public static Rectangle p1Lives2 = new Rectangle(30, 30, Color.GOLD);
	public static Rectangle p1Lives3 = new Rectangle(30, 30, Color.GOLD);
	public static Rectangle p2Lives1 = new Rectangle(30, 30, Color.GOLD);
	public static Rectangle p2Lives2 = new Rectangle(30, 30, Color.GOLD);
	public static Rectangle p2Lives3 = new Rectangle(30, 30, Color.GOLD);
	// Bullets
	public static Rectangle p1Bullet = new Rectangle(30, 30, Color.BLACK);
	public static Rectangle p2Bullet = new Rectangle(30, 30, Color.BLACK);
	// Bullet TranslateTransition
	public static TranslateTransition p1BulletTransition = new TranslateTransition();
	public static TranslateTransition p2BulletTransition = new TranslateTransition();
	// Bullet Desired X for TranslateTransitions
	public static double p1DesiredX = 700;
	public static double p2DesiredX = -700;
	// Bullet Recharge Line TranslateTransition
	public static TranslateTransition p1RechargeTransition = new TranslateTransition();
	public static TranslateTransition p2RechargeTransition = new TranslateTransition();
	// Bullet Recharge Bars
	public static Rectangle p1BulletRecharge = new Rectangle(100, 30, Color.TRANSPARENT);
	public static Rectangle p2BulletRecharge = new Rectangle(100, 30, Color.TRANSPARENT);
	// Bullet Recharge Booleans
	public static boolean p1Ammo = true;
	public static boolean p2Ammo = true;
	// Bullet Recharge Fillings
	public static Rectangle p1AmmoFill = new Rectangle(1, 30, Color.CRIMSON);
	public static Rectangle p2AmmoFill = new Rectangle(1, 30, Color.ROYALBLUE);
	// Bullet Recharge Filling Line
	public static Line p1AmmoLine = new Line(150, 465, 150, 495);
	public static Line p2AmmoLine = new Line(750, 465, 750, 495);
	// Animation Timer
	public static AnimationTimer gameLoop;
	// Pause Boolean
	public static boolean pause = false;
	// Pause Menu
	public static Text pauseText = new Text("PAUSE");
	public static Button pauseContinue = new Button("Continue?");
	public static Button pauseExit = new Button("Exit");
	// Game Over Button
	public static Button gameOver = new Button("GAME OVER!");
	// Winner
	public static String winner = new String("");

	// Timer Variables
	public static Timeline timer;
	public static ArrayList<clock> timerArr = new ArrayList<clock>();
	public static int winningTime;

	// This timer starts and ends with the game. This is used to track who can win
	// the fastest for the high score.
	public class clock extends Pane {
		private clock() {
			time.setFont(Font.font(50));
			time.setLayoutX(393);
			time.setLayoutY(15);
			getChildren().add(time);
			timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> timeLabel()));
			// Since the game ends when one player is out of lives, not when a timer runs
			// out, the timer runs forever.
			timer.setCycleCount(Timeline.INDEFINITE);
			timer.play();
		}

		int secondsPass = 0;
		String labelString = " ";
		String tempSecs = "";
		String tempMins = "";
		int mins = 0;
		Label time = new Label("00:00");

		private void timeLabel() {
			if (secondsPass >= 0) {
				secondsPass++;
				// This blocks sets the front portion of the label to how many minutes have gone
				// buy.
				if (mins < 10) {
					tempMins = "0" + mins;
					labelString = tempMins + ":" + secondsPass;
				}
				// Sets the back portion of the label to how many seconds have passed. If time
				// is less than 10, adds a zero in front.
				if (secondsPass < 10) {
					tempSecs = "0" + secondsPass;
					labelString = tempMins + ":" + tempSecs;
				}
				// This increments the number of minutes and resets secondsPass if 60 seconds
				// has passed.
				if (secondsPass % 60 == 0) {
					secondsPass = 0;
					mins++;
					tempMins = mins + "";
				}
				time.setText(labelString);
				// Pulls the time out of the timer for high score comparison.
				winningTime = secondsPass;
			}
		}
	}

	// Text File Readers
	public static FileReader fileReader;
	public static BufferedReader reader;
	// Text File Writers
	public static FileWriter fileWriter;
	public static BufferedWriter writer;
	// This int will store the int of the high score to compare it with the current
	// winning time.
	public static int highScoreInt = 0;
	// This String will store the String value of the high score for display.
	public static String highScoreIntString = "";
	// This String will store the name of the high scorer.
	public static String highScoreName = "";

	// These will contain the text messages of of the high score page.
	public static Text highScoreNameIntro;
	public static Text highScoreNameLine;
	public static Text highScoreIntIntro;
	public static Text highScoreIntLine;
	public static Text noHighScore;

	// This is the method that reads the text file for the high score. The only
	// restriction to the player's name is that it cannot have a colon.
	public static void fileReader() throws Exception {
		fileReader = new FileReader(System.getProperty("user.dir") + "\\Highscore.txt");
		reader = new BufferedReader(fileReader);
		String line = reader.readLine();
		boolean colonCheck = false;
		if (line != null) {
			highScoreName = "";
			highScoreIntString = "";
			for (int i = 0; i < line.length(); i++) {
				// Checks if there is a colon. Everything before the colon is the player's name.
				if ((int) line.charAt(i) == 58) {
					colonCheck = true;
				}
				// Pulls out the high scorer's name.
				if (colonCheck == false) {
					char tempChar = line.charAt(i);
					highScoreName = highScoreName + "" + tempChar;
				}
				// Pulls out the high score as a String.
				if (colonCheck == true) {
					if ((int) line.charAt(i) >= 48 && (int) line.charAt(i) <= 57) {
						char tempChar = line.charAt(i);
						highScoreIntString = highScoreIntString + "" + tempChar;
					}
				}
			}
			// Parses the String to an int.
			highScoreInt = Integer.parseInt(highScoreIntString.trim());
		}
		reader.close();
	}

	// Writes the name of the new high scorer to the text file. Always writes the
	// score in a "Name: Score" format.
	public static void fileWriter() throws Exception {
		fileWriter = new FileWriter("HighScore.txt");
		writer = new BufferedWriter(fileWriter);
		writer.write(winner + ": " + winningTime);
		writer.close();
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("HIYA!");
		// Main Scene and Root
		rootTitle = new Pane();
		mainScene = new Scene(rootTitle, 900, 600);
		primaryStage.setScene(mainScene);
		primaryStage.show();

		// Title
		Text title = new Text("HIYA!");
		title.setFont(Font.font("impact", 80));
		title.setX(365);
		title.setY(200);
		rootTitle.getChildren().add(title);
		// Creator Text
		Text creators = new Text("DESIGNED BY MARCUS UY AND RYAN SETO 2019.");
		creators.setY(590);
		creators.setX(316);
		rootTitle.getChildren().add(creators);

		// Player Name Scene and Root
		// This scene is where players input their name for potential high scoring.
		rootPlayerName = new Pane();
		scenePlayerName = new Scene(rootPlayerName, 900, 600);
		// Start Button (Title Scene to Player Name Scene)
		Button startButton = new Button("START");
		final Label startLabel = new Label();
		startButton.setPrefWidth(165);
		startButton.setPrefHeight(40);
		startButton.setLayoutX(450 - startButton.getPrefWidth() / 2);
		// Puts the center of the object on the pixel. Makes positioning more intuitive.
		startButton.setLayoutY(350 - startButton.getPrefWidth() / 2);
		startLabel.setLayoutX(290);
		startLabel.setLayoutY(290);
		startButton.setStyle("-fx-background-color: #3CAEA3");
		rootTitle.getChildren().add(startButton);
		// Dims the button when hovering over it.
		startButton.setOnMouseEntered(event -> startButton.setStyle("-fx-background-color: #379F95"));
		startButton.setOnMouseExited(event -> startButton.setStyle("-fx-background-color: #3CAEA3"));
		// This event handler switches from the Title Scene to the Player Name Scene
		startButton.setOnAction(event -> {
			primaryStage.setScene(scenePlayerName);
		});
		// Player Name Title
		Text playerNameTitle = new Text("Write your name.");
		playerNameTitle.setFont(Font.font("impact", 40));
		playerNameTitle.setLayoutX(310);
		playerNameTitle.setLayoutY(200);
		rootPlayerName.getChildren().add(playerNameTitle);
		// Player 1 Model
		Rectangle p1Model = new Rectangle(50, 50, Color.CRIMSON);
		p1Model.setX(225 - p1Model.getWidth() / 2);
		p1Model.setY(300 - p1Model.getHeight() / 2);
		p1Model.setStroke(Color.BLACK);
		p1Model.setStrokeWidth(1);
		rootPlayerName.getChildren().add(p1Model);
		Label p1Label = new Label("P1");
		p1Label.setLayoutX(p1Model.getX() + 18);
		p1Label.setLayoutY(p1Model.getY() + 18);
		rootPlayerName.getChildren().add(p1Label);
		// Player 2 Model
		Rectangle p2Model = new Rectangle(50, 50, Color.ROYALBLUE);
		p2Model.setX(675 - p2Model.getWidth() / 2);
		p2Model.setY(300 - p2Model.getHeight() / 2);
		p2Model.setStroke(Color.BLACK);
		p2Model.setStrokeWidth(1);
		rootPlayerName.getChildren().add(p2Model);
		Label p2Label = new Label("P2");
		p2Label.setLayoutX(p2Model.getX() + 18);
		p2Label.setLayoutY(p2Model.getY() + 18);
		rootPlayerName.getChildren().add(p2Label);
		// Player 1 TextField
		p1TF.setPrefColumnCount(10);
		p1TF.setLayoutX(p1Model.getX() - 35);
		p1TF.setLayoutY(p1Model.getY() + 65);
		rootPlayerName.getChildren().add(p1TF);
		// Player 2 TextField
		p2TF.setPrefColumnCount(10);
		p2TF.setLayoutX(p2Model.getX() - 35);
		p2TF.setLayoutY(p2Model.getY() + 65);
		rootPlayerName.getChildren().add(p2TF);
		// Player Name Scene Begin Button (Player Name Scene to Game Scene)
		Button startGameButton = new Button("BEGIN");
		startGameButton.setPrefWidth(165);
		startGameButton.setPrefHeight(40);
		startGameButton.setLayoutX(450 - startGameButton.getPrefWidth() / 2);
		startGameButton.setLayoutY(400 - startGameButton.getPrefHeight() / 2);
		rootPlayerName.getChildren().add(startGameButton);
		// Back Button (Player Name to Title Scene)
		Button backButtonPN = new Button("<-");
		rootPlayerName.getChildren().add(backButtonPN);
		backButtonPN.setPrefWidth(60);
		backButtonPN.setPrefHeight(40);
		backButtonPN.setLayoutX(50 - backButtonPN.getPrefWidth() / 2);
		backButtonPN.setLayoutY(40 - backButtonPN.getPrefWidth() / 2);
		// This event handler switches from the Player Name Scene to the Title Scene
		backButtonPN.setOnAction(event -> primaryStage.setScene(mainScene));
		// Dims the button when hovering over it
		backButtonPN.setStyle("-fx-background-color: #3CAEA3");
		backButtonPN.setOnMouseEntered(event -> backButtonPN.setStyle("-fx-background-color: #379F95"));
		backButtonPN.setOnMouseExited(event -> backButtonPN.setStyle("-fx-background-color: #3CAEA3"));
		startGameButton.setOnAction(event -> {
			// This try catch makes it so if the file cannot be found, no error is printed.
			try {
				fileReader();
			} catch (Exception e) {
				System.out.println("File not found.");
			}
			// Resets the scene. If the Begin Button is spammed, too many error messages for
			// the player are displayed on the scene. This fixes that error.
			rootPlayerName.getChildren().clear();
			rootPlayerName.getChildren().addAll(backButtonPN, p1Model, p1TF, p2Model, p2TF, p1Label, p2Label,
					startGameButton, playerNameTitle);
			// Scenario 1: P1 or P2 have no name.
			if (p1TF.getCharacters().toString().equals(" ") || p1TF.getCharacters().toString().equals("")
					|| p2TF.getCharacters().toString().equals(" ") 
					|| p2TF.getCharacters().toString().equals("")) {
				emptyName = new Label("Please enter a name.");
				emptyName.setLayoutX(397);
				emptyName.setLayoutY(435);
				rootPlayerName.getChildren().add(emptyName);
			}
			// Scenario 2: P1 and P2 have the same name.
			else if (p1TF.getCharacters().toString().equals(p2TF.getCharacters().toString())
					|| p1TF.getCharacters().toString().equals(p2TF.getCharacters().toString() + " ")
					|| p2TF.getCharacters().toString().equals(p1TF.getCharacters().toString() + " ")) {
				sameName = new Label("Please have two unique names.");
				sameName.setLayoutX(367);
				sameName.setLayoutY(435);
				rootPlayerName.getChildren().add(sameName);
			}
			// Scenario 3: P1 has the same name as the high score. Players can then say yes
			// or no.
			else if (p1TF.getCharacters().toString().equals(highScoreName)) {
				samePerson = new Label("P1, are you the same person as the high scorer?");
				// This method is located at the bottom of the code.
				samePersonMethod();
				// The Yes Button event handler. Exact same as Scenario 5.
				yesSamePerson.setOnAction(e -> {
					primaryStage.setScene(sceneGame);
					timerArr.add(new clock());
					rootGame.getChildren().add(timerArr.get(0));
					// This method is located at the bottom of the code.
					beginGame();
				});
				// The No Button event handler.
				noSamePerson.setOnAction(e -> {
					// This method is located at the bottom of the code.
					noButton();
				});

			}
			// Scenario 4: P2 has the same name as the high score.
			else if (p2TF.getCharacters().toString().equals(highScoreName)) {
				samePerson = new Label("P2, are you the same person as the high scorer?");
				// This method is located at the bottom of the code.
				samePersonMethod();
				// The Yes Button event handler. Exacts same as Scenario 5.
				yesSamePerson.setOnAction(e -> {
					primaryStage.setScene(sceneGame);
					timerArr.add(new clock());
					rootGame.getChildren().add(timerArr.get(0));
					// This method is located at the bottom of the code.
					beginGame();
				});
				// The No Button event handler.
				noSamePerson.setOnAction(e -> {
					// This method is located at the bottom of the code.
					noButton();
				});

			}
			// Scenario 5: If none of the above error scenarios are true, then the game can
			// begin.
			else {
				primaryStage.setScene(sceneGame);
				// Creates/Starts the timer.
				timerArr.add(new clock());
				rootGame.getChildren().add(timerArr.get(0));
				// This method is located at the bottom of the code.
				beginGame();
			}
		});

		// High Score Scene and Root
		rootHighScore = new Pane();
		sceneHighScore = new Scene(rootHighScore, 900, 600);
		// High Score Button (Title Scene to High Score).
		Button highscoresButton = new Button("HIGH SCORES");
		rootTitle.getChildren().add(highscoresButton);
		highscoresButton.setPrefWidth(165);
		highscoresButton.setPrefHeight(40);
		highscoresButton.setLayoutX(450 - highscoresButton.getPrefWidth() / 2);
		highscoresButton.setLayoutY(400 - highscoresButton.getPrefWidth() / 2);
		// Dims the button when hovering over it.
		highscoresButton.setStyle("-fx-background-color: #EE4540");
		highscoresButton.setOnMouseEntered(event -> {
			highscoresButton.setStyle("-fx-background-color: #D93F3B");
		});
		highscoresButton.setOnMouseExited(event -> {
			highscoresButton.setStyle("-fx-background-color: #EE4540");
		});
		// This event handler switches from the Title Scene to the High Score Scene
		highscoresButton.setOnAction(event -> primaryStage.setScene(sceneHighScore));
		// High Score Page (Serves as a border for the High Score)
		Rectangle highScorePage = new Rectangle(400, 580);
		highScorePage.setX(250);
		highScorePage.setY(10);
		highScorePage.setStroke(Color.BLACK);
		highScorePage.setFill(Color.WHITE);
		rootHighScore.getChildren().add(highScorePage);
		// High Score Title
		Text highScoreTitle = new Text("HIGHSCORE");
		highScoreTitle.setFont(Font.font("verdana", 40));
		highScoreTitle.setY(55);
		highScoreTitle.setX(310);
		rootHighScore.getChildren().add(highScoreTitle);
		try {
			fileReader();
		} catch (Exception e) {
			System.out.println("File not found.");
		}
		// Checks if there is a high score. The code displays messages accordingly.
		if (highScoreName.equals("") && highScoreIntString.equals("")) {
			noHighScore = new Text("There is currently no high score.");
			noHighScore.setFont(Font.font(18));
			rootHighScore.getChildren().add(noHighScore);
			noHighScore.setLayoutX(265);
			noHighScore.setLayoutY(140);
		} else {
			rootHighScore.getChildren().remove(noHighScore);
			highScoreNameIntro = new Text("The current high scorer belongs to:");
			highScoreNameIntro.setFont(Font.font(18));
			highScoreNameLine = new Text("        " + highScoreName);
			highScoreNameLine.setFont(Font.font(14));
			highScoreIntIntro = new Text("with a score of:");
			highScoreIntIntro.setFont(Font.font(18));
			highScoreIntLine = new Text("        " + highScoreIntString + " seconds.");
			highScoreIntLine.setFont(Font.font(14));
			// High Score VBox to put the text in.
			VBox highScoreText = new VBox(20);
			highScoreText.getChildren().addAll(highScoreNameIntro, highScoreNameLine, highScoreIntIntro,
					highScoreIntLine);
			highScoreText.setLayoutX(265);
			highScoreText.setLayoutY(120);
			rootHighScore.getChildren().add(highScoreText);
		}
		// Back Button (High Score to Title Page).
		Button backButtonHS = new Button("<-");
		rootHighScore.getChildren().add(backButtonHS);
		backButtonHS.setPrefWidth(60);
		backButtonHS.setPrefHeight(40);
		backButtonHS.setLayoutX(50 - backButtonHS.getPrefWidth() / 2);
		backButtonHS.setLayoutY(40 - backButtonHS.getPrefWidth() / 2);
		// This event handler switches from the High Score Scene to the Title Scene.
		backButtonHS.setOnAction(event -> primaryStage.setScene(mainScene));
		// Dims the button when hovering over it.
		backButtonHS.setStyle("-fx-background-color: #3CAEA3");
		backButtonHS.setOnMouseEntered(event -> {
			backButtonHS.setStyle("-fx-background-color: #379F95");
		});
		backButtonHS.setOnMouseExited(event -> {
			backButtonHS.setStyle("-fx-background-color: #3CAEA3");
		});

		// Instructions Scene and Pane
		rootI = new Pane();
		sceneI = new Scene(rootI, 900, 600);
		// Instructions Button (Title Scene to Instructions Scene)
		Button instructionsButton = new Button("INSTRUCTIONS");
		final Label insLabel = new Label();
		rootTitle.getChildren().add(instructionsButton);
		instructionsButton.setPrefWidth(165);
		instructionsButton.setPrefHeight(40);
		instructionsButton.setLayoutX(450 - instructionsButton.getPrefWidth() / 2);
		instructionsButton.setLayoutY(450 - instructionsButton.getPrefWidth() / 2);
		insLabel.setLayoutX(290);
		insLabel.setLayoutY(290);
		instructionsButton.setStyle("-fx-background-color: #3CAEA3");
		instructionsButton.setOnMouseEntered(event -> {
			instructionsButton.setStyle("-fx-background-color: #379F95");
		});
		instructionsButton.setOnMouseExited(event -> {
			instructionsButton.setStyle("-fx-background-color: #3CAEA3");
		});
		// Instructions Button Event Handler
		instructionsButton.setOnAction(event -> primaryStage.setScene(sceneI));
		// Instructions Page (Serves as a border for the Instructions)
		Rectangle instructionsPage = new Rectangle(400, 580);
		instructionsPage.setX(250);
		instructionsPage.setY(10);
		instructionsPage.setStroke(Color.BLACK);
		instructionsPage.setFill(Color.WHITE);
		rootI.getChildren().add(instructionsPage);
		// Instructions Title
		Text instructionsTitle = new Text("INSTRUCTIONS");
		instructionsTitle.setFont(Font.font("verdana", 40));
		instructionsTitle.setX(295);
		instructionsTitle.setY(55);
		rootI.getChildren().add(instructionsTitle);
		// Game Instructions Header
		Text gameInstructions = new Text("Goal of the Game:");
		gameInstructions.setFont(Font.font("verdana", 20));
		gameInstructions.setX(265);
		gameInstructions.setY(110);
		rootI.getChildren().add(gameInstructions);
		// Game Instructions Text
		VBox gameTextInstructions = new VBox(20);
		Text gameTextInstructionsLine1 = new Text();
		gameTextInstructionsLine1.setText("The goal of the game is to eliminate the other player.");
		Text gameTextInstructionsLine2 = new Text();
		gameTextInstructionsLine2.setText("Each player has 3 lives and can shoot a bullet every 2 " 
				+ "seconds.");
		Text gameTextInstructionsLine3 = new Text();
		gameTextInstructionsLine3.setText("High scores are determined by who can win the fastest.");
		gameTextInstructions.setLayoutX(310);
		gameTextInstructions.setLayoutY(120);
		gameTextInstructions.getChildren().addAll(gameTextInstructionsLine1, gameTextInstructionsLine2,
				gameTextInstructionsLine3);
		rootI.getChildren().add(gameTextInstructions);
		// P1 Instructions Header
		Text p1Instructions = new Text("P1:");
		p1Instructions.setFont(Font.font("verdana", 20));
		p1Instructions.setX(265);
		p1Instructions.setY(240);
		rootI.getChildren().add(p1Instructions);
		// P1 Instructions Text
		VBox p1TextInstructions = new VBox(20); // instructions text
		Text p1TextInstructionsLine1 = new Text("Press W to move up.");
		Text p1TextInstructionsLine2 = new Text("Press S to move down.");
		Text p1TextInstructions3 = new Text("Press D to shoot.");
		p1TextInstructions.getChildren().addAll(p1TextInstructionsLine1, p1TextInstructionsLine2, 
				p1TextInstructions3);
		p1TextInstructions.setLayoutX(310);
		p1TextInstructions.setLayoutY(225);
		rootI.getChildren().add(p1TextInstructions);
		// P2 Instructions Header
		Text p2Instructions = new Text("P2:");
		p2Instructions.setFont(Font.font("verdana", 20));
		p2Instructions.setX(265);
		p2Instructions.setY(350);
		rootI.getChildren().add(p2Instructions);
		// P2 Instructions Text
		VBox p2TextInstructions = new VBox(20);
		Text p2TextInstructionsLine1 = new Text("Press UP Arrow to move up.");
		Text p2TextInstructionsLine2 = new Text("Press DOWN Arrow to move down.");
		Text p2TextInstructionsLine3 = new Text("Press LEFT Arrow to shoot.");
		p2TextInstructions.getChildren().addAll(p2TextInstructionsLine1, p2TextInstructionsLine2,
				p2TextInstructionsLine3);
		p2TextInstructions.setLayoutX(310);
		p2TextInstructions.setLayoutY(335);
		rootI.getChildren().add(p2TextInstructions);
		// Other Instructions Header
		Text otherInstructions = new Text("Other:");
		otherInstructions.setFont(Font.font("verdana", 20));
		otherInstructions.setLayoutX(265);
		otherInstructions.setLayoutY(470);
		rootI.getChildren().add(otherInstructions);
		// Other Instructions Text
		VBox otherTextInstructions = new VBox(20);
		Text pauseInstructionsLine1 = new Text("Press P to pause.");
		Text pauseInstructionsLine2 = new Text("There is no 3 second break after resuming the game.");
		// There is no 3 second pause since most games that require focus and reflexes,
		// ie Geometry Dash and Smash Bros., do not grant a buffer time to discourage
		// pausing.
		otherTextInstructions.getChildren().addAll(pauseInstructionsLine1, pauseInstructionsLine2);
		otherTextInstructions.setLayoutX(310);
		otherTextInstructions.setLayoutY(480);
		rootI.getChildren().add(otherTextInstructions);
		// Back Button (Instructions Scene to Title Scene).
		Button backButtonI = new Button("<-");
		rootI.getChildren().add(backButtonI);
		backButtonI.setPrefWidth(60);
		backButtonI.setPrefHeight(40);
		backButtonI.setLayoutX(50 - backButtonI.getPrefWidth() / 2);
		backButtonI.setLayoutY(40 - backButtonI.getPrefWidth() / 2);
		backButtonI.setOnAction(event -> primaryStage.setScene(mainScene));
		// Dims the button when hovering over it.
		backButtonI.setStyle("-fx-background-color: #3CAEA3");
		backButtonI.setOnMouseEntered(event -> {
			backButtonI.setStyle("-fx-background-color: #379F95");
		});
		backButtonI.setOnMouseExited(event -> {
			backButtonI.setStyle("-fx-background-color: #3CAEA3");
		});

		// Game Scene and Root.
		rootGame = new Pane();
		sceneGame = new Scene(rootGame, 900, 600);
		// Key Numbers
		int mapCenter = 250;
		int screenCenter = (int) sceneGame.getWidth() / 2;
		int p1Center = 100;
		int p2Center = 800;
		// Map Location
		map.setX(screenCenter - map.getWidth() / 2);
		map.setY(mapCenter - map.getHeight() / 2);
		rootGame.getChildren().add(map);
		map.setFill(Color.TRANSPARENT);
		map.setStroke(Color.BLACK);
		map.setStrokeWidth(2);
		// Player 1 Start Position
		p1.setX(p1Center - p1.getWidth() / 2);
		p1.setY(mapCenter - p1.getHeight() / 2);
		rootGame.getChildren().add(p1);
		p1.setFill(Color.CRIMSON);
		p1.setStroke(Color.BLACK);
		p1.setStrokeWidth(1);
		// Player 1 Name Position
		p1Name.setPrefWidth(98);
		p1Name.setPrefHeight(30);
		p1Name.setLayoutX(map.getX() - p1Name.getPrefWidth() - 3);
		p1Name.setLayoutY(map.getY() + map.getHeight() + 5);
		rootGame.getChildren().add(p1Name);
		Rectangle p1NameBorder = new Rectangle(p1Name.getPrefWidth(), p1Name.getPrefHeight());
		p1NameBorder.setX(p1Name.getLayoutX() - 2);
		p1NameBorder.setY(p1Name.getLayoutY());
		p1NameBorder.setFill(Color.TRANSPARENT);
		p1NameBorder.setStroke(Color.BLACK);
		p1NameBorder.setStrokeWidth(1);
		rootGame.getChildren().add(p1NameBorder);
		// Player 2 Start Position
		p2.setX(p2Center - p2.getWidth() / 2);
		p2.setY(mapCenter - p2.getHeight() / 2);
		rootGame.getChildren().add(p2);
		p2.setFill(Color.ROYALBLUE);
		p2.setStroke(Color.BLACK);
		p2.setStrokeWidth(1);
		// Player 2 Name Position
		p2Name.setPrefWidth(98);
		p2Name.setPrefHeight(30);
		p2Name.setLayoutX(map.getX() + map.getWidth() + 7);
		p2Name.setLayoutY(map.getY() + map.getHeight() + 5);
		rootGame.getChildren().add(p2Name);
		Rectangle p2NameBorder = new Rectangle(p2Name.getPrefWidth(), p2Name.getPrefHeight());
		p2NameBorder.setX(p2Name.getLayoutX() - 2);
		p2NameBorder.setY(p2Name.getLayoutY());
		p2NameBorder.setFill(Color.TRANSPARENT);
		p2NameBorder.setStroke(Color.BLACK);
		p2NameBorder.setStrokeWidth(1);
		rootGame.getChildren().add(p2NameBorder);

		// Lives locations.
		// P1 Life 1.
		p1Lives1.setX(map.getX());
		p1Lives1.setY(map.getY() + map.getHeight() + 5);
		p1Lives1.setStroke(Color.BLACK);
		p1Lives1.setStrokeWidth(1);
		rootGame.getChildren().add(p1Lives1);
		// P1 Life 2.
		p1Lives2.setX(p1Lives1.getX() + p1Lives2.getWidth() + 5);
		p1Lives2.setY(map.getY() + map.getHeight() + 5);
		p1Lives2.setStroke(Color.BLACK);
		p1Lives2.setStrokeWidth(1);
		rootGame.getChildren().add(p1Lives2);
		// P1 Life 3.
		p1Lives3.setX(p1Lives2.getX() + p1Lives3.getWidth() + 5);
		p1Lives3.setY(map.getY() + map.getHeight() + 5);
		p1Lives3.setStroke(Color.BLACK);
		p1Lives3.setStrokeWidth(1);
		rootGame.getChildren().add(p1Lives3);
		// P2 Life 1.
		p2Lives1.setX(map.getX() + map.getWidth() - p2Lives1.getWidth());
		p2Lives1.setY(map.getY() + map.getHeight() + 5);
		p2Lives1.setStroke(Color.BLACK);
		p2Lives1.setStrokeWidth(1);
		rootGame.getChildren().add(p2Lives1);
		// P2 Life 2.
		p2Lives2.setX(p2Lives1.getX() - p2Lives2.getWidth() - 5);
		p2Lives2.setY(map.getY() + map.getHeight() + 5);
		p2Lives2.setFill(Color.GOLD);
		p2Lives2.setStroke(Color.BLACK);
		p2Lives2.setStrokeWidth(1);
		rootGame.getChildren().add(p2Lives2);
		// P2 Life 3.
		p2Lives3.setX(p2Lives2.getX() - p2Lives3.getWidth() - 5);
		p2Lives3.setY(map.getY() + map.getHeight() + 5);
		p2Lives3.setStroke(Color.BLACK);
		p2Lives3.setStrokeWidth(1);
		rootGame.getChildren().add(p2Lives3);
		// P1 Recharge Bar. This is the frame for the recharge bar to fill.
		p1BulletRecharge.setX(map.getX());
		p1BulletRecharge.setY(p1Lives1.getY() + p1Lives1.getHeight() + 5);
		p1BulletRecharge.setStroke(Color.BLACK);
		p1BulletRecharge.setStrokeWidth(1);
		rootGame.getChildren().add(p1BulletRecharge);
		// P2 Recharge Bar. This is the frame for the recharge bar to fill.
		p2BulletRecharge.setX(p2Lives3.getX());
		p2BulletRecharge.setY(p2Lives3.getY() + p2Lives3.getHeight() + 5);
		p2BulletRecharge.setStroke(Color.BLACK);
		p2BulletRecharge.setStrokeWidth(1);
		rootGame.getChildren().add(p2BulletRecharge);

		// Key Pressed event handler.
		sceneGame.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			// Move Up (P1) Event Handler
			// Each event handler turns a movement boolean to true. That boolean is set to
			// false when the key is released.
			if (event.getCode() == KeyCode.W) {
				// Only allows players to move if both characters are alive and the game is not
				// paused.
				if ((p1Lives != 0 && p2Lives != 0) && !pause) {
					p1MoveUp = true;
				}
			}
			// Move Down (P1) Event Handler
			if (event.getCode() == KeyCode.S) {
				if ((p1Lives != 0 && p2Lives != 0) && !pause) {
					p1MoveDown = true;
				}
			}
			// Move Up (P2) Event Handler
			if (event.getCode() == KeyCode.UP) {
				if ((p1Lives != 0 && p2Lives != 0) && !pause) {
					p2MoveUp = true;
				}
			}
			// Move Down (P2) Event Handler
			if (event.getCode() == KeyCode.DOWN) {
				if ((p1Lives != 0 && p2Lives != 0) && !pause) {
					p2MoveDown = true;
				}
			}
			// Player 1 Bullet Event Handler
			if (event.getCode() == KeyCode.D) {
				// Try catch is so that when the player hits the button before the recharge is
				// complete, an error that says "duplicate children" does not occur.
				try {
					if ((p1Lives != 0 && p2Lives != 0) && !pause) {
						p1Bullet();
						if (p1Ammo == false) {
							p1BulletRecharge();
							rootGame.getChildren().add(p1AmmoFill);
						}
					}
				} catch (IllegalArgumentException e) {
					System.out.println("P1 is recharging.");
				}

			}
			// Player 2 Bullet Event Handler.
			if (event.getCode() == KeyCode.LEFT) {
				try {
					if ((p1Lives != 0 && p2Lives != 0) && !pause) {
						p2Bullet();
						if (p2Ammo == false) {
							p2BulletRecharge();
							rootGame.getChildren().add(p2AmmoFill);
						}
					}
				} catch (IllegalArgumentException e) {
					System.out.println("P2 is recharging.");
				}
			}

			// Pause Button.
			if (event.getCode() == KeyCode.P) {
				if (pause == false) {
					pause = true;
					gameLoop.stop();
					// Freezes all moving objects in place.
					p1BulletTransition.pause();
					p2BulletTransition.pause();
					p1RechargeTransition.pause();
					p2RechargeTransition.pause();
					// Displays pause menu.
					pauseText.setFont(Font.font("impact", 80));
					pauseText.setX(350);
					pauseText.setY(200);
					rootGame.getChildren().add(pauseText);
					pauseContinue.setPrefWidth(125);
					pauseContinue.setPrefHeight(75);
					pauseContinue.setLayoutX(300 - pauseContinue.getPrefWidth() / 2);
					pauseContinue.setLayoutY(325 - pauseContinue.getPrefWidth() / 2);
					rootGame.getChildren().add(pauseContinue);
					pauseExit.setPrefWidth(125);
					pauseExit.setPrefHeight(75);
					pauseExit.setLayoutX(600 - pauseExit.getPrefWidth() / 2);
					pauseExit.setLayoutY(325 - pauseExit.getPrefWidth() / 2);
					rootGame.getChildren().add(pauseExit);
					timer.pause();
				} else if (pause == true) {
					// Please see the method at the bottom of the code.
					continueGame();
				}
			}
		});
		// Continue Button event handler, same logic as clicking P again.
		pauseContinue.setOnAction(event -> {
			// Please see the method at the bottom of the code.
			continueGame();
		});
		// Exit Button event handler.
		pauseExit.setOnAction(event -> {
			gameReset();
			primaryStage.setScene(mainScene);
		});
		// Key Released event handler.
		sceneGame.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
			// Move Up (P1) Event Handler.
			// Sets the movement boolean to false.
			if (event.getCode() == KeyCode.W) {
				if ((p1Lives != 0 && p2Lives != 0) && !pause) {
					p1MoveUp = false;
				}
			}
			// Move Down (P1) Event Handler.
			if (event.getCode() == KeyCode.S) {
				if ((p1Lives != 0 && p2Lives != 0) && !pause) {
					p1MoveDown = false;
				}
			}
			// Move Up (P2) Event Handler.
			if (event.getCode() == KeyCode.UP) {
				if ((p1Lives != 0 && p2Lives != 0) && !pause) {
					p2MoveUp = false;
				}
			}
			// Move Down (P2) Event Handler.
			if (event.getCode() == KeyCode.DOWN) {
				if ((p1Lives != 0 && p2Lives != 0) && !pause) {
					p2MoveDown = false;
				}
			}
		});

		// Animation Timer/Game Loop.
		gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				// P1 Collision Detection.
				if (p1.getBoundsInParent().intersects(p2Bullet.getBoundsInParent())) {
					rootGame.getChildren().remove(p2Bullet);
					// This method sets the X, Y, TranslateX, and TranslateY to 0.
					p2BulletReset();
					p1Lives--;
				}
				// P2 Collision Detection.
				if (p2.getBoundsInParent().intersects(p1Bullet.getBoundsInParent())) {
					rootGame.getChildren().remove(p1Bullet);
					p1BulletReset();
					p2Lives--;
				}

				// P1 Bullet Disappear Check.
				if (p1Bullet.getTranslateX() == p1DesiredX) {
					rootGame.getChildren().remove(p1Bullet);
					p1BulletReset();
				}
				// P2 Bullet Disappear Check.
				if (p2Bullet.getTranslateX() == p2DesiredX) {
					rootGame.getChildren().remove(p2Bullet);
					p2BulletReset();
				}

				// Bullet Collision (With Each Other).
				if (p1Bullet.getBoundsInParent().intersects(p2Bullet.getBoundsInParent())
						&& p1Bullet.getTranslateX() != 0.0) {
					rootGame.getChildren().remove(p1Bullet);
					p1BulletReset();
					rootGame.getChildren().remove(p2Bullet);
					p2BulletReset();
				}

				// P1 Lives Check.
				if (p1Lives == 2) {
					rootGame.getChildren().remove(p1Lives3);
				} else if (p1Lives == 1) {
					rootGame.getChildren().remove(p1Lives2);
				} else if (p1Lives == 0) {
					rootGame.getChildren().remove(p1Lives1);
				}
				// P2 Lives Check.
				if (p2Lives == 2) {
					rootGame.getChildren().remove(p2Lives3);
				} else if (p2Lives == 1) {
					rootGame.getChildren().remove(p2Lives2);
				} else if (p2Lives == 0) {
					rootGame.getChildren().remove(p2Lives1);
				}

				// P1 Bullet Recharge Animation.
				// Please read the p1BulletRecharge method at the bottom first.
				// Updates the width of the AmmoFill to the distance the AmmoLine has traveled.
				p1AmmoFill.setWidth(p1AmmoLine.getTranslateX());
				// If the bar is full, removes the AmmoFill and AmmoLine.
				if (p1AmmoFill.getWidth() == p1BulletRecharge.getWidth()) {
					rootGame.getChildren().remove(p1AmmoFill);
					p1AmmoLine.setStartX(p1BulletRecharge.getX());
					p1AmmoLine.setEndX(p1AmmoLine.getStartX());
					rootGame.getChildren().remove(p1AmmoLine);
					p1Ammo = true;
				}
				// P2 Bullet Recharge Animation.
				// Please read the p2BulletRecharge method at the bottom first.
				// Updates the width of the AmmoFill to the distance the AmmoLine has traveled.
				p2AmmoFill.setWidth(Math.abs(p2AmmoLine.getTranslateX()));
				// Updates the X of the AmmoFill to the X of the line.
				p2AmmoFill.setX(p2BulletRecharge.getX() + p2BulletRecharge.getWidth() 
					+ p2AmmoLine.getTranslateX());
				// Removes AmmoFill and AmmoLine is the recharge bar is full.
				if (p2AmmoFill.getWidth() == p2BulletRecharge.getWidth()) {
					rootGame.getChildren().remove(p2AmmoFill);
					p2AmmoLine.setStartX(p2BulletRecharge.getX() + p2BulletRecharge.getWidth());
					p2AmmoLine.setEndX(p2AmmoLine.getStartX());
					rootGame.getChildren().remove(p2AmmoLine);
					p2Ammo = true;
				}

				// P1 Move Up.
				p1NewY = p1.getY() - 8;
				// This if makes sure that the player cannot move outside the frame of the map.
				if (p1NewY > map.getY() && p1NewY < map.getY() + map.getHeight() - p1.getHeight()) {
					if (p1MoveUp) {
						p1.setY(p1NewY);
					}
				}
				// P1 Move Down.
				p1NewY = p1.getY() + 8;
				if (p1NewY > map.getY() && p1NewY < map.getY() + map.getHeight() - p1.getHeight()) {
					if (p1MoveDown) {
						p1.setY(p1NewY);
					}
				}
				// P2 Move Up.
				p2NewY = p2.getY() - 8;
				if (p2NewY > map.getY() && p2NewY < map.getY() + map.getHeight() - p2.getHeight()) {
					if (p2MoveUp) {
						p2.setY(p2NewY);
					}
				}
				// P2 Move Down.
				p2NewY = p2.getY() + 8;
				if (p2NewY > map.getY() && p2NewY < map.getY() + map.getHeight() - p2.getHeight()) {
					if (p2MoveDown) {
						p2.setY(p2NewY);
					}
				}

				// Game Over State.
				if (p1Lives == 0 || p2Lives == 0) {
					gameLoop.stop();
					// Freezes all moving objects.
					p1BulletTransition.pause();
					p2BulletTransition.pause();
					p1RechargeTransition.pause();
					p2RechargeTransition.pause();
					timer.stop();
					// Displays the Game Over Button.
					gameOver.setPrefWidth(400);
					gameOver.setPrefHeight(100);
					gameOver.setLayoutX(450 - gameOver.getPrefWidth() / 2);
					gameOver.setLayoutY(mapCenter - gameOver.getPrefHeight() / 2);
					rootGame.getChildren().add(gameOver);
					// Determines the winner.
					if (p1Lives == 0) {
						winner = p2NameInput;
					} else if (p2Lives == 0) {
						winner = p1NameInput;
					}
					try {
						fileReader();
					} catch (Exception e) {
						System.out.println("File not found.");
					}
					// Determines if the winner has beaten the high score.
					if (winningTime < highScoreInt || highScoreInt == 0) {
						try {
							// Writes the winner and their score to the text file.
							fileWriter();
							// Reads the file to get the new high score.
							fileReader();
						} catch (Exception e) {
							System.out.println("File not found.");
						}
					}
					// Updates the high score page.
					rootHighScore.getChildren().clear();
					rootHighScore.getChildren().addAll(backButtonHS, highScorePage, highScoreTitle);
					rootHighScore.getChildren().remove(noHighScore);
					highScoreNameIntro = new Text("The current high scorer belongs to:");
					highScoreNameIntro.setFont(Font.font(18));
					highScoreNameLine = new Text("        " + highScoreName);
					highScoreNameLine.setFont(Font.font(14));
					highScoreIntIntro = new Text("with a score of:");
					highScoreIntIntro.setFont(Font.font(18));
					highScoreIntLine = new Text("        " + highScoreIntString + " seconds.");
					highScoreIntLine.setFont(Font.font(14));
					// High Score VBox to put the text in.
					VBox highScoreText = new VBox(20);
					highScoreText.getChildren().addAll(highScoreNameIntro, highScoreNameLine,
							highScoreIntIntro, highScoreIntLine);
					highScoreText.setLayoutX(265);
					highScoreText.setLayoutY(120);
					rootHighScore.getChildren().add(highScoreText);
					// This event handler closes/resets the game and returns to the main screen.
					gameOver.setOnAction(event -> {
						gameReset();
						primaryStage.setScene(mainScene);
					});
				}
			}
		};
	}

	// Same Name as High Scorer Method
	// Displays a "menu" that asks the player if they are the same
	// person as the high scorer. Players can say "Yes" or "No".
	public static void samePersonMethod() {
		samePerson.setLayoutX(325);
		samePerson.setLayoutY(435);
		rootPlayerName.getChildren().add(samePerson);
		yesSamePerson = new Button("Yes?");
		yesSamePerson.setPrefWidth(50);
		yesSamePerson.setPrefHeight(10);
		yesSamePerson.setLayoutX(400 - yesSamePerson.getPrefWidth() / 2);
		yesSamePerson.setLayoutY(500 - yesSamePerson.getPrefWidth() / 2);
		rootPlayerName.getChildren().add(yesSamePerson);
		noSamePerson = new Button("No?");
		noSamePerson.setPrefWidth(50);
		noSamePerson.setPrefHeight(10);
		noSamePerson.setLayoutX(500 - noSamePerson.getPrefWidth() / 2);
		noSamePerson.setLayoutY(500 - noSamePerson.getPrefWidth() / 2);
		rootPlayerName.getChildren().add(noSamePerson);
		rootPlayerName.getChildren().remove(emptyName);
		rootPlayerName.getChildren().remove(sameName);
		rootPlayerName.getChildren().remove(changeName);
	}

	// Same Name As High Scorer No Button Method
	// Removes the Yes Button, No Button, and the Error Message
	// for the player, displays new message.
	public static void noButton() {
		rootPlayerName.getChildren().remove(samePerson);
		rootPlayerName.getChildren().remove(yesSamePerson);
		rootPlayerName.getChildren().remove(noSamePerson);
		changeName = new Label("Please change your name.");
		changeName.setLayoutX(380);
		changeName.setLayoutY(435);
		rootPlayerName.getChildren().add(changeName);
	}

	// Begin Game Method
	// Starts the game loop, clears any Player Name Errors Messages,
	// sets Player Names.
	public static void beginGame() {
		gameLoop.start();
		p1NameInput = p1TF.getCharacters().toString();
		p1Name.setText(p1NameInput);
		p2NameInput = p2TF.getCharacters().toString();
		p2Name.setText(p2NameInput);
		rootPlayerName.getChildren().remove(emptyName);
		rootPlayerName.getChildren().remove(sameName);
		rootPlayerName.getChildren().remove(samePerson);
		rootPlayerName.getChildren().remove(changeName);
		rootPlayerName.getChildren().remove(yesSamePerson);
		rootPlayerName.getChildren().remove(noSamePerson);
	}

	// Continue Game Method (From Pause)
	// This code turns pause to false and restarts the gameLoop. Afterwards, the
	// animations continue where they left off. Then the pause menu is removed.
	public static void continueGame() {
		pause = false;
		gameLoop.start();
		p1BulletTransition.play();
		p2BulletTransition.play();
		p1RechargeTransition.play();
		p2RechargeTransition.play();
		// Removes pause menu.
		rootGame.getChildren().remove(pauseText);
		rootGame.getChildren().remove(pauseContinue);
		rootGame.getChildren().remove(pauseExit);
		timer.play();
	}

	// P1 Bullet Method
	// Creates the bullet and moves it from Player 1's Y coordinate to Player 2's Y
	// coordinate.
	public static void p1Bullet() {
		if (p1Ammo) {
			// Sets the bullet to leave from the center of the player.
			p1Bullet.setX(p1.getX() + p1.getWidth() / 2 - p1Bullet.getWidth() / 2);
			p1Bullet.setY(p1.getY() + p1.getHeight() / 2 - p1Bullet.getHeight() / 2);
			rootGame.getChildren().add(p1Bullet);
			p1BulletTransition.setDuration(Duration.millis(500));
			p1BulletTransition.setNode(p1Bullet);
			p1BulletTransition.setToX(p1DesiredX);
			p1BulletTransition.play();
		}
		p1Ammo = false;
	}

	// P2 Bullet Method
	// Creates the bullet and moves it from Player 2's Y coordinate to Player 1's Y
	// coordinate.
	public static void p2Bullet() {
		if (p2Ammo) {
			// Sets the bullet to leave from the center of the player.
			p2Bullet.setX(p2.getX() + p2.getWidth() / 2 - p2Bullet.getWidth() / 2);
			p2Bullet.setY(p2.getY() + p2.getHeight() / 2 - p2Bullet.getHeight() / 2);
			rootGame.getChildren().add(p2Bullet);
			p2BulletTransition.setDuration(Duration.millis(500));
			p2BulletTransition.setNode(p2Bullet);
			p2BulletTransition.setToX(p2DesiredX);
			p2BulletTransition.play();
		}
		p2Ammo = false;
	}

	// P1 Bullet Reset Position
	public static void p1BulletReset() {
		p1Bullet.setX(0);
		p1Bullet.setY(0);
		p1Bullet.setTranslateX(0);
		p1Bullet.setTranslateY(0);
	}

	// P2 Bullet Reset Position
	public static void p2BulletReset() {
		p2Bullet.setX(0);
		p2Bullet.setY(0);
		p2Bullet.setTranslateX(0);
		p2Bullet.setTranslateY(0);
	}

	// P1 Bullet Recharge Bar Method
	// Initializes AmmoFill and AmmoLine. AmmoLine moves from the beginning to the
	// end of the Recharge Bar. From here, the gameLoop updates AmmoFill to the
	// TranslateX() of AmmoLine.
	public static void p1BulletRecharge() {
		p1AmmoFill.setX(p1BulletRecharge.getX());
		p1AmmoFill.setY(p1BulletRecharge.getY());
		p1AmmoFill.setTranslateX(0);
		p1AmmoFill.setTranslateY(0);
		p1AmmoLine.setTranslateX(0);
		p1AmmoLine.setStartX(150);
		p1AmmoLine.setEndX(150);
		p1RechargeTransition.setDuration(Duration.millis(2000));
		p1RechargeTransition.setNode(p1AmmoLine);
		p1RechargeTransition.setToX(p1BulletRecharge.getWidth());
		p1RechargeTransition.play();
	}

	// P2 Bullet Recharge Bar Method
	// Same logic as P1 Bullet Recharge with the added feature that TranslateX() is
	// negative since the AmmoLine moves left.
	public static void p2BulletRecharge() {
		p2AmmoFill.setX(p2BulletRecharge.getX() + p2BulletRecharge.getWidth() - p2AmmoFill.getWidth());
		p2AmmoFill.setY(p2BulletRecharge.getY());
		p2AmmoFill.setTranslateX(0);
		p2AmmoFill.setTranslateY(0);
		p2AmmoLine.setTranslateX(0);
		p2AmmoLine.setStartX(750);
		p2AmmoLine.setEndX(750);
		p2RechargeTransition.setDuration(Duration.millis(2000));
		p2RechargeTransition.setNode(p2AmmoLine);
		p2RechargeTransition.setToX(-p2BulletRecharge.getWidth());
		p2RechargeTransition.play();
	}

	// Resets Game.
	public static void gameReset() {
		// Removes the Pause Menu/Game Over menu, which ever is active.
		rootGame.getChildren().remove(pauseText);
		rootGame.getChildren().remove(pauseContinue);
		rootGame.getChildren().remove(pauseExit);
		rootGame.getChildren().remove(gameOver);
		// "Deletes" the timer.
		rootGame.getChildren().remove(timerArr.get(0));
		timerArr.remove(0);
		// Resets Player starting positions.
		p1.setY(250);
		p2.setY(250);
		// Resets movement booleans.
		p1MoveUp = false;
		p1MoveDown = false;
		p2MoveUp = false;
		p2MoveDown = false;
		// Resets all animations (Bullets and Recharge Bars).
		p1BulletTransition.stop();
		rootGame.getChildren().remove(p1Bullet);
		p1BulletReset();
		p2BulletTransition.stop();
		rootGame.getChildren().remove(p2Bullet);
		p2BulletReset();
		p1RechargeTransition.stop();
		rootGame.getChildren().remove(p1AmmoFill);
		p2RechargeTransition.stop();
		rootGame.getChildren().remove(p2AmmoFill);
		p1AmmoFill.setX(p1BulletRecharge.getX());
		p2AmmoFill.setX(p2BulletRecharge.getX() + p2BulletRecharge.getWidth());
		p1Ammo = true;
		p2Ammo = true;
		// Resets lives.
		if (p1Lives == 0) {
			rootGame.getChildren().add(p1Lives1);
			rootGame.getChildren().add(p1Lives2);
			rootGame.getChildren().add(p1Lives3);
			p1Lives = 3;
		} else if (p1Lives == 1) {
			rootGame.getChildren().add(p1Lives2);

		} else if (p1Lives == 2) {
			rootGame.getChildren().add(p1Lives3);
			p1Lives = 3;
		}
		if (p2Lives == 0) {
			rootGame.getChildren().add(p2Lives1);
			rootGame.getChildren().add(p2Lives2);
			rootGame.getChildren().add(p2Lives3);
			p2Lives = 3;
		} else if (p2Lives == 1) {
			rootGame.getChildren().add(p2Lives2);
			rootGame.getChildren().add(p2Lives3);
			p2Lives = 3;
		} else if (p2Lives == 2) {
			rootGame.getChildren().add(p2Lives3);
			p2Lives = 3;
		}
		pause = false;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
