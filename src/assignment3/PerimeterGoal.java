package assignment3;

import java.awt.Color;

public class PerimeterGoal extends Goal{

	public PerimeterGoal(Color c) {
		super(c);
	}

	@Override
	public int score(Block board) {
		Color[][] myColors= board.flatten();
		int total=0;

		for(int c=0;c<myColors[0].length;c++){
			if(myColors[0][c]==targetGoal){//do we need to use .equals to compare color?
				total++;
			}
			if(myColors[myColors.length-1][c]==targetGoal){
				total++;
			}
		}

		for(int r=0;r<myColors.length;r++){
			if(myColors[r][0]==targetGoal){
				total++;
			}
			if(myColors[r][myColors[0].length-1]==targetGoal){
				total++;
			}
		}

		return total;
	}

	@Override
	public String description() {
		return "Place the highest number of " + GameColors.colorToString(targetGoal) 
		+ " unit cells along the outer perimeter of the board. Corner cell count twice toward the final score!";
	}

}
