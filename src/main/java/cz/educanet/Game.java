package cz.educanet;

import cz.educanet.models.Square;
import cz.educanet.utils.FileUtils;
import org.lwjgl.opengl.GL33;
import java.util.ArrayList;

public class Game {


    private static ArrayList<Square> square = new ArrayList<Square>();

    private static String[] maze;
    private static float mazeRow;
    private static float mazeCol;

    public static void init(long window) {
        Shaders.initShaders();
        maze = FileUtils.readFile("src/main/resources/maze").split("\n");

        mazeRow = 2.0f / maze[0].length() ;
        mazeCol = 2.0f / maze.length;


        for (int y = 0;y < maze.length;y++){
            String line = maze[y];
            for(int x = 0;x < line.length();x++){
                if (line.charAt(x) == '0') {
                    square.add(new Square((x * mazeRow) * (+1) - 1 ,(y * mazeCol) * (-1) + 1, mazeRow, mazeCol));
                }
            }
        }
    }

    public static void render(long window) {
        GL33.glUseProgram(Shaders.shaderProgramId);

        for (Square square : square){
            square.draw();
        }
    }

    public static void update(long window) {
    }
}

