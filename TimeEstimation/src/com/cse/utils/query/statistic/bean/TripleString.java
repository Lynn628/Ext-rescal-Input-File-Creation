package com.cse.utils.query.statistic.bean;

/**
 * 对应于三元组（主谓宾用String表示）
 * @author chengwenyao
 *
 */
public class TripleString {
	private String subject;
	private String predicate;
	private String object;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPredicate() {
		return predicate;
	}
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	
	public TripleString(String subject, String predicate, String object) {
		super();
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}
	@Override
	public String toString() {
		return "Triple [subject=" + subject + ", predicate=" + predicate
				+ ", object=" + object + "]";
	}
}
