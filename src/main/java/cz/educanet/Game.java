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
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;


public class Game {


    private static ArrayList<Square> squares = new ArrayList<Square>();

    private static String[] maze;
    private static float tileScaleX;
    private static float tileScaleY;
    private static float t = 0f;

    public static void init(long window) {
        // Setup shaders
        Shaders.initShaders();
        //setup maze
        maze = FileUtils.readFile("src/main/resources/maze").split("\n");


        //construct maze
        tileScaleX = 2.0f / maze[0].length() ;
        tileScaleY = 2.0f / maze.length;
        for (int y = 0;y < maze.length;y++){
            String line = maze[y];
            for(int x = 0;x < line.length();x++){
                if (line.charAt(x) == '1') {
                    squares.add(new Square(x*tileScaleX-1,(y*tileScaleY)*-1+1,tileScaleX,tileScaleY));
                }
            }
        }
    }

    public static void render(long window) {
        GL33.glUseProgram(Shaders.shaderProgramId);

        for (Square square : squares){
            square.draw();
        }
    }

    public static void update(long window) {
        t += 0.0015f;
    }
}

