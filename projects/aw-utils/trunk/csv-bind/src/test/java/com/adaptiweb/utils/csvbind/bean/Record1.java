package com.adaptiweb.utils.csvbind.bean;

import java.util.List;

import com.adaptiweb.utils.csvbind.annotation.CsvField;
import com.adaptiweb.utils.csvbind.editor.CsvFieldStringEditor;


/**
 * Issue import CSV bean. 
 * Mapped CSV line: ACTION;SUBJECT;CUSTOMER;CHANNEL;SOURCE;PARAMETER1;PARAMETER2..PARAMETERn
 * 
 * @see CsvField
 */
public class Record1 {
    @CsvField(index = 0)
    private String action;
    @CsvField(index = 1)
    private String subject;
    @CsvField(index = 2)
    private String customer;
    @CsvField(index = 3)
    private String channel;
    @CsvField(index = 4)
    private String source;
    @CsvField(index = 5, list = true, editor=CsvFieldStringEditor.class)
    private List<String> parameters;
    
    public String getAction() {
        return action;
    }
    public void setAction(String area) {
        this.action = area;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subarea) {
        this.subject = subarea;
    }
    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String msisdn) {
        this.customer = msisdn;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String description) {
        this.source = description;
    }
    public List<String> getParameters() {
        return parameters;
    }
    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }
}
