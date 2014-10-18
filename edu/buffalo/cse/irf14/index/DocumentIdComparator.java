package edu.buffalo.cse.irf14.index;

import java.util.Comparator;

public class DocumentIdComparator implements Comparator<Posting>{
	@Override
    public int compare(Posting posting1, Posting posting2) {
        return posting1.getDocId().compareTo(posting2.getDocId());
    }
}
