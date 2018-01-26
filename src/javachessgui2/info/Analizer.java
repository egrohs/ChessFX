package javachessgui2.info;

import javachessgui2.model.Piece;
import javachessgui2.model.Square;

public class Analizer {
	public static Square[][] board = new Square[8][8];

	private static void geraBoard(Piece[][] b) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				board[c][r] = new Square(c, r);
				board[c][r].setPiece(b[c][r]);
			}
		}
	}

	public static void calc(Piece[][] b) {
		geraBoard(b);
		// TODO a pe�a esta saindo da origem mas nao esta entrando no destino, board[][]
		// !!!
		for (int c = 0; c < 8; c++) {
			for (int r = 0; r < 8; r++) {
				Square sq = board[c][r];
				sq.direction(board);
			}
		}
		System.out.println("done");
		printVals();
	}

	private static void printVals() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				if (board[c][r].result < 10 && board[c][r].result >= 0)
					System.out.print("00");
				if (board[c][r].result > -10 && board[c][r].result < 0)
					System.out.print("0");
				if (board[c][r].result >= 10)
					System.out.print("0");
				System.out.print((int) Math.round(board[c][r].result) + " ");
				// if (color == 1) {
				// System.out.print(board[c][r].whiteInf + " ");
				// } else {
				// System.out.print(board[c][r].blackInf + " ");
				// }
			}
			System.out.println();
		}
	}
}
