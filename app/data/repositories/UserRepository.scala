package data.repositories

import com.google.inject.{ImplementedBy, Inject, Singleton}
import data.entities.tracker.User
import data.tables.UserTable
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

@ImplementedBy(classOf[UserDaoImpl])
trait UserRepository {
  def get(id: Int): Future[Option[User]]
  def create(user: User): Future[User]
}

@Singleton
class UserDaoImpl @Inject()(val dbConfigProvider: DatabaseConfigProvider)
  extends UserRepository
    with HasDatabaseConfigProvider[JdbcProfile]
    with UserTable {

  import driver.api._

  override def get(id: Int) =
    db.run(users.filter(_.id === id).result)
      .map(_.headOption)

  override def create(user: User) =
    db.run(users.filter(_.id === user.id).result.headOption).flatMap {
      case Some(found) =>
        Future.successful(found)
      case None =>
        db.run((users returning users) += user)
    }

}
