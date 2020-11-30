//package DataStructure.Rachita;
import java.util.ArrayList; 
import java.util.Iterator; 
import java.util.LinkedList; 
import java.util.*;
import java.io.*; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class bfs_multi_final { 

    static int FOUND=0;
    static LinkedList<Integer> foun = new LinkedList<Integer>(); 
    

    public static void main(String args[]) 
    { 
        System.out.println("WELCOME TO SHORTEST ROUTE ALGOROITHM");
        System.out.println("Do you want a random hospital.txt generated? Y/N");
        Scanner sc=new Scanner (System.in);
        char ch= sc.next().charAt(0);   
        
        ArrayList<Integer> xy = new ArrayList<Integer>(); 
        int v=0, e=0, h=0; 
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
                    visited=new boolean[e];
                    for (int i = 0; i <e; i++) { 
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
        int hosp3 []= Arrays.copyOf(hosp,h);
        printShortestDistance(adj,visited, hosp3); 

    } 

    static void hospFile(ArrayList hello)
    {
        try
        {
            FileWriter fw=new FileWriter("hospital.txt");
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


    private static void addEdge(ArrayList<ArrayList<Integer>> adj, int i, int j) 
    { 
        adj.get(i).add(j); 
        adj.get(j).add(i); 
    } 

    private static void printShortestDistance(ArrayList<ArrayList<Integer>> adj,boolean visited[], int dest[]) 
    {
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
        for(int i=0;i<dest.length;i++)
            queue.add(dest[i]);
        Multisource_BFS(adj,queue,visited);
    } 

    static void Multisource_BFS(ArrayList<ArrayList<Integer>> adj,LinkedList<Integer> q, boolean visited[]) 
    { 
        String storePath[]=new String[visited.length];
        int spi=0;
        while(!q.isEmpty()) 
        {
            LinkedList<Integer> que= new LinkedList<Integer>(); 
            que = (LinkedList) q.clone(); 
            q.clear();
            while(!que.isEmpty()) 
            { 
                int k = que.pop();
                String path="";
                for(int i:adj.get(k))
                {  
                    if(!visited[i]) 
                    { 
                        q.push(i);
                        path=i+"~"+k;
                        if(spi==0)
                            storePath[spi]=path;
                        else
                        {
                            int pos=searchData(storePath,path);
                            if(pos==-1)
                                storePath[spi]=path;
                            else
                                storePath[spi]=path.substring(0,path.indexOf('~')) +"~"+ storePath[pos];
                        }
                        spi++;
                        visited[i] = true; 
                    } 
                } 
                path="";
            } 
        } 
        
        storeInFile(storePath);
    }

    static int searchData(String storePath[],String path)
    {
        int i;
        for(i=0;i<storePath.length;i++)
        {
            if(storePath[i]!=null)
            {
                if(storePath[i].startsWith(path.substring(path.lastIndexOf('~')+1)))
                    return i;
            }
        }
        return -1;
    }

    static void storeInFile(String finalpath[])
    {
        try
        {
            FileWriter fw=new FileWriter("RouteMap.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            int i;
            pw.println("Route Length");
            for(i=0;i<finalpath.length;i++)
            {
                if(finalpath[i]!=null)
                {
                    String [] parts= (finalpath[i]).split("~");
                    pw.println(finalpath[i]+" "+((parts.length)-1));
                }
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
}