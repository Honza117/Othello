package xvican02.src.gui;

import xvican02.src.backend.board.Board;
import xvican02.src.backend.board.BoardField;
import xvican02.src.backend.board.Disk;
import xvican02.src.backend.board.Field;
import xvican02.src.backend.board.Rules;
import xvican02.src.backend.game.Game;
import xvican02.src.backend.game.ReversiRules;
import xvican02.src.gui.dialog.NewGameDialog;
import xvican02.src.testClass.LoadGame;
import xvican02.src.testClass.StartNewGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vico on 3.5.2016.
 */
public class MainWindow extends DefaultTableCellRenderer {
    private JFrame mFrame;
    private StartNewGame mNewGame;
    private JTable mTable;
    private boolean first = true;
    public Field[] oldf_field = new Field[16];
    private Board oldBoard = null;
    private List<String> OldBlackStones;
    private List<String> OldWhiteStones;
    public JLabel colorOfCurrentPlayer;
    public JPanel currentPlayer = new JPanel();

    public MainWindow(StartNewGame game) {
        mNewGame = game;
    }

    public void getMainGui(boolean load) {
        if (!load) {
            mNewGame.setFirstPlayer();
        }
        setMainFrame();
    }

    private void setMainFrame() {
        ImageIcon icon = new ImageIcon(getClass().getResource("../../lib/icon.png"));
        Color darkGreen = new Color(85, 139, 47);
        mFrame = new JFrame("ReversiApp");
        mFrame.setIconImage(icon.getImage());
        mFrame.setSize(1600, 900);
        mFrame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
        mFrame.getContentPane().setBackground(Color.gray);
        ImageIcon startImage = null;
        if (mNewGame.game.currentPlayer().toString().equals("black")){
          startImage  = new ImageIcon(getClass().getResource("../../lib/disk_black.png"));
        } else {
            startImage = new ImageIcon(getClass().getResource("../../lib/disk_white.png"));
        }



        colorOfCurrentPlayer = new JLabel(startImage);
        colorOfCurrentPlayer.setText(String.valueOf(mNewGame.game.currentPlayer().currentStoneCount(mNewGame.board)));
        currentPlayer.setLayout(new GridLayout(1, 1));
        currentPlayer.setBackground(Color.gray);
        currentPlayer.add(colorOfCurrentPlayer);

        mFrame.setJMenuBar(createMenuBar());
        mTable = setTableBoard();
        mTable.setBackground(darkGreen);
        mFrame.add(mTable);
        mFrame.add(currentPlayer);
        mFrame.setVisible(true);

    }

    private JMenuBar createMenuBar() {
        JMenuBar mJMenuBar = new JMenuBar();
        mJMenuBar.setBackground(new Color(220, 237, 200));

        ImageIcon newGame = new ImageIcon(getClass().getResource("../../lib/ic_new_game.png"));
        ImageIcon openGame = new ImageIcon(getClass().getResource("../../lib/ic_open_game.png"));
        ImageIcon saveGame = new ImageIcon(getClass().getResource("../../lib/ic_save_game.png"));
        ImageIcon exitGame = new ImageIcon(getClass().getResource("../../lib/ic_exit_game.png"));
        ImageIcon undoIcon = new ImageIcon(getClass().getResource("../../lib/ic_undo.png"));


        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_F);


