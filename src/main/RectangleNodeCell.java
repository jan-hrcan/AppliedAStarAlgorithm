package main;

import algorithm.NodeCell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RectangleNodeCell extends Rectangle{

    /** GUI cell attributes*/
    private ContextMenu contextMenu;
    private MenuItem makeStart;
    private MenuItem makeEnd;
    private NodeCell cell;
    private Text textS;
    private Text textE;

    /** Colors */
    private Color cellColor;
    private Color startCellColor;
    private Color endCellColor;
    private Color blockedCellColor;

    /** Static final size attributes*/
    private static final double WIDTH = 50;
    private static final double HEIGHT = 50;

    /** Main rectangle constructor */
    public RectangleNodeCell(double _x, double _y, int _i, int _j,
                             Color _c, Color _cS, Color _cE, Color _cB, AnchorPane _root){
        /***/
        super(_x, _y, WIDTH, HEIGHT); // calling the Rectangle class constructor
        this.cell = new NodeCell(_i, _j); // associative relationship with RectangleNodeCell
        this.cellColor = _c;
        this.startCellColor = _cS;
        this.endCellColor = _cE;
        this.blockedCellColor = _cB;
        this.setFill(this.cellColor); // setting all of the rectangles to be the same color
        this.textS = new Text("S");
        this.textS.setFont(Font.font(20));
        this.textE = new Text("E");
        this.textE.setFont(Font.font(20));
        /***/
        contextMenu = new ContextMenu();
        makeStart = new MenuItem("Make cell as a starting point");
        makeEnd = new MenuItem("Make cell as a ending point");
        contextMenu.getItems().addAll(makeStart, makeEnd);
        /***/
        RectangleNodeCell c = this;
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown()) {
                    if (event.getClickCount() == 2 && !cell.isBlocked()) {
                        setFill(blockedCellColor);
                        cell.setBlocked(true);
                    }
                    else if (event.getClickCount() == 2 && cell.isBlocked()){
                        setFill(cellColor);
                        cell.setBlocked(false);
                    }
                }
                else if (event.isSecondaryButtonDown()){
                    contextMenu.show(c, c.getX()+300, c.getY()+50);
                }
            }
        });
        makeStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!cell.isStart()) {
                    cell.setStart(true);
                    setFill(startCellColor);
                    makeStart.setText("Undo to normal");
                    textS.setX(c.getX()+20);
                    textS.setY(c.getY()+20);
                    _root.getChildren().add(textS);
                }
                else if (cell.isStart()){
                    setFill(cellColor);
                    cell.setStart(false);
                    _root.getChildren().remove(textS);
                    makeStart.setText("Make cell as a starting point");
                }
            }
        });
        makeEnd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!cell.isEnd()) {
                    cell.setEnd(true);
                    setFill(endCellColor);
                    textE.setX(c.getX()+20);
                    textE.setY(c.getY()+20);
                    _root.getChildren().add(textE);
                    makeEnd.setText("Undo to normal");
                }
                else if (cell.isEnd()){
                    setFill(cellColor);
                    cell.setEnd(false);
                    _root.getChildren().remove(textE);
                    makeEnd.setText("Make cell as a ending point");
                }
            }
        });
    }

    public NodeCell getCell() {
        return cell;
    }

    public void setCell(NodeCell cell) {
        this.cell = cell;
    }
}
