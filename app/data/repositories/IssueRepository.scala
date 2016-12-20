package data.repositories

import com.google.inject.{ImplementedBy, Inject, Singleton}
import data.entities.tracker.{Issue, IssueItem}
import data.tables.{IssueTable, UserTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

@ImplementedBy(classOf[IssueRepositoryImpl])
trait IssueRepository {
  def getAll(offset: Int, limit: Int): Future[Seq[IssueItem]]
  def get(id: Int): Future[Option[IssueItem]]
  def create(issue: Issue): Future[Issue]
}

@Singleton
class IssueRepositoryImpl @Inject()(val dbConfigProvider: DatabaseConfigProvider)
  extends IssueRepository
    with HasDatabaseConfigProvider[JdbcProfile]
    with IssueTable
    with UserTable {

  import driver.api._

  override def create(issue: Issue) =
    db.run(issues.getFirst(issue.id)).flatMap {
      case Some(found) => Future.successful(found)
      case _ => db.run(issues.insert(issue))
    }

  override def get(id: Int) =
    db.run((issues join users on (_.userId === _.id))
      .filter(_._1.id === id).result.headOption)
      .map(_.map((IssueItem.apply _).tupled))

  override def getAll(offset: Int, limit: Int) =
    db.run((issues join users on (_.userId === _.id))
      .drop(offset).take(limit).result)
      .map(_.map((IssueItem.apply _).tupled))

}
