package javachessgui2.model;

import java.util.Hashtable;

public class GameNode {

	public String fen;

	Hashtable child_nodes = new Hashtable();

	public GameNode parent_node = null;

	int add_san_cnt = 0;

	int rank = 0;

	public int num_childs = 0;

	public String gen_san = "";

	public Boolean mainline = true;
	public Boolean primary = true;

	public int caret_index_from = 0;
	public int caret_index_to = 0;

	public GameNode(String set_fen) {
		fen = set_fen;
	}

	public String getFen() {
		return fen;
	}
}