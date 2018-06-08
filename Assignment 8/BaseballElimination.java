/******************************************************************************
 *  Compilation:  javac-algs4 BaseballElimination.java
 *  Execution:    java-algs4 BaseballElimination
 *  Dependencies:
 *
 *
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.SeparateChainingHashST;

public class BaseballElimination {
    private int n;      // number of teams
    private FlowNetwork flowNetwork;
    private SeparateChainingHashST<String, Integer> teams;
    private int[] w;    // wins
    private int[] r;    // losses
    private int[] l;    // remaining games
    private int[][] g;  // g[i][j] games left to play against team j

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        if (!in.isEmpty()) {
            n = in.readInt();
            teams = new SeparateChainingHashST<>();
            w = new int[n];
            r = new int[n];
            l = new int[n];
            g = new int[n][n];
            for (int i = 0; i < n; i++) {
                teams.put(in.readString(), i);
                w[i] = in.readInt();
                l[i] = in.readInt();
                r[i] = in.readInt();
                for (int j = 0; j < 4; j++) {
                    g[i][j] = in.readInt();
                }
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return n;
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keys();
    }

    private void checkTeamValid(String team) {
        if (!teams.contains(team)) throw new IllegalArgumentException("Invalide team");
    }

    // number of wins for given team
    public int wins(String team) {
        checkTeamValid(team);
        return w[teams.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        checkTeamValid(team);
        return l[teams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        checkTeamValid(team);
        return r[teams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        checkTeamValid(team1);
        checkTeamValid(team2);
        return g[teams.get(team1)][teams.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        checkTeamValid(team);

        int givenTeam = teams.get(team);
        if (isTrivialElimination(givenTeam)) return true;

        // (n - 1) * (n - 1 - 1) / 2 + n - 1 + 2
        int v = (n - 1) * (n - 2) / 2 + n + 1;
        flowNetwork = new FlowNetwork(v);
        int k = 1, l = 0, m = 0;
        for (int i = 0; i < n; i++) {
            if (i == givenTeam) continue;
            for (int j = i + 1; j < n; j++) {
                if (j == givenTeam) continue;
                flowNetwork.addEdge(new FlowEdge(0, k, g[i][j]));
                flowNetwork.addEdge(new FlowEdge(k, v - n + l, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(k, v + 1 - n + m, Double.POSITIVE_INFINITY));
                k++;
                m++;
            }
            flowNetwork.addEdge(new FlowEdge(v - n + l, v - 1, w[givenTeam] + r[givenTeam] - w[i]));
            l++;
            m = l;
        }
        StdOut.println(flowNetwork.toString());
        return true;
    }

    // check if the given team is trivial elimination
    private boolean isTrivialElimination(int team) {
        for (int i = 0; i < n; i++) {
            if (i != team) {
                if (w[team] + r[team] < w[i]) return true;
            }
        }
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        checkTeamValid(team);
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        // for (String team : division.teams()) {
            // if (division.isEliminated(team)) {
            //     StdOut.print(team + " is eliminated by the subset R = { ");
            //     for (String t : division.certificateOfElimination(team)) {
            //         StdOut.print(t + " ");
            //     }
            //     StdOut.println("}");
            // }
            // else {
            //     StdOut.println(team + " is not eliminated");
            // }
        // }
        // StdOut.println("Wins of Montreal: " + division.wins("Montreal"));
        // StdOut.println("Losses of Philadelphia: " + division.losses("Philadelphia"));
        // StdOut.println("Remaining of Atlanta: " + division.remaining("Atlanta"));
        // StdOut.println("Montreal against Atlanta: " + division.against("Montreal", "Atlanta"));
        StdOut.println(division.isEliminated("Ghaddafi"));
    }
}