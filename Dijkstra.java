import java.util.Scanner;
import java.util.ArrayList;

class Edge{//ノード情報保持
    int before,after;
    double cost;
    Edge(int beforeC,int afterC,double costC){
	before=beforeC;
	cost=costC;
	after=afterC;
    }
}

public class Dijkstra{
    static Scanner sc=new Scanner(System.in);
    ArrayList<Integer> shortest_road=new ArrayList<Integer>();
    Edge[] edgeData;
    double[] point_cost;
    int[] checkedNode;
    int[] result_road;
    int data,goal,start;


    Dijkstra(int data_size,int point_size){//データ量、ノード数受け取り
	data=data_size;
	point_cost=new double[point_size];
	edgeData=new Edge[data];
	checkedNode=new int[point_cost.length];
	result_road=new int[point_cost.length];
    }

    void setData(int a,int before_node,int after_node,double cost,double before_high,double after_high){
	//ノード間の情報受け取り
	double high=Math.abs(before_high-after_high);
	if(high>200)
	    cost=cost*2.0;
	else if(high>150)
	    cost=cost*1.5;
	else if(high>75)
	    cost=cost*1.3;
	else if(high>25)
	    cost=cost*1.15;
	edgeData[a]=new Edge(before_node,after_node,cost);
    }

    void setData(int a,int before_node,int after_node,double cost){
	edgeData[a]=new Edge(before_node,after_node,cost);
    }


    double dijkstra(int start_,int goal_){//アルゴリズム実行部分、返り値:総距離
	start=start_;
	goal=goal_;
       	for(int i=0;i<point_cost.length;i++){
	    point_cost[i]=Integer.MAX_VALUE;
	    checkedNode[i]=0;
	}
	point_cost[start]=0;
	checkedNode[start]=1;
	int nextN=Comparison(start);
	int h,g;
	while(true){
	    h=nextN;
	    nextN=Comparison(h);
	    if(nextN==goal){
		for(g=0;g<checkedNode.length;g++)
		    if(g!=goal)
			if(checkedNode[g]==0)
			    break;
		if(g==checkedNode.length)
		    break;
		if(point_cost[goal]<=point_cost[g])
		    break;
		else nextN=g;
	    }
	}
	shortest_road.clear();
	Route();
	return point_cost[goal];
    }
    public int Comparison(int nodeS){//計算部分
	Edge catch_data;
	int sNode=-1;
	double sCost=Integer.MAX_VALUE;
	for(int a=0;a<data;a++){
	    catch_data=edgeData[a];
       	if(catch_data.before==nodeS){
		  if(catch_data.cost+point_cost[nodeS]<point_cost[catch_data.after]){
		      point_cost[catch_data.after]=catch_data.cost+point_cost[nodeS];
		      result_road[catch_data.after]=catch_data.before;
		  }
	}
	else if(catch_data.after==nodeS)
	    if(catch_data.cost+point_cost[nodeS]<point_cost[catch_data.before]){
		point_cost[catch_data.before]=catch_data.cost+point_cost[nodeS];
		result_road[catch_data.before]=catch_data.after;
	    }
	}
	for(int k=0;k<checkedNode.length;k++)
	    if(checkedNode[k]==0)
		if(point_cost[k]<sCost){
		    sCost=point_cost[k];
		    sNode=k;
		}
	checkedNode[nodeS]=1;
	return sNode;
    }

    
    int getdata(){
	return data;
    }
    ArrayList<Integer> getRoute_Array(){//ArrayListで最短経路を返す
	return shortest_road;
    }

    int getRoute_point(int num){
	/*一つずつ値を返す
	 使用例:{  for(int c=a.getshortest_roadLength()-1;0<=c;c--)
	 System.out.println(a.getRoute_point(c)); }*/
	return shortest_road.get(num);
    }

    int getshortest_roadLength(){//最短経路のノード数を返す
        return shortest_road.size();
    }

    void Route(){//最短経路をshortest_roadに格納
	int k=goal,n;
	while(true){
	    shortest_road.add(k);
	    n=k;
	    k=result_road[n];
	    if(n==start)
		break;
	}
    }
}
