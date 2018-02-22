import java.util.ArrayList;
import java.util.List;

public class TicTacToeAI {
	    //return the score of the current game
		static int score(TicTacToe game){
			if(game.gameEnd() ==0){
				return 1;
			}else if(game.gameEnd() == 1){
				return -1;
			}else{
				return 0;
			}
			
		}
	    //return the minimum number of the list
		static int minScore(List<Integer> list){
			int min = list.get(0);
			for(int n : list){
				if(n<min){
					min = n;
				}
			}
			return min;
		}
	    //return the minimum number of the list
		static int maxScore(List<Integer> list){
			int max = list.get(0);
			for(int n : list){
				if(n>max){
					max = n;
				}
			}
			return max;
		}
		
		static int count;
	    //encapsulation of the minimax method and display the result
		static void minimax(TicTacToe game){
			count = 0;
			System.out.println("Game Result:" +minimaxHelper(game));
			System.out.println("Moves considered without alpha-beta pruning: "+count);
			
		}
	    //minimax method which increment the count and calculate the winner of the game
		static int minimaxHelper(TicTacToe game){
			int winner = game.gameEnd();
			if(winner !=3){
				count++;
				return score(game);
			}
			List<Integer> children = game.availableMoves();
			List<Integer> scores = new ArrayList<Integer>();
			for(int n : children){
				scores.add(minimaxHelper(game.newMove(n)));
			}
			if(game.getPlayer() ==0){
				return maxScore(scores);
			}else{
				return minScore(scores);
			}
			
		}
		static int alphaCount;
		static int betaCount;
	    //encapsulation method for the minimax with alpha beta pruning
		static void minimaxAlphaBeta(TicTacToe game){
			count=0;
			alphaCount=0;
			betaCount=0;
			System.out.println("Game Result:" +minimaxAlphaBeta(game, -10000, 10000));
			System.out.println("Moves considered with alpha-beta pruning: "+count);
			System.out.println("Alpha cuts: " + alphaCount);
			System.out.println("Beta cuts: " + betaCount);
		}
	    //minimax with alpha beta pruning implemented to optimize the speed of the algorithm
		static int minimaxAlphaBeta(TicTacToe game, int alpha, int beta){
			int winner = game.gameEnd();
			if(winner !=3){
				count++;
				return score(game);
			}
			List<Integer> children = game.availableMoves();
			
			if(game.getPlayer() ==0){
				for(int n : children){
					int score = minimaxAlphaBeta(game.newMove(n),alpha,beta);
					//update alpha
					if(score > alpha){
						alpha= score;
					}
					//cutoff
					if(alpha >= beta){
						alphaCount ++;
						break;
					}
				}
				return alpha;
			}else{
				for(int n : children){
					int score = minimaxAlphaBeta(game.newMove(n),alpha,beta);
					//update beta
					if(score < beta){
						beta=score;
					}
					//cutoff
					if(alpha >=beta){
						betaCount++;
						break;
					}
				}
				return beta;
				
			}
		}
		
/*public static void main(String[] args){
			TicTacToe game = new TicTacToe();
	        System.out.println("Initial board:");
	        game.print();
			System.out.println();
			minimax(game);
			System.out.println();
			minimaxAlphaBeta(game);
}*/
	}


