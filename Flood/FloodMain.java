package Flood;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Author: Mher Khalatyan
 * Date: June 24, 2022
 * Description:A storm is coming and water levels are rising. Sandbags stop the water from coming in.
 *  When the water meets land, it floods the land and will continue to do so until a sandbag is reached.
 *  Write a program that will read a map in from a file. The first line in the file represents the number of rows, and the second line in the file represents the number of columns.
 *  Read the data in file into a 2D array.
 *  Create a GUI to display the map visually to the user. Water cells should be blue, land cells should be green, sandbags should be brown, and the home should be red.
 *  Include a button called ‘Stop the Flood’. When the button is clicked, determine if the house is safe from the flood.
 *  Find the water cell in the bottom row of the map and call a recursive method that will turn all cells touching the water cell to water except for sandbags.
 *  Show changes on the GUI as they occur.
 *  Determine if the house is safe from flood and update the GUI with a message indicating the result
 */
public class FloodMain{

    public static void main(String[] args) throws IOException{
        File file = new File("input.txt");//name of the file input
        Scanner sc = new Scanner(file);
        int row;
        int col;
        row = Integer.parseInt(sc.nextLine());//get the rows
        col = Integer.parseInt(sc.nextLine());//get the cols
        int[][] grid = new int[row][col];//make the grid with the dimensions of the row and col
        String cellAssigner;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    cellAssigner = sc.next();

                    if(cellAssigner.equals("L")){//if the letter is L(land) make the value 1
                        grid[i][j] = 1;
                    }else if(cellAssigner.equals("W")){//if the letter is W(water) make the value 2
                        grid[i][j] = 2;
                    }else if(cellAssigner.equals("S")){//if the letter is S(sandbag) make the value 3
                        grid[i][j] = 3;
                    }else if(cellAssigner.equals("H")){//if the letter is H(house) make the value 4
                        grid[i][j] = 4;
                    }
                }

            }
        FloodGUI gui = new FloodGUI(row, col, grid);
        
    }
}
