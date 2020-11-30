//package algo2;
import java.util.ArrayList; 
import java.util.Iterator; 
import java.util.LinkedList; 
import java.util.*;
import java.io.*; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class bfs1 { 
	
static int FOUND=0;
static LinkedList<Integer> foun = new LinkedList<Integer>(); 
 public static void main(String args[]) 
 { 
    System.out.println("WELCOME TO SHORTEST ROUTE ALGOROITHM");
    System.out.println("Do you want a random hospital.txt generated? Y/N");
    Scanner sc=new Scanner (System.in);
    char ch= sc.next().charAt(0);   
    
    ArrayList<Integer> xy = new ArrayList<Integer>(); 
    int v=0, e=0, h=0, k=0; 
    String ver="", ed="";
    ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(v); 

    boolean visited[]={false};

    BufferedReader br = null;
    try {   
        File file = new File("road.txt"); 
        br = new BufferedReader(new FileReader(file)); 
        if(br==null)
            System.out.println("File not found");
        String st; 
        int cou=1; //count lines in the file
        
        while ((st = br.readLine()) != null) 
        {
            if(cou==1 || cou==2 || cou==4)
            {}
            else if(cou==3)
            {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(st);
                int x=0;
                while(m.find()) {
                    if(x==0)
                        ver=(m.group());
                    else
                        ed=(m.group());
                    x+=1;
                }
                v=Integer.parseInt(ver);
                e=Integer.parseInt(ed);
                visited=new boolean[v];
                for (int i = 0; i <v; i++) { 
                    adj.add(new ArrayList<Integer>()); 
                }
            }
            else{
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(st);
                int x=0;
                while(m.find()) {
                    if(x==0)
                        ver=(m.group());
                    else
                        ed=(m.group());
                    x+=1;
                }
                int a=0,b=0;
                a=Integer.parseInt(ver);
                b=Integer.parseInt(ed);
                if(ch=='Y')
                    createList(xy,a,b);
                addEdge(adj, a, b); 
            }
            cou+=1;
        }
    } 
    catch (FileNotFoundException fnfe) 
    {return;} 
    catch (IOException ex) 
    {return;} 
    if(ch=='Y')
    {
        HashSet hs = new HashSet();
        hs.addAll(xy);
        xy.clear();
        xy.addAll(hs);

        ArrayList<Integer> finalList = new ArrayList<Integer>();
        for(int i=0; i<xy.size(); i++)
        {
            if(i%(2)==0)
            {
                int f=xy.get(i);
                finalList.add(f);
            }
        }
        xy=finalList;
        //System.out.println(xy);
        hospFile(xy);
    }
    int hosp[]=new int[1];
    BufferedReader br2 = null;
    try {      

        File file = new File("hospital.txt"); 
        br2 = new BufferedReader(new FileReader(file)); 
        String st; 
        int cou=1;
        while ((st = br2.readLine()) != null) 
        {
            if(cou==1)
            {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(st);
                while(m.find()) {
                    ver=(m.group());
                }
                h=Integer.parseInt(ver);
                hosp= new int[h];
            }
            else{
                int ab=0;
                ab=Integer.parseInt(st);
                hosp[cou-2]=ab;
                visited[hosp[cou-2]]=true;
            }
            cou+=1;
        }
    } 
    catch (FileNotFoundException fnfe) 
    {return;} 
    catch (IOException ex) 
    {return;} 
    //hosp=readHosp();
     System.out.println("Enter the value of k: ");
     k=sc.nextInt();
     int hosp2 []= Arrays.copyOf(hosp,h); 
     int pt=0;
     int flag=0;
     for(int xx=0; xx<v; xx++)
     {
        if(hosp.length>= pt || xx!=hosp2[pt])
        {
            int hosp3 []= Arrays.copyOf(hosp,h);
            printShortestDistance(adj, xx, hosp3, v, k, flag); 
        }
        else
            pt+=1;
        flag+=1;
     }
 } 

 private static void addEdge(ArrayList<ArrayList<Integer>> adj, int i, int j) 
 { 
     adj.get(i).add(j); 
     adj.get(j).add(i); 
 } 
 static void hospFile(ArrayList hello)
    {
        try
        {
            FileWriter fw=new FileWriter("hospital.Txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            int i;
            pw.println("# "+hello.size());
            for(i=0;i<hello.size();i++)
            {
                pw.println(hello.get(i));
                
            }
            pw.close();
            bw.close();
            fw.close();
        }
        catch(FileNotFoundException fnfe)
        {return;}
        catch(IOException ioe)
        {return;}
    }

    private static void createList(ArrayList xy,int a, int b){
        xy.add(a);
        xy.add(a);
    }


 private static void printShortestDistance( ArrayList<ArrayList<Integer>> adj,int s, int dest[], int v, int k, int flag) 
 { 
     int prediction[] = new int[v]; 
     BFS(adj, s, dest, v, prediction, k);
     
     try
        {
            FileWriter fw=new FileWriter("RouteMap.Txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            int i;
            if(flag==0)
                pw.println("Route Length");
            flag+=1;
            while(FOUND!=0)
            {
                FOUND=FOUND-1; 
                LinkedList<Integer> shpath = new LinkedList<Integer>(); 
                int cr = foun.pop(); 
                shpath.add(cr); 
                while (prediction[cr] != -1) { 
                shpath.add(prediction[cr]); 
                    cr = prediction[cr]; 
                } 
                pw.print(shpath.get(shpath.size() - 1)); 
                for (i = shpath.size() - 2; i >= 0; i--) { 
                    pw.print("~"+shpath.get(i)); 
                } 
                pw.print(" "+ (shpath.size()-1));
                pw.println("");
            }
            pw.close();
            bw.close();
            fw.close();
        }
        catch(FileNotFoundException fnfe)
        {return;}
        catch(IOException ioe)
        {return;}

     
 } 
 private static boolean BFS(ArrayList<ArrayList<Integer>> adj, int source,  int dest[], int ver, int pred[], int k) 
 { 
     LinkedList<Integer> queue = new LinkedList<Integer>(); 
     boolean visit[] = new boolean[ver]; 

     for (int i = 0; i < ver; i++) { 
        visit[i] = false; 
         pred[i] = -1; 
     } 
     visit[source] = true; 
     queue.add(source); 

     while (!queue.isEmpty()) { 
         int node = queue.remove(); 
         for (int i = 0; i < adj.get(node).size(); i++) { 
             if (visit[adj.get(node).get(i)] == false) 
             { 
                visit[adj.get(node).get(i)] = true; 
                 pred[adj.get(node).get(i)] = node; 
                 queue.add(adj.get(node).get(i)); 

                 for (int r=0; r<dest.length; r++)
                	 if (adj.get(node).get(i) == dest[r]) 
                	 {
                		 foun.add(dest[r]);
                		 FOUND=FOUND+1;
                		 dest[r]=-1;
                	 }
                if(FOUND==k)
                	return true; 
             } 
         } 
     } 
     return false; 
 } 
} 
