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
import org.apache.commons.lang3.time.DateFormatUtils

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.annotation.Id

class CreateUser{

        @Id
        @BeanProperty
        var id: Int = _

        @BeanProperty
        var name: String = _

        @BeanProperty
        var email: String = _

        @BeanProperty
        var password: String = _

        @BeanProperty
        var created_at: String = DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date())

        @BeanProperty
        var updated_at: String = DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date())


//      val num = new Random();
//       return
//      println(num.nextInt());

}
