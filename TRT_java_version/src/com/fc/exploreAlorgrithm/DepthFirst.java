package com.fc.exploreAlorgrithm;

import java.util.LinkedHashSet;
import java.util.List;

import com.fc.caseRunner.CaseRunner;
import com.fc.model.CharacterWorkMachine;
import com.fc.model.TreeStruct;
import com.fc.tuple.CorpTupleWithTestCase;
import com.fc.tuple.Tuple;


/**
 * 
 * @author Xintao Niu
 *
 */
public class DepthFirst extends CharacterWorkMachine{
	private LinkedHashSet<Tuple> depSort;
	
	public DepthFirst(TreeStruct tree, CaseRunner runner,
			CorpTupleWithTestCase generate) {
		super(tree, runner, generate);
		// TODO Auto-generated constructor stub
		depSort = new LinkedHashSet<Tuple>();
	}
	
	/**
	 * inital task
	 */
	@Override
	protected void inital() {
		//sort the tuple as deep first
		Tuple root = tree.getRoot();
		depFirst(root);
		this.tree.setUntestedTuple(depSort);
		
	}
	
	protected void depFirst(Tuple tuple){
		if(this.depSort.contains(tuple))
			return;
		int degree = tuple.getDegree() - 1;
		List<Tuple> children = tuple.getChildTuplesByDegree(degree);
		for(Tuple child : children){
			this.depFirst(child);
		}
		if(this.tree.getUntestedTuple().contains(tuple))
			this.depSort.add(tuple);
	}

	@Override
	protected Tuple seletctTupleUnderTest() {
		Tuple[] tuples = (Tuple[]) this.tree.getUntestedTuple().toArray(new Tuple[tree.getUntestedTuple().size()]);
		return tuples[0];
	}
}
