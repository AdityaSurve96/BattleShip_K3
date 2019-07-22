package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import application.Board.Cell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * This is the Main Class which will help us to play the game . Method which
 * will startGame , Create Boards.
 * <p>
 * Other tasks are like:
 * </p>
 * <ol>
 * <li>Handles the Mouse events with player and opponent moves.</li>
 * <li>Initialize Variables.</li>
 * </ol>
 * 
 * @author K3
 *
 */

public class Battle extends Application {

	private boolean executing = false;

	private boolean isCheating = true;

	private Board opponentBoard, firstPlayerBoard;

	private int numberOfShips = 5;

	private boolean normalGame;

	private boolean salvation;

	private int hits = 1;

	private ArrayList<String> cellsSelected = new ArrayList<>();

	private ArrayList<Cell> numberOfShots = new ArrayList<>();

	private ArrayList<Integer> shipLengths = new ArrayList<Integer>() {

		{
			add(5);
			add(4);
			add(3);
			add(3);
			add(2);
		}
	};

	private int currentShip = 0;
	private boolean opponentTurn = false;

	private Random random = new Random();

	private Button st = new Button("START");

	private Button reset = new Button("RESET");

	private Button pause = new Button("PAUSE");

	private Button load = new Button("LOAD");

	private Button exit = new Button("EXIT");

	private Button doNotCheat = new Button("DO NOT CHEAT");

	private double cellSize = 30.0;
	
	private AI ai =new AI();

	private Rectangle ship1 = new Rectangle(50, 450, 150, 30);
	private Rectangle ship2 = new Rectangle(50, 490, 120, 30);
	private Rectangle ship3 = new Rectangle(50, 530, 90, 30);
	private Rectangle ship4 = new Rectangle(50, 570, 90, 30);
	private Rectangle ship5 = new Rectangle(50, 610, 60, 30);

	private boolean clicked = false;
	private boolean draggingBoat = false;
	private boolean placingBoats = true;

	private boolean needToRotate = true;
	private boolean isRotated = false;
	private Rectangle selectedShip;
	private double previoustime = 0;
	private double currenttime = 0;

	HBox hBox, hBox1;

	static int player1Score, player2Score;

	Text timer1, timer2, player2ScoreDisplay, player1ScoreDisplay;
	Timeline timelinePlayer1, timelinePlayer2;

	int mins = 0, secs = 0, millis = 0;
	int mins1 = 0, secs1 = 0, millis1 = 0;

	boolean player1Timer = true;
	boolean player2Timer = true;

	void change(Text text) {
		if (millis == 1000) {
			secs++;
			millis = 0;
		}
		if (secs == 60) {
			mins++;
			secs = 0;
		}
		text.setText((((mins / 10) == 0) ? "0" : "") + mins + ":" + (((secs / 10) == 0) ? "0" : "") + secs + ":"
				+ (((millis / 10) == 0) ? "00" : (((millis / 100) == 0) ? "0" : "")) + millis++);
	}

	void change1(Text text) {
		if (millis1 == 1000) {
			secs1++;
			millis1 = 0;
		}
		if (secs1 == 60) {
			mins1++;
			secs1 = 0;
		}
		text.setText((((mins1 / 10) == 0) ? "0" : "") + mins1 + ":" + (((secs1 / 10) == 0) ? "0" : "") + secs1 + ":"
				+ (((millis1 / 10) == 0) ? "00" : (((millis1 / 100) == 0) ? "0" : "")) + millis1++);
		// text.setText("OPPO");
	}

