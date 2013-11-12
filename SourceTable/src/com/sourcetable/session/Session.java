package com.sourcetable.session;

import com.sourcetable.datasource.*;

public class Session {	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Session starts...");
				
		DataSource ds = new MySQL(DsType.MYSQL);
		System.out.println(ds.getType());
	}
}
