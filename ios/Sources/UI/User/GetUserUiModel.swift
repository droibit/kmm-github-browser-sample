import Foundation
import Shared

struct UserUiModel {
    let user: User
    let repos: [Repo]
}

struct GetUserUiModel {
    let inProgress: Bool
    let userUiModel: UserUiModel?
    let error: String?

    var hasNoState: Bool {
        !inProgress && userUiModel == nil && error == nil
    }

    init(inProgress: Bool = false,
         userUiModel: UserUiModel? = nil,
         error: String? = nil)
    {
        self.inProgress = inProgress
        self.userUiModel = userUiModel
        self.error = error
    }
}
