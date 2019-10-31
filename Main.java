import Objects.Failure;
import Objects.Problem;
import Objects.Problems.Puncake.PancakesState;
import Objects.Problems.Result;
import Objects.Solution;
import Searchers.BestFirstSearch.Astar;
import Searchers.BestFirstSearch.BreadthFirstSearch;
import Searchers.BestFirstSearch.Dijkstra;
import Searchers.BestFirstSearch.PureHeuristicSearch;
import Searchers.BiDirectionalSearch.BloomFilter.BloomFilterSearch;
import Searchers.BiDirectionalSearch.FractionalMeetInTheMiddle;
import Searchers.BiDirectionalSearch.MeetInTheMiddle;
import Searchers.DepthFirstSearch.IterativeDeepeningAstar;
import Searchers.DepthFirstSearch.IterativeDeepeningDepthFirstSearch;
import Searchers.Searcher;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        int seconds = 10;
        int pancakes = 6;
        boolean heuristics = true;
        testAll(seconds,pancakes, heuristics);

    }

    private void testBloom(){
        PancakesState pancakesState = new PancakesState(true,8);
        Problem problem = new Problem(pancakesState,pancakesState.getPerfectState());
        IterativeDeepeningAstar iterativeDeepeningAstar = new IterativeDeepeningAstar();
        BloomFilterSearch bloomFilterSearch = new BloomFilterSearch((int)(Math.pow(10,6)),32*pancakesState.getSize());
        System.out.println(bloomFilterSearch.solve(problem));
    }

    private static void testAll(int seconds, int pancakes, boolean heuristics) {
        List<Searcher> searchers = new ArrayList<>();

        searchers.add(new IterativeDeepeningDepthFirstSearch());
        searchers.add(new BreadthFirstSearch());
        searchers.add(new Dijkstra());
        searchers.add(new MeetInTheMiddle());
        searchers.add(new FractionalMeetInTheMiddle(0.66));
        searchers.add(new FractionalMeetInTheMiddle(0.33));
        searchers.add(new PureHeuristicSearch());
        searchers.add(new Astar());
        searchers.add(new IterativeDeepeningAstar());
        //searchers.add(new BloomFilterSearch(1000,100));

        testSearchers(seconds,pancakes,searchers,heuristics);
    }


    private static void testSearchers(int numberOfSeconds, int numberOfPancakes, List<Searcher> searchers, boolean heuristics){
        PancakesState pancakesState = new PancakesState(heuristics,numberOfPancakes);
        Problem problem = new Problem(pancakesState,pancakesState.getPerfectState());
        ExecutorService service = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                numberOfSeconds, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());;
        List<Result> results = new ArrayList<>();
        for(Searcher searcher:searchers){
            ProblemSolver problemSolver =  new ProblemSolver(problem,searcher);
            Future<Solution> future = service.submit(problemSolver);
            try {
                Solution solution = future.get(numberOfSeconds,TimeUnit.SECONDS);
                results.add(solution);
            } catch (TimeoutException e){
                int milliseconds = numberOfSeconds * 1000;
                Failure failure = new Failure(milliseconds,searcher.toString());
                results.add(failure);
                future.cancel(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        service.shutdownNow();
        writeSolution(results);
    }

    private static void writeSolution(List<Result> solutions) {
        solutions.sort(Comparator.comparingDouble(Result::getTime));
        try {
            FileWriter fileWriter = new FileWriter("last_test");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            Date date = new Date();
            bufferedWriter.write(date.toString());
            for (Result result : solutions){
                bufferedWriter.newLine();
                bufferedWriter.newLine();
                bufferedWriter.write(result.toString());
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
