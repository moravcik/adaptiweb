<#-- 
greetingBean is a spring bean
name is freemarker model attribute    
-->
<@spel expression="We say '{@greetingBean.printGreeting(name)}'"/> from Freemarker