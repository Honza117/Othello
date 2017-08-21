package xvican02.src;

import xvican02.src.gui.MainWindow;
import xvican02.src.gui.dialog.NewGameDialog;
import xvican02.src.testClass.LoadGame;
import xvican02.src.testClass.StartNewGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by vico on 3.5.2016.
 */
public class OthelloGame {


    public static void main(String args[]) {
        FirstWindow window = new FirstWindow();
        window.show();

    }

}

class FirstWindow {
    private JFrame mFrame;

    public void show() {

        JLabel l1;

        JLabel createGame = new JLabel(new ImageIcon(getClass().getResource("../lib/ic_create_game_btn.png")));

        JLabel openGame = new JLabel(new ImageIcon(getClass().getResource("../lib/ic_open_game_btn.png")));
        openGame.setVisible(true);
        openGame.setBackground(Color.GREEN);
        JLabel help = new JLabel(new ImageIcon(getClass().getResource("../lib/ic_help_btn.png")));

        mFrame = new JFrame("ReversiApp");
        mFrame.setIconImage(new ImageIcon(getClass().getResource("../lib/icon.png")).getImage());
        mFrame.setSize(1024, 800);
        mFrame.setLayout(new GridLayout(1, 1));
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("../lib/background.png")));
        background.setLayout(new GridLayout(6, 1));

        mFrame.add(background);
        l1 = new JLabel(new ImageIcon(getClass().getResource("../lib/name.png")));
        background.add(l1);

        background.add(createGame);
        background.add(openGame);
        background.add(help);
        mFrame.setVisible(true);

        setAllListeners(createGame, openGame, help);
    }

    private void setAllListeners(JLabel cGame, JLabel lGame, JLabel mHelp) {
        cGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                StartNewGame mGame;
                NewGameDialog dialog = new NewGameDialog();
                if ((mGame = dialog.show(mFrame)) != null) {
                    MainWindow window = new MainWindow(mGame);
                    window.getMainGui(false);
                    mFrame.setVisible(false);
                    mFrame.dispose();
                }
            }
        });

        lGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoadGame loadGame = new LoadGame();
                loadGame.showDialogJFile();
                StartNewGame mGame = loadGame.loadAllComponetsToGame();
                mGame.board.print();

                MainWindow window = new MainWindow(mGame);
                window.getMainGui(true);
                mFrame.setVisible(false);
                mFrame.dispose();
            }
        });

        mHelp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(mFrame, "Vítajte v readme pre hru Othello/Reversi.\n" +
                        "\n" +
                        "Jednoduchá logická hra pre všetky vekové kategórie. \n" +
                        "Hra funguje tak, že sa otvorí OthelloGame.jar a na výber je možnosť vytvoriť novú hru otvoriť hru alebo otvoriť pomocníka.\n" +
                        "Táto hra umožnuje hrať othello s hracími doskami 6x6, 8x8, 10x10 a 12x12. Na výber sú dva módy hráč vs hrác alebo hráč vs počítač. \n" +
                        "Pri výbere hráč vs počítač sa dá vybrať z dvoch úrovni umelej inteligencie a to mód \"Easy\" alebo \"Hard\". Ďalej je na výber klasický mód alebo mód\n" +
                        "so zamŕzaním kameňov. Pri zamŕzaní sa dá vybrať aký je spodný limit zamŕzania potom aj vrchný limit na koľko majú zamrznúť kamene a počet kameňov pri zamrznutí. \n" +
                        "Hra má Funkciu späť o jeden ťah. Hra funguje na pravidlách, ktoré môžete nájsť na wikipédie.\n" +
                        "\n" +
                        "Autori hry:\n" +
                        "  Jan Uhlíř\n" +
                        "  Peter Vican\n");
            }
        });

    }
}
