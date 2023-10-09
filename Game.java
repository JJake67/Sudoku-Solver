public class Game {
    private Board currentBoard;

    public Game(){
        this.currentBoard = new Board();
    }

    public static void main(String args[]){
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
        Game game = new Game();

        Node[][] curBoard = game.currentBoard.getCurBoard();
        for (int x = 0; x < 9; x ++){
            for (int y = 0; y < 9; y ++){
                if (startBoard[x][y] != 0){
                    curBoard[x][y] = new Node(startBoard[x][y],true);
                }
                else{
                    curBoard[x][y] = new Node();
                }
            }
            System.out.println();
        }
        game.currentBoard.setCurBoard(curBoard);
        game.currentBoard.displayBoard();
        game.currentBoard.solveNode(0, 0);
        game.currentBoard.displayBoard();
        //Get starting board
        //Display
        //Update node variables for the known ones
        //Call currentBoard.solve();
    }
    

    /**
     * @return Board return the currentBoard
     */
    public Board getCurrentBoard() {
        return currentBoard;
    }

    /**
     * @param currentBoard the currentBoard to set
     */
    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }

}
