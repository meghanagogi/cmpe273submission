package wallet


import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.ArrayList

trait LoginRepository extends MongoRepository[WebLogin, String] {
	
	def findByLoginuserId(loginuser_id: Int): ArrayList[WebLogin]
	def findByLoginId(login_id: Int): WebLogin
}