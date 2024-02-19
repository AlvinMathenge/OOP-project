import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
public class Calculator extends BasicCalculator {
    public Calculator() {
        super();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        super.actionPerformed(e);
    }

    public static void main(String[] args) {
        new Calculator();
    }
}