package assignment3;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class Block {
    private int xCoord;
    private int yCoord;
    private int size; // height/width of the square
    private int level; // the root (outer most block) is at level 0
    private int maxDepth;
    private Color color;

    private Block[] children; // {UR, UL, LL, LR}

    public static Random gen = new Random(123);


    /*
     * These two constructors are here for testing purposes.
     */
    public Block() {
    }

    public Block(int x, int y, int size, int lvl, int maxD, Color c, Block[] subBlocks) {
        this.xCoord = x;
        this.yCoord = y;
        this.size = size;
        this.level = lvl;
        this.maxDepth = maxD;
        this.color = c;
        this.children = subBlocks;
    }

    //page 14: It would be possible to generate that same board even if maximum depth were 5. In
    //that case, the unit cells would be one size smaller, even though no Block has been divided to that
    //level?
    public static void main(String[] agrs) {
        /*
        Block blockDepth3 = new Block(0,3);
        blockDepth3.updateSizeAndPosition(16, 0, 0);
        blockDepth3.printColoredBlock();
        System.out.println("------------------------");
        blockDepth3.reflect(0);
        blockDepth3.printColoredBlock();
        System.out.println("------------------------");
         */
    }

    /*
     * Creates a random block given its level and a max depth.
     *
     * xCoord, yCoord, size, and highlighted should not be initialized
     * (i.e. they will all be initialized by default)
     */
    public Block(int lvl, int maxDepth) {
        double randNum;
        this.level = lvl;
        this.maxDepth = maxDepth;

        if (lvl + 1 <= maxDepth) { //not yet at its maximum depth?
            randNum = gen.nextDouble(); //generate a random number in the interval [0, 1), should 1 be put there or not?(default=1?)
            if (randNum < Math.exp(-0.25 * lvl)) {
                this.color = null;
                this.children = new Block[]{new Block(lvl + 1, maxDepth),
                        new Block(lvl + 1, maxDepth), new Block(lvl + 1, maxDepth), new Block(lvl + 1, maxDepth)};
            } else {
                int selection = gen.nextInt(4);
                this.color = GameColors.BLOCK_COLORS[selection];
                this.children = new Block[0];
            }
        } else {
            int selection = gen.nextInt(4);
            this.color = GameColors.BLOCK_COLORS[selection];
            this.children = new Block[0];
        }
    }


    /*
     * Updates size and position for the block and all of its sub-blocks, while
     * ensuring consistency between the attributes and the relationship of the
     * blocks.
     *
     *  The size is the height and width of the block. (xCoord, yCoord) are the
     *  coordinates of the top left corner of the block.
     */
    public void updateSizeAndPosition(int size, int xCoord, int yCoord) {
        if (!(size > 0)) throw new IllegalArgumentException("Negative size is invalid");//negative, is 0 invalid also?

        this.size = size;
        this.xCoord = xCoord;
        this.yCoord = yCoord;

        if (this.color != null) {
            return;
        } else {
            //or it cannot be evenly divided into 2 integers until the max depth is reached? should size%2 check here or outside if?
            if (size % 2 != 0) throw new IllegalArgumentException("Size isn't divisible by 2!");
            this.children[0].updateSizeAndPosition(size / 2, xCoord + size / 2, yCoord);
            this.children[1].updateSizeAndPosition(size / 2, xCoord, yCoord);
            this.children[2].updateSizeAndPosition(size / 2, xCoord, yCoord + size / 2);
            this.children[3].updateSizeAndPosition(size / 2, xCoord + size / 2, yCoord + size / 2);
        }
    }


    /*
     * Returns a List of blocks to be drawn to get a graphical representation of this block.
     *
     * This includes, for each undivided Block:
     * - one BlockToDraw in the color of the block
     * - another one in the FRAME_COLOR and stroke thickness 3
     *
     * Note that a stroke thickness equal to 0 indicates that the block should be filled with its color.
     *
     * The order in which the blocks to draw appear in the list does NOT matter.
     */
    public ArrayList<BlockToDraw> getBlocksToDraw() {//can we use add and addAll method?
        ArrayList<BlockToDraw> output = new ArrayList<BlockToDraw>();
        if (this.color != null) {
            output.add(new BlockToDraw(this.color, this.xCoord, this.yCoord, this.size, 0));
            output.add(new BlockToDraw(GameColors.FRAME_COLOR, this.xCoord, this.yCoord, this.size, 3));
        } else {
            for (Block i : this.children) {
                output.addAll(i.getBlocksToDraw());
            }
        }
        return output;
    }

    /*
     * This method is provided and you should NOT modify it.
     */
    public BlockToDraw getHighlightedFrame() {
        return new BlockToDraw(GameColors.HIGHLIGHT_COLOR, this.xCoord, this.yCoord, this.size, 5);
    }


    /*
     * Return the Block within this Block that includes the given location
     * and is at the given level. If the level specified is lower than
     * the lowest block at the specified location, then return the block
     * at the location with the closest level value.
     *
     * The location is specified by its (x, y) coordinates. The lvl indicates
     * the level of the desired Block. Note that if a Block includes the location
     * (x, y), and that Block is subdivided, then one of its sub-Blocks will
     * contain the location (x, y) too. This is why we need lvl to identify
     * which Block should be returned.
     *
     * Input validation:
     * - this.level <= lvl <= maxDepth (if not throw exception)
     * - if (x,y) is not within this Block, return null.
     */
    public Block getSelectedBlock(int x, int y, int lvl) {//how to calculate if it runs in O(h)?
        if (lvl < this.level || lvl > this.maxDepth) throw new IllegalArgumentException("Out of range level input!");
        //is this the same as this.level <= lvl <= maxDepth?

        if (this.color == null && lvl > this.level) {
            Block output=null;
            int max=0;

            for (Block i : this.children) {
                Block result = i.getSelectedBlock(x, y, lvl);
                if (result!=null && result.level>max){//the block at the location with the closest level value?
                    max= result.getLevel();
                    output=result;
                }
            }
            return output;
        } else if (this.inrange(x,y) && lvl >= this.level) {
            return this;
        }
        return null;
    }

    //helper method for coords
    private boolean inrange(int x,int y){//is this how we detect the major block user selected is within?
        int xMax=this.xCoord+this.size;
        int xMin=this.xCoord;

        int yMax=this.yCoord+this.size;
        int yMin=this.yCoord;
        return(x>=xMin && x<=xMax && y>=yMin && y<=yMax);
    }

    /*
     * Swaps the child Blocks of this Block.
     * If input is 1, swap vertically. If 0, swap horizontally.
     * If this Block has no children, do nothing. The swap
     * should be propagate, effectively implementing a reflection
     * over the x-axis or over the y-axis.
     *
     */
    public void reflect(int direction) {
        if (direction != 0 && direction != 1) throw new IllegalArgumentException("input is neither a 0 nor a 1!");

        if (direction == 0) {//swap horizontally
            if (this.color == null) {//how to swap position in the array?
                this.children[0].Swap(this.children[3]);
                Substitute(this,0,3);
                this.children[1].Swap(this.children[2]);
                Substitute(this,1,2);

                for (Block i : this.children) {//does post/pre order matter?
                    i.reflect(direction);
                }
            }
        } else {//swap vertically
            if (this.color == null) {
                this.children[0].Swap(this.children[1]);
                Substitute(this,0,1);
                this.children[2].Swap(this.children[3]);
                Substitute(this,2,3);

                for (Block i : this.children) {
                    i.reflect(direction);
                }
            }
        }

    }

    private void Swap(Block target){//helper to swap coords
        int tempx=target.xCoord;
        int tempy=target.yCoord;
        target.updateSizeAndPosition(target.size,this.xCoord,this.yCoord);
        this.updateSizeAndPosition(this.size,tempx,tempy);
    }

    private static void Substitute(Block target, int x, int y){//helper to swap position
        Block temp=target.children[x];
        target.children[x]=target.children[y];
        target.children[y]=temp;
    }

    /*
     * Rotate this Block and all its descendants.
     * If the input is 1, rotate clockwise. If 0, rotate
     * counterclockwise. If this Block has no children, do nothing.
     */
    public void rotate(int direction) {//check whether game displays correct result?
        if (direction != 0 && direction != 1) throw new IllegalArgumentException("input is neither a 0 nor a 1!");

        if (direction == 1) {//clockwise
            if (this.color == null) {
                this.children[3].Swap(this.children[0]);
                Substitute(this,3,0);
                this.children[1].Swap(this.children[0]);
                Substitute(this,1,0);
                this.children[1].Swap(this.children[2]);
                Substitute(this,1,2);
                for (Block i : this.children) {
                    i.rotate(direction);
                }
            }
        } else {//counterclockwise
            if (this.color == null) {
                this.children[0].Swap(this.children[1]);
                Substitute(this,0,1);
                this.children[2].Swap(this.children[3]);
                Substitute(this,2,3);
                this.children[2].Swap(this.children[0]);
                Substitute(this,2,0);
                for (Block i : this.children) {
                    i.rotate(direction);
                }
            }
        }
    }


    /*
     * Smash this Block.
     *
     * If this Block can be smashed,
     * randomly generate four new children Blocks for it.
     * (If it already had children Blocks, discard them.)
     * Ensure that the invariants of the Blocks remain satisfied.
     *
     * A Block can be smashed iff it is not the top-level Block
     * and it is not already at the level of the maximum depth.
     *
     * Return True if this Block was smashed and False otherwise.
     *
     */
    public boolean smash() {
        if(this.getLevel()!=0 && this.getLevel()<this.maxDepth){
            this.color = null;
            this.children =new Block[]{new Block(level+1, maxDepth),
                    new Block(level+1, maxDepth), new Block(level+1, maxDepth), new Block(level+1, maxDepth)};
            this.updateSizeAndPosition(this.size,this.xCoord,this.yCoord);

            return true;
        }else {return false;}
    }


    /*
     * Return a two-dimensional array representing this Block as rows and columns of unit cells.
     *
     * Return and array arr where, arr[i] represents the unit cells in row i,
     * arr[i][j] is the color of unit cell in row i and column j.
     *
     * arr[0][0] is the color of the unit cell in the upper left corner of this Block.
     */
    public Color[][] flatten() {
        int mysize=(int)Math.pow(2,this.maxDepth-this.level);

        Color[][] arr = new Color[mysize][mysize];
        if(this.children.length==0){//this.color!=null
            for(int r=0;r<mysize;r++){
                for(int c=0;c<mysize;c++){
                    arr[r][c]=this.color;
                }
            }
            return arr;
        }else{
            Color[][][] children=new Color[4][][];
            for(int i=0;i<4;i++){
                children[i]=this.children[i].flatten();
            }
            for(int r=0;r<mysize;r++) {
                for (int c = 0; c < mysize; c++) {
                    if (r < mysize/2 && c < mysize/2) arr[r][c] = children[1][r][c];
                    else if (r < mysize/2 && c >= mysize/2) arr[r][c] = children[0][r][c-mysize/2];
                    else if (r >= mysize/2 && c < mysize/2) arr[r][c] = children[2][r-mysize/2][c];
                    else if (r >= mysize/2 && c >= mysize/2) arr[r][c] = children[3][r-mysize/2][c-mysize/2];
                }
            }
            return arr;
        }
    }


    // These two get methods have been provided. Do NOT modify them.
    public int getMaxDepth() {
        return this.maxDepth;
    }

    public int getLevel() {
        return this.level;
    }


    /*
     * The next 5 methods are needed to get a text representation of a block.
     * You can use them for debugging. You can modify these methods if you wish.
     */
    public String toString() {
        return String.format("pos=(%d,%d), size=%d, level=%d"
                , this.xCoord, this.yCoord, this.size, this.level);
    }

    public void printBlock() {
        this.printBlockIndented(0);
    }

    private void printBlockIndented(int indentation) {
        String indent = "";
        for (int i = 0; i < indentation; i++) {
            indent += "\t";
        }

        if (this.children.length == 0) {
            // it's a leaf. Print the color!
            String colorInfo = GameColors.colorToString(this.color) + ", ";
            System.out.println(indent + colorInfo + this);
        } else {
            System.out.println(indent + this);
            for (Block b : this.children)
                b.printBlockIndented(indentation + 1);
        }
    }

    private static void coloredPrint(String message, Color color) {
        System.out.print(GameColors.colorToANSIColor(color));
        System.out.print(message);
        System.out.print(GameColors.colorToANSIColor(Color.WHITE));
    }

    public void printColoredBlock() {
        Color[][] colorArray = this.flatten();
        for (Color[] colors : colorArray) {
            for (Color value : colors) {
                String colorName = GameColors.colorToString(value).toUpperCase();
                if (colorName.length() == 0) {
                    colorName = "\u2588";
                } else {
                    colorName = colorName.substring(0, 1);
                }
                coloredPrint(colorName, value);
            }
            System.out.println();
        }
    }

}
