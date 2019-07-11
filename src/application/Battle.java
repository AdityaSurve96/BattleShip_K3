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
import javafx.scene.control.SplitPane;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * @author admin
 *
 */


// Handles Scene initialization and design,  and the  Mouse events with  player and opponent moves.




public class Battle extends Application {

	private boolean executing = false;

	private Board opponentBoard, firstPlayerBoard;

	private int numberOfShips = 5;

	private boolean opponentTurn = false;

	private Random random = new Random();
	private Button temp = new Button("TEMP");

	private Button st = new Button("START");

	private Button reset = new Button("RESET");
	
	private Button pause = new Button("PAUSE");
	
	private Button load = new Button("LOAD");

	private Button exit	= new Button("EXIT");
/**
 * 
 * @param personStage
 * @param background
 * @return
 */
	private Parent designBoard(Stage personStage, Background background) {

		
		
		BorderPane root = new BorderPane();
		root.setPrefSize(1300, 800);

		Text battle = new Text();
		battle.setText("BATTLESHIP GAME");
		battle.setFill(Color.BLACK);
		battle.setStrokeWidth(2);
		battle.setStroke(Color.WHITE);
		battle.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		battle.setX(500);
		battle.setY(30);
		battle.setUnderline(true);

		Text player1 = new Text();
		player1.setText("PLAYER 1 GRID");
		player1.setX(300);
		player1.setY(100);
		player1.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

		Text Opponent = new Text();
		Opponent.setText("OPPONENT GRID");
		Opponent.setX(800);
		Opponent.setY(100);
		Opponent.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

		HBox actions = new HBox(30, st, reset,pause,load,exit);
		actions.setAlignment(Pos.CENTER);

		//st.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
		buttonGeometry();
		//reset.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
		//pause.setMinSize(Button.USE_PREF_SIZE,Button.USE_PREF_SIZE);
		//load.setMinSize(Button.USE_PREF_SIZE,Button.USE_PREF_SIZE);
		
		actions.setTranslateX(650);
		actions.setTranslateY(750);
		
		//Adding rectangles as ships near PLayerBoard
		
//		Rectangle rectangle = new Rectangle(x, y, width, height);

		root.getChildren().add(battle);
		root.getChildren().add(player1);
		root.getChildren().add(Opponent);
		root.getChildren().add(actions);
		// root.getChildren().add(actions);

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
					Alert playerWin = new Alert(AlertType.INFORMATION);
					playerWin.setTitle("ALERT");
					playerWin.setHeaderText("WINNER ANNOUCEMENT");
					String s ="You Won This Game";
					playerWin.setContentText(s);
					playerWin.show();
					//ShowResult(personStage, "YOU WIN :) ");
					// System.out.println("YOU WIN");
					// System.exit(0);
				}

				if (opponentTurn)
					opponentMove(personStage);
			}

		};

		EventHandler<MouseEvent> playerEvent = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if (executing)
					return;

				Cell cell = (Cell) e.getSource();
				if (firstPlayerBoard.positionShip(new Ship(numberOfShips, e.getButton() == MouseButton.PRIMARY),
						cell.row, cell.col)) {
					--numberOfShips;
					
					/*
					 * if (--numberOfShips == 0) { //startGame(); }
					 */
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
		// hbox.setAlignment(Pos.CENTER);
		// root.setCenter(hbox);
		root.getChildren().add(firstPlayerBoard);
		root.getChildren().add(opponentBoard);
		root.setBackground(background);

		return root;
	}

	/**
	 * 
	 */
	private void buttonGeometry() {
		// TODO Auto-generated method stub
		st.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
		reset.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
		pause.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
		load.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
		exit.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
		
		st.setMinHeight(80);
		st.setMinWidth(150);
		reset.setMinHeight(80);
		reset.setMinWidth(150);
		pause.setMinHeight(80);
		pause.setMinWidth(150);
		load.setMinHeight(80);
		load.setMinWidth(150);
		exit.setMinHeight(80);
		exit.setMinWidth(150);
	}

	/**
	 * 
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
				
				Alert opponentWin = new Alert(AlertType.INFORMATION);
				opponentWin.setTitle("ALERT");
				opponentWin.setHeaderText("WINNER ANNOUCEMENT");
				String s ="You Lost This Game to the Computer";
				opponentWin.setContentText(s);
				opponentWin.show();
				//ShowResult(personStage, "YOU LOSE :( ");
				
				// System.out.println("YOU LOSE");
				// System.exit(0);
			}
		}
	}

	/**
	 * 
	 * @param personStage
	 * @param result
	 */
	
/*	
	private void ShowResult(Stage personStage, String result) {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);

		dialog.initOwner(personStage);

		Text t = new Text(result);
		t.setFont(Font.font("Verdana", 50));

		t.setFill(Color.GREEN);

		BorderPane bp = new BorderPane();
		bp.setCenter(t);

		Scene dialogScene = new Scene(bp, 300, 200);
		dialog.setScene(dialogScene);
		dialog.show();

	}

*/
	
	
	/**
	 * 
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
	 * 
	 * @param primaryStage
	 */
	private void intialise(Stage primaryStage) {
		File n = new File(".");
		String path = null;
		try {
			path = n.getCanonicalFile() + "/ship.png";
			System.out.println(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = new Image("file:///" + path);

		BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

		Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size));
		
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
		
		exit.setOnAction(e ->{
			 System.exit(0);
		}
			 );
		
		
		primaryStage.show();
	}

	/**
	 * 
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		intialise(primaryStage);

	}

	public static void main(String[] args) {
		launch(args);
	}
}