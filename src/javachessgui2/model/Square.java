package javachessgui2.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Square {
	public Map<Integer, List<Square>> whiteInfluece = new LinkedHashMap<Integer, List<Square>>();
	public Map<Integer, List<Square>> blackInfluece = new LinkedHashMap<Integer, List<Square>>();
//	public List<Square> whiteInfluece = new ArrayList<Square>();
//	public List<Square> blackInfluece = new ArrayList<Square>();
	// public List<Piece> indirectAttackedBy = new ArrayList<Piece>();
	public Boolean valid = false;
	// file 0 .. 7 ( a = 0, h = 7 )
	public int i;
	// rank 0 .. 7 ( 8 = 0, 1 = 7 )
	public int j;
	private Piece piece;
	public double result;
	// public int qntWhiteInf, qntBlackInf;
	private boolean add;

	public void direction(Square[][] board) {
		result = 0;
		whiteInfluece.clear();
		blackInfluece.clear();
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
		dir(board, -1, 1);
		// noroeste
		dir(board, -1, -1);
//TODO os fors nao precisam ir ate cont < 10  ou board.length
		for (int cont = 0; cont < 10; cont++) {
			List<Square> wInf = whiteInfluece.get(cont);
			List<Square> bInf = blackInfluece.get(cont);
			for (int index = 0; index < board.length; index++) {
				double wv = 0;
				double bv = 0;
				if (wInf != null && bInf != null) {
					wv = Piece.influence(wInf.get(index).piece.fen_char);
					bv = Piece.influence(bInf.get(index).piece.fen_char);
					if (wv - bv != 0) {
						result = wv - bv;
						return;
					}
				} else if (wInf != null && index < wInf.size()) {
					wv = Piece.influence(wInf.get(index).piece.fen_char);
					result += wv;// TODO soma das influencias brancas;
				} else if (bInf != null && index < bInf.size()) {
					bv = Piece.influence(bInf.get(index).piece.fen_char);
					result += bv;// TODO soma das influencias pretas;
				} else {
					// casa neutra
					// result = 0;
					// return;
				}
			}
		}
	}

	private void dir(Square[][] board, int col, int lin) {
		Integer color = null;
		add = true;
		for (int cont = 1, x = i + col * cont, y = j + lin * cont; add && x >= 0 && x < 8 && y >= 0
				&& y < 8; cont++, x = i + col * cont, y = j + lin * cont) {
			add = false;
			Square sq = null;
			sq = board[x][y];
			Piece p = sq.getPiece();
			if (color == null) {
				color = p.color();
			}
			// TODO knigh
			if (p.code() != Piece.PIECE_NONE) {
				if (Piece.COLOR_NONE == color || color == p.color()) {
					if (!p.single() && (p.type() & p.tipo(col, lin)) != 0) {
						// RQB apenas (risco rei)
						addTh(p.color(), cont, sq);
					} else if (!distaMais1(sq)) {
						// peao ou rei
						if (p.type() == Piece.PAWN && col != 0 && ((p.color() == Piece.WHITE && lin == 1)
								|| (p.color() == Piece.BLACK && lin == -1))) {
							// peao
							addTh(p.color(), cont, sq);
						} else if (p.type() == Piece.KING) {
							// rei
							addTh(p.color(), cont, sq);
						}
					}
				}
			} else {
				add = true;
			}
		}
	}

	private void addTh(int color, int cont, Square sq) {
		List<Square> list = null;
		if (color == Piece.WHITE) {
			list = whiteInfluece.get(cont);
		} else {
			list = blackInfluece.get(cont);
		}
		if (list == null) {
			list = new ArrayList<Square>();
		}
		list.add(sq);
		// color = p.color();
		if (color == Piece.WHITE) {
			whiteInfluece.put(cont, list);
		} else {
			blackInfluece.put(cont, list);
		}
		add = true;
	}

	public boolean distaMais1(Square other) {
		return Math.max(Math.abs(Math.abs(this.i) - Math.abs(other.i)),
				Math.abs(Math.abs(this.j) - Math.abs(other.j))) > 1;
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