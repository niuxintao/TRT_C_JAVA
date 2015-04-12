package com.fc.exploreAlorgrithm;

import com.fc.caseRunner.CaseRunner;
import com.fc.model.CharacterWorkMachine;
import com.fc.model.TreeStruct;
import com.fc.tuple.CorpTupleWithTestCase;

public class GenerateAlogrithm {
	public static final int RANDOM = 0;
	public static final int DEPTH = 1;
	public static final int WIDTH = 2;
	public static final int GREEDY = 3;
	public static final int PATH = 4;
	public static final String[] ALGRITHM_NAME={"random","depth","width","greedy","path"};
	
	public static CharacterWorkMachine getAlogrithm(int kind,TreeStruct tree,CaseRunner runner,CorpTupleWithTestCase generate){
		CharacterWorkMachine work = null;
		if(kind == RANDOM){
			work = new CharacterWorkMachine(tree,runner,generate);
		}else if(kind == DEPTH){
			work = new DepthFirst(tree,runner,generate);
		}else if(kind == WIDTH){
			work = new WidthFirst(tree,runner,generate);
		}else if(kind == GREEDY){
			work = new Greedy(tree,runner,generate);
		}else if(kind == PATH){
			work = new Path(tree,runner,generate);
		}
		return work;
	}
	
}
