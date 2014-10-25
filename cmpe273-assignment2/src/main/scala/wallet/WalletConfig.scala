package wallet

import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import scala.util.Random
import java.util.Date
import java.text.DateFormat
import java.text.DateFormat._
import java.util.ArrayList
import javax.validation.Valid
import scala.collection.JavaConversions._
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.web.client.RestTemplate
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

@Configuration
@EnableAutoConfiguration
@ComponentScan
@RestController
@RequestMapping(value=Array("/api/v1"))
class WalletConfig {

@RequestMapping(value = Array(""), method = Array(RequestMethod.GET))
@ResponseBody
def welcomerequest(): String= {

return "Welcome to the wallet application - Assignment2";

}


var array : ArrayList[IdCard] = new ArrayList()
var webarray: ArrayList[WebLogin] = new ArrayList()
var bankarray: ArrayList[BankAccount] = new ArrayList()

@Autowired
var bankrepository: BankRepository =_

@Autowired
var loginrepository: LoginRepository =_

@Autowired 
var cardrepository: CardRepository =_

@Autowired
var repository: UserRepository =_

//repository.deleteAll;
@RequestMapping(value = Array("/users"), method = Array(RequestMethod.POST), consumes = Array("application/json"), produces = Array("application/json"))
@ResponseBody
def respondtorequest(@RequestBody @Valid userObj : CreateUser, response: HttpServletResponse): Any = {

	var num = new Random();

	userObj.id = num.nextInt(2000);
	repository.save(userObj);
	response.setStatus(HttpServletResponse.SC_CREATED)
	return userObj;

}


@RequestMapping(value = Array("/users/{id}"), method = Array(RequestMethod.GET),produces = Array("application/json"))
@ResponseBody
def getresponsetorequest(@PathVariable("id") user_id: Int, response: HttpServletResponse): Any = {

		var userObj = new CreateUser();
		
		userObj = repository.findById(user_id)
		response.setStatus(HttpServletResponse.SC_OK)
		return userObj;
		


}

@RequestMapping(value = Array("/users/{id}"), method = Array(RequestMethod.PUT),produces = Array("application/json"))
@ResponseBody
def updateresponsetorequest(@RequestBody userObj: CreateUser,@PathVariable("id") user_id : Int,response: HttpServletResponse): Any = {

	
        userObj.id = user_id;
    
		repository.save(userObj);
		response.setStatus(HttpServletResponse.SC_CREATED)
		return userObj; 
	
}

//************************************************* ID CARD ****************************************************************************//



@RequestMapping(value = Array("/users/{id}/idcards"), method = Array(RequestMethod.POST), consumes = Array("application/json"), produces = Array("application/json"))
@ResponseBody
def idcardrespondtorequest(@RequestBody @Valid idcardObj : IdCard, @PathVariable("id") user_id : Int, response: HttpServletResponse): Any = {
	
	var cardid = new Random();

	idcardObj.cardId = cardid.nextInt(100);
	idcardObj.carduserId = user_id;

	cardrepository.save(idcardObj);

	array.add(idcardObj);
	response.setStatus(HttpServletResponse.SC_CREATED)
	return idcardObj;
	


}

@RequestMapping(value = Array("/users/{id}/idcards"), method = Array(RequestMethod.GET),produces = Array("application/json"))
@ResponseBody
def idcardgetresponsetorequest(@PathVariable("id") user_id: Int, response: HttpServletResponse): Any = {

		
		array = cardrepository.findByCarduserId(user_id);
		response.setStatus(HttpServletResponse.SC_OK)
		return array;
		


}

@RequestMapping(value = Array("/users/{id}/idcards/{cardId}"), method = Array(RequestMethod.DELETE) ,produces = Array("application/json"))
@ResponseBody
def idcarddeleteresponsetorequest(@PathVariable("id") user_id: Int, @PathVariable("cardId") cardId: Int,response: HttpServletResponse ): Any = {

			var idcard = new IdCard();
			idcard = cardrepository.findByCardId(cardId);
			cardrepository.delete(idcard);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT)
			return "Deleted"
			 

}

