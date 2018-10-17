import java.util.ArrayList;
public class Node{
    private double x;
    private double y;
    private double hi;
    private ArrayList<NodeCost> nodelist=new ArrayList<NodeCost>();

    public Node(double x, double y, double hi){
        this.x=x;
        this.y=y;
        this.hi=hi;
    }

    public Node(double x,double y,double hi,ArrayList<NodeCost> nodelist){
        this.x=x;
        this.y=y;
        this.hi=hi;
        this.nodelist=nodelist;
    }

    public boolean equals(Node other){//おなじNodeかを判定
        if(x!=other.getX()) return false;
        if(y!=other.getY()) return false;
        if(hi!=other.getHi()) return false;
        return true;
    }

    public double searchCost(Node n, Node goal){//GoalまでのCostをGet
        double ans=0;
        for(int i=0;i<getNodelist().size();i++){
            if(getNodeCost(i).nodeJudgment(n,goal)){
                ans=getNodeCost(i).getGoToCost(n);
            }
        }
        return ans;
    }

    public NodeCost getNodeCost(int i){//i番目のNodeCostを返す
        return nodelist.get(i);
    }

    public int hashCode(){//HashCodeの作成
        String ss=String.valueOf(x)+" "+String.valueOf(y)+" "+String.valueOf(hi);
        return ss.hashCode();
    }

    void print(){//出力用メソッド
        //System.out.print(getX()+" "+getY()+" "+getHi());
        System.out.println(getX()+" "+getY()+" "+getHi());
    }

    //以下SetterとGetter
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getHi() {
        return hi;
    }

    public void setHi(double hi) {
        this.hi = hi;
    }

    public ArrayList<NodeCost> getNodelist() {
        return nodelist;
    }

    public void setNodelist(ArrayList<NodeCost> nodelist) {
        this.nodelist = nodelist;
    }

    public NodeCost getNodecost(int i){
        return nodelist.get(i);
    }
}
