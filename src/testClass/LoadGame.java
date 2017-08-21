package xvican02.src.testClass;

import xvican02.src.backend.board.BoardField;
import xvican02.src.backend.board.Disk;
import xvican02.src.backend.board.Field;

import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by vico on 17.5.2016.
 */
public class LoadGame extends JFileChooser {
    /**
     * loadGame - nacte aktualni hru ze souboru
     *
     * @throws IOException
     * @throws FileNotFoundException
     */

    public int mSize = 0;
    public boolean mAi = false;
    public boolean mDifficult = false;
    public boolean mFreeze = false;
    public int loadI = 0;
    public int loadB = 0;
    public int loadC = 0;
    public String player;
    public int p1Stone;
    public int p2Stone;
    public  List<String> blackStones;
    public  List<String> whiteStones;
    private StartNewGame mStartLoad;

    public void loadGame(File f) throws IOException {
        String line;
        BufferedReader br = null;
        int lineCounter = 0;

        try {
            InputStream fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            br = new BufferedReader(isr);
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        while ((line = br.readLine()) != null) {
            // Deal with the line
            lineCounter++;

            // pro nacteni vyuzijeme switch
            switch (lineCounter) {
                case 1:
                    //Nactu velikost
                    line = line.replace("\n", "");
                    mSize = Integer.parseInt(line);
                    break;
                case 2:
                    if (line == "true\n") {
                        mAi = true;
                    }
                    break;
                case 3:
                    if (line == "true\n") {
                        mDifficult = true;
                    }
                    break;
                case 4:
                    if (line == "true\n") {
                        mFreeze = true;
                    }
                    break;
                case 5:
                    //interval I
                    line = line.replace("\n", "");
                    loadI = Integer.parseInt(line);
                    break;
                case 6:
                    //interval B
                    line = line.replace("\n", "");
                    loadB = Integer.parseInt(line);
                    break;
                case 7:
                    //pocet kamenu C
                    line = line.replace("\n", "");
                    loadC = Integer.parseInt(line);
                    break;
                case 8:
                    //pozmenit v nove hre
                    line = line.replace("\n", "");
                   p1Stone = Integer.parseInt(line);
                    break;
                case 9:
                    //pozmenit v nove hre
                    line = line.replace("\n", "");
                    p2Stone = Integer.parseInt(line);
                    break;
                case 10:
                    player = line.replace("\n", "");
                    break;
                default:
                    if (line.equals("white")) {
                        //pole indexu jako string "i j"
                        //potom potreba zjistit index mezery a substr do a od indexu
                        whiteStones = new ArrayList<String>();
                        while (!(line = br.readLine()).equals("black")) {
                            whiteStones.add(line);
                        }
                    }

                    if ((line.equals("black"))) {
                        //pole indexu jako string "i j"
                        //potom potreba zjistit index mezery a substr do a od indexu
                        blackStones = new ArrayList<String>();
                        while (!(line = br.readLine()).equals("END")) {
                            blackStones.add(line);
                        }
                    }
                    break;
            }
            if (line.equals("END")) {
                break;
            }
        }
    }

    public void showDialogJFile() {
        int result = showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            try {
                loadGame(selectedFile);
            } catch (IOException ex) {
                ex.fillInStackTrace();
            }
        }
    }





    public StartNewGame loadAllComponetsToGame(){
        mStartLoad = new StartNewGame(mSize,mAi,mDifficult,mFreeze,loadI,loadB,loadC);
        mStartLoad.setFirstPlayer();
        player = player.replace("\n", "");
        if (mStartLoad.getGame().currentPlayer().toString() != player){
        	mStartLoad.setLoadStones(whiteStones, blackStones, true);
        }
        else{
        	mStartLoad.setLoadStones(whiteStones, blackStones, false);
        }
        
        mStartLoad.p1.stoneCount = p1Stone;
        mStartLoad.p2.stoneCount = p2Stone;
        if (!mStartLoad.getGame().currentPlayer().toString().equals(player) ){
            mStartLoad.game.nextPlayer();
        }
       return this.mStartLoad;
    }
}

