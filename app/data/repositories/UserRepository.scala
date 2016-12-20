package data.repositories

import com.google.inject.{ImplementedBy, Inject, Singleton}
import data.entities.tracker.User
import data.tables.UserTable
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

@ImplementedBy(classOf[UserRepositoryImpl])
trait UserRepository {
  def get(id: Int): Future[Option[User]]
  def create(user: User): Future[User]
}

@Singleton
class UserRepositoryImpl @Inject()(val dbConfigProvider: DatabaseConfigProvider)
  extends UserRepository
    with HasDatabaseConfigProvider[JdbcProfile]
    with UserTable {

  import driver.api._

  override def get(id: Int) =
    db.run(users.byId(id).first)

  override def create(user: User) =
    db.run(users.getFirst(user.id)).flatMap {
      case Some(found) => Future.successful(found)
      case _ => db.run(users.insert(user))
    }

}
