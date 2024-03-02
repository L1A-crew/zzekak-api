package l1a.jjakkak.core.domain.user.usecase

import l1a.jjakkak.api.config.exception.ExceptionEnum
import l1a.jjakkak.api.config.exception.ZzekakException
import l1a.jjakkak.core.domain.user.WithdrawalResult
import l1a.jjakkak.core.domain.user.message.WithdrawalMessage
import l1a.jjakkak.core.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

interface WithdrawalUseCase {
    fun widthdrawal(message: WithdrawalMessage): WithdrawalResult
}

@Service
internal class WithdrawalUseCaseImpl(
    val userRepo: UserRepository
): WithdrawalUseCase {
    override fun widthdrawal(message: WithdrawalMessage): WithdrawalResult {
        val (userId) = message
        val user = userRepo.findUserByUserIdAndIsRemoved(userId, false)
            ?: throw ZzekakException(ExceptionEnum.NO_EXIST_USER)

        return userRepo.save(user.deleteUser()).let {
            WithdrawalResult(
                result = 'Y',
                message = "성공적으로 탈퇴처리 되었습니다."
            )
        }
    }


}