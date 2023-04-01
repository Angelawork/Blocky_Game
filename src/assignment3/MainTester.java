package assignment3;

import java.util.Random;

import static assignment3.GameColors.colorToString;

public class MainTester {
    public static void main(String[] a){

        for(int i=0;i<10;i++) {
            Block.gen.setSeed(i);
            Block test = new Block(0,4);
            System.out.println("seed:"+i);
            for(int j=0;j<4;j++) {
                PerimeterGoal tester = new PerimeterGoal(GameColors.BLOCK_COLORS[j]);
                BlobGoal tester2 = new BlobGoal(GameColors.BLOCK_COLORS[j]);
                System.out.println(colorToString(GameColors.BLOCK_COLORS[j])+" PerimeterGoal= "+tester.score(test));
                System.out.println(" BlobGoal= "+tester2.score(test));
            }
        }


        /* ERROR TESTER
        Block[] children = new Block[4];
        Block[] ulChildren = new Block[4];

        ulChildren[0] = new Block(0, 0, 0, 2, 2, GameColors.GREEN, new Block[0]);
        ulChildren[1] = new Block(0, 0, 0, 2, 2, GameColors.BLUE, new Block[0]);
        ulChildren[2] = new Block(0, 0, 0, 2, 2, GameColors.RED, new Block[0]);
        ulChildren[3] = new Block(0, 0, 0, 2, 2, GameColors.YELLOW, new Block[0]);

        children[0] = new Block(0, 0, 0, 1, 2, GameColors.GREEN, ulChildren);
        children[1] = new Block(0, 0, 0, 1, 2, GameColors.BLUE, new Block[0]);
        children[2] = new Block(0, 0, 0, 1, 2, GameColors.RED, new Block[0]);
        children[3] = new Block(0, 0, 0, 1, 2, GameColors.YELLOW, new Block[0]);

        Block b = new Block(0, 0, 16, 0, 2, null, children);
        b.children[0].printColoredBlock();
        b.children[0].printBlock();
        Color[][] test = b.children[0].flatten();
        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                System.out.println(colorToString(test[i][j]));
            }
        }
         */


         /* PDF example
  GameColors A=new GameColors();
  Block lv2UR = new Block(12, 8,4,2,2,A.BLUE, new Block[0]);
  Block lv2UL = new Block(8, 8,4,2,2,A.RED, new Block[0]);
  Block lv2LL = new Block(8, 12,4,2,2,A.YELLOW, new Block[0]);
  Block lv2LR = new Block(12, 12,4,2,2,A.BLUE, new Block[0]);

  Block lv1UR = new Block(8,0,8,1,2,A.GREEN, new Block[0]);
  Block lv1UL = new Block(0,0,8,1,2,A.RED, new Block[0]);
  Block lv1LL = new Block(0,8,8,1,2,A.YELLOW, new Block[0]);
  Block lv1LR = new Block(8,8,8,1,2,null, new Block[]{lv2UR,lv2UL,lv2LL,lv2LR});
  Block root=new Block(0,0,16,0,2,null,new Block[]{lv1UR,lv1UL,lv1LL,lv1LR});
  root.printBlock();
   */
    }
}
