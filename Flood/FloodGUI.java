package Flood;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Author: Mher Khalatyan
 * Date: June 24, 2022
 * Description:A storm is coming and water levels are rising. Sandbags stop the water from coming in.
 * When the water meets land, it floods the land and will continue to do so until a sandbag is reached.
 * Write a program that will read a map in from a file. The first line in the file represents the number of rows, and the second line in the file represents the number of columns.
 * Read the data in file into a 2D array.
 * Create a GUI to display the map visually to the user. Water cells should be blue, land cells should be green, sandbags should be brown, and the home should be red.
 * Include a button called ‘Stop the Flood’. When the button is clicked, determine if the house is safe from the flood.
 * Find the water cell in the bottom row of the map and call a recursive method that will turn all cells touching the water cell to water except for sandbags.
 * Show changes on the GUI as they occur.
 * Determine if the house is safe from flood and update the GUI with a message indicating the result
 */
public class FloodGUI extends JFrame implements ActionListener{

    private int row;//rows
    private int col;//cols
    private int[][] grid;//grid with values
    private Color landColor = new Color(127, 234, 112),//custom colors for each letter/label
            waterColor = new Color(110, 197, 253),
            sandColor = new Color(253, 175, 133),
            houseColor = new Color(255, 0, 0);
    JLabel[][] gridForGUI;//the grid where the label will be presented on the GUI
    public JButton stopFlood = new JButton("Stop The Flood!");//button to start the execution of the program

