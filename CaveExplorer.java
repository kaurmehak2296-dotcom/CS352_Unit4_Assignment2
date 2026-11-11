import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CaveExplorer {
	private char[][] cave;
	private String path = "";
	// Step 1: Zero parameter constructor to create the specified cave
	public CaveExplorer() {
	    cave = new char[][] {
	        {'R', 'R', 'R', 'R', 'R', 'R'},
	        {'R', '.', '.', 'S', 'R', 'R'},
	        {'R', '.', 'R', 'R', 'R', 'R'},
	        {'R', '.', 'M', 'R', 'R', 'R'},
	        {'R', 'R', 'R', 'R', 'R', 'R'}
	    };
	}
	// Step 2: toString method
	@Override
	public String toString() {
	    String result = "";

	    for (int row = 0; row < cave.length; row++) {
	        for (int col = 0; col < cave[row].length; col++) {
	            result += cave[row][col];
	        }
	        result += "\n";
	    }

	    return result;
	}
	// Step 3: solve method
	public boolean solve() {
	    path = "";

	    int row = -1;
	    int col = -1;

	    // Find the starting position.
	    for (int r = 0; r < cave.length; r++) {
	        for (int c = 0; c < cave[r].length; c++) {
	            if (cave[r][c] == 'S') {
	                row = r;
	                col = c;
	            }
	        }
	    }

	    if (row == -1) {
	        return false;
	    }

	    boolean[][] visited =
	            new boolean[cave.length][cave[0].length];

	    // North, east, south, west.
	    int[] rowChange = {-1, 0, 1, 0};
	    int[] colChange = {0, 1, 0, -1};
	    char[] direction = {'n', 'e', 's', 'w'};

	    while (true) {
	        visited[row][col] = true;
	        boolean moved = false;

	        for (int i = 0; i < direction.length; i++) {
	            int nextRow = row + rowChange[i];
	            int nextCol = col + colChange[i];

	            // Skip positions outside the cave.
	            if (nextRow < 0 || nextRow >= cave.length
	                    || nextCol < 0
	                    || nextCol >= cave[nextRow].length) {
	                continue;
	            }

	            char next = cave[nextRow][nextCol];

	            if (!visited[nextRow][nextCol]
	                    && (next == '.' || next == 'M')) {
	                boolean foundPool = next == 'M';

	                // Leave a mark behind and move S forward.
	                cave[row][col] = '+';
	                row = nextRow;
	                col = nextCol;
	                cave[row][col] = 'S';

	                path += direction[i];
	                moved = true;

	                if (foundPool) {
	                    return true;
	                }

	                break;
	            }
	        }

	        // A dead end means the pool cannot be reached.
	        if (!moved) {
	            path = "";
	            return false;
	        }
	    }
	}
	// Step 4: getPath method
	public String getPath() {
	    return path;
	}
	
	// Step 5: One parameter constructor to read from a file
	public CaveExplorer(String fname) throws FileNotFoundException {
	    try (Scanner in = new Scanner(new File(fname))) {
	        int rows = in.nextInt();
	        int cols = in.nextInt();
	        in.nextLine();

	        cave = new char[rows][cols];

	        for (int row = 0; row < rows; row++) {
	            String line = in.nextLine();

	            for (int col = 0; col < cols; col++) {
	                cave[row][col] = line.charAt(col);
	            }
	        }
	    }
	}
	
	public static void main(String[] args) {
	    System.out.println("CAVE 1: Hardcoded cave");

	    CaveExplorer ce1 = new CaveExplorer();
	    System.out.println("Starting layout:");
	    System.out.print(ce1);

	    boolean solved1 = ce1.solve();

	    System.out.println("Final layout:");
	    System.out.print(ce1);
	    System.out.println("Pool found: " + solved1);
	    System.out.println("Path: " + ce1.getPath());

	    System.out.println("\nCAVE 2: File cave");

	    try {
	        CaveExplorer ce2 = new CaveExplorer("testdat.txt");

	        System.out.println("Starting layout:");
	        System.out.print(ce2);

	        boolean solved2 = ce2.solve();

	        System.out.println("Final layout:");
	        System.out.print(ce2);
	        System.out.println("Pool found: " + solved2);
	        System.out.println("Path: " + ce2.getPath());
	    } catch (FileNotFoundException e) {
	        System.out.println("Cannot find testdat.txt.");
	    }
	}
}