	/**
	 * In general adding styles and layout to the output screen i.e titles ,grid
	 * layout,mouse effects and movement etc.
	 * <p>
	 * In Detail tasks carried out for board design.
	 * </p>
	 * <ol type="1">
	 * <li>Setting the title for the game.</li>
	 * <li>Giving heading to grid1 and grid2 i.e player 1 grid and opponent
	 * grid</li>
	 * <li>Create HBox i.e icons to specify different functionalities in game like
	 * start,reset,pause and load</li>
	 * <li>Mouse handler events for opponent and player 1</li>
	 * </ol>
	 * 
	 * @param personStage
	 *            Stage that holds the board, grids and ships on it.
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

		HBox actions = new HBox(30, st, reset, pause, load, exit, doNotCheat);
		actions.setAlignment(Pos.CENTER);
		buttonGeometry();

		actions.setTranslateX(630);
		actions.setTranslateY(750);

		root.getChildren().add(battle);
		root.getChildren().add(player1);
		root.getChildren().add(Opponent);
		root.getChildren().add(actions);

		// Player 1

		Text player1Summary = new Text();
		player1Summary.setText("Player 1");
		player1Summary.setFill(Color.GOLD);
		player1Summary.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));

		Text player1TimerHeading = new Text();
		player1TimerHeading.setText("Timer:");
		player1TimerHeading.setFill(Color.RED);
		player1TimerHeading.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

		timer1 = new Text("00:00:000");
		timer1.setFill(Color.WHITE);
		timer1.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

		timelinePlayer1 = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				change(timer1);
			}
		}));

		timelinePlayer1.setCycleCount(Timeline.INDEFINITE);
		timelinePlayer1.setAutoReverse(false);

		Text player1ScoreHeading = new Text();
		player1ScoreHeading.setText("Score:");
		player1ScoreHeading.setFill(Color.RED);
		player1ScoreHeading.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

		player1ScoreDisplay = new Text();
		player1ScoreDisplay.setText("0");
		player1ScoreDisplay.setFill(Color.WHITE);
		player1ScoreDisplay.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

		hBox = new HBox(10, player1TimerHeading, timer1);
		hBox1 = new HBox(10, player1ScoreHeading, player1ScoreDisplay);

		VBox player1Details = new VBox(20, player1Summary, hBox, hBox1);
		player1Details.setLayoutX(50);
		player1Details.setLayoutY(100);

		root.getChildren().add(player1Details);

		// Player 2

		Text player2Summary = new Text();
		player2Summary.setText("Player 2");
		player2Summary.setFill(Color.GOLD);
		player2Summary.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));

		Text player2TimerHeading = new Text();
		player2TimerHeading.setText("Timer:");
		player2TimerHeading.setFill(Color.RED);
		player2TimerHeading.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

		timer2 = new Text("00:00:000");
		timer2.setFill(Color.WHITE);
		timer2.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

		timelinePlayer2 = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				change1(timer2);
			}
		}));
		timelinePlayer2.setCycleCount(Timeline.INDEFINITE);
		timelinePlayer2.setAutoReverse(false);

		Text player2ScoreHeading = new Text();
		player2ScoreHeading.setText("Score:");
		player2ScoreHeading.setFill(Color.RED);
		player2ScoreHeading.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

		player2ScoreDisplay = new Text();
		player2ScoreDisplay.setText("0");
		player2ScoreDisplay.setFill(Color.WHITE);
		player2ScoreDisplay.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

		hBox = new HBox(10, player2TimerHeading, timer2);
		hBox1 = new HBox(10, player2ScoreHeading, player2ScoreDisplay);
		VBox player2Details = new VBox(20, player2Summary, hBox, hBox1);
		player2Details.setLayoutX(1100);
		player2Details.setLayoutY(100);

		root.getChildren().add(player2Details);

		root.getChildren().add(ship1);
		root.getChildren().add(ship2);
		root.getChildren().add(ship3);
		root.getChildren().add(ship4);
		root.getChildren().add(ship5);
		// End Graphics

		

		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if (!executing)
					return;
				Cell cell = (Cell) e.getSource();
				if (cellsSelected.contains(cell.row + "" + cell.col)) {
					return;
				}
				cellsSelected.add(cell.row + "" + cell.col);

				if (cell.targetHit)
					return;
				numberOfShots.add(cell);
				if (normalGame) {
					shootNormalShip(numberOfShots, personStage);
					numberOfShots.clear();
				} else if (salvation) {
					if (hits == firstPlayerBoard.amountOfships) {
						shootSalvationShip(numberOfShots, personStage);
					} else
						hits++;
				}

			}

		};

		EventHandler<MouseEvent> playerEvent = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if (executing)
					return;
				// System.out.println("Draged");

				Cell cell = (Cell) e.getSource();
				if(currentShip<5) {
					if (firstPlayerBoard.positionShip(
							new Ship(shipLengths.get(currentShip), e.getButton() == MouseButton.PRIMARY), cell.row,
							cell.col)) {
						--numberOfShips;
						currentShip++;
	
					}
				}

			}

		};

		EventHandler<MouseEvent> salvaEvent = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if (executing)
					return;
				// System.out.println("Draged");

				/*
				 * Cell cell = (Cell) e.getSource(); if (firstPlayerBoard.positionShip( new
				 * Ship(shipLengths.get(currentShip), e.getButton() == MouseButton.PRIMARY),
				 * cell.row, cell.col)) { --numberOfShips; currentShip++;
				 * 
				 * }
				 */

			}

		};

		selectedShip = ship1;
		// ship1.setOnMousePressed(salvaEvent);
		installBoatListeners(ship1);
		installBoatListeners(ship2);
		installBoatListeners(ship3);
		installBoatListeners(ship4);
		installBoatListeners(ship5);

		opponentBoard = new Board(true, event);

		firstPlayerBoard = new Board(false, playerEvent);
		
		installPlayerBoardListeners(root,firstPlayerBoard);

		firstPlayerBoard.setLayoutX(250);
		firstPlayerBoard.setLayoutY(120);
		opponentBoard.setLayoutX(750);
		opponentBoard.setLayoutY(120);
		ai.reset();
		root.getChildren().add(firstPlayerBoard);
		root.getChildren().add(opponentBoard);
		root.setBackground(background);

		return root;
	}

	private void installBoatListeners(Node boat) {
		final Delta dragDelta = new Delta();

		// Handle dragging, using help from
		// http://stackoverflow.com/questions/22139615/dragging-buttons-in-javafx
		boat.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				System.out.println("Pressed");
				clicked = true;
				needToRotate = true;
				Rectangle rect = (Rectangle) boat;
				select(rect);
				if (boat.rotateProperty().getValue() == 0) {
					isRotated = false;
				} else {
					isRotated = true;
				}
				double localX = rect.getX();
				double localY = rect.getY();
				if (isRotated) {
					localX = localX + selectedShip.getWidth() / 2;
					localY = localY - selectedShip.getWidth() / 2 + cellSize;
				}
				// int size = (int) ((int) selectedShip.getWidth() / cellSize);
				// int x = (int) (localX / cellSize);
				// int y = (int) (localY / cellSize);
				// Ship removeShip = null;
				/*
				 * for (Ship ship : playerGrid.ships) { if (ship.getStartX() == x &&
				 * ship.getStartY() == y) { removeShip = ship; for (int x2 =
				 * removeShip.getStartX(); x2 <= removeShip.getEndX(); x2++) { for (int y2 =
				 * removeShip.getStartY(); y2 <= removeShip.getEndY(); y2++) {
				 * playerGrid.gameBoard.get(x2).get(y2).setShip(null); } } if (removeShip !=
				 * null) { if (removeShip.getIsSet()) { if (!isRotated) { if
				 * (playerDisplayBoard.contains(localX, localY)) { for (int i = 0; i < size && i
				 * <= 9 - x; i++) { Rectangle r = (Rectangle) playerGrid.getNode(x + i, y);
				 * r.setFill(Color.BLACK); }
				 * 
				 * } } if (isRotated) { if (playerDisplayBoard.contains(localX, localY)) { for
				 * (int i = 0; i < size && i <= 9 - y; i++) { Rectangle r = (Rectangle)
				 * playerGrid.getNode(x, y + i); r.setFill(Color.BLACK); }
				 * 
				 * } } } } } }
				 * 
				 * if (playerGrid.ships.remove(removeShip)) {
				 * startButton.disableProperty().set(true); }
				 */
				boat.setCursor(Cursor.MOVE);
			}
		});
		boat.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				clicked = false;
				boat.setCursor(Cursor.HAND);
				draggingBoat = false;
				boat.toFront();
			}
		});
		boat.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				System.out.println("Dragged");
				needToRotate = false;
				if (boat.rotateProperty().getValue() == 0) {
					isRotated = false;
				} else {
					isRotated = true;
				}

				draggingBoat = true;
				Rectangle temp = (Rectangle) boat;
				if (isRotated) {
					// boat.setLayoutX(mouseEvent.getSceneX() - (boat.getBoundsInLocal().getWidth()
					// / 2));
					temp.setX(mouseEvent.getSceneX() - (temp.getBoundsInLocal().getWidth() / 2));
					temp.setY(mouseEvent.getSceneY() - (temp.getBoundsInLocal().getWidth() / 3));
					// boat.setLayoutY(mouseEvent.getSceneY() + (boat.getBoundsInLocal().getWidth()
					// / 3));
				} else {

					temp.setX(mouseEvent.getSceneX());
					temp.setY(mouseEvent.getSceneY());
				}
			}
		});
		boat.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				boat.setCursor(Cursor.HAND);
			}
		});
		boat.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				if (needToRotate) {
					// boat.setLayoutX(mouseEvent.getSceneX());
					// boat.setLayoutY(mouseEvent.getSceneY());
					if (boat.rotateProperty().getValue() == 0) {
						boat.setRotate(90.0);
					} else
						boat.setRotate(0);
				}
			}

		});
	}

	private Rectangle select(Rectangle boat) {
		selectedShip.setStroke(Color.WHITE);
		selectedShip.setStrokeWidth(1.0);
		if (boat != null) {
			boat.setStrokeWidth(2.5);
			boat.setStroke(Color.WHITE);
		}
		selectedShip = boat;
		return selectedShip;
	}

	/**
	 * <p>
	 * setting styling effects for different buttons like pause,start,rest,load on
	 * output screen. Showing code for start button.Done similarly for other buttons
	 * defined in program.
	 * </p>
	 * <code>
	 * st.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
	 * st.setMinHeight(80);
	 * st.setMinWidth(150);
	 * </code>
	 */
	private void buttonGeometry() {

		st.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
		reset.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
		pause.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
		load.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
		exit.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
		doNotCheat.setStyle("-fx-background-color: #000000;-fx-font-size: 2em;-fx-text-fill:#ffffff;");

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

		doNotCheat.setMinHeight(80);
		doNotCheat.setMinWidth(250);

	}

	private void displayScore(String player) {
		if (player.equalsIgnoreCase("player1"))
			player1ScoreDisplay.setText(player1Score + "");
		else
			player2ScoreDisplay.setText(player2Score + "");
	}

	private void installPlayerBoardListeners(BorderPane rootPane,Board playerBoard) {
		rootPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!clicked)
					return;

				rootPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event2) {
						// Adjust pointer to make up for Grid's offset
						double localX = event2.getX() ;
						double localY = event2.getY() ;
						int size = (int) ((int) selectedShip.getWidth() / cellSize);
						if (playerBoard.contains(localX, localY)) {
							int x = (int) (localX / cellSize);
							int y = (int) (localY / cellSize);
							Rectangle r = (Rectangle) playerBoard.getCell(x, y);
							int endX, endY;
							if (isRotated) {
								selectedShip.setLayoutX(r.getX() + r.getParent().getTranslateX()
										- selectedShip.getWidth() / 2 + cellSize / 2);
								selectedShip.setLayoutY(r.getY() + r.getParent().getTranslateY() + size * cellSize
										- selectedShip.getWidth() / 2 - cellSize / 2 + 5);
								endX = x;
								endY = y + size - 1;
							} else {
								selectedShip.setLayoutX(r.getX() + r.getParent().getTranslateX());
								selectedShip.setLayoutY(r.getY() + r.getParent().getTranslateY() + 5);
								endX = x + size - 1;
								endY = y;
							}

							if (firstPlayerBoard.positionShip(
									new Ship(shipLengths.get(currentShip), isRotated == true), x,
									y)) {
								--numberOfShips;
								currentShip++;

							}
						}
						
						playerBoard.setOnMouseReleased(null);

					}
				});
			}

		});
	}

	/**
	 * This method is AI which will detect the move on Player 1 Board.
	 * 
	 * @param personStage
	 */
	private void opponentNormalMove(Stage personStage) {
		int x,y;
		int oldValue,newValue;
		while (opponentTurn) {

			 x = ai.nextX();
			 y = ai.nextY();

			Cell cell = firstPlayerBoard.getCell(x, y);
			if (cell.targetHit) {
				 ai.generate();
				continue;
			}
			oldValue=firstPlayerBoard.amountOfships;
			System.out.println("Opponent Shooting");
			opponentTurn = cell.shoot();
			System.out.println("Opponent shot done");
			if (!opponentTurn) {
				ai.feedback(false, false);
				timelinePlayer2.pause();

				timelinePlayer1.play();

			} else {
				newValue=firstPlayerBoard.amountOfships;
				if (oldValue != newValue) {   //if PC is guessing AND ship is destroyed
                    ai.feedback(true, true);
                }else {
				ai.feedback(true, false);
				}
				player2Score += 5;
				displayScore("player2");
			}

			if (firstPlayerBoard.amountOfships == 0) {

				String s = "You Lost This Game to the Computer";
				finalResultDisplay(s, personStage);

			}

		}
	}

	private void opponentSalvationMove(Stage personStage) {
		ArrayList<String> takenCellStrings = new ArrayList<String>();
		for (int i = 0; i < opponentBoard.amountOfships; i++) {
			int x = random.nextInt(10);
			int y = random.nextInt(10);
			if (takenCellStrings.contains(x + "" + y)) {
				i--;
				continue;

			}
			takenCellStrings.add(x + "" + y);

			Cell cell = firstPlayerBoard.getCell(x, y);

			if (cell.targetHit) {
				i--;
				continue;
			} else {
				numberOfShots.add(cell);
			}
		}

		for (Cell cell : numberOfShots) {

			System.out.println("Opponent Shooting");
			opponentTurn = cell.shoot();
			System.out.println("Opponent shot done");
			if (opponentTurn) {
				player2Score += 5;
				displayScore("player2");
			}
			if (firstPlayerBoard.amountOfships == 0) {

				String s = "You Lost This Game to the Computer";
				finalResultDisplay(s, personStage);

			}

		}
		timelinePlayer2.pause();

		timelinePlayer1.play();
		numberOfShots.clear();
		hits = 1;
	}

	/**
	 * This method will Display the final result on the pop showing who the winner.
	 * 
	 * @param s
	 *            String that specifies a text notifying when one player wins.
	 */
	private void finalResultDisplay(String s, Stage personStage) {
		// TODO Auto-generated method stub
		ButtonType buttonTypeOne = new ButtonType("YES");
		ButtonType buttonTypeTwo = new ButtonType("NO");

		Alert winOrLose = new Alert(AlertType.CONFIRMATION);
		winOrLose.setTitle("WINNER ANNOUCEMENT");
		winOrLose.setHeaderText(s);

		winOrLose.setContentText("Click YES to Restart the Game\nClick NO to Exit the Game");
		winOrLose.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

		Optional<ButtonType> result = winOrLose.showAndWait();

		if (result.get() == buttonTypeOne) {
			restart(personStage);
		} else if (result.get() == buttonTypeTwo) {
			System.exit(0);
		}

	}

	/**
	 * This method will Start the Game once the Player Click the start button and
	 * Player 1 has set up all his ships.
	 */
	private void startGame() {
		// place enemy ships

		numberOfShips = 5;
		for (int i = 0; i < shipLengths.size(); i++) {

			int x = random.nextInt(10);
			int y = random.nextInt(10);

			if (opponentBoard.positionShip(new Ship(shipLengths.get(i), Math.random() < 0.5), x, y)) {

				numberOfShips--;
			} else {
				i -= 1;
			}
		}

		Alert gameModeAlert = new Alert(AlertType.INFORMATION);

		ButtonType buttonSalva = new ButtonType("SALVA");
		ButtonType buttonNormal = new ButtonType("NORMAL");

		gameModeAlert.setTitle("SELECT GAME MODE");

		gameModeAlert.setContentText("Click on the desired button to choose game mode");
		gameModeAlert.getButtonTypes().setAll(buttonSalva, buttonNormal);

		Optional<ButtonType> result = gameModeAlert.showAndWait();

		if (result.get() == buttonSalva) {

			salvation = true;
			normalGame = false;
		} else if (result.get() == buttonNormal) {
			normalGame = true;
			salvation = false;
		}
		executing = true;
		timelinePlayer1.play();
		previoustime=Integer.parseInt(timer1.getText().split(":")[1]);
	}

	private void seeOpponentShips(Board opponentBoard) {
		for (int y = 0; y < 10; y++) {

			for (int x = 0; x < 10; x++) {

				Cell cell = opponentBoard.getCell(y, x);
				if (cell.ship != null) {
					if (!cell.targetHit) {
						if (isCheating)
							cell.setFill(Color.GOLD);
						else
							cell.setFill(Color.WHITE);

					}
				}

			}

		}
	}

	/**
	 * This Method will initialize the Primary stage with the necessary elements in
	 * it
	 * 
	 * @param primaryStage
	 */
	private void intialise(Stage primaryStage) {
		File n = new File(".");
		String path = null;
		try {
			path = n.getCanonicalFile() + "/ship.png";
		} catch (IOException e) {

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

		exit.setOnAction(e -> {
			System.exit(0);
		});

		st.setOnAction(e -> {
			if (numberOfShips == 0)
				startGame();
		});

		reset.setOnAction(e -> {
			restart(primaryStage);
		});

		doNotCheat.setOnAction(e -> {

			seeOpponentShips(opponentBoard);

			isCheating = !isCheating;

		});

		primaryStage.show();
	}

	/**
	 * Method will reset the Game by initialising all the related Nodes.
	 * 
	 * @param primaryStage
	 */
	private void restart(Stage primaryStage) {

		primaryStage.close();
		Platform.runLater(() -> {
			try {
				new Battle().start(new Stage());
			} catch (Exception e) {

				e.printStackTrace();
			}
		});

	}

	/**
	 * This Method Will call the initialise method to set up the Stage.
	 * {@inheritDoc}
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		intialise(primaryStage);

	}

	/**
	 * Launches the application
	 * 
	 * @param args-takes
	 *            the default Arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * 
	 */
	public void shootNormalShip(ArrayList<Cell> cellHits, Stage personStage) {

		for (Cell cell : cellHits) {
			if (cell.targetHit)
				return;

			System.out.println("Player Shooting");
			opponentTurn = !cell.shoot();
			System.out.println("Player Shot done");

			if (opponentTurn) {
				timelinePlayer1.pause();
				// System.out.println("Player 1 Timer paused");
				timelinePlayer2.play();
				// System.out.println("Player 2 Timer resumed");
				opponentNormalMove(personStage);
			} else {
				currenttime= Integer.parseInt(timer1.getText().split(":")[1]);
				System.out.println("Previous Time" + previoustime);
				System.out.println("Cuurent Time "+ currenttime);
				if(currenttime-previoustime<2)
					player1Score += 5;
				else if(currenttime-previoustime<5 && currenttime-previoustime>2)
					player1Score += 3;
				else if(currenttime-previoustime>5 && currenttime-previoustime<10)
					player1Score += 2;
				else if(currenttime-previoustime>10)
					player1Score += 1;
				
				previoustime=currenttime;
				displayScore("player1");
			}

			if (opponentBoard.amountOfships == 0) {

				String s = "You Won This Game";
				finalResultDisplay(s, personStage);

			}
		}

	}

	public void shootSalvationShip(ArrayList<Cell> cellHits, Stage personStage) {
		for (Cell cell : cellHits) {

			System.out.println("Player Shooting");
			opponentTurn = !cell.shoot();
			System.out.println("Player Shot done");
			if (!opponentTurn) {
				player1Score += 5;
				displayScore("player1");
			}
			if (opponentBoard.amountOfships == 0) {

				String s = "You Won This Game";
				finalResultDisplay(s, personStage);

			}
		}
		timelinePlayer1.pause();
		// System.out.println("Player 1 Timer paused");
		timelinePlayer2.play();
		// System.out.println("Player 2 Timer resumed");
		hits = 1;
		numberOfShots.clear();
		opponentSalvationMove(personStage);

	}

	class Delta {
		double x, y;
	}

}
