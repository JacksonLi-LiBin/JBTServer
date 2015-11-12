package com.matrix.jbt.service.impl;

import com.matrix.jbt.dao.impl.InquiryDaoImpl;
import com.matrix.jbt.entity.Inquiry;
import com.matrix.jbt.service.InquiryService;

public class InquiryServiceImpl implements InquiryService {

	@Override
	public boolean addInruiry(Inquiry inquiry) {
		InquiryDaoImpl inquiryDaoImpl=new InquiryDaoImpl();
		return inquiryDaoImpl.addInruiry(inquiry);
	}

}
