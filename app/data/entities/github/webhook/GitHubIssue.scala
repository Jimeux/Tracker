package data.entities.github.webhook

import java.time.LocalDateTime

import com.github.tototoshi.play.json.JsonNaming
import play.api.libs.json.Json

case class GitHubIssue(
  id: Int,
  number: Int,
  title: String,
  user: GitHubUser,
  url: String,
  createdAt: LocalDateTime,
  body: String
)

object GitHubIssue {
  implicit val gitHubIssueParser = JsonNaming.snakecase(Json.format[GitHubIssue])
}