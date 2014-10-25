
package wallet


import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.ArrayList

trait CardRepository extends MongoRepository[IdCard, String] {
	
	def findByCarduserId(carduser_id: Int): ArrayList[IdCard]
	def findByCardId(card_id: Int): IdCard
}