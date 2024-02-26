package l1a.jjakkak.api

object ApiUrl {
    // Docs
    const val DOCS = "/docs"

    // User
//    private const val USER = "/user"
    const val USER = "/user"
    const val USER_JOIN_OR_LOGIN = "$USER/joinOrLogin"
    const val USER_WITHDRAWAL = "$USER/withdrawal"

    // Appointment
    private const val APPOINTMENT = "/appointment"
    const val CREATE_APPOINTMENT = "$APPOINTMENT/create"
}