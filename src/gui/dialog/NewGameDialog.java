package xvican02.src.gui.dialog;

import xvican02.src.testClass.StartNewGame;

import javax.swing.*;
import javax.swing.plaf.OptionPaneUI;
import java.awt.*;

/**
 * Created by vico on 3.5.2016.
 */
public class NewGameDialog {
    JOptionPane mDialog;


    public NewGameDialog() {
        mDialog = new JOptionPane();
    }


    public StartNewGame show(JFrame frame) {
        Integer[] values = {6, 8, 10, 12};
        String[] choosePlayer = {"Player", "AI"};
        String[] chooseDifficult = {"Hard", "Easy"};
        String[] chooseMode = {"Classic", "Freeze"};
        StartNewGame mGame = null;
        int size;
        boolean isAI = false;
        boolean freeze = false;
        boolean modeDifficult = true;
        JTextField downLimitText = new JTextField();
        JTextField upLimitText = new JTextField();
        JTextField countDiskText = new JTextField();
        int downLimit = 0, upLimit = 0, countDisk = 0;



        Object selected = mDialog.showInputDialog(null, "What is the size of board?", "Selection", JOptionPane.DEFAULT_OPTION, null, values, 8);
        if (selected != null) {//null if the user cancels.
            size = Integer.parseInt(selected.toString());
            Object oPlayer = mDialog.showInputDialog(null, "Choose player.", "Select player", JOptionPane.DEFAULT_OPTION, null, choosePlayer, "Player");
            if (oPlayer != null) {
                if (oPlayer.toString() == "AI") {
                    isAI = true;
                    Object oDifficult = mDialog.showInputDialog(null, "Set difficult of AI", "Selection", JOptionPane.DEFAULT_OPTION, null, chooseDifficult, "Hard");
                    if (oDifficult != null) {
                        if (oDifficult.toString() == "Hard") {
                            modeDifficult = false;
                        }
                    } else {
                        return mGame;
                    }
                }
            } else {
                return mGame;
            }

            Object oFreeze = mDialog.showInputDialog(null, "Set your mode of game", "Mode Game", JOptionPane.DEFAULT_OPTION, null, chooseMode, "Classic");
            if (oFreeze != null) {
                if (oFreeze.toString() == "Freeze") {
                    freeze = true;
                    Object[] message = {
                            "Input value 1:", downLimitText,
                            "Input value 2:", upLimitText,
                            "Input value 3:", countDiskText,
                    };
                    int option = JOptionPane.showConfirmDialog(frame, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        downLimit = Integer.getInteger(downLimitText.getText());
                        upLimit = Integer.getInteger(upLimitText.getText());
                        countDisk = Integer.getInteger(countDiskText.getText());
                    } else if (option == JOptionPane.CANCEL_OPTION) {
                        return mGame;
                    }
                }

            }
            mGame = new StartNewGame(size, isAI, modeDifficult, freeze, downLimit, upLimit, countDisk);
        } else {
            System.out.println("User cancelled");
        }

        return mGame;
    }
}

