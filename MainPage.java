import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame{

    private static JButton startGame;
    private static JComboBox<String> diff;

    public MainPage(){

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(1000,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Su dont ku want it solved");
        frame.add(panel);

        diff = new JComboBox<String>();
        diff.addItem("Easy");
        diff.addItem("Medium");
        diff.addItem("Hard");

        startGame = new JButton("Start Solver");
        startGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String difficulty = (String)diff.getSelectedItem();
                new SudokuPage(difficulty);
                //Grab random game from difficulty pass to page

            }
        });
        panel.add(startGame);
        panel.add(diff);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        MainPage main = new MainPage();
    }


    
}
