package services

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

import com.google.inject.{ImplementedBy, Inject, Singleton}
import constants.GitHubConstants.Webhooks
import data.entities.github.webhook.IssuePayload
import data.entities.tracker.{Issue, User}
import data.repositories.{IssueRepository, UserRepository}
import play.api.Configuration
import play.api.libs.Codecs
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future

@ImplementedBy(classOf[WebhookServiceImpl])
trait WebhookService {
  def handlePayload(event: String, payload: JsValue): Future[Issue]
  def verifySignature(signature: String, body: JsValue): Boolean
}

@Singleton
class WebhookServiceImpl @Inject()(
  config: Configuration,
  issueRepo: IssueRepository,
  userRepo: UserRepository
) extends WebhookService {

  class WebhookEventNotSupportedException(event: String) extends Exception {
    override def toString: String = s"Event: $event\n" + super.toString
  }
  class IssueActionNotSupportedException extends Exception

  private final val Secret = config.getString(Webhooks.SecretPath)

  override def handlePayload(event: String, payload: JsValue) = event match {
    case IssuePayload.Name =>
      handleIssue(Json.fromJson[IssuePayload](payload).asOpt)
    case _ =>
      Future.failed(new WebhookEventNotSupportedException(event))
  }

  override def verifySignature(signature: String, body: JsValue) = Secret match {
    case Some(secret) =>
      val mac = Mac.getInstance(Webhooks.HashAlgorithm)
      mac.init(new SecretKeySpec(secret.getBytes, Webhooks.HashAlgorithm))
      val digest = mac.doFinal(body.toString().getBytes(Webhooks.Encoding))
      val signed = "sha1=" + Codecs.toHexString(digest)
      signature == signed
    case _ => false
  }

  private def handleIssue(payload: Option[IssuePayload]) = payload match {
    case Some(json) =>
      json.action match {
        case IssuePayload.Actions.Opened =>
          userRepo.create(User.of(json.issue.user))
          issueRepo.create(Issue.of(json.issue))
        case _ =>
          Future.failed(new IssueActionNotSupportedException)
      }
    case None =>
      Future.failed(new IndexOutOfBoundsException)
  }
}
