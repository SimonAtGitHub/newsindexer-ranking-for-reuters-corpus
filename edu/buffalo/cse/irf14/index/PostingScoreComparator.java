package edu.buffalo.cse.irf14.index;

import java.util.Comparator;

public class PostingScoreComparator implements Comparator<Posting>{
	@Override
    public int compare(Posting posting1, Posting posting2) {
        return posting2.getScore().compareTo(posting1.getScore());
    }
}
