public class NodeCost{
    private Node n1;
    private Node n2;
    private double n1Ton2Cost;
    private double n2Ton1Cost;

    public NodeCost(Node n1, Node n2, double commonCost){
        this.n1=n1;
        this.n2=n2;
        n1Ton2Cost=commonCost;
        n2Ton1Cost=commonCost;
    }

    public NodeCost(Node n1,Node n2,double n1Ton2,double n2Ton1) {
        this.n1 = n1;
        this.n2 = n2;
        n1Ton2Cost = n1Ton2;
        n2Ton1Cost = n2Ton1;
    }

    public boolean nodeJudgment(Node a,Node b){ //Nodeが含まれているかの確認
        return (a.equals(n1)&&b.equals(n2))||(a.equals(n2)&&b.equals(n1));
    }

    public Node getVaryNode(Node n){//引数と異なったNodeを返す
        return n.equals(n1) ? n2 : n1;
    }

    public double getGoToCost(Node n){//引数からのCostを返す
        return n.equals(n1) ? n1Ton2Cost : n2Ton1Cost;
    }

    //以下Getter && Setter
    public Node getN1() {
        return n1;
    }

    public void setN1(Node n1) {
        this.n1 = n1;
    }

    public Node getN2() {
        return n2;
    }

    public void setN2(Node n2) {
        this.n2 = n2;
    }

    public double getN1Ton2Cost() {
        return n1Ton2Cost;
    }

    public void setN1Ton2Cost(double n1Ton2Cost) {
        this.n1Ton2Cost = n1Ton2Cost;
    }

    public double getN2Ton1Cost() {
        return n2Ton1Cost;
    }

    public void setN2Ton1Cost(double n2Ton1Cost) {
        this.n2Ton1Cost = n2Ton1Cost;
    }
}
