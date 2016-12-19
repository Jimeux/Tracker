package data.tables

import slick.driver.PostgresDriver.api._

import scala.reflect._

abstract class BaseTable[E: ClassTag](tag: Tag, tableName: String)
  extends Table[E](tag, tableName) {

  val classOfEntity = classTag[E].runtimeClass

  def id = column[Int]("id", O.PrimaryKey)

}