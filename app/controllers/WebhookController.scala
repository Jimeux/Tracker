package controllers

import javax.inject._

import constants.GitHubConstants.Webhooks
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc._
import services.WebhookService

import scala.concurrent.Future

@Singleton
class WebhookController @Inject()(webhookService: WebhookService) extends Controller {

  /**
    * Endpoint for the GitHub Webhooks API. The secret
    * and endpoint URL must be configured in the repo.
    */
  def payload = Action.async { request =>
    val maybeSignature = request.headers.get(Webhooks.Headers.Signature)
    val maybeEvent = request.headers.get(Webhooks.Headers.Event)
    val maybeBody = request.body.asJson

    (maybeSignature, maybeEvent, maybeBody) match {
      case (Some(signature), Some(event), Some(body)) =>
        if (webhookService.verifySignature(signature, body))
          webhookService.handlePayload(event, body).map(_ => Ok)
        else
          Future.successful(Forbidden)
      case _ =>
        Future.successful(NotAcceptable)
    }
  }

}
