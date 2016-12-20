package data.tables

import data.entities.tracker.User
import slick.driver.PostgresDriver.api._

trait UserTable {

  class Users(tag: Tag) extends BaseTable[User](tag, "users") {
    def login = column[String]("login")
    def avatarUrl = column[String]("avatar_url")
    def url = column[String]("url")
    def siteAdmin = column[Boolean]("site_admin")

    def * = (id, login, avatarUrl, url, siteAdmin) <> ((User.apply _).tupled, User.unapply)
  }

  protected final val users = TableQuery[Users]

}