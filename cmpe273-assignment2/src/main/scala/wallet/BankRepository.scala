package wallet


import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.ArrayList

trait BankRepository extends MongoRepository[BankAccount, String] {
	
	def findByBankuserId(bankuser_id: Int): ArrayList[BankAccount]
	def findByBankId(bank_id: Int): BankAccount
}