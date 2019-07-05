package algorithm;

public class NodeCell {

    /** main node fields */
    private int h_value; // heuristic value
    private int g_value; // travel value
    private int f_value; //(h+g values)
    private NodeCell parent; //node's parent cell

    /** coordinates on the board */
    private Integer i;
    private Integer j;

    /** additional attributes*/
    private boolean isStart = false;
    private boolean isEnd = false;
    private boolean isBlocked = false;

    /** constructor */
    public NodeCell (Integer _iArg, Integer _jArg){
        this.i = _iArg;
        this.j = _jArg;
    }

    public int getH_value() {
        return h_value;
    }

    public void setH_value(int h_value) {
        this.h_value = h_value;
    }

    public int getG_value() {
        return g_value;
    }

    public void setG_value(int g_value) {
        this.g_value = g_value;
    }

    public int getF_value() {
        return f_value;
    }

    public void setF_value(int f_value) {
        this.f_value = f_value;
    }

    public NodeCell getParent() {
        return parent;
    }

    public void setParent(NodeCell parent) {
        this.parent = parent;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getJ() {
        return j;
    }

    public void setJ(Integer j) {
        this.j = j;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean b) {
        isBlocked = b;
    }
}