        JMenu editMeni = new JMenu("Edit");
        JMenuItem undo = new JMenuItem(new MenuItemAction("Undo", undoIcon, KeyEvent.VK_Z));

        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < mNewGame.getGame().getBoard().getSize() + 2; i++) {
                    for (int j = 0; j < mNewGame.getGame().getBoard().getSize() + 2; j++) {

                        Field spaceField = mNewGame.rules.createField(i, j);
                        mNewGame.board.myField[i][j] = spaceField;
                    }
                }
                
                for (int x = 1; x < mNewGame.getGame().getBoard().getSize() + 1; x++) {
        			for (int y = 1; y < mNewGame.getGame().getBoard().getSize() + 1; y++) {
        				mNewGame.board.myField[x][y].addNextField(Field.Direction.D, mNewGame.board.myField[x + 1][y]);
        				mNewGame.board.myField[x][y].addNextField(Field.Direction.L, mNewGame.board.myField[x][y - 1]);
        				mNewGame.board.myField[x][y].addNextField(Field.Direction.LD, mNewGame.board.myField[x + 1][y - 1]);
        				mNewGame.board.myField[x][y].addNextField(Field.Direction.LU, mNewGame.board.myField[x - 1][y - 1]);
        				mNewGame.board.myField[x][y].addNextField(Field.Direction.R, mNewGame.board.myField[x][y + 1]);
        				mNewGame.board.myField[x][y].addNextField(Field.Direction.RD, mNewGame.board.myField[x + 1][y + 1]);
        				mNewGame.board.myField[x][y].addNextField(Field.Direction.RU, mNewGame.board.myField[x - 1][y + 1]);
        				mNewGame.board.myField[x][y].addNextField(Field.Direction.U, mNewGame.board.myField[x - 1][y]);
        			}
        		}

                for (String index : OldWhiteStones) {

                    int spaceIndex = index.indexOf(" ");
                    int indexI = Integer.parseInt(index.substring(0, spaceIndex));
                    int indexJ = Integer.parseInt(index.substring(spaceIndex + 1));

                    Field oldField = mNewGame.board.getField(indexI, indexJ);

                    mNewGame.getGame().currentPlayer().putLoadDisk(oldField, true);
                }

                for (String index : OldBlackStones) {

                    int spaceIndex = index.indexOf(" ");
                    int indexI = Integer.parseInt(index.substring(0, spaceIndex));
                    int indexJ = Integer.parseInt(index.substring(spaceIndex + 1));

                    Field oldField = mNewGame.board.getField(indexI, indexJ);

                    mNewGame.getGame().currentPlayer().putLoadDisk(oldField, false);

                }                        
                 
                System.out.println(mNewGame.game.currentPlayer().toString());
                clearTableInGUI(mTable);
                setTableInGUI(mTable);
                System.out.println(mNewGame.game.currentPlayer().toString());
                mNewGame.f_field = mNewGame.game.currentPlayer().getPlayFields(mNewGame.game);
                mNewGame.getf_field(mTable);
                ImageIcon startImage = null;
                if (mNewGame.game.currentPlayer().toString().equals("black")){
                    startImage  = new ImageIcon(getClass().getResource("../../lib/disk_black.png"));
                } else {
                    startImage = new ImageIcon(getClass().getResource("../../lib/disk_white.png"));
                }
                colorOfCurrentPlayer.setIcon(startImage);
                colorOfCurrentPlayer.setText(String.valueOf(mNewGame.game.currentPlayer().currentStoneCount(mNewGame.board)));
            }
        });

        editMeni.add(undo);

        JMenuItem newGameItem = new JMenuItem(new MenuItemAction("New Game", newGame, KeyEvent.VK_N));
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewGameDialog mDialog = new NewGameDialog();
                MainWindow window = new MainWindow(mDialog.show(mFrame));
                window.getMainGui(false);
            }
        });

        JMenuItem openGameItem = new JMenuItem(new MenuItemAction("Open Game", openGame, KeyEvent.VK_O));
        openGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                LoadGame loadGame = new LoadGame();
                loadGame.showDialogJFile();
                StartNewGame mGame = loadGame.loadAllComponetsToGame();
                mGame.board.print();
                
                MainWindow window = new MainWindow(mGame);
                window.getMainGui(true);       

            }
        });

        JMenuItem saveGameItem = new JMenuItem(new MenuItemAction("Save Game", saveGame, KeyEvent.VK_S));
        saveGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        saveGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mNewGame.saveGame();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        JMenuItem exitGameItem = new JMenuItem("Exit", exitGame);
        exitGameItem.setMnemonic(KeyEvent.VK_E);
        exitGameItem.setToolTipText("Exit application");
        exitGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
                ActionEvent.CTRL_MASK));

        exitGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        gameMenu.add(newGameItem);
        gameMenu.add(openGameItem);
        gameMenu.add(saveGameItem);
        gameMenu.addSeparator();
        gameMenu.add(exitGameItem);

        mJMenuBar.add(gameMenu);
        mJMenuBar.add(editMeni);
        return mJMenuBar;

    }

    public static void highlightsPosibleStep(Field[] mFields, JTable table) {
        int mRow;
        int mCol;

        for (int i = 0; i < 12; i++) {
            BoardField mBoardField = (BoardField) mFields[i];
            if (mBoardField != null) {
                mRow = mBoardField.getRow() - 1;
                mCol = mBoardField.getCol() - 1;
                ImageIcon imageIcon = new ImageIcon(MainWindow.class.getResource("../../lib/possible_step.png"));
                imageIcon.setDescription("possible");
                table.setValueAt(imageIcon, mRow, mCol);
            }
        }
    }

    public static void noHighlightPosibleStep(JTable table, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ImageIcon imageIcon = (ImageIcon) table.getValueAt(i, j);
                if (imageIcon != null && imageIcon.getDescription().equals("possible")) {
                    table.setValueAt(null, i, j);
                }

            }
        }
    }


    private void setTableInGUI(JTable mTable) {
        Field mField;

        for (int i = 0; i < mNewGame.getGame().getBoard().getSize() + 2; i++) {
            for (int j = 0; j < mNewGame.getGame().getBoard().getSize() + 2; j++) {
                mField = mNewGame.getGame().getBoard().getField(i, j);
                if (mField.getDisk() != null) {
                    if (mField.getDisk().isWhite()) {
                        ImageIcon whiteDisk = new ImageIcon(getClass().getResource("../../lib/disk_white.png"));
                        whiteDisk.setDescription("white disk");
                        mTable.setValueAt(whiteDisk, i - 1, j - 1);
                    } else {
                        ImageIcon blackDisk = new ImageIcon(getClass().getResource("../../lib/disk_black.png"));
                        blackDisk.setDescription("black disk");
                        mTable.setValueAt(blackDisk, i - 1, j - 1);
                    }

                }
            }
        }
        if (first) {
            mNewGame.f_field = mNewGame.game.currentPlayer().getPlayFields(mNewGame.game);
            mNewGame.callBack(mTable);
            first = false;
        }



        if (mNewGame.getGameOver()) {
            if (mNewGame.p1.currentStoneCount(mNewGame.board) > mNewGame.p2.currentStoneCount(mNewGame.board)) {
                JOptionPane.showMessageDialog(mFrame,
                        "Vyhral černy hráč! v pomere\n" + "černý hráč " + mNewGame.p1.currentStoneCount(mNewGame.board) + " bíly hráč " + mNewGame.p2.currentStoneCount(mNewGame.board));
            } else if (mNewGame.p1.currentStoneCount(mNewGame.board) < mNewGame.p2.currentStoneCount(mNewGame.board)) {
                JOptionPane.showMessageDialog(mFrame,
                        "Vyhral bíly hráč! v pomere\n" + "černý hráč " + mNewGame.p1.currentStoneCount(mNewGame.board) + " bíly hráč " + mNewGame.p2.currentStoneCount(mNewGame.board));
            } else {
                JOptionPane.showMessageDialog(mFrame,
                        "Remíza! v pomere\n" + "černý hráč" + mNewGame.p1.currentStoneCount(mNewGame.board) + "bíly hráč " + mNewGame.p2.currentStoneCount(mNewGame.board));
            }
        }

    }

    private JTable setTableBoard() {

        int size = mNewGame.getGame().getBoard().getSize();

        DefaultTableModel model = new DefaultTableModel(size, size) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    default:
                        return ImageIcon.class;
                }
            }
        };

        mTable = new JTable(model);
        mTable.setEnabled(false);
        TableColumn column = null;
        for (int i = 0; i < size; i++) {
            mTable.setRowHeight(i, 75);

        }
        mTable.setColumnSelectionAllowed(false);
        mTable.setRowSelectionAllowed(false);
        setTableInGUI(mTable);

        mTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //indexy bilych kamenu
                OldWhiteStones = new ArrayList<String>();
                for (int i = 0; i < mNewGame.getGame().getBoard().getSize() + 2; i++) {
                    for (int j = 0; j < mNewGame.getGame().getBoard().getSize() + 2; j++) {
                        if (mNewGame.getGame().getBoard().getField(i, j).getDisk() != null) {
                            if (mNewGame.getGame().getBoard().getField(i, j).getDisk().isWhite() == true) {
                                String indexI = String.valueOf(i);
                                String indexJ = String.valueOf(j);
                                String toSave = indexI + " " + indexJ;
                                OldWhiteStones.add(toSave);
                            }
                        }
                    }
                }
                //indexy cernych kamenu
                OldBlackStones = new ArrayList<String>();
                for (int i = 0; i < mNewGame.getGame().getBoard().getSize() + 2; i++) {
                    for (int j = 0; j < mNewGame.getGame().getBoard().getSize() + 2; j++) {
                        if (mNewGame.getGame().getBoard().getField(i, j).getDisk() != null) {
                            if (mNewGame.getGame().getBoard().getField(i, j).getDisk().isWhite() == false) {
                                String indexI = String.valueOf(i);
                                String indexJ = String.valueOf(j);
                                String toSave = indexI + " " + indexJ;
                                OldBlackStones.add(toSave);
                            }
                        }
                    }
                }

                int rowSelected = mTable.rowAtPoint(e.getPoint()) + 1;
                int colSelected = mTable.columnAtPoint(e.getPoint()) + 1;
                mNewGame.getRowColTable(rowSelected, colSelected);
                mNewGame.ContinueGame(mTable);
                setTableInGUI(mTable);

                if (mNewGame.isAIset()) {
                    mNewGame.ContinueGame(mTable);
                    setTableInGUI(mTable);
                }

                if (mNewGame.game.currentPlayer().toString().equals("white")) {
                    colorOfCurrentPlayer.setIcon(new ImageIcon(getClass().getResource("../../lib/disk_white.png")));
                    colorOfCurrentPlayer.setText(String.valueOf(mNewGame.game.currentPlayer().currentStoneCount(mNewGame.board)));
                } else {
                    colorOfCurrentPlayer.setIcon(new ImageIcon(getClass().getResource("../../lib/disk_black.png")));
                    colorOfCurrentPlayer.setText(String.valueOf(mNewGame.game.currentPlayer().currentStoneCount(mNewGame.board)));
                }
            }
        });
        return mTable;

    }

    private void clearTableInGUI(JTable table) {
        for (int i = 0; i < mNewGame.board.getSize(); i++) {
            for (int j = 0; j < mNewGame.board.getSize(); j++) {
                table.setValueAt(null, i, j);
            }
        }
    }

    private class MenuItemAction extends AbstractAction {

        public MenuItemAction(String text, ImageIcon icon,
                              Integer mnemonic) {
            super(text);

            putValue(SMALL_ICON, icon);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println(e.getActionCommand());
        }
    }


}

