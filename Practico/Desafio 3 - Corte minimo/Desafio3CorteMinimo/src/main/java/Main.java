import structure.Arc;
import structure.Node;
import structure.UndirectedGraph;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String graphPath = "D:\\Repositorio DLC-4K6-2021\\DLC-4K6-2021\\Practico\\Desafio 3 - Corte minimo\\graph.txt";
        File file = new File(graphPath);

        UndirectedGraph<String> undirectedGraph = new UndirectedGraph<>(true);
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while(bufferedReader.ready()){
                String line = bufferedReader.readLine();
                if(line != null && !line.isEmpty()){
                    String [] tokens = line.split("\\s");
                    String node = tokens[0];
                    undirectedGraph.add(node);
                    for(int i = 1; i < tokens.length; i++){
                        String end = tokens[i];
                        undirectedGraph.addArc(node, end, true);
                    }
                }

            }
            System.out.println("Se cargó todo");
            System.out.println("Tamaño del grafo: " + undirectedGraph.countNodes() + " nodos");

        }catch (IOException ioException){
            System.out.println("No existe el archivo changoo, o no lo pudo leer");
            ioException.printStackTrace();

        }

    }

    public static int corteMinimo(UndirectedGraph<String> undirectedGraph){

        if(undirectedGraph.countEdges() == 0) return -1;

        int corteMinimo =0;
        UndirectedGraph<String> auxiliar = null;
        try{
            int t = (int)((undirectedGraph.countEdges() * undirectedGraph.countEdges()) * Math.log(undirectedGraph.countNodes()));

            for(int i = 0; i<= t; i++){
                auxiliar= (UndirectedGraph<String>) this.clone();
                while(auxiliar.countNodes() > 2){
                    Arc<String> arc = auxiliar.getRandomArc();
                    Node<String> newNode = new Node(arc.getInit()+"-"+arc.getEnd());
                    List<Arc<String>> initNodeArcs = arc.getInit().getArcs();
                    List<Arc<String>> endNodeArcs = arc.getEnd().getArcs();
                    List<Arc<String>> newInitNodeArcs = new LinkedList<>();
                    List<Arc<String>> newEndNodeArcs = new LinkedList<>();

                    for(Arc<String> auxArc : initNodeArcs){
                        if(!auxArc.getEnd().equals(arc.getEnd())){
                            auxiliar.addArc(newNode, auxArc.getEnd(), true);
                        }
                    }
                    for(Arc<String> auxArc: endNodeArcs){
                        if(!auxArc.getInit().equals(arc.getInit())){
                            auxiliar.addArc(arc.getInit(),newNode,true);
                        }
                    }
                    



                }
            }

        } catch (Exception e){
            return -1;
        }

    }
}
