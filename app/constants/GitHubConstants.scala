package constants

object GitHubConstants {

  object Webhooks {

    final val SecretPath = "github.secret"
    final val Encoding = "utf-8"
    final val HashAlgorithm = "HmacSHA1"

    /**
      * HTTP header keys sent with Webhook payloads
      */
    case object Headers {
      /**
        * Event name: https://developer.github.com/webhooks/#events
        */
      val Event = "X-GitHub-Event"
      /**
        * Unique ID for the delivery
        */
      val Delivery = "X-GitHub-Delivery"
      /**
        * HMAC hex digest of payload using the secret key
        */
      val Signature = "X-Hub-Signature"
    }

    /**
      * Names of all Webhook events
      *
      * https://developer.github.com/webhooks/#events
      */
    object Events {
      /**
        * Any time any event is triggered (Wildcard Event).
        */
      final val Wildcard = "*"
      /**
        * Any time a Commit is commented on.
        */
      final val CommitComment = "commit_comment"
      /**
        * Any time a Branch or Tag is created.
        */
      final val Create = "create"
      /**
        * Any time a Branch or Tag is deleted.
        */
      final val Delete = "delete"
      /**
        * Any time a Repository has a new deployment created from the API.
        */
      final val Deployment = "deployment"
      /**
        * Any time a deployment for a Repository has a status update from the API.
        */
      final val DeploymentStatus = "deployment_status"
      /**
        * Any time a Repository is forked.
        */
      final val Fork = "fork"
      /**
        * Any time a Wiki page is updated.
        */
      final val Gollum = "gollum"
      /**
        * Any time a comment on an issue is created, edited, or deleted.
        */
      final val IssueComment = "issue_comment"
      /**
        * Any time a Label is created, edited, or deleted.
        */
      final val Label = "label"
      /**
        * Any time a User is added or removed as a collaborator to a Repository, or has their permissions modified.
        */
      final val Member = "member"
      /**
        * Any time a User is added or removed from a team. Organization hooks only.
        */
      final val Membership = "membership"
      /**
        * Any time a Milestone is created, closed, opened, edited, or deleted.
        */
      final val Milestone = "milestone"
      /**
        * Any time a user is added, removed, or invited to an Organization. Organization hooks only.
        */
      final val Organization = "organization"
      /**
        * Any time a Pages site is built or results in a failed build.
        */
      final val Page_build = "page_build"
      /**
        * Any time a Repository changes from private to public.
        */
      final val Public = "public"
      /**
        * Any time a comment on a Pull Request's unified diff is created, edited, or deleted (in the Files Changed tab).
        */
      final val PullRequestReviewComment = "pull_request_review_comment"
      /**
        * Any time a Pull Request Review is submitted.
        */
      final val PullRequestReview = "pull_request_review"
      /**
        * Any time a Pull Request is assigned, unassigned, labeled, unlabeled, opened, edited, closed, reopened, or synchronized (updated due to a new push in the branch that the pull request is tracking).
        */
      final val PullRequest = "pull_request"
      /**
        * Any Git push to a Repository, including editing tags or branches. Commits via API actions that update references are also counted. This is the default event.
        */
      final val Push = "push"
      /**
        * Any time a Repository is created, deleted, made public, or made private.
        */
      final val Repository = "repository"
      /**
        * Any time a Release is published in a Repository.
        */
      final val Release = "release"
      /**
        * Any time a Repository has a status update from the API
        */
      final val Status = "status"
      /**
        * Any time a team is created, deleted, modified, or added to or removed from a repository. Organization hooks only
        */
      final val Team = "team"
      /**
        * Any time a team is added or modified on a Repository.
        */
      final val TeamAdd = "team_add"
      /**
        * Any time a User stars a Repository.
        */
      final val Watch = "watch"
    }

  }

}