    public FloodGUI(int row, int col, int[][] grid) {
        this.row = row;
        this.col = col;
        this.grid = grid;
        gridForGUI = new JLabel[row][col];
        //GUI part
        JPanel panelForGrid = new JPanel();//panel for the grid to be at the top
        JPanel buttonPanel = new JPanel();//panel for the button to be at the bottom of the gui/under the grid
        GridLayout gridLayout = new GridLayout(row, col);
        BoxLayout boxLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
        BoxLayout framie = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);
        panelForGrid.setLayout(gridLayout);
        buttonPanel.setLayout(framie);
        setSize(400, 440);
        setTitle("Stop the Flood");
        setLayout(boxLayout);
        /**
         * Making the JLabels and assigning a color to each value
         */

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.gridForGUI[i][j] = new JLabel();//create a new JLabel
                this.gridForGUI[i][j].setOpaque(true);
                this.gridForGUI[i][j].setHorizontalAlignment(JLabel.CENTER);//make the text be in the center
                if (grid[i][j] == 1) {//if the grid value is 1 make it have the land attributes
                    this.gridForGUI[i][j].setText("L");
                    this.gridForGUI[i][j].setBackground(landColor);
                } else if (grid[i][j] == 2) {//if the grid value is 2 make it have water attributes
                    this.gridForGUI[i][j].setText("W");
                    this.gridForGUI[i][j].setBackground(waterColor);
                } else if (grid[i][j] == 3) {//if the grid value is 3 make it have sand attributes
                    this.gridForGUI[i][j].setText("S");
                    this.gridForGUI[i][j].setBackground(sandColor);
                } else if (grid[i][j] == 4) {//if the grid value is 4 make it have house attributes
                    this.gridForGUI[i][j].setText("H");
                    this.gridForGUI[i][j].setBackground(houseColor);
                }
                panelForGrid.add(this.gridForGUI[i][j]);//add the label to the top panel
            }
        }
        add(panelForGrid);//add the panel to the GUI
        stopFlood.addActionListener(this);//add actionlistener to the button
       buttonPanel.add(stopFlood);//add button to the bottom panel
       add(buttonPanel);//add the panel to the GUI
        setVisible(true);//to make the GUI window visible
    }
   public void actionPerformed(ActionEvent event){
        Object source = event.getSource();

        if(source.equals(stopFlood)){
            int counter = 0;
            int startingRow = 0;
            int startingCol = 0;
            int findWater;
            //find water's starting row and column values to see where the recursive method will start
            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    findWater = grid[i][j];//temporary placeholder
                    if(findWater==2){//if the value is the same value as the water
                        startingRow = i;//save the row value
                        startingCol = j;//save the col value
                    }
                    Neighbor[] neighbors = getNeighbors(grid, startingRow, startingCol);//make a Neighbor array which has 4 of the neighbors(top, bottom, left, right)
                }
            }
            System.out.println(floodTheLand(grid, startingRow, startingCol, this));//print if the house is safe or not
        }
    }

    /**
     * Update the color of the grid when changes are made
     * @param grid
     */
    public void updateLand(int[][] grid){
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[i].length;j++){
                if(grid[i][j] == 1){
                    this.gridForGUI[i][j].setText("L");
                    this.gridForGUI[i][j].setBackground(landColor);
                }else if(grid[i][j] == 2){
                    this.gridForGUI[i][j].setText("W");
                    this.gridForGUI[i][j].setBackground(waterColor);
                }
            }
        }
        repaint();
    }
    public String checkIfHouseSafe(int[][] grid, int houseRow, int houseCol){
        int counter = 0;//counter to see if 4 of the neighbors are the same
        int rowHouse = 0;//row value of the house
        int colHouse = 0;//col value of the house
        for(int i=0;i<grid.length;i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 4) {//if the grid value equals to the house value
                    rowHouse = i;//save the row value of the house
                    colHouse = j;//save the col value of the house
                }
            }
        }
        Neighbor[] neighbors = getNeighbors(grid, rowHouse, colHouse);//get the neighbors of the house
        for(Neighbor neighbor : neighbors){
            if(grid[neighbor.row][neighbor.col] == 3){//if the neighbors are sandbags
                counter++;
            }
        }
        if(counter==4){//if all the neighbors are sandbags
            return "House is Safe";
        }else{//iff all the neighbors aren't sandbags
            return "House isn't safe";
        }
    }
    /**
     *
     * @param grid
     * @param startingRow
     * @param startingCol
     * @return if the house is safe or not
     */
    public String floodTheLand(int[][] grid, int startingRow, int startingCol, FloodGUI gui){
        Neighbor[] neighbors = getNeighbors(grid, startingRow, startingCol);//get the neighbors of the water
        int cell;
        for(Neighbor neighbor : neighbors){
                if(null != neighbor){//check to see if it is bounds and not null
                    cell = grid[neighbor.row][neighbor.col];//get the neighbors value
                    if(cell == 1){//if the neighbor value is land
                        grid[neighbor.row][neighbor.col] = 2;//change to water;
                        floodTheLand(grid, neighbor.row, neighbor.col, this);//repeat the method recursively
                    }
                }
            }
                gui.updateLand(grid);//update the grid to change colors
        int houseRow = 0;
        int houseCol = 0;
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[i].length;j++){
                if(grid[i][j] == 4){//check if the value is the house
                    houseRow = i;
                    houseCol = j;
                }
            }
        }
        String ifSafe = checkIfHouseSafe(grid, houseRow, houseCol);
        return ifSafe;
    }

    public static Neighbor[] getNeighbors(int[][] grid, int row, int col){//get the value of the neighbors
        Neighbor[] neighbor = new Neighbor[4];//get the 4 neighbors
        int neighborPosition = 0;
        if(row+1<grid.length){//bottom neighbor
            neighbor[neighborPosition] = new Neighbor(row+1,col);
            neighborPosition++;
        }
        if(row-1 >= 0){//top neighbor
            neighbor[neighborPosition] = new Neighbor(row-1,col);
            neighborPosition++;
        }
        if(col+1<grid[0].length){//right neighbor
            neighbor[neighborPosition] = new Neighbor(row,col+1);
            neighborPosition++;
        }
        if(col-1 >= 0){//left neighbor
            neighbor[neighborPosition] = new Neighbor(row, col-1);
            neighborPosition++;
        }
        return neighbor;
    }
}