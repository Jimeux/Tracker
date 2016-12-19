package data.entities.tracker

import java.sql.Timestamp

import data.entities.github.webhook.GitHubIssue
import format.TimestampFormat.implicitTimestampFormat
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Json, Writes}

case class Issue(
  id: Int,
  number: Int,
  title: String,
  userId: Int,
  url: String,
  createdAt: Timestamp,
  body: String
) extends BaseEntity

object Issue {
  implicit val issueReads = Json.reads[Issue]
  implicit val issueWrites = Json.writes[Issue]

  def of(i: GitHubIssue): Issue =
    Issue(i.id, i.number, i.title, i.user.id, i.url, Timestamp.valueOf(i.createdAt), i.body)
}

case class IssueItem(issue: Issue, user: User)

object IssueItem {
  implicit val issueItemWrites: Writes[IssueItem] = (
    JsPath.write[Issue] and
      (JsPath \ "user").write[User]
    )(unlift(IssueItem.unapply))
}