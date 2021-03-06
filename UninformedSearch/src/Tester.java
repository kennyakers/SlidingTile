
import java.io.Console;

/**
 * Kenny Akers and Aidan Chandra AI 9/10/18
 *
 * Sliding tile solver using informed search (RBFS)
 *
 */
public class Tester {

    private static String startState;
    private static int dimension;
    private static boolean debug;
    private static boolean override;
    private static boolean statistics;
    private static boolean greedy;

    /*
    Required Arguments:
        -dimension <n> Generates a random board with dimension nxn
            or
        -state [<state>]     Generates a state based off of the provided string

    Optional Arguments:
        -override          Will attempt to solve from an unsolvable start state
        -verbose           Toggles on verbose output
     */
    public static void main(String[] args) {

        Console console = System.console();
        String option = "";
        dimension = 0;
        startState = "";
        debug = false;
        override = false;
        statistics = false;
        greedy = false;

        if (console == null) {
            System.err.println("No console");
            return;
        }

        for (String arg : args) {
            switch (arg) {
                case "-dimension":
                    option = arg;
                    continue;
                case "-state":
                    option = arg;
                    continue;
                case "-override":
                    override = true;
                    continue;
                case "-verbose":
                    debug = true;
                    continue;
                case "-stats":
                    statistics = true;
                    continue;
                case "-greedy":
                    greedy = true;
                    continue;
            }

            switch (option) {
                case "-dimension":
                    try {
                        dimension = Integer.parseInt(arg);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid dimension: " + arg);
                    }
                    break;
                case "-state":
                    startState += arg + ",";
                    break;
            }
        }

        if (debug) {
            System.out.println("Start state: " + startState);
            System.out.println("Dimension: " + dimension);
            System.out.println("Override: " + override);
            System.out.println("Statistics: " + statistics);
            System.out.println("Greedy: " + greedy);
        }
        Board board;

        if (!startState.isEmpty()) {
            String[] tiles = startState.split(",");
            int[] tileValues = new int[tiles.length];

            if (tileValues.length == Math.pow((int) Math.sqrt(tileValues.length), 2)) {
                if (debug) {
                    System.out.println("Start state inputted.");
                }
                for (int i = 0; i < tiles.length; i++) {
                    tileValues[i] = Integer.parseInt(tiles[i]);
                }
                board = new Board(tileValues, override, debug);
            } else {
                if (debug) {
                    System.out.println("Not enough values provided for valid board. Defaulting to random start state.");
                }
                board = new Board(dimension, override, debug);
            }
        } else {
            if (debug) {
                System.out.println("Generating new " + dimension + "x" + dimension + " state");
            }
            board = new Board(dimension, override, debug);
        }
        GameState sol;
        if (greedy) {
            sol = GreedyBestFirstSearch.bestFirstSearch(board, GreedyBestFirstSearch.HeuristicMethods.MANHATTAN, debug);
        } else {
            sol = InformedSearch.bestFirstSearch(board, InformedSearch.HeuristicMethods.MANHATTAN, debug);
        }
        Board solution = (Board) sol;
        if (statistics) {
            System.out.println("STATISTICS");
            System.out.println("Goal State Depth: " + InformedSearch.goalStateDepth);
            System.out.println("Max Depth Explored: " + InformedSearch.maxDepth);
        }

        System.out.println("\nSolution:");
        solution.print();

    }

}
