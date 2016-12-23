package data.repositories

import com.google.inject.{ImplementedBy, Inject, Singleton}
import data.entities.tracker.User
import data.tables.UserTable
import play.api.db.slick.DatabaseConfigProvider

@ImplementedBy(classOf[UserRepositoryImpl])
trait UserRepository extends BaseRepository[UserTable, User]

@Singleton
class UserRepositoryImpl @Inject()(val dbConfigProvider: DatabaseConfigProvider)
  extends BaseRepositoryImpl[UserTable, User](UserTable.table)
    with UserRepository