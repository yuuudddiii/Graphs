import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    double [] [] edges;
    
    Graph (double [] [] a) 
    {
        edges= new double [a.length][a.length];
        for (int i=0;i<a.length;i++)
            for (int j=0;j<a.length;j++)
                edges[i][j]=a[i][j];
    }

    public HashSet <Integer> neighbours(int v) // neighbours of the v'th value of the matrix
    {
        HashSet <Integer> h = new HashSet <Integer> ();
        for (int i=0;i<edges.length;i++) if (edges[v][i]!=-1) h.add(i);
        return h;
    }

    public HashSet <Integer> vertices() // returns the vertices present in the Graph
    {
        HashSet <Integer> h = new HashSet <Integer>();
        for (int i=0;i<edges.length;i++) h.add(i);
        return h;
    }
    
    ArrayList <Integer> addToEnd (int i, ArrayList <Integer> path)
    // returns a new path with i at the end of path
    {
        ArrayList <Integer> k;
        k=(ArrayList<Integer>)path.clone();
        k.add(i);
        return k;
    }
    
    public int findSmallest(HashMap <Integer,Double> t)
    {
        Object [] things= t.keySet().toArray();
        double val=t.get(things[0]);
        int least=(int) things[0];
        Set <Integer> k = t.keySet();
        for (Integer i : k)
        {
            if (t.get(i) < val)
            {
                least=i;
                val=t.get(i);
            }
        }
        return least;
    }
    
    public ArrayList <Integer> dijkstra (int start, int end, int maxJunctions) {
        int N = vertices().size(); // determine the number of vertices in the Graph
        int [] [] tShortest = new int [N][N]; // array that contains the lowest number of junctions to the columnd index from the row index
        ArrayList<Integer> vertexPriority = new ArrayList<Integer>(); 
        int junctions = 0;
        HashSet<Integer> S = new HashSet<Integer>();    
        S.add(start); 
        HashMap <Integer, Double> Q = new HashMap <Integer, Double>();
        for (int i = 0; i<N; i++) Q.put(i, Double.POSITIVE_INFINITY); // set path weight to all vertices to infinity
        Q.put(start, 0.0); // start path with weight 0
        
        ArrayList <Integer> [] paths = new ArrayList[N];
        for (int i = 0; i<Q.size(); i++) {
            ArrayList <Integer> path = new ArrayList<Integer>();
            path.add(start);
            paths[i] = path;
        }
        //!Q.isEmpty()
        while (!Q.isEmpty()) {
        	int v = start;
        	junctions += 1;
        	v = findSmallest(Q); //find lowest weight vertex
            if (v == end && Q.get(v) != Double.POSITIVE_INFINITY) // check if the end has been reached and weigth has been determined
                return paths[end];
            else {
                double w = Q.get(v);
                S.add(v);
                for (Integer u : neighbours(v)) { // check through the neighbours of v
                        if (!S.contains(u)) { //S does not yet contain u
                            double w1 = edges[v][u]+ w; // add the neighbour's weight to the current path's weight
                            if (w1 < Q.get(u) && paths[v].size() < maxJunctions) { // if that weight is less than the weight to that vertex in Q 
                                Q.put(u, w1); // replace the weight to that vertex in Q
                                paths[u] = addToEnd(u,paths[v]); // add (the path to that point) in paths
                        }
                    }
                }
                Q.remove(v);
            }
            
        }
        return new ArrayList <Integer> ();
    }
    
    public ArrayList<Integer> Traverse (int start, int end, int maxJunctions) {
    	int N = vertices().size();
    	double [] [] tShortest = new double [N][N];
    	HashMap <Integer, Double> Q = new HashMap <Integer, Double>();
    	for (int i = 0; i<N; i++) Q.put(i, Double.POSITIVE_INFINITY); // set path weight to all vertices to infinity
    	Q.put(start, 0.0); // start path with weight 0
    	for (double [] i : tShortest) {
    		for (int j = 0; j<i.length; j++) {
    			i[j] = Double.POSITIVE_INFINITY;
    		}
    	}
    	tShortest[0][start] = 0;
    	ArrayList <Integer> [][] paths = new ArrayList[N][N];
        for (int i = 0; i<N; i++) {
        	for (int m = 0; m<N; m++) {
            ArrayList <Integer> path = new ArrayList<Integer>();
            path.add(start);
            paths[0][i] = path;
        	}
        }
        int junction = 0;
        int v = start;
        for (int i = 0; i<tShortest.length; i++) {
        	junction+=1;
        	double w = Q.get(v);
        	if (v == end) return paths[junction][end];
        	if (junction == maxJunctions) {
        		double lowest = 0;
        		int junct = 0;
        		for (int j = 0; j<paths.length; j++) {
        			if (lowest > tShortest[j][end]) lowest = tShortest[j][end];
        			junct = j;
        		}
        		return paths[junct][end];
        	}
        	else {
        		for (Integer u : neighbours(v)) {
        			if (tShortest[junction][u] > tShortest[junction][v]) {
        				double w1 = edges[v][u] + w;
        				tShortest[junction][u] = w1;
        				paths[junction][u] = addToEnd(u,paths[junction-1][v]); // add (the path to that point) in paths
        			}
        		}
        	}
        	v =findSmallest(Q);
        }
        	
        return new ArrayList<Integer>();
        	
    }
    
    public double totalWeight(ArrayList<Integer> path) {
        double totalWeight = 0;
        Iterator<Integer> iterator = path.iterator();
        int [] arr = new int[path.size()];
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = iterator.next().intValue();
        }
                
        for (int i = 0; i<arr.length-1; i++) {
            totalWeight += (edges[arr[i]][arr[i+1]]);
        }
        
        return totalWeight;
    }
    
}