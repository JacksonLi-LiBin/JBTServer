package com.matrix.jbt.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * contacts entity
 * 
 * @author JacksonLi
 * @date 2014/3/13
 */
@XmlRootElement
public class Contact {
	String fullName;
	List<String> phones;
	List<String> emails;

	@Override
	public String toString() {
		return "Contact [fullName=" + fullName + ", phones=" + phones
				+ ", emails=" + emails + "]";
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public Contact(String fullName, List<String> phones, List<String> emails) {
		super();
		this.fullName = fullName;
		this.phones = phones;
		this.emails = emails;
	}

	public Contact() {
		super();
	}

}
