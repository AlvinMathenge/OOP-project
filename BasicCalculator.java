import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;


class BasicCalculator extends JFrame implements ActionListener {
    protected DecimalFormat df = new DecimalFormat("#,###.00");
    protected String[] symbols = {
            "AC", "MOD", "+/-", "%", "log", "/",
            "pi", "e", "7", "8", "9", "x",
            "!", "MOD", "4", "5", "6", "-",
            "x²", "x³", "1", "2", "3", "+",
            "cos", "sin", "tan", "0", ".", "=",
            "10^x", "x^y", "e^x", "√x", "∛x", "1/x",
    };
    protected int operator = 0;
    protected boolean sinSelected = false;
    protected boolean cosSelected = false;
    protected boolean tanSelected = false;
    protected double constantValue = 0; // Added for storing constant value

    protected JPanel panel = new JPanel(new BorderLayout(5, 5));
    protected JPanel btnPanel = new JPanel(new GridLayout(7, 4, 2, 2));
    protected JButton[] btns = new JButton[symbols.length];
    protected JTextArea screen = new JTextArea(5, 40);
    protected double firstNum = 0, secondNum = 0;
    protected JTextField calculatingTf = new JTextField(40);

    public BasicCalculator() {
        init();
    }

    public void init() {
        setTitle("Calculator");
        screen.setFont(new Font("Times New Roman", Font.BOLD, 18));
        screen.setBackground(Color.BLACK);
        panel.setBackground(Color.BLACK);
        btnPanel.setBackground(Color.BLACK);
        screen.setForeground(Color.WHITE);

        for (int i = 0; i < symbols.length; i++) {
            btns[i] = new JButton(symbols[i]);
            btns[i].setOpaque(true);
            btns[i].setBorderPainted(false);
            btns[i].setForeground(Color.WHITE);
            btns[i].addActionListener(this);
            btnPanel.add(btns[i]);

            if (symbols[i].matches("[0-9]")) {
                btns[i].setBackground(Color.GRAY);
            } else if (symbols[i].matches("[/x+-=]")) {
                btns[i].setBackground(new Color(255, 149, 0));
            } else {
                btns[i].setBackground(Color.BLACK);
            }
        }

        btns[29].setPreferredSize(new Dimension(btns[29].getPreferredSize().width * 2, btns[29].getPreferredSize().height * 2));

        calculatingTf.setForeground(Color.WHITE);
        calculatingTf.setBackground(Color.BLACK);

        panel.add(calculatingTf, BorderLayout.SOUTH);
        panel.add(btnPanel, BorderLayout.CENTER);
        panel.add(screen, BorderLayout.NORTH);
        add(panel);
        setSize(340, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand().toString();
        switch (cmd) {
            case ".":
                if (!screen.getText().contains(".")) {
                    screen.append(cmd);
                }
                break;
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                screen.append(cmd);
                break;
            case "/":
            case "x":
            case "-":
            case "+":
            case "%":
            case "+/-":
            case "AC":
                performRegularOperation(cmd);
                break;
            case "sin":
            case "cos":
            case "tan":
                selectTrigonometricFunction(cmd);
                break;
            case "=":
                performCalculation();
                break;
            case "pi":
                constantValue = Math.PI;
                screen.setText(Double.toString(constantValue));
                break;
            case "e":
                constantValue = Math.E;
                screen.setText(Double.toString(constantValue));
                break;
            case "log":
                performLogarithm();
                break;
            case "x²":
                double squareValue = Double.parseDouble(screen.getText());
                double squareResult = Math.pow(squareValue, 2);
                screen.setText(Double.toString(squareResult));
                break;
            case "x³":
                double cubeValue = Double.parseDouble(screen.getText());
                double cubeResult = Math.pow(cubeValue, 3);
                screen.setText(Double.toString(cubeResult));
                break;
            case "x^y":
                firstNum = Double.parseDouble(screen.getText());
                operator = 5;
                screen.setText("");
                break;
            case "e^x":
                double expValue = Double.parseDouble(screen.getText());
                double expResult = Math.exp(expValue);
                screen.setText(Double.toString(expResult));
                break;
            case "10^x":
                double powTenValue = Double.parseDouble(screen.getText());
                double powTenResult = Math.pow(10, powTenValue);
                screen.setText(Double.toString(powTenResult));
                break;
            case "√x":
                double sqrtValue = Double.parseDouble(screen.getText());
                double sqrtResult = Math.sqrt(sqrtValue);
                screen.setText(Double.toString(sqrtResult));
                break;
            case "∛x":
                double cbrtValue = Double.parseDouble(screen.getText());
                double cbrtResult = Math.cbrt(cbrtValue);
                screen.setText(Double.toString(cbrtResult));
                break;
            case "1/x":
                double reciprocalValue = Double.parseDouble(screen.getText());
                double reciprocalResult = 1 / reciprocalValue;
                screen.setText(Double.toString(reciprocalResult));
                break;
            case "!":
                performFactorial();
                break;
            case "MOD":
                performModuloOperation();
                break;
            default:
        }
    }

    private void performRegularOperation(String cmd) {
        if (!screen.getText().isEmpty()) {
            switch (cmd) {
                case "/":
                case "x":
                case "-":
                case "+":
                    firstNum = Double.parseDouble(screen.getText());
                    operator = switch (cmd) {
                        case "/" -> 1;
                        case "x" -> 2;
                        case "-" -> 3;
                        case "+" -> 4;
                        default -> 0;
                    };
                    screen.setText("");
                    break;
                case "+/-":
                    double value = Double.parseDouble(screen.getText());
                    value *= -1;
                    screen.setText(String.valueOf(value));
                    break;
                case "%":
                    double percentageValue = Double.parseDouble(screen.getText());
                    percentageValue /= 100;
                    screen.setText(String.valueOf(percentageValue));
                    break;
                case "AC":
                    screen.setText("");
                    break;
            }
        }
    }

    private void selectTrigonometricFunction(String cmd) {
        switch (cmd) {
            case "sin":
                sinSelected = true;
                break;
            case "cos":
                cosSelected = true;
                break;
            case "tan":
                tanSelected = true;
                break;
            default:
        }

        screen.setText("");
        calculatingTf.setText(cmd);
    }

    private void performCalculation() {
        if (!screen.getText().isEmpty()) {
            secondNum = Double.parseDouble(screen.getText());
            if (sinSelected || cosSelected || tanSelected) {
                double num = Double.parseDouble(screen.getText());
                double result = 0;
                if (sinSelected) {
                    result = Math.sin(Math.toRadians(num));
                } else if (cosSelected) {
                    result = Math.cos(Math.toRadians(num));
                } else if (tanSelected) {
                    result = Math.tan(Math.toRadians(num));
                }
                screen.setText(String.valueOf(result));
                calculatingTf.setText(calculatingTf.getText() + "(" + num + ") = " + result);

                sinSelected = false;
                cosSelected = false;
                tanSelected = false;
            } else {
                switch (operator) {
                    case 1 -> {
                        screen.setText(String.valueOf(firstNum / secondNum));
                        calculatingTf.setText(String.valueOf(firstNum + "/" + secondNum + "=" + (df.format(firstNum / secondNum))));
                    }
                    case 2 -> {
                        screen.setText(String.valueOf(firstNum * secondNum));
                        calculatingTf.setText(String.valueOf(firstNum + "x" + secondNum + "=" + (df.format(firstNum * secondNum))));
                    }
                    case 3 -> {
                        screen.setText(String.valueOf(firstNum - secondNum));
                        calculatingTf.setText(String.valueOf(firstNum + "-" + secondNum + "=" + (df.format(firstNum - secondNum))));
                    }
                    case 4 -> {
                        screen.setText(String.valueOf(firstNum + secondNum));
                        calculatingTf.setText(String.valueOf(firstNum + "+" + secondNum + "=" + (df.format(firstNum + secondNum))));
                    }
                    case 5 -> {
                        double result = Math.pow(firstNum, secondNum);
                        screen.setText(String.valueOf(result));
                        calculatingTf.setText(String.valueOf(firstNum + "^" + secondNum + "=" + result));
                    }
                    case 6 -> {
                        double result = customModuloOperation(firstNum, secondNum);
                        screen.setText(String.valueOf(result));
                        calculatingTf.setText(String.valueOf(firstNum + " MOD " + secondNum + "=" + result));
                    }
                    default -> {
                    }
                }
            }
        }
    }

    private void performFactorial() {
        if (!screen.getText().isEmpty()) {
            int num = Integer.parseInt(screen.getText());
            long result = factorial(num);
            screen.setText(String.valueOf(result));
            calculatingTf.setText(num + "! = " + result);
        }
    }

    private long factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private void performModuloOperation() {
        if (!screen.getText().isEmpty()) {
            firstNum = Double.parseDouble(screen.getText());
            operator = 6;
            screen.setText("");
        }
    }

    private void performLogarithm() {
        if (!screen.getText().isEmpty()) {
            double logValue = Double.parseDouble(screen.getText());
            if (logValue > 0) {
                double logResult = Math.log10(logValue);
                screen.setText(Double.toString(logResult));
            } else {
                screen.setText("Invalid input");
            }
        }
    }

    private double customModuloOperation(double a, double b) {
        return a % b;
    }
}