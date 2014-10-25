
package wallet


import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;


trait UserRepository extends MongoRepository[CreateUser, String] {

	def findById(id: Int): CreateUser
	
}

