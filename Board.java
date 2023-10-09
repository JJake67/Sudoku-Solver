import java.util.*;

import javax.sound.midi.SysexMessage;

import java.lang.Math;

public class Board {

    private Node[][] curBoard;

    public Board(){
        this.curBoard = new Node[9][9];
    }

    public boolean solveNode(int nodeX, int nodeY){
        boolean solved = false;
        Node curNode = this.curBoard[nodeX][nodeY];
        if (curNode.isKnown() == true){
            if (nodeX == 8 && nodeY == 8){
                return true;
            }
            //Finds next box in sudoku coordinates
            //Loops to next row if at end etc etc
            int nextX = nodeX;
            int nextY = nodeY;
            if (nodeY == 8){
                nextY = 0;
                nextX = nodeX + 1;
            }
            else {
                nextY = nextY + 1;
            }
            solved = solveNode(nextX, nextY);
            if (solved == true){
                return true;
            }
            else {
                return false;
            }
        }
        int available[] = updateAvailable(nodeX, nodeY);
        if (available.length == 0){
            curNode.setValue(0);
            this.curBoard[nodeX][nodeY] = curNode;
            solved = false;
        }
        //Remove zeros from available
        for (int x = 0; x < available.length; x++){
            curNode.setValue(available[x]);
            this.curBoard[nodeX][nodeY] = curNode;
            boolean valid = this.checkValid(nodeX,nodeY);

            if (valid == true){
                if (nodeX == 8 && nodeY == 8){
                    return true;
                }
                //Finds next box in sudoku coordinates
                //Loops to next row if at end etc etc
                int nextX = nodeX;
                int nextY = nodeY;
                if (nodeY == 8){
                    nextY = 0;
                    nextX = nodeX + 1;
                }
                else {
                    nextY = nextY + 1;
                }
                //this.displayBoard();
                solved = solveNode(nextX, nextY);
                if (solved == true){
                    return true;
                }
            }
        }
        if (solved == true){
            //this.displayBoard();
            return true;
        }
        else {
            curNode.setValue(0);
            this.curBoard[nodeX][nodeY] = curNode;
            return false;
        }
    }
    
    public int[] updateAvailable(int nodeX, int nodeY) {
        int[] available = new int[]{1,2,3,4,5,6,7,8,9};
        Integer[] availableInt= new Integer[]{1,2,3,4,5,6,7,8,9};
        List<Integer> availableList = new ArrayList<>(Arrays.asList(availableInt));
        //Get row
        int xVal = 0;
        for (int x = 0; x < 9; x++){
            xVal = curBoard[x][nodeY].getValue();
            if (availableList.contains(xVal) && xVal != 0){
                available[xVal-1] = 0;
                availableList.set(xVal-1,0);
            }
        }

        //Get column
        int yVal = 0;
        for (int y = 0; y < 9; y++){
            yVal = curBoard[nodeX][y].getValue();
            if (availableList.contains(yVal) && yVal != 0){
                available[yVal-1] = 0;
                availableList.set(yVal-1,0);

            }
        }

        //Get grid
        int gridX = 0;
        int gridY = 0;
        switch(nodeX) {
            case 0: case 1: case 2:
                gridX = 0;
                break;
            case 3: case 4: case 5:
                gridX = 3;
                break;
            case 6: case 7: case 8:
                gridX = 6;
                break;
        }
        switch(nodeY) {
            case 0: case 1: case 2:
                gridY = 0;
                break;
            case 3: case 4: case 5:
                gridY = 3;
                break;
            case 6: case 7: case 8:
                gridY = 6;
                break;
        }

        int gridVal = 0;
        for (int x = gridX; x < gridX + 3; x ++){
            for (int y = gridY; y < gridY + 3; y++){
                gridVal = curBoard[x][y].getValue();
                if (availableList.contains(gridVal) && gridVal != 0){
                    available[gridVal-1] = 0;
                    availableList.set(gridVal-1,0);
                }
            }
        }
        int avalCount = 0;
        for (int z = 0; z < available.length; z++){
            if (available[z] != 0){
                avalCount = avalCount + 1;
            }
        }
        int[] availableNew = new int[avalCount];
        avalCount = 0;
        for (int z = 0; z < available.length; z++){
            if (available[z] != 0){
                availableNew[avalCount] = available[z];
                avalCount = avalCount+1;
            }
        }
        return availableNew;
    }

    public boolean checkValid(int nodeX, int nodeY){
        int row[] = new int[9];
        int xVal = 0;
        for (int x = 0; x < 9; x++){
            xVal = curBoard[x][nodeY].getValue();
            if (xVal != 0){
                row[x] = xVal;
            }
        }
        //Invalid
        if (repeats(row) == false){
            return false;
        }

        int column[] = new int[9];
        int yVal = 0;
        for (int y = 0; y < 9; y++){
            yVal = curBoard[nodeX][y].getValue();
            if (yVal != 0){
                column[y] = yVal;
            }
        }
        //Invalid
        if (repeats(column) == false){
            return false;
        }

        int gridX = 0;
        int gridY = 0;
        switch(nodeX) {
            case 0: case 1: case 2:
                gridX = 0;
                break;
            case 3: case 4: case 5:
                gridX = 3;
                break;
            case 6: case 7: case 8:
                gridX = 6;
                break;
        }
        switch(nodeY) {
            case 0: case 1: case 2:
                gridY = 0;
                break;
            case 3: case 4: case 5:
                gridY = 3;
                break;
            case 6: case 7: case 8:
                gridY = 6;
                break;
        }

        int[] gridRow = new int[9];
        int count = 0;
        int gridVal = 0;
        for (int x = gridX; x < (gridX + 3); x ++){
            for (int y = gridY; y < (gridY + 3); y++){
                gridVal = curBoard[x][y].getValue();
                if (gridVal != 0){
                    gridRow[count] = gridVal;
                    count = count + 1;
                }
            }
        }

        //Invalid
        if (repeats(gridRow) == false){
            return false;
        }

        return true;
    }

    public boolean repeats(int[] row){
        //Could make more efficient by removing length and just ignoring the zeros
        //for each val in row
        int[] newRow = new int[10];

        //counts how many of each appear
        for (int i = 0; i < row.length; i++){
            newRow[row[i]]++;
        }

        //Checks those from 1-9, if any have been counted twice, not allowed 
        for (int i = 1; i < row.length; i++){
            if (newRow[i] > 1){
                return false;
            }
        }
        return true;
    }

    public void displayBoard(){
        for (int x = 0; x < 9; x ++){
            for (int y = 0; y < 9; y ++){
                System.out.print(curBoard[x][y].getValue() + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //Getters and Setters
    /**
     * @return Node[][] return the curBoard
     */
    public Node[][] getCurBoard() {
        return curBoard;
    }

    /**
     * @param curBoard the curBoard to set
     */
    public void setCurBoard(Node[][] curBoard) {
        this.curBoard = curBoard;
    }

}