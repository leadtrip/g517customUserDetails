package g517customuserdetails

import grails.gorm.transactions.Transactional
import wood.mikew.Role
import wood.mikew.User
import wood.mikew.UserRole

class BootStrap {

    def init = { servletContext ->
        addUser()
    }

    @Transactional
    def addUser() {
        def adminRole
        Role.withTransaction { rl ->
            adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
        }

        def testUser
        User.withTransaction { us ->
            testUser = new User(
                    username: 'bspoon',
                    password: 'password1',
                    fullname: 'Bob spoon',
                    address: '29 Chocolate spread drive, Toaster, Kitchenshire')
                .save(flush: true)
        }

        UserRole.create testUser, adminRole

        UserRole.withTransaction { urole ->
            UserRole.withSession {
                it.flush()
                it.clear()
            }
        }
    }

    def destroy = {
    }
}
