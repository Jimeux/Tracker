package data.repositories

import com.google.inject.{ImplementedBy, Inject, Singleton}
import data.entities.tracker.{Issue, IssueItem, User}
import data.tables._
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

@ImplementedBy(classOf[IssueRepositoryImpl])
trait IssueRepository extends BaseRepository[IssueTable, Issue] {
  def getWithUser(id: Int): Future[Option[IssueItem]]
  def getAllWithUser(offset: Int, limit: Int): Future[Seq[IssueItem]]
  def createWithUser(issue: Issue, user: User): Future[IssueItem]
}

@Singleton
class IssueRepositoryImpl @Inject()(
  val dbConfigProvider: DatabaseConfigProvider,
  val userRepo: UserRepository
) extends BaseRepositoryImpl[IssueTable, Issue](IssueTable.table)
  with IssueRepository {

  import driver.api._

  def createWithUser(issue: Issue, user: User) = for {
    i <- createIfNotExists(issue)
    u <- userRepo.createIfNotExists(user)
  } yield toIssueItem((i, u))

  def getWithUser(id: Int) =
    db.run((table join userRepo.table on (_.userId === _.id))
      .filter(_._1.id === id).result.headOption)
      .map(_.map(toIssueItem))

  def getAllWithUser(offset: Int, limit: Int) =
    db.run((table join userRepo.table on (_.userId === _.id))
      .drop(offset).take(limit).result)
      .map(_.map(toIssueItem))

  def toIssueItem(row: (Issue, User)) = IssueItem.apply _ tupled row
}
