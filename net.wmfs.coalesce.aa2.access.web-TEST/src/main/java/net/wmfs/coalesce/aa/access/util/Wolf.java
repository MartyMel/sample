package net.wmfs.coalesce.aa.access.util;

import org.springframework.beans.factory.support.AutowireCandidateQualifier;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.stereotype.Component;

@Component
public class Wolf implements Runner {

	@Override
	public void print() {
		System.out.println("Wolf");

	}

}
