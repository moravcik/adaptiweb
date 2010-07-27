package com.adaptiweb.utils.csvbind.bean;

import com.adaptiweb.utils.csvbind.annotation.CsvField;


public class Record2 {
    @CsvField(index = 0)
    private String customer;
    @CsvField(index = 1)
    private String result = null;
    @CsvField(index = 2)
    private String trailer1;
    @CsvField(index = 3)
    private String trailer2;
    
    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String msisdn) {
        this.customer = msisdn;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
	public String getTrailer1() {
		return trailer1;
	}
	public void setTrailer1(String trailer1) {
		this.trailer1 = trailer1;
	}
	public String getTrailer2() {
		return trailer2;
	}
	public void setTrailer2(String trailer2) {
		this.trailer2 = trailer2;
	}
}
