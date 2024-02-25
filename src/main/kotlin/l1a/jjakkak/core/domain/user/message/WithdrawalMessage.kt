package l1a.jjakkak.core.domain.user.message

import l1a.jjakkak.core.domain.user.AuthToken

data class WithdrawalMessage (
    val decodeToken: AuthToken,
    val isRemoved: Char
)
