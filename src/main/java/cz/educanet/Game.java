package cz.educanet;

import cz.educanet.models.Square;
import cz.educanet.utils.FileUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;


import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Game {

    private static Square square;
    private static float squareSize; //Square size
    private static String mazeNumbers;
    private static int mazeWidth;
    private static int mazeHeight;


    public static void init(long window) {
        squareSize = 1.0f/((float)mazeWidth/2);
        float[] vertices = {  //square origin point is in Bottom Left
                x + squareSize  ,y + squareSize, 0.0f, // 0 -> Top    Right
                x + squareSize  ,y             , 0.0f, // 1 -> Bottom Right
                x               ,y             , 0.0f, // 2 -> Bottom Left
                x               ,y + squareSize, 0.0f, // 3 -> Top    Left
        };

        float[] colors = {
                1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 0.0f,
        };

        int[] indices = {
                0, 1, 3, // First triangle
                1, 2, 3 // Second triangle
        };

        square = new Square(vertices, indices, colors);

        // Setup shaders
        Shaders.initShaders();
    }

    public static void readMaze(){
        String mazeLoc = "src/main/resources/maze";
        File mazeFile = new File(mazeLoc);


        if(mazeFile.exists() && mazeFile.canRead()){
            mazeNumbers = FileUtils.readFile(mazeLoc);
        }




        mazeWidth = mazeNumbers.indexOf("\n");
        System.out.println("Maze Rows: " + mazeHeight + "\nMaze Cols: " + mazeWidth);

        try (Stream<Path> files = Files.list(Paths.get("src/main/resources/maze"))) {
            mazeHeight = (int) files.count();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void render(long window) {
        GL33.glUseProgram(Shaders.shaderProgramId);

        // Draw using the glDrawElements function

        GL33.glBindVertexArray(square.vaoId);
        GL33.glDrawElements(GL33.GL_TRIANGLES, square.indices.length, GL33.GL_UNSIGNED_INT, 0);
    }

    public static void update(long window) {

    }

}

