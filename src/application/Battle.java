package application;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import application.Board.Cell;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//Initialize Variables.
/**
 * 
 * This is the Main Class which will help us to play the game . Method which will startGame , Create Boards
 * and Many More
 * @author K3
 *
 */

public class Battle extends Application {

	private boolean executing = false;

	private Board opponentBoard, firstPlayerBoard;

	private int numberOfShips = 5;

	private boolean opponentTurn = false;

	private Random random = new Random();

	private Button st = new Button("START");

	private Button reset = new Button("RESET");
	
	private Button pause = new Button("PAUSE");
	
	private Button load = new Button("LOAD");
	
	

	
	/**
	 * Adding styles and layout to the output screen i.e titles ,grid layout,mouse effects etc.
	 * @param personStage
	 * @param background
	 * @return
	 */
	private Parent designBoard(Stage personStage, Background background) {

		
		
		BorderPane root = new BorderPane();
		root.setPrefSize(1300, 800);
		
		//Setting the title for the game.

		Text battle = new Text();
		battle.setText("BATTLESHIP GAME");
		battle.setFill(Color.BLACK);
		battle.setStrokeWidth(2);
		battle.setStroke(Color.WHITE);
		battle.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		battle.setX(500);
		battle.setY(30);
		battle.setUnderline(true);
		
		//giving heading to the grid1 i.e player 1 grid

		Text player1 = new Text();
		player1.setText("PLAYER 1 GRID");
		player1.setX(300);
		player1.setY(100);
		player1.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		
		//giving heading to the grid2 i.e opponent grid

		Text Opponent = new Text();
		Opponent.setText("OPPONENT GRID");
		Opponent.setX(800);
		Opponent.setY(100);
		Opponent.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		
		//Create HBox i.e icons to specify different functionalities in game like start,reset,pause and load. 

		HBox actions = new HBox(30, st, reset,pause,load);
		actions.setAlignment(Pos.CENTER);
		buttonGeometry();
		
		actions.setTranslateX(700);
		actions.setTranslateY(750);
		
		//adding all functionalities in root.

		root.getChildren().add(battle);
		root.getChildren().add(player1);
		root.getChildren().add(Opponent);
		root.getChildren().add(actions);
		// root.getChildren().add(actions);
		
		
		// Mouse event for opponent

		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if (!executing)
					return;

				Cell cell = (Cell) e.getSource();
				if (cell.targetHit)
					return;

				opponentTurn = !cell.shoot();

				if (opponentBoard.amountOfships == 0) {
					
					String s ="You Won This Game";
					
					finalResultDisplay(s);

					//ShowResult(personStage, "YOU WIN :) ");
					// System.out.println("YOU WIN");
					// System.exit(0);
				}

				if (opponentTurn)
					opponentMove(personStage);
			}

		};

		
		//Mouse event for player 1
		EventHandler<MouseEvent> playerEvent = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if (executing)
					return;

				Cell cell = (Cell) e.getSource();
				if (firstPlayerBoard.positionShip(new Ship(numberOfShips, e.getButton() == MouseButton.PRIMARY),
						cell.row, cell.col)) {
					--numberOfShips;
					
				}
			}

		};

		opponentBoard = new Board(true, event);

		firstPlayerBoard = new Board(false, playerEvent);

		HBox hbox = new HBox(100, firstPlayerBoard, opponentBoard);
		firstPlayerBoard.setLayoutX(250);
		firstPlayerBoard.setLayoutY(120);
		opponentBoard.setLayoutX(750);
		opponentBoard.setLayoutY(120);

		root.getChildren().add(firstPlayerBoard);
		root.getChildren().add(opponentBoard);
		root.setBackground(background);

		return root;
	}

	/**
	 * setting styling effects for different buttons like pause,start,rest,load on output screen
	 */
	private void buttonGeometry() {
		// TODO Auto-generated method stub
		st.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
		reset.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
		pause.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
		load.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
		
		
		st.setMinHeight(80);
		st.setMinWidth(150);
		reset.setMinHeight(80);
		reset.setMinWidth(150);
		pause.setMinHeight(80);
		pause.setMinWidth(150);
		load.setMinHeight(80);
		load.setMinWidth(150);
	}

	/**
	 * This method is AI which will detect the move on Player 1 Board.
	 * @param personStage
	 */
	private void opponentMove(Stage personStage) {
		while (opponentTurn) {
			int x = random.nextInt(10);
			int y = random.nextInt(10);

			Cell cell = firstPlayerBoard.getCell(x, y);
			if (cell.targetHit)
				continue;

			opponentTurn = cell.shoot();

			if (firstPlayerBoard.amountOfships == 0) {

				String s ="You Lost This Game to the Computer";
				finalResultDisplay(s);

				// ShowResult(personStage, "YOU LOSE :( ");
				
			}
		}
	}

	/**
	 * This method will Display the final result on the pop showing who the winner.
	 * @param s
	 */
	private void finalResultDisplay(String s) {
		// TODO Auto-generated method stub
		Alert opponentWin = new Alert(AlertType.INFORMATION);
		opponentWin.setTitle("ALERT");
		opponentWin.setHeaderText("WINNER ANNOUCEMENT");
		
		opponentWin.setContentText(s);
		opponentWin.show();
	}

	
	/**
	 * This method will Start the Game once the Player Click the start button and Player 1 has set up 
	 * all his ships.
	 */
	private void startGame() {
		// place enemy ships
		int type = 5;

		while (type > 0) {
			int x = random.nextInt(10);
			int y = random.nextInt(10);

			if (opponentBoard.positionShip(new Ship(type, Math.random() < 0.5), x, y)) {
				type--;
			}
		}

		executing = true;
	}

	/**
	 * This Method setup the Stage ,Scene and Design Boards on the Scene.   
	 * @param primaryStage
	 */
	private void intialise(Stage primaryStage) {
		File n = new File(".");
		String path = null;
		try {
			path = n.getCanonicalFile() + "/ship.png";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = new Image("file:///" + path);

		BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

		Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size));

		Scene scene = new Scene(designBoard(primaryStage, background));
		primaryStage.setTitle("Battleship");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);

		st.setOnAction(e -> {
			if (numberOfShips == 0)
				startGame();
		});

		reset.setOnAction(e -> {
			restart(primaryStage);
		});
		
		
		
		
		primaryStage.show();
	}

	/**
	 * Method will reset the Game by intitalising all the related Nodes.
	 * @param primaryStage
	 */
	private void restart(Stage primaryStage) {
		// TODO Auto-generated method stub
		// System.out.println("Restarting app!");
		primaryStage.close();
		Platform.runLater(() -> {
			try {
				new Battle().start(new Stage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	/**
	 * This Method Will call the Initialise to set up the Stage
	 * {@inheritDoc}
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		intialise(primaryStage);

	}

	/**
	 * Launches the application
	 * @param args-takes the default Arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
