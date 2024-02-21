/*
 * Copyright 2021 Marc Liberatore.
 */

package sequencer;

import java.util.List;

import static org.junit.Assert.fail;

import java.util.ArrayList;

public class Assembler {

	/**
	 * Creates a new Assembler containing a list of fragments.
	 * 
	 * The list is copied into this assembler so that the original list will not be
	 * modified by the actions of this assembler.
	 * 
	 * @param fragments
	 */
	private List<Fragment> fragments;

	public Assembler(List<Fragment> fragments) {
		this.fragments = new ArrayList<Fragment>(fragments);
	}

	/**
	 * Returns the current list of fragments this assembler contains.
	 * 
	 * @return the current list of fragments
	 */
	public List<Fragment> getFragments() {
		return this.fragments; 
	}

	/**
	 * Attempts to perform a single assembly, returning true iff an assembly was
	 * performed.
	 * 
	 * This method chooses the best assembly possible, that is, it merges the two
	 * fragments with the largest overlap, breaking ties between merged fragments by
	 * choosing the shorter merged fragment.
	 * 
	 * Merges must have an overlap of at least 1.
	 * 
	 * After merging two fragments into a new fragment, the new fragment is inserted
	 * into the list of fragments in this assembler, and the two original fragments
	 * are removed from the list.
	 * 
	 * @return true iff an assembly was performed
	 */
	public boolean assembleOnce() {
		int globalMaxOverlap = 0;
		int secondMaxIDX = 0;
		int firstMaxIDX = 0;
		int globalTieBreaker = 2147483647;
		
		for (int firstIt = 0 ; firstIt < fragments.size() ; ++firstIt) {
			for (int parseAll = 0 ; parseAll < fragments.size() ; ++parseAll) {
				if (parseAll != firstIt) {	
					int overlap = fragments.get(firstIt).calculateOverlap(fragments.get(parseAll));
					if (overlap > globalMaxOverlap || (overlap == globalMaxOverlap && fragments.get(parseAll).length() < globalTieBreaker)) {
						globalMaxOverlap = overlap;
						firstMaxIDX = firstIt;
						secondMaxIDX = parseAll;
						globalTieBreaker = fragments.get(parseAll).length();
					}
				}
			}
		}
		if (globalMaxOverlap == 0) {
			return false;
		}
		else
		{	
			int firstRem = Math.min(firstMaxIDX, secondMaxIDX);
			int secondRem = Math.max(firstMaxIDX, secondMaxIDX);
			Fragment insert = new Fragment(fragments.get(firstMaxIDX).mergedWith(fragments.get(secondMaxIDX)).toString());
			fragments.remove(firstRem);
			fragments.remove(secondRem - 1);
			fragments.add(insert);
			return true;
		}
	}

	/**
	 * Repeatedly assembles fragments until no more assembly can occur.
	 */
	public void assembleAll() {
		while (this.fragments.size() > 1) {
			if (this.assembleOnce() == false) {
				break;
			}
		}
	}
}
