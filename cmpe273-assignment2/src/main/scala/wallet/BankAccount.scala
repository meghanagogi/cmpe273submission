package wallet

import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import scala.collection.JavaConversions._
import scala.reflect.{BeanProperty, BooleanBeanProperty}
import scala.util.Random
import java.util.Date
import org.springframework.data.annotation.Id

class BankAccount{

		@Id
        @BeanProperty
        var bankId : Int = _

        @BeanProperty
        var bankuserId : Int =_

        @BeanProperty
        var account_name : String = _

        @BeanProperty
        var routing_number : String = _

        @BeanProperty
        var account_number : String = _

       }
