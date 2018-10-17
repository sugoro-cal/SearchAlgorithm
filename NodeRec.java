public class NodeRec{
    private Node now;
    private NodeRec parent;
    private double previousCost;
    private double futureCost;

    NodeRec(Node now){
        parent=null;
        this.now=now;
        previousCost=0;
        futureCost=0;
    }

    NodeRec(Node now,NodeRec parent,double previousCost,double futureCost){
        this.parent=parent;
        this.now=now;
        this.previousCost=previousCost;
        this.futureCost=futureCost;
    }

    public double getTotalCost(){//previousCost+futureCostを返す
        return this.previousCost+this.futureCost;
    }

    public int hashCode(){//HashCodeの生成
        String ss=String.valueOf(now)+String.valueOf(parent)+String.valueOf(previousCost)+String.valueOf(futureCost);
        return ss.hashCode();
    }

    //以下Getter && Setter
    public Node getNow() {
        return now;
    }

    public void setNow(Node now) {
        this.now = now;
    }

    public NodeRec getParent() {
        return parent;
    }

    public void setParent(NodeRec parent) {
        this.parent = parent;
    }

    public double getPreviousCost() {
        return previousCost;
    }

    public void setPreviousCost(double previousCost) {
        this.previousCost = previousCost;
    }

    public double getFutureCost() {
        return futureCost;
    }

    public void setFutureCost(double futureCost) {
        this.futureCost = futureCost;
    }

    @Override
    public boolean equals(Object o){//Equalsメソッド
        NodeRec n=(NodeRec)o;
        Node nonode=getNow();
        Node compnode=n.getNow();
        if(!nonode.equals(compnode)) return false;
        else return true;
    }
}

