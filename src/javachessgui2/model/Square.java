package javachessgui2.model;

import java.awt.Point;
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

	public void calcResult(Square[][] board) {
		Point[] dirs = { new Point(1, 1), new Point(-1, -1), new Point(1, -1), new Point(-1, 1), new Point(1, 0),
				new Point(0, 1), new Point(-1, 0), new Point(0, -1), };

		for (Point d : dirs) {
			Point res = new Point(i + d.x, j + d.y);
			int inc = 0;
			while (res.x >= 0 && res.x < 8 && res.y >= 0 && res.y < 8) {
				Square attacker = board[res.x][res.y];
				switch (Character.toLowerCase(attacker.getPiece().getFen_char())) {
				//TODO peoes tem que ver a cor, pois nao atacam pra traz.
				case 'p':
					
					break;

				default:
					break;
				}
				inc++;
				List<Square> list = attackers.get(inc);
				if (list == null) {
					list = new ArrayList<Square>();
					attackers.put(inc, list);
				}
				list.add(attacker);
				res = new Point(res.x + d.x, res.y + d.y);
			}
		}
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

	public Square() {
	}

	public Square(Piece p) {
		this.piece = p;
	}

	public Square(String algeb) {
		from_algeb(algeb);
	}

	public Square(int set_i, int set_j) {
		from_ij(set_i, set_j);
	}

	public Piece getPiece() {
		return piece;
	}
}