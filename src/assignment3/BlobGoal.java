package assignment3;

import java.awt.Color;

public class BlobGoal extends Goal{

	public BlobGoal(Color c) {
		super(c);
	}

	@Override
	public int score(Block board) {
		Color[][] myColors= board.flatten();
		int row=myColors.length;
		int col=myColors[0].length;

		boolean[][] visited=new boolean[row][col];
		int total=0;

		for(int r=0;r<row;r++){
			for(int c=0;c<col;c++){
				int result=undiscoveredBlobSize(r,c,myColors,visited);
				if(result>total){total=result;}
			}
		}
		return total;
	}

	@Override
	public String description() {
		return "Create the largest connected blob of " + GameColors.colorToString(targetGoal) 
		+ " blocks, anywhere within the block";
	}

	//should this method be recursive?:
	//the method returns the number of unit cells of the target color that
	//are orthogonally connect to the the unit cell in position (i, j) and have not been visited yet
	public int undiscoveredBlobSize(int i, int j, Color[][] unitCells, boolean[][] visited) {
		int count=0;
		if(unitCells[i][j]!=targetGoal || visited[i][j]){//compare by == or .equals?
			return 0;
		}else if(unitCells[i][j]==targetGoal){//otherwise this cell will end up being double counted (or worse).
			if(!visited[i][j]){count++; visited[i][j]=true;}

			if(i+1 < unitCells.length) {
				count += undiscoveredBlobSize(i + 1, j, unitCells, visited);
			}if (i-1>=0) {
				count += undiscoveredBlobSize(i -1, j, unitCells, visited);
			}if(j+1 < unitCells[0].length) {
				count += undiscoveredBlobSize(i, j+1, unitCells, visited);
			}if(j-1>=0) {
				count += undiscoveredBlobSize(i, j-1, unitCells, visited);
			}
		}
		return count;

	}

}
