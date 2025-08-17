import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener{
    private static JFrame frame;
    private static JPanel panel;
    private static JTextField clickAmount;
    private static JTextField clickDelay;
    private static JTextField beforeStartDelay;
    private static JButton startButton;
    private static JCheckBox infCheckBox;
    private static JLabel invalidMsg = new JLabel("");
    public volatile boolean isInfinite = false;

    public GUI(){
        frame = new JFrame();

        startButton = new JButton("Click to start auto clicking");
        startButton.addActionListener(this);

        JLabel startLabel = new JLabel("Press the start button to start and F4 to stop!");

        JLabel clickAmountLabel = new JLabel("Enter the amount of clicks");
        clickAmount = new JTextField();
        JLabel clickDelayLabel = new JLabel("Enter the delay between clicks (ms)");
        clickDelay = new JTextField();
        JLabel beforeStartDelayLabel = new JLabel("Enter the delay before starting the program (ms)");
        beforeStartDelay = new JTextField();

        infCheckBox = new JCheckBox("Infinite (Until stopping)");
        infCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(infCheckBox.isSelected()){
                    clickAmount.setEnabled(false);
                    isInfinite = true;
                } else{
                    clickAmount.setEnabled(true);
                    isInfinite = false;
                }
            }
        });

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(50,150,100,150));
        panel.setLayout(new GridLayout(0,1));
        panel.add(startLabel);
        panel.add(clickAmountLabel);
        panel.add(clickAmount);
        panel.add(infCheckBox);
        panel.add(clickDelayLabel);
        panel.add(clickDelay);
        panel.add(beforeStartDelayLabel);
        panel.add(beforeStartDelay);
        panel.add(startButton);
        panel.add(invalidMsg);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Auto Clicker");
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if(e.getSource() == startButton){
            if(isLong(clickAmount.getText()) && isLong(clickDelay.getText()) && isLong(beforeStartDelay.getText()) && !isInfinite){
                invalidMsg.setText("");
                AutoClicker.autoClickerStart(Long.parseLong(clickAmount.getText()), Long.parseLong(clickDelay.getText()), Long.parseLong(beforeStartDelay.getText()));
            } else if(isLong(clickDelay.getText()) && isLong(beforeStartDelay.getText()) && isInfinite){
                invalidMsg.setText("");
                AutoClicker.infAutoClickerStart(Long.parseLong(clickDelay.getText()), Long.parseLong(beforeStartDelay.getText()));
            } else{
                invalidMsg.setText("Insert a valid input (Integer)");
            }
        }
    }
    public boolean isLong(String str){
        if(str == null || str.isEmpty()){
            return false;
        }
        try{
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    public boolean isInteger(String str){
        if(str == null || str.isEmpty()){
            return false;
        }
        try{
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
