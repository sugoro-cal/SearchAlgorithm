import java.util.*;
import java.io.*;

public class AStar{
    private Node start;
    private Node goal;
    private ArrayList<Node> nodeList;
    private NodeRec result=null;

    //under debug
    public static void main(String[] args){
        ArrayList<Node> list=readFile(args[0]);
        Node a=list.get(0);
        list.remove(0);
        AStar as=new AStar(a,list.get(list.size()-1),list);
        ArrayList<Node> ans=as.search();
        for(int i=0;i<ans.size();i++) ans.get(i).print();
    }

    static ArrayList<Node> readFile(String filename){//ファイル読み込み
        ArrayList<Node> inputnode=new ArrayList<Node>();
        try{
            File fi=new File(filename);
            Scanner sc=new Scanner(fi);
            int num=sc.nextInt();
            for(int i=0;i<num+2;i++) inputnode.add(new Node(sc.nextDouble(),sc.nextDouble(),sc.nextDouble()));//Node生成
            for(int i=0;i<num+2;i++){
                for(int j=0;j<num+2;j++) {//NodeCostのけいさん
                    if(j==i) continue;
                    inputnode.get(i).getNodelist().add(new NodeCost(inputnode.get(i),inputnode.get(j),sc.nextDouble()));
                }
            }
            sc.close();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
        /*for(int i=0;i<inputnode.size();i++) {
            Node a=inputnode.get(i);
            for(int j=0;j<a.getNodelist().size();j++){
                System.out.println(a.getNodecost(j).getN1()+" "+a.getNodeCost(j).getN2()+" "+a.getNodeCost(j).getN1Ton2Cost());
            }
            System.out.println("readfileend");
        }*/
        return inputnode;
    }
    //debug end

    public AStar(Node start, Node goal) {
        this.start = start;
        this.goal = goal; //Nodelistの最後にGoal nodeを入れる n2をGoalにする
    }

    public AStar(Node start, Node goal, ArrayList<Node> nodeList) {
        this.start = start;
        this.goal = goal;
        this.nodeList = nodeList;
    }

    public ArrayList<Node> search(){//最適解を探す <- ERROR
        HashMap<String,NodeRec> l1Hash=new HashMap<String,NodeRec>();
        PriorityQueue<NodeRec> l1Queue=new PriorityQueue<NodeRec>(new NodeComp());
        HashMap<String,NodeRec> l2=new HashMap<String,NodeRec>();
        NodeRec a=null;
        l1Queue.add(new NodeRec(getStart(),null,0,getFutureCost(getStart())));
        l1Hash.put(makeKeys(l1Queue.peek()),l1Queue.peek());
        while(true){ //cost=l1Queue , kakunin=l1Hash
            a=l1Queue.poll();
            if(a==null) break;
            l1Hash.remove(makeKeys(a));
            l2.put(makeKeys(a),a);
            if(isAllPassed(a)) break;
            ArrayList<NodeRec> temp=new ArrayList<NodeRec>();
            //System.out.println(a.getNow().getNodelist().size());
            for(int i=0;i<a.getNow().getNodelist().size();i++) {//NodeRecの作成
                temp.add(new NodeRec(getToNode(a.getNow(), i), a, getCurrentCost(a, i), getFutureCost(a.getNow())));
                /*System.out.print(getToNode(temp.get(i).getNow(), i));//以下NodeRecの確認
                System.out.print(" "+a);
                System.out.print(" ");
                temp.get(i).getNow().print();
                System.out.print(" "+getCurrentCost(temp.get(i), i));
                System.out.println(" "+getFutureCost(temp.get(i).getNow()));*/
            }
            for(int i=0;i<a.getNow().getNodelist().size();i++)//NodeRecの確認
                System.out.println(temp.get(i));
            System.out.println(temp.size());
            for(int i=0;i<temp.size();i++){ //NodeRecの確認
                NodeRec current=temp.get(i);
                //System.out.println(l1Hash.containsKey(makeKeys(current)) ? "true" : "false"); //L1にNodeRecがふくまれているかどうか <- ここでエラーが発生 NodeRecがNull?
                if(!l1Hash.containsKey(makeKeys(current))&&!l2.containsKey(makeKeys(current))) {
                    l1Queue.add(current);
                    l1Hash.put(makeKeys(current),current);
                    //System.out.println("addclear");//確認用
                    //System.out.println(current);//確認用
                }
                else if(l1Hash.containsKey(makeKeys(current))&&calcAllCost(current)<calcAllCost(l1Hash.get(makeKeys(current)))){
                    NodeRec ka=l1Hash.get(makeKeys(current));
                    l1Hash.remove(makeKeys(ka));
                    current.setParent(ka.getParent());
                    l1Hash.put(makeKeys(current),current);
                    l1Queue.remove(ka);
                    l1Queue.add(current);
                    //System.out.println("koushinfin");//確認用
                }
                else if(l2.containsKey(makeKeys(current))&&calcAllCost(current)<calcAllCost(l2.get(makeKeys(current)))){
                    NodeRec ka=l2.get(makeKeys(current));
                    l2.remove(makeKeys(ka));
                    l1Hash.put(makeKeys(current),current);
                    l1Queue.add(current);
                    //System.out.println("l2 koushinfin");//確認用
                }
            }
        }
        for(Map.Entry<String,NodeRec> entry : l2.entrySet()) { //NodeRecがすべてつながっているかの確認
            if (isAllPassed(entry.getValue())) result = entry.getValue();
        }
        return resultList();
    }

    public NodeRec getNodeParent(NodeRec p){
        return p.getParent();
    }

    public double calcAllCost(NodeRec a){//Cost計算
        return a.getPreviousCost()+a.getFutureCost();
    }

    public Node getToNode(Node now,int i){//Node nowからいくNodeを返す
        return now.getNodeCost(i).getVaryNode(now);
    }

    public double getCurrentCost(NodeRec pare, int i){//いまのNodeまでのCost
        Node a=pare.getNow();
        return a.getNodecost(i).getGoToCost(a);
    }

    public double getFutureCost(Node n){//GoalまでのCost
        int i=getNodeList().indexOf(getGoal());
        return getNodeList().get(i).searchCost(n,getGoal());
    }

    public ArrayList<Node> resultList(){//NodeRecをNodeに入れて返す
        NodeRec ans=result;
        ArrayList<Node> route=new ArrayList<Node>();
        while(ans!=null){
            route.add(ans.getNow());
            ans=ans.getParent();
        }
        Collections.reverse(route);
        return route;
    }

    private boolean isAllPassed(NodeRec currentNode){//すべて通ったかの確認
        //System.out.println();
        int n=getNodeList().size();
        //System.out.println(n);
        ArrayList<Node> repo=new ArrayList<Node>();
        int cnt=0;
        NodeRec nr=currentNode;
        while(nr!=null){
            //System.out.println(nr);//確認用
            Node a=nr.getNow();
            //System.out.println(a);//確認用
            if(repo.indexOf(a)==-1){
                repo.add(a);
                cnt++;
            }
            //System.out.println(repo.size());//確認用
            nr=nr.getParent();
        }
        if(cnt!=n) return false;
        return true;
    }

    public String makeKeys(NodeRec a){//MapのKeyを作成
        return String.valueOf(a.getNow().getX())+" "+String.valueOf(a.getNow().getY())+" "+String.valueOf(a.getNow().getHi());
    }

    //以下Getter && Setter
    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getGoal() {
        return goal;
    }

    public void setGoal(Node goal) {
        this.goal = goal;
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public NodeRec getResult() {
        return result;
    }

    public void setResult(NodeRec result) {
        this.result = result;
    }
}

class NodeComp implements Comparator<NodeRec>{//Comparator
    @Override
    public int compare(NodeRec p1, NodeRec p2) {
        if(p1.getPreviousCost()+p1.getFutureCost()<p2.getPreviousCost()+p2.getFutureCost())return 1;
        else if(p1.getPreviousCost()+p1.getFutureCost()>p2.getPreviousCost()+p2.getFutureCost())return -1;
        return 0;
    }
}
