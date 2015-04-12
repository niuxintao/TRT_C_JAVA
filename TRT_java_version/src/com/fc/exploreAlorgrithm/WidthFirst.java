package com.fc.exploreAlorgrithm;

import com.fc.caseRunner.CaseRunner;
import com.fc.model.CharacterWorkMachine;
import com.fc.model.TreeStruct;
import com.fc.tuple.CorpTupleWithTestCase;
import com.fc.tuple.Tuple;


/**
 * the tuple is bulid in the sequnce of width first
 * @author Xintao Niu
 *
 */
public class WidthFirst extends CharacterWorkMachine{
	public WidthFirst(TreeStruct tree, CaseRunner runner,
			CorpTupleWithTestCase generate) {
		super(tree, runner, generate);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected Tuple seletctTupleUnderTest() {
		Tuple[] tuples = (Tuple[]) this.tree.getUntestedTuple().toArray(new Tuple[tree.getUntestedTuple().size()]);
		return tuples[0];
	}

}
