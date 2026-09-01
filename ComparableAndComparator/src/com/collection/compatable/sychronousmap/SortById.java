package com.collection.compatable.sychronousmap;

import java.util.Comparator;

public class SortById implements Comparator<Organization> {

	@Override
	public int compare(Organization e1, Organization e2) {
		int result = e1.getEmpName().compareTo(e2.getEmpName());

		return result;

	}

}
