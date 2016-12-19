package data.tables

import java.sql.Timestamp

import data.entities.tracker.Issue
import slick.driver.PostgresDriver.api._

trait IssueTable {

  class Issues(tag: Tag) extends BaseTable[Issue](tag, "issues") {
    def number = column[Int]("number")
    def title = column[String]("title")
    def userId = column[Int]("user_id")
    def url = column[String]("url")
    def createdAt = column[Timestamp]("created_at")
    def body = column[String]("body")

    def * = (id, number, title, userId, url, createdAt, body) <> ((Issue.apply _).tupled, Issue.unapply)
  }

  protected final val issues = TableQuery[Issues]

}