/*
 * Copyright (C) 2016-2019 Lightbend Inc. <http://www.lightbend.com>
 */

package akka.stream.alpakka.amqp.javadsl

import akka.NotUsed
import akka.annotation.ApiMayChange
import akka.stream.alpakka.amqp.{AmqpSourceSettings, IncomingMessage}
import akka.stream.javadsl.Source

object AmqpSource {

  /**
   * Java API: Convenience for "at-most once delivery" semantics. Each message is acked to RabbitMQ
   * before it is emitted downstream.
   */
  @ApiMayChange // https://github.com/akka/alpakka/issues/1513
  def atMostOnceSource(settings: AmqpSourceSettings, bufferSize: Int): Source[IncomingMessage, NotUsed] =
    akka.stream.alpakka.amqp.scaladsl.AmqpSource
      .atMostOnceSource(settings, bufferSize)
      .asJava

  /**
   * Java API:
   * The `committableSource` makes it possible to commit (ack/nack) messages to RabbitMQ.
   * This is useful when "at-least once delivery" is desired, as each message will likely be
   * delivered one time but in failure cases could be duplicated.
   *
   * If you commit the offset before processing the message you get "at-most once delivery" semantics,
   * and for that there is a [[#atMostOnceSource]].
   *
   * Compared to auto-commit, this gives exact control over when a message is considered consumed.
   */
  @ApiMayChange // https://github.com/akka/alpakka/issues/1513
  def committableSource(settings: AmqpSourceSettings, bufferSize: Int): Source[CommittableIncomingMessage, NotUsed] =
    akka.stream.alpakka.amqp.scaladsl.AmqpSource
      .committableSource(settings, bufferSize)
      .map(cm => cm.asJava)
      .asJava

}
