package javachessgui2.info;

import javachessgui2.model.Piece;
import javachessgui2.model.Square;

public class Analizer {
	Square[][] board = new Square[8][8];

	public Analizer(Piece[][] b) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				board[c][r] = new Square(b[c][r]);
			}
		}
	}

	public void printVals(int color) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				if (color == 1) {
					System.out.print(board[c][r].whiteInf + " ");
				} else {
					System.out.print(board[c][r].blackInf + " ");
				}
			}
			System.out.println();
		}
	}

	private void whiteIncreaseValue(int x, int y, char defender) {
		//Piece defender = board[x][y].getPiece();
		if (0 <= x && x < 8 && 0 <= y && y < 8) {
			board[x][y].whiteInf += Piece.influence(defender);
			board[x][y].qntWhiteInf++;
		}
	}

	private void blackDecreaseValue(int x, int y, char defender) {
		//Piece defender = board[x][y].getPiece();
		if (0 <= x && x < 8 && 0 <= y && y < 8) {
			board[x][y].blackInf += Piece.influence(defender);
			board[x][y].qntBlackInf++;
		}
	}

	public void computeSquareValues() {
		for (Square[] squares : board) {
			for (Square s : squares) {
				// s.value = 0;
				s.qntWhiteInf = 0;
				s.qntBlackInf = 0;
				s.whiteInf = 0;
				s.blackInf = 0;
			}
		}

		int x, y;
		int[] xx = { -2, -1, 1, 2 };
		int[] yy = { -2, -1, 1, 2 };
		for (int c = 0; c < 8; c++) {
			for (int r = 0; r < 8; r++) {
				char defender = board[c][r].getPiece().getFen_char();
				switch (defender) {
				// White Pieces first
				case 'R':
					// horizontal
					x = c + 1;
					while (x < 8) {
						whiteIncreaseValue(x, r, defender);
						if (-1 == "-KQR".indexOf(board[x][r].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "K".indexOf(board[x][r].getPiece().getFen_char())) {
							whiteIncreaseValue(x + 1, r, defender);
							break;
						}
						x++;
					}
					x = c - 1;
					while (x >= 0) {
						whiteIncreaseValue(x, r, defender);
						if (-1 == "-KQR".indexOf(board[x][r].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "K".indexOf(board[x][r].getPiece().getFen_char())) {
							whiteIncreaseValue(x - 1, r, defender);
							break;
						}
						x--;
					}
					// Vertical
					y = r + 1;
					while (y < 8) {
						whiteIncreaseValue(c, y, defender);
						if (-1 == "-KQR".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "K".indexOf(board[c][y].getPiece().getFen_char())) {
							whiteIncreaseValue(c, y + 1, defender);
							break;
						}
						y++;
					}
					y = r - 1;
					while (y >= 0) {
						whiteIncreaseValue(c, y, defender);
						if (-1 == "-KQR".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "K".indexOf(board[c][y].getPiece().getFen_char())) {
							whiteIncreaseValue(c, y - 1, defender);
							break;
						}
						y--;
					}
					break;
				case 'r':
					// horizontal
					x = c + 1;
					while (x < 8) {
						blackDecreaseValue(x, r, defender);
						if (-1 == "-kqr".indexOf(board[x][r].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "k".indexOf(board[x][r].getPiece().getFen_char())) {
							blackDecreaseValue(x + 1, r, defender);
							break;
						}
						x++;
					}
					x = c - 1;
					while (x >= 0) {
						blackDecreaseValue(x, r, defender);
						if (-1 == "-kqr".indexOf(board[x][r].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "k".indexOf(board[x][r].getPiece().getFen_char())) {
							blackDecreaseValue(x - 1, r, defender);
							break;
						}
						x--;
					}
					// Vertical
					y = r + 1;
					while (y < 8) {
						blackDecreaseValue(c, y, defender);
						if (-1 == "-kqr".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "k".indexOf(board[c][y].getPiece().getFen_char())) {
							blackDecreaseValue(c, y + 1, defender);
							break;
						}
						y++;
					}
					y = r - 1;
					while (y >= 0) {
						blackDecreaseValue(c, y, defender);
						if (-1 == "-kqr".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "k".indexOf(board[c][y].getPiece().getFen_char())) {
							blackDecreaseValue(c, y - 1, defender);
							break;
						}
						y--;
					}
					break;
				case 'N':
					for (int x1 : xx) {
						for (int y1 : yy) {
							if ((Math.abs(x1) + Math.abs(y1)) == 3) {
								int x2 = x1 + c;
								int y2 = y1 + r;
								if (x2 >= 0 && x2 < 8 && y2 >= 0 && y2 < 8) {
									whiteIncreaseValue(x2, y2, defender);
								}
							}
						}
					}
					break;
				case 'n':
					for (int x1 : xx) {
						for (int y1 : yy) {
							if ((Math.abs(x1) + Math.abs(y1)) == 3) {
								int x2 = x1 + c;
								int y2 = y1 + r;
								if (x2 >= 0 && x2 < 8 && y2 >= 0 && y2 < 8) {
									blackDecreaseValue(x2, y2, defender);
								}
							}
						}
					}
					break;
				case 'B':
					x = c + 1;
					y = r + 1;
					while (x < 8 && y < 8) {
						whiteIncreaseValue(x, y, defender);
						if (-1 == "-BPQK".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "PK".indexOf(board[c][y].getPiece().getFen_char())) {
							whiteIncreaseValue(x + 1, y + 1, defender);
							break;
						}

						x++;
						y++;
					}
					x = c - 1;
					y = r - 1;
					while (x >= 0 && y >= 0) {
						whiteIncreaseValue(x, y, defender);
						if (-1 == "P-BQK".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "K".indexOf(board[c][y].getPiece().getFen_char())) {
							whiteIncreaseValue(x - 1, y - 1, defender);
							break;
						}
						x--;
						y--;
					}
					x = c + 1;
					y = r - 1;
					while (x < 8 && y >= 0) {
						whiteIncreaseValue(x, y, defender);
						if (-1 == "P-BQK".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "K".indexOf(board[c][y].getPiece().getFen_char())) {
							whiteIncreaseValue(x + 1, y - 1, defender);
							break;
						}
						x++;
						y--;
					}
					x = c - 1;
					y = r + 1;
					while (x >= 0 && y < 8) {
						whiteIncreaseValue(x, y, defender);
						if (-1 == "-BPQK".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "PK".indexOf(board[c][y].getPiece().getFen_char())) {
							whiteIncreaseValue(x - 1, y + 1, defender);
							break;
						}
						x--;
						y++;
					}
					break;
				case 'b':
					x = c + 1;
					y = r + 1;
					while (x < 8 && y < 8) {
						blackDecreaseValue(x, y, defender);
						if (-1 == "p-bqk".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "k".indexOf(board[c][y].getPiece().getFen_char())) {
							blackDecreaseValue(x + 1, y + 1, defender);
							break;
						}
						x++;
						y++;
					}
					x = c - 1;
					y = r - 1;
					while (x >= 0 && y >= 0) {
						blackDecreaseValue(x, y, defender);
						if (-1 == "-bpqk".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "pk".indexOf(board[c][y].getPiece().getFen_char())) {
							blackDecreaseValue(x - 1, y - 1, defender);
							break;
						}
						x--;
						y--;
					}
					x = c + 1;
					y = r - 1;
					while (x < 8 && y >= 0) {
						blackDecreaseValue(x, y, defender);
						if (-1 == "-bpqk".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "pk".indexOf(board[c][y].getPiece().getFen_char())) {
							blackDecreaseValue(x + 1, y - 1, defender);
							break;
						}
						x++;
						y--;
					}
					x = c - 1;
					y = r + 1;
					while (x >= 0 && y < 8) {
						blackDecreaseValue(x, y, defender);
						if (-1 == "p-bqk".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "k".indexOf(board[c][y].getPiece().getFen_char())) {
							blackDecreaseValue(x - 1, y + 1, defender);
							break;
						}
						x--;
						y++;
					}
					break;
				case 'Q':
					// Rook + Bishop?
					x = c + 1;
					while (x < 8) {
						whiteIncreaseValue(x, r, defender);
						if (-1 == "-KQR".indexOf(board[x][r].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "K".indexOf(board[x][r].getPiece().getFen_char())) {
							whiteIncreaseValue(x + 1, r, defender);
							break;
						}
						x++;
					}
					x = c - 1;
					while (x >= 0) {
						whiteIncreaseValue(x, r, defender);
						if (-1 == "-KQR".indexOf(board[x][r].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "K".indexOf(board[x][r].getPiece().getFen_char())) {
							whiteIncreaseValue(x - 1, r, defender);
							break;
						}
						x--;
					}
					// Vertical
					y = r + 1;
					while (y < 8) {
						whiteIncreaseValue(c, y, defender);
						if (-1 == "-KQR".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "K".indexOf(board[c][y].getPiece().getFen_char())) {
							whiteIncreaseValue(c, y + 1, defender);
							break;
						}
						y++;
					}
					y = r - 1;
					while (y >= 0) {
						whiteIncreaseValue(c, y, defender);
						if (-1 == "-KQR".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "K".indexOf(board[c][y].getPiece().getFen_char())) {
							whiteIncreaseValue(c, y - 1, defender);
							break;
						}
						y--;
					}
					x = c + 1;
					y = r + 1;
					while (x < 8 && y < 8) {
						whiteIncreaseValue(x, y, defender);
						if (-1 == "-KQBP".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "KP".indexOf(board[c][y].getPiece().getFen_char())) {
							whiteIncreaseValue(x + 1, y + 1, defender);
							break;
						}
						x++;
						y++;
					}
					x = c - 1;
					y = r - 1;
					while (x >= 0 && y >= 0) {
						whiteIncreaseValue(x, y, defender);
						if (-1 == "P-KQB".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "K".indexOf(board[c][y].getPiece().getFen_char())) {
							whiteIncreaseValue(x - 1, y - 1, defender);
							break;
						}
						x--;
						y--;
					}
					x = c + 1;
					y = r - 1;
					while (x < 8 && y >= 0) {
						whiteIncreaseValue(x, y, defender);
						if (-1 == "P-KQB".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "K".indexOf(board[c][y].getPiece().getFen_char())) {
							whiteIncreaseValue(x + 1, y - 1, defender);
							break;
						}
						x++;
						y--;
					}
					x = c - 1;
					y = r + 1;
					while (x >= 0 && y < 8) {
						whiteIncreaseValue(x, y, defender);
						if (-1 == "-KQBP".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "KP".indexOf(board[c][y].getPiece().getFen_char())) {
							whiteIncreaseValue(x - 1, y + 1, defender);
							break;
						}
						x--;
						y++;
					}
					break;
				case 'q':
					x = c + 1;
					while (x < 8) {
						blackDecreaseValue(x, r, defender);
						if (-1 == "-kqr".indexOf(board[x][r].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "k".indexOf(board[x][r].getPiece().getFen_char())) {
							blackDecreaseValue(x + 1, r, defender);
							break;
						}
						x++;
					}
					x = c - 1;
					while (x >= 0) {
						blackDecreaseValue(x, r, defender);
						if (-1 == "-kqr".indexOf(board[x][r].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "k".indexOf(board[x][r].getPiece().getFen_char())) {
							blackDecreaseValue(x - 1, r, defender);
							break;
						}
						x--;
					}
					// Vertical
					y = r + 1;
					while (y < 8) {
						blackDecreaseValue(c, y, defender);
						if (-1 == "-kqr".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "k".indexOf(board[c][y].getPiece().getFen_char())) {
							blackDecreaseValue(c, y + 1, defender);
							break;
						}
						y++;
					}
					y = r - 1;
					while (y >= 0) {
						blackDecreaseValue(c, y, defender);
						if (-1 == "-kqr".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "k".indexOf(board[c][y].getPiece().getFen_char())) {
							blackDecreaseValue(c, y - 1, defender);
							break;
						}
						y--;
					}
					x = c + 1;
					y = r + 1;
					while (x < 8 && y < 8) {
						blackDecreaseValue(x, y, defender);
						if (-1 == "p-kqb".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "k".indexOf(board[c][y].getPiece().getFen_char())) {
							blackDecreaseValue(x + 1, y + 1, defender);
							break;
						}
						x++;
						y++;
					}
					x = c - 1;
					y = r - 1;
					while (x >= 0 && y >= 0) {
						blackDecreaseValue(x, y, defender);
						if (-1 == "-kqbp".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "kp".indexOf(board[c][y].getPiece().getFen_char())) {
							blackDecreaseValue(x - 1, y - 1, defender);
							break;
						}
						x--;
						y--;
					}
					x = c + 1;
					y = r - 1;
					while (x < 8 && y >= 0) {
						blackDecreaseValue(x, y, defender);
						if (-1 == "-kqbp".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "kp".indexOf(board[c][y].getPiece().getFen_char())) {
							blackDecreaseValue(x + 1, y - 1, defender);
							break;
						}
						x++;
						y--;
					}
					x = c - 1;
					y = r + 1;
					while (x >= 0 && y < 8) {
						blackDecreaseValue(x, y, defender);
						if (-1 == "p-kqb".indexOf(board[c][y].getPiece().getFen_char())) {
							break;
						}
						if (0 <= "k".indexOf(board[c][y].getPiece().getFen_char())) {
							blackDecreaseValue(x - 1, y + 1, defender);
							break;
						}
						x--;
						y++;
					}
					break;
				case 'K':
					for (int dx = -1; dx < 2; dx++) {
						for (int dy = -1; dy < 2; dy++) {
							if (Math.abs(dx) + Math.abs(dy) != 0) {
								x = c + dx;
								y = r + dy;
								if (x >= 0 && x < 8 && y >= 0 && y < 8) {
									whiteIncreaseValue(x, y, defender);
								}
							}
						}
					}
					break;
				case 'k':
					for (int dx = -1; dx < 2; dx++) {
						for (int dy = -1; dy < 2; dy++) {
							if (Math.abs(dx) + Math.abs(dy) != 0) {
								x = c + dx;
								y = r + dy;
								if (x >= 0 && x < 8 && y >= 0 && y < 8) {
									blackDecreaseValue(x, y, defender);
								}
							}
						}
					}
					break;
				case 'P':
					if (r + 1 < 8 && c > 0) {
						whiteIncreaseValue(c - 1, r - 1, defender);
					}
					if (r + 1 < 8 && c < 7) {
						whiteIncreaseValue(c + 1, r - 1, defender);
					}
					break;
				case 'p':
					if (r - 1 >= 0 && c > 0) {
						blackDecreaseValue(c - 1, r + 1, defender);
					}
					if (r - 1 >= 0 && c < 7) {
						blackDecreaseValue(c + 1, r + 1, defender);
					}
					break;
				default:
					break;
				}
			}
		}
	}
}
