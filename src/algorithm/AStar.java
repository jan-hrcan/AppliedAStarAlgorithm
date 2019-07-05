package algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {

    /** cost fields */
    private final int DIAGONAL_TRAVEL_COST = 14; // hypotenuse in a triangle is 1.4 (multiplied by 10)
    private final int HORIZONTAL_VERTICAL_TRAVEL_COST = 10;

    /** A* algorithm fields*/
    private PriorityQueue<NodeCell> opened;
    private NodeCell[][] allCells;
    private NodeCell[][] closed;
    private NodeCell start;
    private NodeCell end;
    private static Integer sizeX;
    private static Integer sizeY;

    /** main A* constructor */
    public AStar (Integer _sizeX, Integer _sizeY, NodeCell[][] _allCells){
       // this.allCells = new NodeCell[_sizeX][_sizeY];
        allCells = _allCells;
        this.sizeX = _sizeX;
        this.sizeY = _sizeY;
        this.opened = new PriorityQueue<>(new Comparator<NodeCell>() {
            @Override
            public int compare(NodeCell o1, NodeCell o2) {
                if (o1.getF_value()<o2.getF_value()) return -1;
                else if (o1.getF_value()>o2.getF_value()) return 1;
                else return 0;
            }
        });
        this.closed = new NodeCell[_sizeX][_sizeY];
    }

    /** game A* constructor */
    public AStar(){}

    /** main public method that starts the algorithm and returns data*/
    public List<NodeCell> doAlgorithm(){
        algorithm(); // calls the main algorithm method

        List<NodeCell> list = new ArrayList<NodeCell>();
        if (this.closed[end.getI()][end.getJ()] == end){
            NodeCell node = allCells[end.getI()][end.getJ()];
            while(node.getParent() != null){
                list.add(node.getParent());
                node = node.getParent();
            }
        }
        if (!list.isEmpty()) list.remove(list.size()-1);
        return list;
    }

    /** assigning start and end */
    private void assignStartAndEnd (){
        for (int i=0; i<sizeX; ++i){
            for (int j=0; j<sizeY; ++j){
                if (allCells[i][j].isStart()){
                    this.start = allCells[i][j]; // setting the starting cell
                }
                else if (allCells[i][j].isEnd()){
                    this.end = allCells[i][j]; // setting the ending cell
                }
            }
        }
    }

    /** The main algorithm method */
    private void algorithm(){
        assignStartAndEnd();
        calculateHeuristicsUsingManhattan(); // calculating random distance from end node
        setBlockedCells(); // setting the blocked cells to null in the list
        this.start.setF_value(0); // the starting node has 0 on the F value
        this.opened.add(this.allCells[start.getI()][start.getJ()]); // starting cell has been added into the queue

        NodeCell node; // node used for examination
        while(true) {
            node = this.opened.poll(); // takes out the head value from the queue
            if (node == null) break; // poll() returns null if the queue is empty so it needs to be checked
            this.closed[node.getI()][node.getJ()] = node; // the node is put into the closed list
                if (node.equals(this.end)) return; // if the algorithm has reached the end node while loop is terminated

            NodeCell node_t; // node used for comparing it to the main node


            if (node.getI()-1 >= 0){
                // |*|c|*|
                // |*| |*|
                // |*|*|*|
                node_t = allCells[node.getI()-1][node.getJ()];
                check(node_t, node, HORIZONTAL_VERTICAL_TRAVEL_COST+node.getF_value());
                if (node.getJ()-1 >= 0){
                    // |c|*|*|
                    // |*| |*|
                    // |*|*|*|
                    node_t = allCells[node.getI()-1][node.getJ()-1];
                    check(node_t, node, DIAGONAL_TRAVEL_COST+node.getF_value());
                }
                if (node.getJ()+1 < allCells[0].length){
                    // |*|*|c|
                    // |*| |*|
                    // |*|*|*|
                    node_t = allCells[node.getI()-1][node.getJ()+1];
                    check(node_t, node, DIAGONAL_TRAVEL_COST+node.getF_value());
                }
            }
            if (node.getI()+1 < allCells.length){
                // |*|*|*|
                // |*| |*|
                // |*|c|*|
                node_t = allCells[node.getI()+1][node.getJ()];
                check(node_t, node, HORIZONTAL_VERTICAL_TRAVEL_COST+node.getF_value());
                if (node.getJ()-1 >= 0){
                    // |*|*|*|
                    // |*| |*|
                    // |c|*|*|
                    node_t = allCells[node.getI()+1][node.getJ()-1];
                    check(node_t, node, DIAGONAL_TRAVEL_COST+node.getF_value());
                }
                if (node.getJ()+1 < allCells[0].length){
                    // |*|*|*|
                    // |*| |*|
                    // |*|*|c|
                    node_t = allCells[node.getI()+1][node.getJ()+1];
                    check(node_t, node, DIAGONAL_TRAVEL_COST+node.getF_value());
                }
            }
            if (node.getJ()-1 >= 0){
                // |*|*|*|
                // |c| |*|
                // |*|*|*|
                node_t = allCells[node.getI()][node.getJ()-1];
                check(node_t, node, HORIZONTAL_VERTICAL_TRAVEL_COST+node.getF_value());
            }
            if (node.getJ()+1 < allCells[0].length){
                // |*|*|*|
                // |*| |c|
                // |*|*|*|
                node_t = allCells[node.getI()][node.getJ()+1];
                check(node_t, node, HORIZONTAL_VERTICAL_TRAVEL_COST+node.getF_value());
            }

        }
    }

    /** Method for updating the cost
     * and parenting and re-parenting nodes*/
    private void check(NodeCell _node_t, NodeCell _node, int _cost){
        if (_node_t == null || this.closed[_node_t.getI()][_node_t.getJ()] == _node_t) return; // if the node is blocked or closed just return

        int _node_t_tempFValue = _node_t.getH_value() + _cost; // temp value used for re-parenting if needed

        if (_node_t.getF_value()>_node_t_tempFValue || !opened.contains(_node_t)){
            _node_t.setF_value(_node_t_tempFValue);
            _node_t.setParent(_node);
            if (!opened.contains(_node_t)) opened.add(_node_t);
        }
    }

    /** Method for calculating heuristics
     * (calculating the approximate distance from the end node)*/
    private void calculateHeuristicsUsingManhattan (){
        if (this.allCells != null){
            for (int i=0; i<sizeX; i++){
                for (int j=0; j<sizeY; j++){
                    this.allCells[i][j].setH_value((this.end.getI()-i)+(this.end.getJ()-j));
                }
            }
        }
    }

    /** Method for setting blocked cells
     * (if a cell is blocked it gets a NULL value
     * in the allCells list)*/
    private void setBlockedCells(){
        if (this.allCells != null){
            for (int i=0; i<sizeX; i++){
                for (int j=0; j<sizeY; j++){
                    if (this.allCells[i][j].isBlocked()){
                        this.allCells[i][j] = null;
                    }
                }
            }
        }
    }
}
