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

class WebLogin{

		@BeanProperty
        var loginuserId: Int = _
        
        @Id
        @BeanProperty
        var loginId : Int = _

        @BeanProperty
        var url : String = _

        @BeanProperty
        var login : String = _

        @BeanProperty
        var password : String = _

       }
