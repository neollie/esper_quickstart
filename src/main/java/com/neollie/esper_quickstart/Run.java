package com.neollie.esper_quickstart;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class Run {
	public static void main(String[] args) {
		Configuration config = new Configuration();
		config.addEventTypeAutoName("com.neollie.esper_quickstart");
		EPServiceProvider epService = EPServiceProviderManager
		        .getDefaultProvider(config);

		String expression = "select avg(price) from OrderEvent.win:time(30 sec)";
		EPStatement statement = epService.getEPAdministrator()
		        .createEPL(expression);

		MyListener listener = new MyListener();
		statement.addListener(listener);

		OrderEvent event = new OrderEvent("shirt", 74.50);
		epService.getEPRuntime().sendEvent(event);
	}
}
