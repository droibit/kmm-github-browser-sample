import Foundation
import Shared

struct RepoUiModel {
    let repo: Repo
    let contributors: [Contributor]
}

struct GetRepoUiModel {
    let inProgress: Bool
    let repoUiModel: RepoUiModel?
    let error: String?

    init(inProgress: Bool = false,
         repoUiModel: RepoUiModel? = nil,
         error: String? = nil)
    {
        self.inProgress = inProgress
        self.repoUiModel = repoUiModel
        self.error = error
    }
}
