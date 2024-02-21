/*
 * Copyright 2021 Marc Liberatore.
 */

package sequencer;

public class Fragment {


	private String nucleotides;
	/**
	 * Creates a new Fragment based upon a String representing a sequence of
	 * nucleotides, containing only the uppercase characters G, C, A and T.
	 * 
	 * @param nucleotides
	 * @throws IllegalArgumentException if invalid characters are in the sequence of
	 *                                  nucleotides
	 */

	public Fragment(String nucleotides) throws IllegalArgumentException {
		for (int i = 0; i < nucleotides.length(); i++) {
			char car = nucleotides.charAt(i);
			if (car != 'G' && car != 'C' && car != 'A' && car != 'T') {
				throw new IllegalArgumentException("Invalid character in sequence: " + car);
			}
		}
		this.nucleotides = nucleotides;
	}

	/**
	 * Returns the length of this fragment.
	 * 
	 * @return the length of this fragment
	 */
	public int length() {
		return this.nucleotides.length();
	}

	/**
	 * Returns a String representation of this fragment, exactly as was passed to
	 * the constructor.
	 * 
	 * @return a String representation of this fragment
	 */
	@Override
	public String toString() {
		return this.nucleotides;
	}

	/**
	 * Return true if and only if this fragment contains the same sequence of
	 * nucleotides as another sequence.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!(o instanceof Fragment)) {
			return false;
		}



		Fragment f = (Fragment) o;

		if (f.nucleotides.equals(this.nucleotides)) {
			return true;
		}

		// Don't unconditionally return false; check that
		// the relevant instances variables in this and f 
		// are semantically equal
		return false;
	}

	/**
	 * Returns the number of nucleotides of overlap between the end of this fragment
	 * and the start of another fragment, f.
	 * 
	 * The largest overlap is found, for example, CAA and AAG have an overlap of 2,
	 * not 1.
	 * 
	 * @param f the other fragment
	 * @return the number of nucleotides of overlap
	 */
	public int calculateOverlap(Fragment f) {
		int lowLen = this.nucleotides.length() - f.nucleotides.length();
		int overlap = 0;
		
		if (lowLen < 0) {
			lowLen = this.nucleotides.length();
		}
		else
		{
			lowLen = f.nucleotides.length();
		}
		
		for (int i = 1 ; i <= lowLen ; ++i) {
			String first = this.nucleotides.substring(this.nucleotides.length() - i);
			String last = f.nucleotides.substring(0, i);
			if (first.equals(last)) {
				overlap = i;
			}
		}

		return overlap;
	}


	/**
	 * Returns a new fragment based upon merging this fragment with another fragment
	 * f.
	 * 
	 * This fragment will be on the left, the other fragment will be on the right;
	 * the fragments will be overlapped as much as possible during the merge.
	 * 
	 * @param f the other fragment
	 * @return a new fragment based upon merging this fragment with another fragment
	 */
	public Fragment mergedWith(Fragment f) {
		int maxOverlap = this.calculateOverlap(f);
		String concat = this.nucleotides + f.nucleotides.substring(maxOverlap);
		return (new Fragment(concat));
		 
	}
}
