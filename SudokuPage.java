import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.Border;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.midi.SysexMessage;

import java.lang.Math;
public class SudokuPage extends JFrame{

    private sudokuPanel sudokuPanel = new sudokuPanel();
    private JPanel buttonsPanel;
    private Container contentPane;

    public SudokuPage(String difficulty){
        contentPane = getContentPane();

        buttonsPanel = new JPanel();
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    sudokuPanel.solveNode(0,0);
                }
                
             });
                
                
        /* 
        JButton solveButton = new JButton("Solve");
            solveButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingWorker<Boolean, Void> backgroundUpdater = new SwingWorker<Boolean, Void>(){
                        @Override
                        public Boolean doInBackground(){
                            return sudokuPanel.solveNode(0,0);                           
                        }
                    };
                    backgroundUpdater.execute();
                    boolean dont = true;
                    while (dont){
                        Node[][] curBoard = sudokuPanel.board.getCurBoard();
                        JLabel holderArray[][] = new JLabel[9][9];
                        
                        for (int x = 0; x < 9; x++){          
                            for (int y = 0; y < 9; y++){
                                holderArray[x][y] = new JLabel();
                                Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
                                holderArray[x][y].setBorder(border);

                                if (curBoard[x][y].getValue() != 0){
                                    holderArray[x][y].setText(Integer.toString(curBoard[x][y].getValue()));
                                    sudokuPanel.add(holderArray[x][y]);
                        
                            }
                                else {
                                    holderArray[x][y].setText("_");
                                    sudokuPanel.add(holderArray[x][y]);
                                }
                            }
                        }
                        //sudokuPanel.changeDisplay(sudokuPanel.board.getCurBoard());
                        sudokuPanel.revalidate();
                        repaint();
                        //System.out.print("HwAHsda");
                    }
                }
            });
        */
        buttonsPanel.add(solveButton);
        JPanel layoutPanel = new JPanel();

        layoutPanel.add(sudokuPanel, BorderLayout.NORTH);
        layoutPanel.add(buttonsPanel, BorderLayout.SOUTH);
        contentPane.add(layoutPanel);
        String dif = difficulty;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		
		setSize(screenSize.width/4, screenSize.height/2);
		setLocation(screenSize.width/4, screenSize.height/4);
        setVisible(true);

        //sudokuPanel.solveNode(0,0);
    }

    public static void main(String[] args){
        
    }
    

    public class sudokuPanel extends JPanel{
        
        private JLabel[][] holderArray;
        private Board board = new Board();
        public boolean solved;
        public sudokuPanel(){
            setLayout(new GridLayout(9,9));
            //placeholder, after working for just start board, will add more
            int startBoard[][] = {
                {0,0,0,0,7,0,0,0,0},
                {0,9,0,5,0,6,0,8,0},
                {0,0,8,4,0,1,2,0,0},
                {0,5,9,0,0,0,8,4,0},
                {7,0,0,0,0,0,0,0,6},
                {0,2,3,0,0,0,5,7,0},
                {0,0,5,3,0,7,4,0,0},
                {0,1,0,6,0,8,0,9,0},
                {0,0,0,0,1,0,0,0,0}
            };
            //Builds node array from startBoard
            Node[][] curBoard = new Node[9][9];
            for (int x = 0; x < 9; x ++){
                for (int y = 0; y < 9; y ++){
                    if (startBoard[x][y] != 0){
                        curBoard[x][y] = new Node(startBoard[x][y],true);
                    }
                    else{
                        curBoard[x][y] = new Node();
                    }
                }
            }
            board.setCurBoard(curBoard);
            //sets node array as the node array in the board object

            //Displays current board
            holderArray = new JLabel[9][9];
            for (int x = 0; x < 9; x++){          
                for (int y = 0; y < 9; y++){
                    holderArray[x][y] = new JLabel();
                    Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
                    holderArray[x][y].setBorder(border);

                    if (curBoard[x][y].getValue() != 0){
                        holderArray[x][y].setText(Integer.toString(curBoard[x][y].getValue()));
                        add(holderArray[x][y]);
                    }
                    else {
                        holderArray[x][y].setText("_");
                        add(holderArray[x][y]);
                    }
                }
            }

            //Adds panel to current object
        }

        public boolean solveNode(int nodeX, int nodeY){
            Node[][] curBoard = board.getCurBoard();
            this.solved = false;
            Node curNode = curBoard[nodeX][nodeY];
            if (curNode.isKnown() == true){
                if (nodeX == 8 && nodeY == 8){
                    board.displayBoard();
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
                this.solved = solveNode(nextX, nextY);
                if (this.solved == true){
                    board.displayBoard();
                    return true;
                }
                else {
                    return false;
                }
            }
            int available[] = board.updateAvailable(nodeX, nodeY);
            if (available.length == 0){
                curNode.setValue(0);
                curBoard[nodeX][nodeY] = curNode;
                this.board.setCurBoard(curBoard);
                this.solved = false;
            }
            //Remove zeros from available
            for (int x = 0; x < available.length; x++){
                curNode.setValue(available[x]);
                curBoard[nodeX][nodeY] = curNode;
                this.board.setCurBoard(curBoard);
                boolean valid = board.checkValid(nodeX,nodeY);

                if (valid == true){
                    changeDisplay(curBoard); 
                    
                   
                    //System.out.println("Hi");

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
                    final int nextXFinal = nextX;
                    final int nextYFinal = nextY;
                    //changeDisplay(curBoard);
                    //Timer timer = new Timer(0, new ActionListener() {
                    //@Override
                    //public void actionPerformed(ActionEvent arg0) {
    
                    //    sudokuPanel.this.solved = solveNode(nextXFinal, nextYFinal);
                    //}
                    //});
                    //timer.setRepeats(false); 
                    //timer.start(); 
                    this.solved = solveNode(nextX, nextY);
                    //solved = true;
                    //solved = solveNode(nextX, nextY);
                    if (this.solved == true){
                        return true;
                    }
                }
            }
            if (this.solved == true){
                board.displayBoard();
                return true;
            }
            else {
                curNode.setValue(0);
                curBoard[nodeX][nodeY] = curNode;
                return false;
            }
        }

        public void changeDisplay(Node[][] boardNodes){

            //System.out.println("plz howm nay");
            for (int x = 0; x < 9; x ++){
                for (int y = 0; y < 9; y ++){
                    if (boardNodes[x][y].getValue() != 0){
                        //System.out.println(String.valueOf(boardNodes[x][y].getValue()));
                        holderArray[x][y].setText(String.valueOf(boardNodes[x][y].getValue()));
                    }
                }
            }
            revalidate();
            repaint();

        }
    }
}
