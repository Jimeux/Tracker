package data.tables

import data.entities.tracker.{BaseEntity, User}
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

class UserTable(tag: Tag) extends BaseTable[User](tag, "users") {
  def login = column[String]("login")
  def avatarUrl = column[String]("avatar_url")
  def url = column[String]("url")
  def siteAdmin = column[Boolean]("site_admin")

  def * = (id, login, avatarUrl, url, siteAdmin) <> ((User.apply _).tupled, User.unapply)
}

object UserTable {
  lazy val table = TableQuery[UserTable]
}