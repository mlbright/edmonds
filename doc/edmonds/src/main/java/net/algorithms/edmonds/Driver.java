package net.algorithms.edmonds;

import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Driver {  
    public AdjacencyList adj;
    public Edmonds ed;
    public Map<String, Node> nodes;

    public Driver() {
        this.adj = new AdjacencyList();
        this.ed = new Edmonds();
        this.nodes = new HashMap<String, Node>();
    }

    private boolean readFile(String fileName) {
        if (fileName == null) {
            return false;
        }
        try {
            BufferedReader br  = new BufferedReader(new FileReader(fileName));
            String currLine;            
            while((currLine = br.readLine()) != null) {
                parseMachine(currLine);
            }
        } catch (FileNotFoundException fnfe) {           
            fnfe.printStackTrace();
            return false;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        } catch (Exception e) {         
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    private void parseMachine(String line){
        String[] arr = line.split("\\s+");
        if (arr.length < 4) {
            System.err.println("Incorrect edge specification");
            return;
        }
        String index = arr[0].substring(1);
        String start = arr[1].substring(1);
        Node source;
        if (this.nodes.containsKey(start)) {
            source = this.nodes.get(start);
        } else {
            source = new Node(Integer.parseInt(start));
            this.nodes.put(start,source);
        }
        String end = arr[2].substring(1);
        Node dest;
        if (this.nodes.containsKey(end)) {
            dest = this.nodes.get(end);
        } else {
            dest = new Node(Integer.parseInt(end));
            this.nodes.put(end,dest);
        }
        int cost = Integer.parseInt(arr[3]);
        this.adj.addEdge(source,dest,cost);
    }

    public static void main(String [] args) {
        if (args.length != 2) {
            System.out.println("Please provide a input file and a root node!!");
            return;
        }
        String fileName = args[0];
        String root = args[1];
        Driver driver = new Driver();
        if (driver.readFile(fileName)) {
            Node n = driver.nodes.get(root);
            AdjacencyList result = driver.ed.getMinBranching(n,driver.adj);
            for (Edge e : result.getAllEdges()) {
                String out = Integer.toString(e.from.name) + '-' + Integer.toString(e.to.name);
                System.out.println(out);
            }
        } else {
            System.out.println("Please provide a vaild file!!");
        }
    }
}
