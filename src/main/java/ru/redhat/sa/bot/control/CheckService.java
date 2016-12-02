package ru.redhat.sa.bot.control;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService (serviceName ="BotCheckService", name = "BotCheckServiceInterface")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface CheckService {
	
	@WebMethod
	public String askBot(String query);
}
