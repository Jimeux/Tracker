package data.entities.github.webhook

import com.github.tototoshi.play.json.JsonNaming
import play.api.libs.json.Json

/**
  * Any time an Issue is assigned, unassigned, labeled, unlabeled,
  * opened, edited, milestoned, demilestoned, closed, or reopened.
  */
case class IssuePayload(
  action: String,
  issue: GitHubIssue //,
  //sender: User
)

object IssuePayload {

  implicit val issuePayloadParser = JsonNaming.snakecase(Json.format[IssuePayload])

  final val Name = "issues"
  final val Action = "action"

  object Actions {
    final val Assigned = "assigned"
    final val Unassigned = "unassigned"
    final val Labeled = "labeled"
    final val Unlabeled = "unlabeled"
    final val Opened = "opened"
    final val Edited = "edited"
    final val Milestoned = "milestoned"
    final val Demilestoned = "demilestoned"
    final val Closed = "closed"
    final val Reopened = "reopened"
  }

}