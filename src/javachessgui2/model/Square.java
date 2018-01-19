package javachessgui2.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Square {
	public Map<Integer, List<Square>> attackers = new LinkedHashMap<Integer, List<Square>>();
	// public List<Piece> targeredByBlack = new ArrayList<Piece>();
	// public List<Piece> targeredByWhite = new ArrayList<Piece>();
	// public List<Piece> indirectAttackedBy = new ArrayList<Piece>();
	public Boolean valid = false;
	// file 0 .. 7 ( a = 0, h = 7 )
	public int i;
	// rank 0 .. 7 ( 8 = 0, 1 = 7 )
	public int j;
	private Piece piece;
	public double whiteInf, blackInf, result;
	public int qntWhiteInf, qntBlackInf;

	public void direction(Square[][] board) {
		// List<Square> list = attackers.get(inc);
		// if (list == null) {
		// list = new ArrayList<Square>();
		// attackers.put(inc, list);
		// }
		// if() {
		// list.add(attacker);}
		// res = new Point(res.x + d.x, res.y + d.y);
		// }

		// vai indo numa das 8 direcoes ate encontrar uma peça com range. Continua
		// enquando entrar peças da mesma cor..

		// leste
		dir(board, 1, 0);
		// oeste
		dir(board, -1, 0);
		// norte
		dir(board, 0, -1);
		// sul
		dir(board, 0, 1);
		// nordeste
		dir(board, 1, -1);
		// sudeste
		dir(board, 1, 1);
		// sudoeste
		dir(board, 1, 1);
		// noroeste
		dir(board, -1, -1);
	}

	private void dir(Square[][] board, int col, int lin) {
		Integer color = null;
		for (int cont = 1, x = i + col * cont, y = j + lin * cont; x >= 0 && x < 8 && y >= 0
				&& y < 8; cont++, x = i + col * cont, y = j + lin * cont) {
			Square sq = null;
			sq = board[x][y];
			Piece p = sq.getPiece();
			if (color == null) {
				color = p.color();
			}
			if (p != null && color == p.color() && (sq.piece.type() & sq.piece.tipo(col, lin)) != 0) {
				List<Square> list = attackers.get(cont);
				if (list == null) {
					list = new ArrayList<Square>();
				}
				list.add(sq);
				// color = p.color();
				attackers.put(cont, list);
			} else {
				break;
			}
		}
	}

	private void ff(Square[][] board) {
		// for (int cont = 1; cont < 8; cont++) {
		// // retas
		// if ((this.piece.code() & Piece.STRAIGHT) != 0) {
		// // torre, queen
		//
		//
		// board[i+cont][j];
		// board[i][j+cont];
		//
		// if() {
		// break;
		// }
		//
		// if ((this.piece.code() & Piece.SINGLE) != 0) {
		// // king
		// }
		// }
		// // diagonal, bishop, queen, *king(+1), *peao(+1, pra frente)
		// if ((this.piece.code() & Piece.DIAGONAL) != 0) {
		// board[i+cont][j+cont];
		// if ((this.piece.code() & Piece.SINGLE) != 0) {
		// // king
		// }
		// } else if ((this.piece.code() & Piece.PAWN) != 0) {
		// // king
		// }
		// // move em ele, knight
		// if ((this.piece.code() & Piece.KNIGHT) != 0) {
		//
		// }
		// }
	}

	public void print() {
		System.out.println(valid ? "[ Square : i = " + i + " , j = " + j + " , algeb = " + to_algeb() + " ]"
				: "[ Square : invalid ]");
	}

	public String to_algeb() {
		if (!valid) {
			return null;
		}
		char file = (char) i;
		file += 'a';
		char rank = (char) (7 - j);
		rank += '1';
		String algeb = "" + file + rank;
		return algeb;
	}

	public Boolean from_ij(int set_i, int set_j) {
		if ((set_i < 0) || (set_i > 7) || (set_j < 0) || (set_j > 7)) {
			return (valid = false);
		}
		i = set_i;
		j = set_j;
		return (valid = true);
	}

	public Boolean from_algeb(String algeb) {
		if (algeb.length() < 2) {
			return (valid = false);
		}
		char file = algeb.charAt(0);
		if ((file < 'a') || (file > 'h')) {
			return (valid = false);
		}
		char rank = algeb.charAt(1);
		if ((rank < '1') || (rank > '8')) {
			return (valid = false);
		}
		i = file - 'a';
		j = 7 - (rank - '1');
		return (valid = true);
	}

	public Boolean is_equal(Square s) {
		return ((s.i == i) && (s.j == j));
	}

	public Square(String algeb) {
		if (!from_algeb(algeb)) {
			throw new IllegalArgumentException("creating square " + algeb);
		}
	}

	public Square(int set_i, int set_j) {
		from_ij(set_i, set_j);
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	@Override
	public String toString() {
		if (piece != null) {
			return piece.fen_char + "(" + i + ", " + j + ")";
		}
		return "(" + i + ", " + j + ")";
	}
}