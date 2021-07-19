package ro.ctrln.javafx.calculator.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import ro.ctrln.javafx.calculator.operations.Operation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class CalculatorController {

    @FXML
    public TextArea calculatorOperationsArea;
    @FXML
    public Label errorsLabel;


    // SCRIS CIFRE

    @FXML
    public void writeZero(ActionEvent actionEvent) {
        checkOperation();
        if (!calculatorOperationsArea.getText().equalsIgnoreCase("0")) {
            calculatorOperationsArea.setText(calculatorOperationsArea.getText().concat("0"));
        }
        setPositionCaret();
    }

    //METODA GENERICA 1 -> 9
    @FXML
    public void writeDigit(String digit) {
        checkOperation();
        if (replaceZero(digit)) {
            calculatorOperationsArea.setText(calculatorOperationsArea.getText().concat(digit));
        }
        setPositionCaret();
    }

    @FXML
    public void writeOne(ActionEvent actionEvent) {
        writeDigit("1");
    }

    @FXML
    public void writeTwo(ActionEvent actionEvent) {
        writeDigit("2");
    }

    @FXML
    public void writeThree(ActionEvent actionEvent) {
        writeDigit("3");
    }

    @FXML
    public void writeFour(ActionEvent actionEvent) {
        writeDigit("4");
    }

    @FXML
    public void writeFive(ActionEvent actionEvent) {
        writeDigit("5");
    }

    @FXML
    public void writeSix(ActionEvent actionEvent) {
        writeDigit("6");
    }

    @FXML
    public void writeSeven(ActionEvent actionEvent) {
        writeDigit("7");
    }

    @FXML
    public void writeEight(ActionEvent actionEvent) {
        writeDigit("8");
    }

    @FXML
    public void writeNine(ActionEvent actionEvent) {
        writeDigit("9");
    }


    // COMMA

    @FXML
    public void writeComma(ActionEvent actionEvent) {
        if (!commaOnOperand(calculatorOperationsArea.getText())) {
            calculatorOperationsArea.setText(calculatorOperationsArea.getText().concat("."));
        }
        setPositionCaret();
    }

    private boolean commaOnOperand(String text) {
        if (noExistingMathOperations()) {
            return text.contains(".");  // verificam daca operandul 1 are virgula deja
        } else {
            String[] operands = {};
            for (String mathOperations : operationsCharacters) {
                if (operands.length == 2) {
                    break;
                }
                operands = splitOperands(text, mathOperations);
            }
            return operands[1].contains(".");

        }

    }


    //OPERATIII

    @FXML
    public void addition(ActionEvent actionEvent) {
        if (noExistingMathOperations())
            calculatorOperationsArea.setText(calculatorOperationsArea.getText().concat("+"));

    }

    @FXML
    public void division(ActionEvent actionEvent) {
        if (noExistingMathOperations())
            calculatorOperationsArea.setText(calculatorOperationsArea.getText().concat("/"));

    }

    @FXML
    public void subtraction(ActionEvent actionEvent) {
        if (noExistingMathOperations())
            calculatorOperationsArea.setText(calculatorOperationsArea.getText().concat("-"));

    }

    @FXML
    public void multiplication(ActionEvent actionEvent) {
        if (noExistingMathOperations())
            calculatorOperationsArea.setText(calculatorOperationsArea.getText().concat("*"));
    }


    //METODE

    private boolean noExistingMathOperations() {
        boolean b = !calculatorOperationsArea.getText().contains("+") &&
                !calculatorOperationsArea.getText().contains("-") &&
                !calculatorOperationsArea.getText().contains("/") &&
                !calculatorOperationsArea.getText().contains("*");
        return b;
    }

    @FXML
    public void clearCalculatorOperationsArea(ActionEvent actionEvent) {
        calculatorOperationsArea.setText("");

    }

    @FXML
    public void evaluate(ActionEvent actionEvent) {
        String operation = calculatorOperationsArea.getText();
        if (!operation.isEmpty()) {
            if (operation.contains("+")) {
                performAddition(operation);

            } else if (operation.contains("-")) {
                performASubtraction(operation);

            } else if (operation.contains("/")) {
                performDivision(operation);

            } else if (operation.contains("*")) {
                performMultiplication(operation);
            } else {
                errorsLabel.setText("Unknown operation");
            }
        }
    }

    // IMPIEDICA PUNEREA UNUI SPATIU DUPA OPERANDUL 2
    private String cleanOperand(String operand) {
        return operand.replaceAll("\n", "");
    }


    private void performAddition(String operation) {
        String[] operands = splitOperands(operation, "+");
        if (operands.length == 2) {
            doOperation(operands, Operation.ADDITION);
        }

    }

    private void performASubtraction(String operation) {
        String[] operands = splitOperands(operation, "-");
        if (operands.length == 2) {
            doOperation(operands, Operation.SUBTRACTION);
        }
    }

    private void performMultiplication(String operation) {

        String[] operands = splitOperands(operation, "*");
        if (operands.length == 2) {
            doOperation(operands, Operation.MULTIPLICATION);
        }
    }

    private void performDivision(String operation) {
        String[] operands = splitOperands(operation, "/");
        if (operands.length == 2) {
            doOperation(operands, Operation.DIVISION);
        }
    }

    private String[] splitOperands(String operation, String splitter) {
        String[] operands = {};
        try {
            if (Arrays.asList("+", "-", "*", "/").contains(splitter)) {
                operation = operation.replace(splitter, "----");
            }
            operands = operation.split("----");

        } catch (Exception ex) {
            errorsLabel.setText("No operands found");
            ex.printStackTrace();
        }
        //errorsLabel.setText(operands[0] + " " + operands[1]);
        return operands;
    }

    private void doOperation(String[] operands, Operation operation) {
        try {
            BigDecimal firstOperand = new BigDecimal(cleanOperand(operands[0]));
            BigDecimal secondOperand = new BigDecimal(cleanOperand(operands[1]));

            switch (operation) {
                case ADDITION:
                    writeResult(firstOperand.add(secondOperand));
                    break;
                case SUBTRACTION:
                    writeResult(firstOperand.subtract(secondOperand));
                    break;
                case DIVISION:
                    writeResult(firstOperand.divide(secondOperand, RoundingMode.HALF_DOWN));
                    break;
                case MULTIPLICATION:
                    writeResult(firstOperand.multiply(secondOperand));
                    break;
            }

        } catch (NumberFormatException nfe) {
            errorsLabel.setText("Invalid operands");

        }
    }

    private void writeResult(BigDecimal result) {
        calculatorOperationsArea.setText(calculatorOperationsArea.getText()
                .replaceAll("\n", "")
                .replaceAll("\r", "")
                .concat(" = ").concat(result.toString()));
    }

    private void checkOperation() {
        if (calculatorOperationsArea.getText().contains("=")) {
            calculatorOperationsArea.setText("");
        }
    }


    //REZOLVARE ZERO MULTIPLU SI PREECEDING ZERO

    private boolean replaceZero(String replacement) {
        boolean zeroReplaced = false;
        if (calculatorOperationsArea.getText().equalsIgnoreCase("0")) {
            calculatorOperationsArea.setText(replacement);
            zeroReplaced = true;
        }
        return !zeroReplaced;
    }


    // PRELUCRARE INPUT TASTATURA
    @FXML
    public void handleKeyTyped(KeyEvent keyEvent) {
        if (allowedCharacters.contains(keyEvent.getCharacter())) {
            handleDigitCharacter(keyEvent);
            handleComma(keyEvent); // pentru ingnorare virgurle duble de la tastatura
            handleOperations(keyEvent);
            handleEvaluationKeys(keyEvent);

        } else {
            keyEvent.consume();
        }
    }

    private void handleEvaluationKeys(KeyEvent keyEvent) {
        if (keyEvent.getCharacter().equalsIgnoreCase("=") ||
                keyEvent.getCharacter().equalsIgnoreCase("\r")) {
            keyEvent.consume();
            evaluate(new ActionEvent());
        }
    }

    private void handleOperations(KeyEvent keyEvent) {
        if (operationsCharacters.contains((keyEvent.getCharacter()))) {
            if (!noExistingMathOperations()) {
                keyEvent.consume();
            }
        }
    }

    private void handleComma(KeyEvent keyEvent) {
        if (keyEvent.getCharacter().equalsIgnoreCase(".")) {
            writeComma(new ActionEvent());
            keyEvent.consume();
        }
    }

    private void handleDigitCharacter(KeyEvent keyEvent) {
        if (isDigitCharacter(keyEvent.getCharacter())) {
            switch (keyEvent.getCharacter()) {
                case "0":
                    writeZero(new ActionEvent());
                    break;
                case "1":
                    writeOne(new ActionEvent());
                    break;
                case "2":
                    writeTwo(new ActionEvent());
                    break;
                case "3":
                    writeThree(new ActionEvent());
                    break;
                case "4":
                    writeFour(new ActionEvent());
                    break;
                case "5":
                    writeFive(new ActionEvent());
                    break;
                case "6":
                    writeSix(new ActionEvent());
                    break;
                case "7":
                    writeSeven(new ActionEvent());
                    break;
                case "8":
                    writeEight(new ActionEvent());
                    break;
                case "9":
                    writeNine(new ActionEvent());
                    break;

            }
            keyEvent.consume();
        }
    }

    private List<String> allowedCharacters = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "=", "+",
            "-", "/", "*", "\r", "\n");

    private boolean allowedCharacter(String charcater) {
        return allowedCharacters.contains(charcater);
    }

    private List<String> operationsCharacters = Arrays.asList("*", "+", "-", "/");

    private boolean operationCharacter(String character) {
        return operationsCharacters.contains(character);
    }

    private List<String> digitsAsCharacters = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    private boolean isDigitCharacter(String character) {
        return digitsAsCharacters.contains(character);
    }


    //POZITIONARE CURSOR LA DREAPTA

    private void setPositionCaret() {
        calculatorOperationsArea.positionCaret(calculatorOperationsArea.getText().length());
    }

}
