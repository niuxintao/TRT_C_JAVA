package com.fc.exploreAlorgrithm;

import com.fc.caseRunner.CaseRunner;
import com.fc.model.CharacterWorkMachine;
import com.fc.model.TreeStruct;
import com.fc.tuple.CorpTupleWithTestCase;
import com.fc.tuple.Tuple;

public class Greedy extends CharacterWorkMachine {

	public Greedy(TreeStruct tree, CaseRunner runner,
			CorpTupleWithTestCase generate) {
		super(tree, runner, generate);
		// TODO Auto-generated constructor stub
	}

	public int greedyValue(Tuple tuple) {
		int unTestedChildrenNum = 0;
		int unTestedFathersNum = 0;
		for (Tuple child : tuple.getAllChilds()) {
			if (this.tree.getUntestedTuple().contains(child))
				unTestedChildrenNum++;
		}
		for (Tuple father : tuple.getAllFathers()) {
			if (this.tree.getUntestedTuple().contains(father))
				unTestedFathersNum++;
		}
		
		return unTestedChildrenNum > unTestedFathersNum?unTestedChildrenNum:unTestedFathersNum;
	}

	public Tuple getMaxGreedyTuple() {
		int min = Integer.MAX_VALUE;
		Tuple result = null;
		for(Tuple tuple :this.tree.getUntestedTuple() ){
			int value = this.greedyValue(tuple);
			if(value < min){
				min = value;
				result = tuple;
			}
		}
		return result;
	}

	@Override
	protected Tuple seletctTupleUnderTest() {
		return getMaxGreedyTuple();
	}

}
