import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aufgabe5 {

    private final String[] args;
    private Map<Integer, List<Integer>> vertices = new HashMap<>();
    private Map<Integer, List<Integer>> altV = new HashMap<>();
    private int[][] edgesList;
    private int[][] p1Paths;
    private int[][] p2Paths;

    public static void main(String[] args) {
        new Aufgabe5(args);
    }

    public Aufgabe5(String[] _args){
        //args = _args;
        args = new String[]{"beispiele/huepfburg0.txt"};
        readInput();
        System.out.println(vertices);
        findPaths(1);
    }

    private void findPaths(int start){
        List<int[]> currentPath = new ArrayList<>();
        int[] used = new int[edgesList.length];
        currentPath.add(new int[]{start, 0});
        while (!currentPath.isEmpty()){
            List<Integer> branches = vertices.get(currentPath.get(currentPath.size() - 1)[0]);
            int currentBranch = currentPath.get(currentPath.size() - 1)[1];
            if (currentBranch >= branches.size()){
                currentPath.remove(currentPath.size() - 1);
                continue;
            }
            System.out.println(Arrays.toString(edgesList[branches.get(currentBranch)]));
            currentPath.get(currentPath.size() - 1)[1]++;
            if (used[branches.get(currentBranch)] >= 2)
                continue;
            used[branches.get(currentBranch)]++;
            currentPath.add(new int[]{edgesList[branches.get(currentBranch)][1], 0});
        }
    }

    //Read input using the passed arguments
    private void readInput(){
        if (args.length < 1){
            System.out.println("Syntax: Aufgabe5 <Pfad zur Eingabedatei>");
            System.exit(0);
        }
        File inputFile = new File(args[0]);
        if (!inputFile.exists()){
            System.out.println("Datei existiert nicht.");
            System.exit(0);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8))){

            int edges = parseToIntArray(br.readLine())[1];
            edgesList = new int[edges][2];
            for (int i = 0; i < edges; i++) {
                int[] line = parseToIntArray(br.readLine());
                edgesList[i] = line;
                if (!vertices.containsKey(line[0])){
                    vertices.put(line[0], new ArrayList<>());
                }
                vertices.get(line[0]).add(i);
            }
    }
        catch (IOException e){
            System.out.println("Error while loading input file.");
            System.exit(0);
        }

    }

    //Parse line of numbers to and integer array
    private int[] parseToIntArray(String line){
        return Arrays.stream(line
                        .split("(?!^)[^0-9']"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