//*********************************** WEB LOGIN *************************************************//

@RequestMapping(value = Array("/users/{id}/weblogins"), method = Array(RequestMethod.POST), consumes = Array("application/json"), produces = Array("application/json"))
@ResponseBody
def loginrespondtorequest(@RequestBody @Valid loginObj : WebLogin, @PathVariable("id") user_id : Int, response: HttpServletResponse): Any = {
	
	var lognum = new Random();
	
	loginObj.loginId = lognum.nextInt(500);

	loginObj.loginuserId = user_id;

	loginrepository.save(loginObj);

	webarray.add(loginObj);
	response.setStatus(HttpServletResponse.SC_CREATED)
	return loginObj;
	


}

@RequestMapping(value = Array("/users/{id}/weblogins"), method = Array(RequestMethod.GET),produces = Array("application/json"))
@ResponseBody
def logingetresponsetorequest(@PathVariable("id") user_id: Int, response: HttpServletResponse): Any = {

		
		webarray = loginrepository.findByLoginuserId(user_id);
		response.setStatus(HttpServletResponse.SC_OK)
		return webarray;
		


}

@RequestMapping(value = Array("/users/{id}/weblogins/{loginId}"), method = Array(RequestMethod.DELETE) ,produces = Array("application/json"))
@ResponseBody
def logindeleteresponsetorequest(@PathVariable("id") user_id: Int, @PathVariable("loginId") loginId: Int, response: HttpServletResponse ): Any = {

			var login = new WebLogin();

			login = loginrepository.findByLoginId(loginId);
			loginrepository.delete(login);
			
			response.setStatus(HttpServletResponse.SC_NO_CONTENT)
			return "Deleted"
			 

}

//************************************************** BANK ACCOUNT *****************************************************************************//
@RequestMapping(value = Array("/users/{id}/bankaccounts"), method = Array(RequestMethod.POST), consumes = Array("application/json"), produces = Array("application/json"))
@ResponseBody
def bankrespondtorequest(@RequestBody @Valid bankObj : BankAccount, @PathVariable("id") user_id : Int, response: HttpServletResponse): Any = {
	
	var banknum = new Random();
	bankObj.bankId = banknum.nextInt(500);

	val template = new RestTemplate()

	var responseString = template.getForObject("http://www.routingnumbers.info/api/data.json?rn=" + bankObj.getRouting_number,classOf[String])
	var parser:JSONParser = new JSONParser()
	var jsonObject = parser.parse(responseString).asInstanceOf[JSONObject]

	if(jsonObject.get("customer_name") != null)
	{
	bankObj.bankuserId = user_id;

	bankObj.setAccount_name(jsonObject.get("customer_name").toString())

	bankrepository.save(bankObj);

	bankarray.add(bankObj);

	response.setStatus(HttpServletResponse.SC_CREATED)

	return bankObj;
	}
	else
	{
		response.setStatus(HttpServletResponse.SC_NOT_FOUND)
		}


}

@RequestMapping(value = Array("/users/{id}/bankaccounts"), method = Array(RequestMethod.GET),produces = Array("application/json"))
@ResponseBody
def bankgetresponsetorequest(@PathVariable("id") user_id: Int, response: HttpServletResponse): Any = {

		bankarray = bankrepository.findByBankuserId(user_id);

		response.setStatus(HttpServletResponse.SC_OK)
		return bankarray;

}

@RequestMapping(value = Array("/users/{id}/bankaccounts/{bankId}"), method = Array(RequestMethod.DELETE) ,produces = Array("application/json"))
@ResponseBody
def bankdeleteresponsetorequest(@PathVariable("id") user_id: Int, @PathVariable("bankId") bankId: Int, response: HttpServletResponse ): Any = {

			
			var bank = new BankAccount();

			bank = bankrepository.findByBankId(bankId);
			bankrepository.delete(bank);
			
			response.setStatus(HttpServletResponse.SC_NO_CONTENT)
			
			return "Deleted"
			 

}

}
 